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

package com.dci.intellij.dbn.connection;

import com.dci.intellij.dbn.connection.config.ConnectionDatabaseSettings;
import com.dci.intellij.dbn.database.DatabaseInterfaceProvider;
import com.dci.intellij.dbn.database.generic.GenericInterfaceProvider;
import com.dci.intellij.dbn.database.mysql.MySqlInterfaceProvider;
import com.dci.intellij.dbn.database.oracle.OracleInterfaceProvider;

import java.sql.SQLException;

public class DatabaseInterfaceProviderFactory {
    // fixme replace with generic data dictionary
    public static final DatabaseInterfaceProvider GENERIC_INTERFACE_PROVIDER = new GenericInterfaceProvider();
    public static final DatabaseInterfaceProvider ORACLE_INTERFACE_PROVIDER = new OracleInterfaceProvider();
    public static final DatabaseInterfaceProvider MYAQL_INTERFACE_PROVIDER = new MySqlInterfaceProvider();

    public static DatabaseInterfaceProvider createInterfaceProvider(ConnectionHandler connectionHandler) throws SQLException {
        DatabaseType databaseType;
        if (connectionHandler != null && connectionHandler.isVirtual()) {
            databaseType = connectionHandler.getDatabaseType();
        } else {
            ConnectionDatabaseSettings databaseSettings = connectionHandler.getSettings().getDatabaseSettings();
            databaseType = databaseSettings.getDatabaseType();
            if (databaseType == null || databaseType == DatabaseType.UNKNOWN) {
                try {
                    databaseType = ConnectionUtil.getDatabaseType(connectionHandler.getStandaloneConnection());
                    databaseSettings.setDatabaseType(databaseType);
                } catch (SQLException e) {
                    if (databaseSettings.getDatabaseType() == null) {
                        databaseSettings.setDatabaseType(DatabaseType.UNKNOWN);
                    }
                    throw e;
                }
            }
        }


        if (databaseType == DatabaseType.ORACLE) {
            return ORACLE_INTERFACE_PROVIDER;
        } else if (databaseType == DatabaseType.MYSQL) {
            return MYAQL_INTERFACE_PROVIDER;
        }
        return GENERIC_INTERFACE_PROVIDER;

    }
}
