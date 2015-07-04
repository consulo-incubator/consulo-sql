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

package com.dci.intellij.dbn.data.model.resultSet;

import com.dci.intellij.dbn.data.model.basic.BasicColumnInfo;
import com.dci.intellij.dbn.data.type.DBDataType;
import com.dci.intellij.dbn.data.type.DBNativeDataType;
import com.dci.intellij.dbn.object.common.DBObjectBundle;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetColumnInfo extends BasicColumnInfo {
    int resultSetColumnIndex;
    public ResultSetColumnInfo(DBObjectBundle objectBundle, ResultSet resultSet, int columnIndex) throws SQLException {
        super(null, null, columnIndex);
        resultSetColumnIndex = columnIndex + 1;
        ResultSetMetaData metaData = resultSet.getMetaData();
        name = metaData.getColumnName(resultSetColumnIndex);

        String dataTypeName = metaData.getColumnTypeName(resultSetColumnIndex);
        int precision = getPrecision(metaData);
        int scale = metaData.getScale(resultSetColumnIndex);
        DBNativeDataType nativeDataType = objectBundle.getNativeDataType(dataTypeName);
        dataType = new DBDataType(nativeDataType, precision, scale);
    }

    // lenient approach for oracle bug returning the size of LOBs instead of the precision.
    private int getPrecision(ResultSetMetaData metaData) throws SQLException {
        try {
            return metaData.getPrecision(resultSetColumnIndex);
        } catch (NumberFormatException e) {
            return 4000;
        }
    }

    public int getResultSetColumnIndex() {
        return resultSetColumnIndex;
    }
}
