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

package com.dci.intellij.dbn.editor.data.filter;

import com.dci.intellij.dbn.data.sorting.SingleColumnSortingState;
import com.dci.intellij.dbn.object.DBColumn;
import com.dci.intellij.dbn.object.DBDataset;
import com.dci.intellij.dbn.object.DBTable;

import java.util.List;

public class DatasetFilterUtil {

    public static void addOrderByClause(DBDataset dataset, StringBuilder buffer, SingleColumnSortingState sortingState) {
        if (dataset instanceof DBTable) {
            DBTable table = (DBTable) dataset;
            if (sortingState.isValid()) {
                buffer.append(" order by ");
                buffer.append(sortingState.getColumnName());
                buffer.append(" ");
                buffer.append(sortingState.getDirectionAsString());
            } else {
                List<DBColumn> primaryKeyColumns = table.getPrimaryKeyColumns();
                if (primaryKeyColumns.size() > 0) {
                    buffer.append(" order by ");
                    for (DBColumn column : primaryKeyColumns) {
                        buffer.append(column.getName());
                        if (primaryKeyColumns.get(primaryKeyColumns.size()-1) == column ) {
                            buffer.append(" asc");
                        } else {
                            buffer.append(", ");
                        }
                    }
                }
            }
        }
    }

    public static void addForUpdateClause(DBDataset dataset, StringBuilder buffer) {
        if (dataset instanceof DBTable && dataset.hasLobColumns()) {
            buffer.append(" for update");
        }
    }

    public static void createSelectStatement(DBDataset dataset, StringBuilder buffer) {
        buffer.append("select ");
        int index = 0;
        for (DBColumn column : dataset.getColumns()) {
            if (index > 0) {
                buffer.append(", ");
            }
            buffer.append(column.getQuotedName(false));
            index++;
        }
        buffer.append(" from ");
        buffer.append(dataset.getSchema().getQuotedName(false));
        buffer.append(".");
        buffer.append(dataset.getQuotedName(false));

    }

    public static void createSimpleSelectStatement(DBDataset dataset, StringBuilder buffer) {
        buffer.append("select a.* from ");
        buffer.append(dataset.getSchema().getQuotedName(false));
        buffer.append(".");
        buffer.append(dataset.getQuotedName(false));
        buffer.append(" a");

    }
}
