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

package com.dci.intellij.dbn.object.common.loader;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.ConnectionUtil;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.openapi.diagnostic.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBSourceCodeLoader {
    protected Logger logger = Logger.getInstance(getClass().getName());

    private DBObject object;
    private boolean lenient;

    protected DBSourceCodeLoader(DBObject object, boolean lenient) {
        this.object = object;
        this.lenient = lenient;
    }

    public String load() throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        ConnectionHandler connectionHandler = object.getConnectionHandler();
        try {
            connection = connectionHandler.getPoolConnection();
            resultSet = loadSourceCode(connection);
                                                 
            StringBuilder sourceCode = new StringBuilder();
            while (resultSet.next()) {
                String codeLine = resultSet.getString(1);
                sourceCode.append(codeLine);
            }

            if (sourceCode.length() == 0 && !lenient)
                throw new SQLException("Object not found in database.");

            return sourceCode.toString();
        } finally {
            ConnectionUtil.closeResultSet(resultSet);
            connectionHandler.freePoolConnection(connection);
        }
    }

    public abstract ResultSet loadSourceCode(Connection connection) throws SQLException;
}
