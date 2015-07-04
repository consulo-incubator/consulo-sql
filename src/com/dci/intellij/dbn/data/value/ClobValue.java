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

package com.dci.intellij.dbn.data.value;

import com.dci.intellij.dbn.common.util.CommonUtil;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClobValue implements LazyLoadedValue {
    private Clob clob;

    public ClobValue(Clob clob) {
        this.clob = clob;
    }

    public void updateValue(ResultSet resultSet, int columnIndex, String value) throws SQLException {
        if (clob == null) {
            value = CommonUtil.nvl(value, "");
            resultSet.updateClob(columnIndex, new StringReader(value));
            //resultSet.updateCharacterStream(columnIndex, new StringReader(value));
            clob = resultSet.getClob(columnIndex);
        } else {
            if (clob.length() > value.length()) {
                clob.truncate(value.length());
            }

            clob.setString(1, value);
            resultSet.updateClob(columnIndex, clob);
            //resultSet.updateCharacterStream(columnIndex, new StringReader(value));
        }
    }

    public String loadValue() throws SQLException {
        return loadValue(0);
    }

    public String loadValue(int maxSize) throws SQLException {
        if (clob == null) {
            return null;
        } else {
            try {
                int size = (int) (maxSize == 0 ? clob.length() : Math.min(maxSize, clob.length()));
                char[] buffer = new char[size];
                clob.getCharacterStream().read(buffer, 0, size);
                return new String(buffer);
            } catch (IOException e) {
                throw new SQLException("Could not read value from CLOB.");
            }
        }
    }

    @Override
    public long size() throws SQLException {
        return clob == null ? 0 : clob.length();
    }

    public String getDisplayValue() {
        /*try {
            return "[CLOB] " + size() + "";
        } catch (SQLException e) {
            return "[CLOB]";
        }*/

        return "[CLOB]";
    }
}
