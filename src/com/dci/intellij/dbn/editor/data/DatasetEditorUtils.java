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

package com.dci.intellij.dbn.editor.data;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.ConnectionUtil;
import com.dci.intellij.dbn.object.DBColumn;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatasetEditorUtils {
    public static List<String> loadDistinctColumnValues(DBColumn column) {
        List<String> list = new ArrayList<String>();
        ConnectionHandler connectionHandler = column.getConnectionHandler();
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = connectionHandler.getPoolConnection();
            resultSet = connectionHandler.getInterfaceProvider().getMetadataInterface().getDistinctValues(
                column.getDataset().getSchema().getName(),
                column.getDataset().getName(),
                column.getName(), connection);

            while (resultSet.next()) {
                String value = resultSet.getString(1);
                list.add(value);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            ConnectionUtil.closeResultSet(resultSet);
            connectionHandler.freePoolConnection(connection);
        }
        return list;
    }
}
