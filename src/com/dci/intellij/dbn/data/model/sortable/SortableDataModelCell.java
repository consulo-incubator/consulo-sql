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

import com.dci.intellij.dbn.data.model.ColumnInfo;
import com.dci.intellij.dbn.data.model.DataModelCell;
import com.dci.intellij.dbn.data.model.basic.BasicDataModelCell;

public class SortableDataModelCell extends BasicDataModelCell implements Comparable {

    public SortableDataModelCell(SortableDataModelRow row, Object userValue, ColumnInfo columnInfo) {
        super(userValue, row, columnInfo);
    }

    public int compareTo(Object o) {
        DataModelCell cell = (DataModelCell) o;
        Comparable local = (Comparable) getUserValue();
        Comparable remote = (Comparable) cell.getUserValue();

        if (local == null) return -1;
        if (remote == null) return 1;
        // local class may differ from remote class for
        // columns with data conversion error
        if (local.getClass().equals(remote.getClass())) {
            return local.compareTo(remote);
        } else {
            Class typeClass = cell.getColumnInfo().getDataType().getTypeClass();
            return local.getClass().equals(typeClass) ? 1 :
                   remote.getClass().equals(typeClass) ? -1 : 0;
        }
    }

}
