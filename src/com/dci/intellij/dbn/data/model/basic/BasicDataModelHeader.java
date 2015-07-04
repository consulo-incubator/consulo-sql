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

package com.dci.intellij.dbn.data.model.basic;


import com.dci.intellij.dbn.common.dispose.DisposeUtil;
import com.dci.intellij.dbn.data.model.ColumnInfo;
import com.dci.intellij.dbn.data.model.DataModelHeader;
import com.dci.intellij.dbn.data.type.DBDataType;

import java.util.ArrayList;
import java.util.List;

public class BasicDataModelHeader implements DataModelHeader {
    private List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>();

    public List<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public ColumnInfo getColumnInfo(int columnIndex) {
        return columnInfos.get(columnIndex);
    }

    public int getColumnIndex(String name) {
        for (int i=0; i<columnInfos.size(); i++) {
            ColumnInfo columnInfo = columnInfos.get(i);
            if (columnInfo.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public String getColumnName(int columnIndex) {
        return getColumnInfo(columnIndex).getName();
    }

    public DBDataType getColumnDataType(int columnIndex) {
        return getColumnInfo(columnIndex).getDataType();
    }

    public int getColumnCount() {
        return columnInfos.size();
    }

    public void dispose() {
        DisposeUtil.disposeCollection(columnInfos);
    }
}
