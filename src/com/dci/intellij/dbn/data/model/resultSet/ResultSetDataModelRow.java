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

import com.dci.intellij.dbn.data.model.ColumnInfo;
import com.dci.intellij.dbn.data.model.sortable.SortableDataModelRow;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ResultSetDataModelRow<T extends ResultSetDataModelCell> extends SortableDataModelRow<T> {
    public ResultSetDataModelRow(ResultSetDataModel model, ResultSet resultSet) throws SQLException {
        super(model);
        for (int i = 0; i < model.getColumnCount(); i++) {
            ColumnInfo columnInfo = getModel().getColumnInfo(i);
            T cell = createCell(resultSet, columnInfo);
            getCells().add(cell);
        }
    }

    @Override
    public ResultSetDataModel getModel() {
        return (ResultSetDataModel) super.getModel();
    }

    protected T createCell(ResultSet resultSet, ColumnInfo columnInfo) throws SQLException {
        return (T) new ResultSetDataModelCell(this, resultSet, columnInfo);
    }
}
