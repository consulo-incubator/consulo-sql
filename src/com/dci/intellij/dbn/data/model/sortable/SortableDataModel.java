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

import com.dci.intellij.dbn.data.model.DataModelRow;
import com.dci.intellij.dbn.data.model.DataModelState;
import com.dci.intellij.dbn.data.model.basic.BasicDataModel;
import com.dci.intellij.dbn.data.sorting.SingleColumnSortingState;
import com.dci.intellij.dbn.data.sorting.SortDirection;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class SortableDataModel<T extends SortableDataModelRow> extends BasicDataModel<T> {
    protected SortableDataModel(Project project) {
        super(project);
    }

    public boolean sort(int columnIndex, SortDirection direction) {
        boolean sort = updateSortingState(columnIndex, direction);
        if (sort) {
            sort();
            notifyRowsUpdated(0, getRows().size());
        }
        return sort;
    }

    @NotNull
    @Override
    public SortableDataModelState getState() {
        return (SortableDataModelState) super.getState();
    }

    @Override
    protected DataModelState createState() {
        return new SortableDataModelState();
    }

    protected boolean updateSortingState(int columnIndex, SortDirection direction) {
        SingleColumnSortingState sortingState = getState().getSortingState();
        String columnName = getColumnName(columnIndex);
        if (direction == SortDirection.INDEFINITE) {
            if (columnName.equals(sortingState.getColumnName())) {
                sortingState.swichDirection();
            } else {
                sortingState.setDirection(SortDirection.ASCENDING);
                sortingState.setColumnName(columnName);
            }
            return true;
        } else {
            if (!sortingState.isColumnName(columnName) || !sortingState.isDirection(direction)) {
                sortingState.setColumnName(columnName);
                sortingState.setDirection(direction);
                return true;
            }
            return false;
        }
    }

    protected void sortByIndex() {
        Collections.sort(getRows(), INDEX_COMPARATOR);
        updateRowIndexes(0);
    }

    protected void sort() {
        sort(getRows());
    }

    protected void sort(List<T> rows) {
        if (getState().getSortingState().isValid()) {
            Collections.sort(rows);
        }
        updateRowIndexes(rows, 0);
    }

    private static final Comparator<DataModelRow> INDEX_COMPARATOR = new Comparator<DataModelRow>() {
        public int compare(DataModelRow row1, DataModelRow row2) {
            return row1.getIndex() - row2.getIndex();
        }
    };


    public SortDirection getSortDirection() {
        return getState().getSortingState().getDirection();
    }

    public int getSortColumnIndex() {
        // fixme - cache sort column index somehow
        SortableDataModelState modelState = getState();
        return isDisposed() || modelState == null ? -1 :
                getHeader().getColumnIndex(modelState.getSortingState().getColumnName());
    }
}
