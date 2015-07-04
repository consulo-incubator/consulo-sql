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
import com.dci.intellij.dbn.data.model.sortable.SortableDataModelCell;
import com.dci.intellij.dbn.data.type.DBDataType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetDataModelCell extends SortableDataModelCell {
    public ResultSetDataModelCell(ResultSetDataModelRow row, ResultSet resultSet, ColumnInfo columnInfo) throws SQLException {
        super(row, null, columnInfo);
        DBDataType dataType = columnInfo.getDataType();
        Object userValue = dataType.getValueFromResultSet(resultSet, columnInfo.getColumnIndex() + 1);
        setUserValue(userValue);
    }

    @Override
    public ResultSetDataModelRow getRow() {
        return (ResultSetDataModelRow) super.getRow();
    }
}
