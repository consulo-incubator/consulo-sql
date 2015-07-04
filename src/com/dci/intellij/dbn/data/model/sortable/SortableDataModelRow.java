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

package com.dci.intellij.dbn.data.model.sortable;

import com.dci.intellij.dbn.data.model.basic.BasicDataModelRow;

public class SortableDataModelRow<T extends SortableDataModelCell> extends BasicDataModelRow<T> implements Comparable {

    protected SortableDataModelRow(SortableDataModel model) {
        super(model);
    }

    @Override
    public SortableDataModel getModel() {
        return (SortableDataModel) super.getModel();
    }

    @Override
    public T getCellAtIndex(int index) {
        return super.getCellAtIndex(index);
    }

    public int compareTo(Object o) {
        SortableDataModel model = getModel();
        int index = model.getSortColumnIndex();

        if (index == -1) return 0;
        SortableDataModelRow row = (SortableDataModelRow) o;

        SortableDataModelCell local = cells.get(index);
        SortableDataModelCell remote = row.getCellAtIndex(index);

        int compareIndex = model.getSortDirection().getCompareIndex();

        if (remote == null) return compareIndex;
        if (local == null) return -compareIndex;

        return compareIndex * local.compareTo(remote);
    }

}
