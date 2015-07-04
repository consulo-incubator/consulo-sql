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

import com.dci.intellij.dbn.data.model.ColumnInfo;
import com.dci.intellij.dbn.data.type.BasicDataType;
import com.dci.intellij.dbn.data.type.DBDataType;

public class BasicColumnInfo implements ColumnInfo {
    protected String name;
    protected int columnIndex;
    protected DBDataType dataType;

    public BasicColumnInfo(String name, DBDataType dataType, int columnIndex) {
        this.name = name;
        this.columnIndex = columnIndex;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public DBDataType getDataType() {
        return dataType;
    }

    public void dispose() {
        dataType = null;
    }

    public boolean isSortable() {
        return dataType.isNative() && dataType.getNativeDataType().getBasicDataType().is(BasicDataType.LITERAL, BasicDataType.NUMERIC, BasicDataType.DATE_TIME);
    }

}
