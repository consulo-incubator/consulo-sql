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

package com.dci.intellij.dbn.database.common;

import com.dci.intellij.dbn.database.DatabaseDDLInterface;
import com.dci.intellij.dbn.database.DatabaseInterfaceProvider;
import com.dci.intellij.dbn.database.DatabaseObjectTypeId;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DatabaseDDLInterfaceImpl extends DatabaseInterfaceImpl implements DatabaseDDLInterface {

    public DatabaseDDLInterfaceImpl(String fileName, DatabaseInterfaceProvider provider) {
        super(fileName, provider);
    }

    public boolean includesTypeAndNameInSourceContent(DatabaseObjectTypeId objectTypeId) {
        return
                objectTypeId == DatabaseObjectTypeId.FUNCTION ||
                objectTypeId == DatabaseObjectTypeId.PROCEDURE ||
                objectTypeId == DatabaseObjectTypeId.PACKAGE ||
                objectTypeId == DatabaseObjectTypeId.TRIGGER ||
                objectTypeId == DatabaseObjectTypeId.TYPE;

    }

    protected final void execute(String statementText, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(20);
        statement.execute(statementText);

    }

    protected final String getSingleValue(Connection connection, String loaderId, Object... arguments) throws SQLException {
        ResultSet resultSet = executeQuery(connection, loaderId, arguments);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    /*********************************************************
     *                   CREATE statements                   *
     *********************************************************/
    public void createView(String viewName, String code, Connection connection) throws SQLException {
        executeQuery(connection, "create-view", viewName, code);
    }

    public void createObject(String code, Connection connection) throws SQLException {
        executeQuery(connection, "create-object", code);
    }


   /*********************************************************
    *                   DROP statements                     *
    *********************************************************/
   public void dropObject(String objectType, String objectName, Connection connection) throws SQLException {
       executeQuery(connection, "drop-object", objectType, objectName);
   }

}
