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

package com.dci.intellij.dbn.database.oracle;

import com.dci.intellij.dbn.database.DatabaseInterfaceProvider;
import com.dci.intellij.dbn.database.DatabaseObjectTypeId;
import com.dci.intellij.dbn.database.common.DatabaseMetadataInterfaceImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OracleMetadataInterface extends DatabaseMetadataInterfaceImpl {

    public OracleMetadataInterface(DatabaseInterfaceProvider provider) {
        super("oracle_metadata_interface.xml", provider);
    }

    public ResultSet loadCharsets(Connection connection) throws SQLException {return null;}

    public ResultSet loadTriggerSourceCode(String ownerName, String triggerName, Connection connection) throws SQLException {
        return loadObjectSourceCode(ownerName, triggerName, "TRIGGER", connection);
    }


    public String createDDLStatement(DatabaseObjectTypeId objectTypeId, String objectName, String code) {
        return
            objectTypeId == DatabaseObjectTypeId.VIEW ?
                    "CREATE OR REPLACE VIEW " + objectName + " as\n" + code :
                    "CREATE OR REPLACE\n" + code;
    }

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String createDateString(Date date) {
        String dateString = DATE_FORMAT.format(date);
        return "to_date('" + dateString + "', 'yyyy-mm-dd HH24:MI:SS')";
    }


}
