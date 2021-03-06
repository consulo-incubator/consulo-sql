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

package com.dci.intellij.dbn.object.impl;

import com.dci.intellij.dbn.browser.ui.HtmlToolTipBuilder;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.ddl.DDLFileManager;
import com.dci.intellij.dbn.ddl.DDLFileType;
import com.dci.intellij.dbn.ddl.DDLFileTypeId;
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.object.DBProcedure;
import com.dci.intellij.dbn.object.DBProgram;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.dci.intellij.dbn.object.common.loader.DBObjectTimestampLoader;
import com.dci.intellij.dbn.object.common.loader.DBSourceCodeLoader;
import com.dci.intellij.dbn.object.common.status.DBObjectStatus;
import com.dci.intellij.dbn.object.common.status.DBObjectStatusHolder;

import javax.swing.Icon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBProcedureImpl extends DBMethodImpl implements DBProcedure {
    protected DBProcedureImpl(DBSchemaObject parent, ResultSet resultSet) throws SQLException {
        // type functions are not editable independently
        super(parent, DBContentType.NONE, resultSet);
        assert this.getClass() != DBProcedureImpl.class;
    }

    public DBProcedureImpl(DBSchema schema, ResultSet resultSet) throws SQLException {
        super(schema, DBContentType.CODE, resultSet);
    }

    @Override
    protected void initObject(ResultSet resultSet) throws SQLException {
        super.initObject(resultSet);
        name = resultSet.getString("PROCEDURE_NAME");
    }

    public DBObjectType getObjectType() {
        return DBObjectType.PROCEDURE;
    }

    public Icon getIcon() {
        if (getContentType() == DBContentType.CODE) {
            DBObjectStatusHolder objectStatus = getStatus();
            if (objectStatus.is(DBObjectStatus.VALID)) {
                if (objectStatus.is(DBObjectStatus.DEBUG)){
                    return Icons.DBO_PROCEDURE_DEBUG;
                }
            } else {
                return Icons.DBO_PROCEDURE_ERR;
            }

        }
        return Icons.DBO_PROCEDURE;
    }

    public Icon getOriginalIcon() {
        return Icons.DBO_PROCEDURE;
    }

    public void buildToolTip(HtmlToolTipBuilder ttb) {
        ttb.append(true, getObjectType().getName(), true);
        ttb.createEmptyRow();
        super.buildToolTip(ttb);
    }


    public DBProgram getProgram() {
        return null;
    }

    public String getMethodType() {
        return "PROCEDURE";
    }

    /*********************************************************
     *                         Loaders                       *
     *********************************************************/


    private class SourceCodeLoader extends DBSourceCodeLoader {
        protected SourceCodeLoader(DBObject object) {
            super(object, false);
        }

        public ResultSet loadSourceCode(Connection connection) throws SQLException {
            return getConnectionHandler().getInterfaceProvider().getMetadataInterface().loadObjectSourceCode(
                   getSchema().getName(), getName(), "PROCEDURE", connection);
        }
    }
    private static DBObjectTimestampLoader TIMESTAMP_LOADER = new DBObjectTimestampLoader("PROCEDURE") {};

    /*********************************************************
     *                  DBCompilableObject                   *
     *********************************************************/

    public String loadCodeFromDatabase(DBContentType contentType) throws SQLException {
        String header = getConnectionHandler().getInterfaceProvider().getDDLInterface().createMethodEditorHeader(this);
        String body = new SourceCodeLoader(this).load();
        return header + body;
    }

    public String getCodeParseRootId(DBContentType contentType) {
        return getParentObject() instanceof DBSchema && contentType == DBContentType.CODE ? "procedure_declaration" : null;
    }

    public DDLFileType getDDLFileType(DBContentType contentType) {
        return DDLFileManager.getInstance(getProject()).getDDLFileType(DDLFileTypeId.PROCEDURE);
    }

    public DDLFileType[] getDDLFileTypes() {
        return new DDLFileType[]{getDDLFileType(null)};
    }

    public DBObjectTimestampLoader getTimestampLoader(DBContentType contentType) {
        return TIMESTAMP_LOADER;
    }

}