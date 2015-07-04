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

package com.dci.intellij.dbn.data.record;


import com.dci.intellij.dbn.connection.ConnectionUtil;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterInput;
import com.dci.intellij.dbn.object.DBColumn;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.Disposable;
import gnu.trove.THashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class DatasetRecord implements Disposable {
    private ResultSet resultSet;
    private DatasetFilterInput filterInput;
    private Map<String, Object> values = new THashMap<String, Object>();

    public DatasetRecord(DatasetFilterInput filterInput) throws SQLException {
        this.filterInput = filterInput;
        loadRecordValues(filterInput);
    }

    public DatasetFilterInput getFilterInput() {
        return filterInput;
    }

    private void loadRecordValues(DatasetFilterInput filterInput) throws SQLException {
        DBDataset dataset = getDataset();
        StringBuilder selectStatement = new StringBuilder();
        selectStatement.append("select ");
        Iterator<DBColumn> iterator = dataset.getColumns().iterator();
        while (iterator.hasNext()) {
            DBColumn column = iterator.next();
            selectStatement.append(column.getName());
            if (iterator.hasNext()) {
                selectStatement.append(", ");
            }
        }

        selectStatement.append(" from ");
        selectStatement.append(dataset.getQualifiedName());
        selectStatement.append(" where ");

        iterator = filterInput.getColumns().iterator();
        while (iterator.hasNext()) {
            DBColumn column = iterator.next();
            selectStatement.append(column.getName());
            selectStatement.append(" = ? ");
            if (iterator.hasNext()) {
                selectStatement.append(" and ");
            }
        }

        Connection connection = dataset.getConnectionHandler().getPoolConnection();
        PreparedStatement statement = connection.prepareStatement(selectStatement.toString());

        int index = 1;
        iterator = filterInput.getColumns().iterator();
        while (iterator.hasNext()) {
            DBColumn column = iterator.next();
            Object value = filterInput.getColumnValue(column);
            column.getDataType().setValueToPreparedStatement(statement, index, value);
            index++;
        }

        resultSet = statement.executeQuery();
        if (resultSet.next()) {
            index = 1;

            for (DBColumn column : dataset.getColumns()) {
                Object value = column.getDataType().getValueFromResultSet(resultSet, index);
                values.put(column.getName(), value);
                index++;
            }
        }
    }

    public DBDataset getDataset() {
        return filterInput.getDataset();
    }

    public Object getColumnValue(DBColumn column) {
        return values.get(column.getName());
    }

    @Override
    public void dispose() {
        ConnectionUtil.closeResultSet(resultSet);
        filterInput = null;
        resultSet = null;
        values.clear();
        values = null;
    }
}
