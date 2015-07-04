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

import com.dci.intellij.dbn.data.model.DataModelCell;
import com.dci.intellij.dbn.data.model.DataModelRow;

import java.util.ArrayList;
import java.util.List;

public class BasicDataModelRow<T extends DataModelCell> implements DataModelRow<T> {
    protected BasicDataModel model;
    protected List<T> cells;
    private int index;

    public BasicDataModelRow(BasicDataModel model) {
        cells = new ArrayList<T>(model.getColumnCount());
        this.model = model;
    }

    public BasicDataModel getModel() {
        return model;
    }

    public List<T> getCells() {
        return cells;
    }

    public T getCellAtIndex(int index) {
        return cells.get(index);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void dispose() {
        for (DataModelCell cell : cells) {
            cell.dispose();
        }
        cells.clear();
        cells = null;
        model = null;
    }
}
