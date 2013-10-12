package com.dci.intellij.dbn.database.mysql;

import com.dci.intellij.dbn.connection.DatabaseType;
import com.dci.intellij.dbn.database.DatabaseCompatibilityInterface;
import com.dci.intellij.dbn.database.DatabaseDDLInterface;
import com.dci.intellij.dbn.database.DatabaseDebuggerInterface;
import com.dci.intellij.dbn.database.DatabaseExecutionInterface;
import com.dci.intellij.dbn.database.DatabaseMessageParserInterface;
import com.dci.intellij.dbn.database.DatabaseMetadataInterface;
import com.dci.intellij.dbn.database.common.DatabaseInterfaceProviderImpl;
import com.dci.intellij.dbn.database.common.DatabaseNativeDataTypes;
import com.dci.intellij.dbn.language.sql.dialect.mysql.MysqlSQLLanguageDialect;

public class MySqlInterfaceProvider extends DatabaseInterfaceProviderImpl {
    private DatabaseMessageParserInterface MESSAGE_PARSER_INTERFACE = new MySqlMessageParserInterface();
    private DatabaseCompatibilityInterface COMPATIBILITY_INTERFACE = new MySqlCompatibilityInterface();
    private DatabaseMetadataInterface METADATA_INTERFACE = new MySqlMetadataInterface(this);
    private DatabaseDDLInterface DDL_INTERFACE = new MySqlDDLInterface(this);
    private DatabaseExecutionInterface EXECUTION_INTERFACE = new MySqlExecutionInterface();
    private DatabaseNativeDataTypes NATIVE_DATA_TYPES = new MySqlNativeDataTypes();

    public MySqlInterfaceProvider() {
        super(MysqlSQLLanguageDialect.class, null);
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }

    public DatabaseNativeDataTypes getNativeDataTypes() {
        return NATIVE_DATA_TYPES;
    }

    public DatabaseMessageParserInterface getMessageParserInterface() {
        return MESSAGE_PARSER_INTERFACE;
    }

    public DatabaseCompatibilityInterface getCompatibilityInterface() {
        return COMPATIBILITY_INTERFACE;
    }

    public DatabaseMetadataInterface getMetadataInterface() {
        return METADATA_INTERFACE;
    }

    public DatabaseDebuggerInterface getDebuggerInterface() {
        return null;
    }

    public DatabaseDDLInterface getDDLInterface() {
        return DDL_INTERFACE;
    }

    public DatabaseExecutionInterface getDatabaseExecutionInterface() {
        return EXECUTION_INTERFACE;
    }


}
