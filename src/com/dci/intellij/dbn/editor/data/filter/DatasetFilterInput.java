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

import com.dci.intellij.dbn.object.DBColumn;
import com.dci.intellij.dbn.object.DBDataset;
import gnu.trove.THashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatasetFilterInput {
    private DBDataset dataset;
    private List<DBColumn> columns = new ArrayList<DBColumn>();
    private Map<DBColumn, Object> values = new THashMap<DBColumn, Object>();

    public DatasetFilterInput(DBDataset dataset) {
        this.dataset = dataset;
    }

    public DBDataset getDataset() {
        return dataset;
    }

    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setColumnValue(DBColumn column, Object value) {
        columns.add(column);
        values.put(column, value);
    }

    public Object getColumnValue(DBColumn column) {
        return values.get(column);
    }
}
