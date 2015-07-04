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

import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.object.DBMaterializedView;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.common.loader.DBSourceCodeLoader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBMaterializedViewImpl extends DBViewImpl implements DBMaterializedView {
    public DBMaterializedViewImpl(DBSchema schema, ResultSet resultSet) throws SQLException {
        super(schema, resultSet);
    }

    public DBObjectType getObjectType() {
        return DBObjectType.MATERIALIZED_VIEW;
    }


    /*********************************************************
     *                  DBEditableCodeObject                 *
     ********************************************************/

    public String loadCodeFromDatabase(DBContentType contentType) throws SQLException {
        SourceCodeLoader loader = new SourceCodeLoader(this);
        return loader.load();
    }

    /*********************************************************
     *                         Loaders                       *
     *********************************************************/

    private class SourceCodeLoader extends DBSourceCodeLoader {
        protected SourceCodeLoader(DBObject object) {
            super(object, false);
        }

        public ResultSet loadSourceCode(Connection connection) throws SQLException {
            return getConnectionHandler().getInterfaceProvider().getMetadataInterface().loadMaterializedViewSourceCode(
                   getSchema().getName(), getName(), connection);
        }
    };
}
