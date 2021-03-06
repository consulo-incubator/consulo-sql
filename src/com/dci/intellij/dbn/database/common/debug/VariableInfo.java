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

package com.dci.intellij.dbn.database.common.debug;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;      

public class VariableInfo extends BasicOperationInfo {
    private String value;

    public void registerParameters(CallableStatement statement) throws SQLException {
        statement.registerOutParameter(1, Types.VARCHAR);
        statement.registerOutParameter(2, Types.VARCHAR);
    }

    public void read(CallableStatement statement) throws SQLException {
        value = statement.getString(1);
        error = statement.getString(2);
    }

    public String getValue() {
        return value;
    }

}
