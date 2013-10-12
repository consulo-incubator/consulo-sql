package com.dci.intellij.dbn.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.browser.model.BrowserTreeChangeListener;
import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.dispose.DisposeUtil;
import com.dci.intellij.dbn.common.environment.EnvironmentType;
import com.dci.intellij.dbn.common.event.EventManager;
import com.dci.intellij.dbn.common.filter.Filter;
import com.dci.intellij.dbn.common.thread.BackgroundTask;
import com.dci.intellij.dbn.common.ui.tree.TreeEventType;
import com.dci.intellij.dbn.connection.config.ConnectionSettings;
import com.dci.intellij.dbn.connection.transaction.UncommittedChangeBundle;
import com.dci.intellij.dbn.database.DatabaseInterfaceProvider;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.navigation.psi.NavigationPsiCache;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObjectBundle;
import com.dci.intellij.dbn.object.common.DBObjectBundleImpl;
import com.dci.intellij.dbn.vfs.SQLConsoleFile;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class ConnectionHandlerImpl implements ConnectionHandler {
    private ConnectionSettings connectionSettings;
    private ConnectionBundle connectionBundle;
    private ConnectionStatus connectionStatus;
    private ConnectionPool connectionPool;
    private ConnectionInfo connectionInfo;
    private DBObjectBundle objectBundle;
    private DatabaseInterfaceProvider interfaceProvider;
    private UncommittedChangeBundle changesBundle;

    private boolean isDisposed;
    private boolean checkingIdleStatus;

    private SQLConsoleFile sqlConsoleFile;
    private NavigationPsiCache psiCache = new NavigationPsiCache(this);

    public ConnectionHandlerImpl(ConnectionBundle connectionBundle, ConnectionSettings connectionSettings) {
        this.connectionBundle = connectionBundle;
        this.connectionSettings = connectionSettings;
        connectionStatus = new ConnectionStatus();
        connectionPool = new ConnectionPool(this);
    }

    public ConnectionBundle getConnectionBundle() {
        return connectionBundle;
    }

    public ConnectionSettings getSettings() {
        return connectionSettings;
    }

    public ConnectionStatus getConnectionStatus() {
        return connectionStatus;
    }

    public boolean isActive() {
        return connectionSettings.getDatabaseSettings().isActive();
    }

    public DatabaseType getDatabaseType() {
        return connectionSettings.getDatabaseSettings().getDatabaseType();
    }

    public Filter<BrowserTreeNode> getObjectFilter() {
        return getSettings().getFilterSettings().getObjectTypeFilterSettings().getElementFilter();
    }

    public SQLConsoleFile getSQLConsoleFile() {
        if (sqlConsoleFile == null) {
            sqlConsoleFile = new SQLConsoleFile(this);
        }
        return sqlConsoleFile;
    }

    @Override
    public NavigationPsiCache getPsiCache() {
        return psiCache;
    }

    @Override
    public EnvironmentType getEnvironmentType() {
        return getSettings().getDetailSettings().getEnvironmentType();
    }

    public boolean hasUncommittedChanges() {
        return changesBundle != null && !changesBundle.isEmpty();
    }

    public void commit() throws SQLException {
        connectionPool.getStandaloneConnection(false).commit();
        changesBundle = null;
    }

    public void rollback() throws SQLException {
        connectionPool.getStandaloneConnection(false).rollback();
        changesBundle = null;
    }

    @Override
    public void ping(boolean check) {
        connectionPool.keepAlive(check);
    }

    public void notifyChanges(VirtualFile virtualFile) {
        if (!isAutoCommit()) {
            if (changesBundle == null) {
                changesBundle = new UncommittedChangeBundle();
            }
            changesBundle.notifyChange(virtualFile);
        }
    }

    @Override
    public void resetChanges() {
        changesBundle = null;
    }

    @Override
    public UncommittedChangeBundle getUncommittedChanges() {
        return changesBundle;
    }

    @Override
    public boolean isConnected() {
        return connectionStatus.isConnected();
    }

    public String toString() {
        return getPresentableText();
    }

    public Project getProject() {
        return connectionBundle.getProject();
    }

    public Module getModule() {
        if (connectionBundle instanceof ModuleConnectionBundle) {
            ModuleConnectionBundle moduleConnectionManager = (ModuleConnectionBundle) connectionBundle;
            return moduleConnectionManager.getModule();
        }
        return null;
    }

    public boolean isValid(boolean check) {
        if (check) {
            try {
                getStandaloneConnection();
            } catch (SQLException e) {
                return false;
            }
        }
        return isValid();
    }

    @Override
    public int getIdleMinutes() {
        return connectionPool == null ? 0 : connectionPool.getIdleMinutes();
    }

    public boolean isValid() {
        return connectionStatus.isValid() && connectionBundle.containsConnection(this);
    }

    public boolean isVirtual() {
        return false;
    }

    @Override
    public boolean isAutoCommit() {
        return connectionSettings.getDetailSettings().isAutoCommit();
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        connectionPool.setAutoCommit(autoCommit);
        connectionSettings.getDetailSettings().setAutoCommit(autoCommit);
    }

    public void disconnect() throws SQLException {
        try {
            connectionPool.closeConnections();
        } finally {
            getConnectionStatus().setConnected(false);
        }
    }

    public String getId() {
        return connectionSettings.getDatabaseSettings().getId();
    }

    public String getUserName() {
        return connectionSettings.getDatabaseSettings().getUser() == null ? "" : connectionSettings.getDatabaseSettings().getUser();
    }

    public ConnectionInfo getConnectionInfo() throws SQLException {
        if (connectionInfo == null) {
            Connection connection = getStandaloneConnection();
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            connectionInfo = new ConnectionInfo(databaseMetaData);
        }

        //System.out.println(ResultSetLister.list("Catalogs", getStandaloneConnection().getMetaData().getTypeInfo()));
        return connectionInfo;
    }

    public DBObjectBundle getObjectBundle() {
        if (objectBundle == null) {
            objectBundle = new DBObjectBundleImpl(this, connectionBundle);
        }
        return objectBundle;
    }

    public DBSchema getUserSchema() {
        String userName = getUserName().toUpperCase();
        DBSchema defaultSchema = getObjectBundle().getSchema(userName);
        if (defaultSchema == null) {
            List<DBSchema> schemas = getObjectBundle().getSchemas();
            if (schemas.size() > 0) {
                return schemas.get(0);
            }
        }

        return defaultSchema;
    }

    public Connection getStandaloneConnection() throws SQLException {
        return connectionPool.getStandaloneConnection(true);
    }

    public Connection getStandaloneConnection(DBSchema schema) throws SQLException {
        Connection connection = connectionPool.getStandaloneConnection(true);
        if (!schema.isPublicSchema()) {
            getInterfaceProvider().getMetadataInterface().setCurrentSchema(schema.getQuotedName(false), connection);
        }
        return connection;
    }

    @Nullable
    public Connection getPoolConnection() throws SQLException {
        return connectionPool.allocateConnection();
    }

    public Connection getPoolConnection(DBSchema schema) throws SQLException {
        Connection connection = connectionPool.allocateConnection();
        if (!schema.isPublicSchema()) {
            getInterfaceProvider().getMetadataInterface().setCurrentSchema(schema.getQuotedName(false), connection);
        }
        return connection;
    }

    public void freePoolConnection(Connection connection) {
        if (!isDisposed)
            connectionPool.releaseConnection(connection);
    }

    public void closePoolConnection(Connection connection) {
        if (!isDisposed)
            connectionPool.releaseConnection(connection);
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }

    public DatabaseInterfaceProvider getInterfaceProvider() {
        if (interfaceProvider == null || interfaceProvider.getDatabaseType() != getDatabaseType()) {
            try {
                interfaceProvider = DatabaseInterfaceProviderFactory.createInterfaceProvider(this);
            } catch (SQLException e) {
                // do not initialize 
                return DatabaseInterfaceProviderFactory.GENERIC_INTERFACE_PROVIDER;
            }
        }
        return interfaceProvider;
    }

    public SqlLikeLanguageVersion<?> getLanguageDialect(SqlLikeLanguage language) {
        return getInterfaceProvider().getLanguageDialect(language);
    }

    public static Comparator<ConnectionHandler> getComparator(boolean asc) {
        return asc ? ASC_COMPARATOR : DESC_COMPARATOR;
    }

    public static final Comparator<ConnectionHandler> ASC_COMPARATOR = new Comparator<ConnectionHandler>() {
        public int compare(ConnectionHandler connection1, ConnectionHandler connection2) {
            return connection1.getPresentableText().toLowerCase().compareTo(connection2.getPresentableText().toLowerCase());
        }
    };

    public static final Comparator<ConnectionHandler> DESC_COMPARATOR = new Comparator<ConnectionHandler>() {
        public int compare(ConnectionHandler connection1, ConnectionHandler connection2) {
            return connection2.getPresentableText().toLowerCase().compareTo(connection1.getPresentableText().toLowerCase());
        }
    };

    /*********************************************************
     *                       TreeElement                     *
     *********************************************************/
    public String getQualifiedName() {
        if (connectionBundle instanceof ProjectConnectionBundle) {
            return "Project - " + getPresentableText();
        } else {
            ModuleConnectionBundle connectionManager = (ModuleConnectionBundle) this.connectionBundle;
            Module module = connectionManager.getModule();
            return module.getName() + " - " + getPresentableText();
        }
    }

    public String getName() {
        return connectionSettings.getDatabaseSettings().getName();
    }

    public String getDescription() {
        return connectionSettings.getDatabaseSettings().getDescription();
    }

    public String getPresentableText(){
        return connectionSettings.getDatabaseSettings().getName();
    }

    public Icon getIcon(){
        return connectionStatus.isConnected() ? Icons.CONNECTION_ACTIVE : 
               connectionStatus.isValid() ? Icons.CONNECTION_INACTIVE :
                        Icons.CONNECTION_INVALID;
    }

   /*********************************************************
    *                      Disposable                       *
    *********************************************************/
    public void dispose() {
        if (!isDisposed) {
            isDisposed = true;
            DisposeUtil.dispose(objectBundle);
            DisposeUtil.dispose(connectionPool);
            DisposeUtil.dispose(sqlConsoleFile);
            DisposeUtil.dispose(psiCache);
            connectionPool = null;
            changesBundle = null;
        }
    }

    public boolean isDisposed() {
        return isDisposed;
    }

    public void setConnectionConfig(final ConnectionSettings connectionSettings) {
        boolean refresh = this.connectionSettings.getDatabaseSettings().hashCode() != connectionSettings.getDatabaseSettings().hashCode();
        this.connectionSettings = connectionSettings;
        if (refresh) {
            connectionPool.closeConnectionsSilently();

            final Project project = getProject();
            new BackgroundTask(getProject(), "Trying to connect to " + getName(), false) {
                @Override
                public void execute(@NotNull ProgressIndicator progressIndicator) {
                    initProgressIndicator(progressIndicator, true);
                    ConnectionManager connectionManager = ConnectionManager.getInstance(project);
                    connectionManager.testConnection(ConnectionHandlerImpl.this, false);
                    //fixme check if the connection is pointing to a new database and reload if this is the case
                    //objectBundle.checkForDatabaseChange();

                    EventManager.notify(project, BrowserTreeChangeListener.TOPIC).nodeChanged(getObjectBundle(), TreeEventType.NODES_CHANGED);
                }
            }.start();
        }
    }
}
