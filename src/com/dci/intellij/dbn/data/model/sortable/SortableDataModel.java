package com.dci.intellij.dbn.data.model.sortable;

import com.dci.intellij.dbn.common.sorting.SortDirection;
import com.dci.intellij.dbn.data.model.DataModelRow;
import com.dci.intellij.dbn.data.model.DataModelSortingState;
import com.dci.intellij.dbn.data.model.DataModelState;
import com.dci.intellij.dbn.data.model.basic.BasicDataModel;
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
        DataModelSortingState sortingState = getState().getSortingState();
        String columnName = getColumnName(columnIndex);
        if (direction == SortDirection.UNDEFINED) {
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
