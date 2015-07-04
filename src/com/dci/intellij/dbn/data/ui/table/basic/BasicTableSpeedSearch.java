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

package com.dci.intellij.dbn.data.ui.table.basic;

import com.dci.intellij.dbn.data.model.ColumnInfo;
import com.dci.intellij.dbn.data.model.DataModelHeader;
import com.intellij.ui.SpeedSearchBase;

public class BasicTableSpeedSearch extends SpeedSearchBase<BasicTable> {
    private BasicTable table;

    private ColumnInfo[] columnInfos;
    private int columnIndex = 0;

    public BasicTableSpeedSearch(BasicTable table) {
        super(table);
        this.table = table;
    }

    protected int getSelectedIndex() {
        return columnIndex;
    }

    protected Object[] getAllElements() {
        if (columnInfos == null) {
            DataModelHeader modelHeader = table.getModel().getHeader();
            columnInfos = modelHeader.getColumnInfos().toArray(new ColumnInfo[modelHeader.getColumnCount()]);
        }
        return columnInfos;
    }

    protected String getElementText(Object o) {
        ColumnInfo columnInfo = (ColumnInfo) o;
        return columnInfo.getName();
    }

    protected void selectElement(Object o, String s) {
        for(ColumnInfo columnInfo : columnInfos) {
            if (columnInfo == o) {
                columnIndex = columnInfo.getColumnIndex();
                int rowIndex = table.getSelectedRow();
                if (rowIndex == -1) rowIndex = 0;
                table.scrollRectToVisible(table.getCellRect(rowIndex, columnIndex, true));
                table.setColumnSelectionInterval(columnIndex, columnIndex);
                break;
            }
        }
    }

    
}