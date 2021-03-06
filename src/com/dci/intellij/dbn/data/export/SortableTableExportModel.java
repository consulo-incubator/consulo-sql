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

package com.dci.intellij.dbn.data.export;

import com.dci.intellij.dbn.data.model.ColumnInfo;
import com.dci.intellij.dbn.data.model.sortable.SortableDataModelCell;
import com.dci.intellij.dbn.data.type.BasicDataType;
import com.dci.intellij.dbn.data.type.DBNativeDataType;
import com.dci.intellij.dbn.data.ui.table.sortable.SortableTable;
import com.intellij.openapi.project.Project;

public class SortableTableExportModel implements DataExportModel{
    private boolean selection;
    private SortableTable table;

    int[] selectedRows;
    int[] selectedColumns;

    public SortableTableExportModel(boolean selection, SortableTable table) {
        this.selection = selection;
        this.table = table;

        if (selection) {
            selectedRows = table.getSelectedRows();
            selectedColumns = table.getSelectedColumns();
        }
    }

    public Project getProject() {
        return table.getProject();
    }

    public String getTableName() {
        return table.getName();
    }

    public int getColumnCount() {
        return selection ?
            selectedColumns.length :
            table.getModel().getColumnCount();
    }

    public int getRowCount() {
        return selection ?
            selectedRows.length :
            table.getModel().getRowCount();
    }

    public Object getValue(int rowIndex, int columnIndex) {
        int realRowIndex = getRealRowIndex(rowIndex);
        int realColumnIndex = getRealColumnIndex(columnIndex);
        SortableDataModelCell dataModelCell = (SortableDataModelCell) table.getModel().getValueAt(realRowIndex, realColumnIndex);
        return dataModelCell == null ? null : dataModelCell.getUserValue();
    }

    public String getColumnName(int columnIndex) {
        int realColumnIndex = getRealColumnIndex(columnIndex);
        return table.getModel().getColumnName(realColumnIndex);
    }

    public BasicDataType getBasicDataType(int columnIndex) {
        int realColumnIndex = getRealColumnIndex(columnIndex);
        ColumnInfo columnInfo = table.getModel().getColumnInfo(realColumnIndex);
        DBNativeDataType nativeDataType = columnInfo.getDataType().getNativeDataType();

        return nativeDataType == null ?
                BasicDataType.LITERAL :
                nativeDataType.getDataTypeDefinition().getBasicDataType();

    }

    /**
     * Returns the column index from the underlying sortable table model.
     */
    private int getRealColumnIndex(int columnIndex) {
        if (selection) {
            int selectedColumnIndex = selectedColumns[columnIndex];
            return table.getModelColumnIndex(selectedColumnIndex);
        } else {
            return table.getModelColumnIndex(columnIndex);
        }
    }

    private int getRealRowIndex(int rowIndex) {
        if (selection) {
            return selectedRows[rowIndex];
        } else {
            return rowIndex;
        }

    }
}
