/*
 * Copyright 2012-2014 Dan Cioca
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dci.intellij.dbn.object.common;

import com.dci.intellij.dbn.common.content.DynamicContent;
import com.dci.intellij.dbn.common.content.loader.DynamicContentLoader;
import com.dci.intellij.dbn.common.content.loader.DynamicContentResultSetLoader;
import com.dci.intellij.dbn.database.DatabaseDDLInterface;
import com.dci.intellij.dbn.database.DatabaseMetadataInterface;
import com.dci.intellij.dbn.ddl.DDLFileType;
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.psql.PSQLLanguage;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.list.DBObjectList;
import com.dci.intellij.dbn.object.common.list.DBObjectListContainer;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationList;
import com.dci.intellij.dbn.object.common.loader.DBObjectTimestampLoader;
import com.dci.intellij.dbn.object.common.property.DBObjectProperties;
import com.dci.intellij.dbn.object.common.property.DBObjectProperty;
import com.dci.intellij.dbn.object.common.status.DBObjectStatusHolder;
import com.dci.intellij.dbn.vfs.DatabaseEditableObjectFile;
import com.dci.intellij.dbn.vfs.DatabaseFileSystem;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public abstract class DBSchemaObjectImpl extends DBObjectImpl implements DBSchemaObject {
    private DBObjectList<DBObject> referencedObjects;
    private DBObjectList<DBObject> referencingObjects;
    private DBObjectStatusHolder objectStatus;

    public DBSchemaObjectImpl(DBSchema schema, DBContentType contentType, ResultSet resultSet) throws SQLException {
        super(schema, contentType, resultSet);
    }

    public DBSchemaObjectImpl(DBSchemaObject parent, DBContentType contentType, ResultSet resultSet) throws SQLException {
        super(parent, contentType, resultSet);
    }

    protected void initProperties() {
        DBObjectProperties properties = getProperties();
        properties.set(DBObjectProperty.EDITABLE);
        properties.set(DBObjectProperty.REFERENCEABLE);
        properties.set(DBObjectProperty.SCHEMA_OBJECT);
    }

    @Override
    protected void initLists() {
        DBObjectProperties properties = getProperties();
        if (properties.is(DBObjectProperty.REFERENCEABLE)) {
            DBObjectListContainer childObjects = initChildObjects();
            referencedObjects = childObjects.createObjectList(DBObjectType.ANY, this, REFERENCED_OBJECTS_LOADER, false, true);
            referencingObjects = childObjects.createObjectList(DBObjectType.ANY, this, REFERENCING_OBJECTS_LOADER, false, true);
        }
    }

    public synchronized DBObjectStatusHolder getStatus() {
        if (objectStatus == null) {
            objectStatus = new DBObjectStatusHolder(getContentType());
        }
        return objectStatus;
    }

    public boolean isEditable(DBContentType contentType) {
        return false;
    }

    public List<DBObject> getReferencedObjects() {
        return referencedObjects.getObjects();
    }

    public List<DBObject> getReferencingObjects() {
        return referencingObjects.getObjects();
    }

    protected List<DBObjectNavigationList> createNavigationLists() {
        return new ArrayList<DBObjectNavigationList>();
    }

    public Timestamp loadChangeTimestamp(DBContentType contentType) throws SQLException {
        return getTimestampLoader(contentType).load(this);
    }

    public DBObjectTimestampLoader getTimestampLoader(DBContentType contentType) {
        return new DBObjectTimestampLoader(getTypeName().toUpperCase());
    }

    public String createDDLStatement(String code) {
        return getConnectionHandler().getInterfaceProvider().getMetadataInterface().
                createDDLStatement(getObjectType().getTypeId(), getName(), code);
    }

    public DDLFileType getDDLFileType(DBContentType contentType) {
        return null;
    }

    public DDLFileType[] getDDLFileTypes() {
        return null;
    }

    public String loadCodeFromDatabase(DBContentType contentType) throws SQLException {
        return null;
    }

    public SqlLikeLanguage getCodeLanguage(DBContentType contentType) {
        return PSQLLanguage.INSTANCE;
    }

    public String getCodeParseRootId(DBContentType contentType) {
        return null;
    }

    @NotNull
    public DatabaseEditableObjectFile getVirtualFile() {
        return DatabaseFileSystem.getInstance().findDatabaseFile(this);
    }

    public void executeUpdateDDL(DBContentType contentType, String oldCode, String newCode) throws SQLException {
        Connection connection = getConnectionHandler().getStandaloneConnection(getSchema());
        DatabaseDDLInterface ddlInterface = getConnectionHandler().getInterfaceProvider().getDDLInterface();
        ddlInterface.updateObject(getName(), getObjectType().getName(), oldCode,  newCode, connection);
    }

    /*********************************************************
     *                         Loaders                       *
     *********************************************************/
    private static final DynamicContentLoader REFERENCED_OBJECTS_LOADER = new DynamicContentResultSetLoader() {
        public ResultSet createResultSet(DynamicContent dynamicContent, Connection connection) throws SQLException {
            DatabaseMetadataInterface metadataInterface = dynamicContent.getConnectionHandler().getInterfaceProvider().getMetadataInterface();
            DBSchemaObject schemaObject = (DBSchemaObject) dynamicContent.getParent();
            return metadataInterface.loadReferencedObjects(schemaObject.getSchema().getName(), schemaObject.getName(), connection);
        }

        public DBObject createElement(DynamicContent dynamicContent, ResultSet resultSet, LoaderCache loaderCache) throws SQLException {
            String objectOwner = resultSet.getString("OBJECT_OWNER");
            String objectName = resultSet.getString("OBJECT_NAME");

            DBSchemaObject schemaObject = (DBSchemaObject) dynamicContent.getParent();

            DBSchema schema = (DBSchema) loaderCache.getObject(objectOwner);
            if (schema == null) {
                schema = schemaObject.getConnectionHandler().getObjectBundle().getSchema(objectOwner);
                loaderCache.setObject(objectOwner,  schema);
            }

            return schema.getChildObject(objectName, true);
        }
    };

    private static final DynamicContentLoader REFERENCING_OBJECTS_LOADER = new DynamicContentResultSetLoader() {
        public ResultSet createResultSet(DynamicContent dynamicContent, Connection connection) throws SQLException {
            DatabaseMetadataInterface metadataInterface = dynamicContent.getConnectionHandler().getInterfaceProvider().getMetadataInterface();
            DBSchemaObject schemaObject = (DBSchemaObject) dynamicContent.getParent();
            return metadataInterface.loadReferencingObjects(schemaObject.getSchema().getName(), schemaObject.getName(), connection);
        }

        public DBObject createElement(DynamicContent dynamicContent, ResultSet resultSet, LoaderCache loaderCache) throws SQLException {
            String objectOwner = resultSet.getString("OBJECT_OWNER");
            String objectName = resultSet.getString("OBJECT_NAME");

            DBSchemaObject schemaObject = (DBSchemaObject) dynamicContent.getParent();
            DBSchema schema = schemaObject.getConnectionHandler().getObjectBundle().getSchema(objectOwner);
            return schema == null ? null : schema.getChildObject(objectName, true);
        }
    };
}
