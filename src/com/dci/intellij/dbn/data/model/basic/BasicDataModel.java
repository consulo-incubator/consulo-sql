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

import com.dci.intellij.dbn.common.thread.ConditionalLaterInvocator;
import com.dci.intellij.dbn.data.find.DataSearchResult;
import com.dci.intellij.dbn.data.model.ColumnInfo;
import com.dci.intellij.dbn.data.model.DataModel;
import com.dci.intellij.dbn.data.model.DataModelCell;
import com.dci.intellij.dbn.data.model.DataModelHeader;
import com.dci.intellij.dbn.data.model.DataModelListener;
import com.dci.intellij.dbn.data.model.DataModelRow;
import com.dci.intellij.dbn.data.model.DataModelState;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BasicDataModel<T extends DataModelRow> implements DataModel<T> {
    protected DataModelHeader header;
    private DataModelState state;
    private Set<TableModelListener> tableModelListeners = new HashSet<TableModelListener>();
    private Set<ListDataListener> listDataListeners = new HashSet<ListDataListener>();
    private Set<DataModelListener> dataModelListeners = new HashSet<DataModelListener>();
    private List<T> rows = new ArrayList<T>();
    private Project project;
    private boolean isDisposed;

    private DataSearchResult searchResult;

    public BasicDataModel(Project project) {
        this.project = project;
    }

    @Override
    public boolean isReadonly() {
        return true;
    }

    public Project getProject() {
        return project;
    }

    public DataModelHeader getHeader() {
        return header;
    }

    @NotNull
    public DataModelState getState() {
        if (state == null) {
            state = createState();
        }
        return state;
    }

    public void setState(DataModelState state) {
        this.state = state;
    }

    protected DataModelState createState() {
        return new DataModelState();
    }

    @Override
    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
        getState().setRowCount(getRowCount());
    }

    public void addRow(T tableRow) {
        rows.add(tableRow);
        getState().setRowCount(getRowCount());
    }

    public void addRowAtIndex(int index, T row) {
        rows.add(index, row);
        updateRowIndexes(index);
        getState().setRowCount(getRowCount());
    }

    public void removeRowAtIndex(int index) {
        DataModelRow row = rows.remove(index);
        row.dispose();
        updateRowIndexes(index);
        getState().setRowCount(getRowCount());
    }

    public T getRowAtIndex(int index) {
        // model may be reloading when this is called, hence
        // IndexOutOfBoundsException is thrown if the range is not checked
        return index > -1 && rows.size() > index ? rows.get(index) : null;
    }

    public DataModelCell getCellAt(int rowIndex, int columnIndex) {
        return rows.get(rowIndex).getCellAtIndex(columnIndex);
    }

    public ColumnInfo getColumnInfo(int columnIndex) {
        return getHeader().getColumnInfo(columnIndex);
    }

    public int indexOfRow(T row) {
        return rows.indexOf(row);
    }

    protected void updateRowIndexes(int startIndex) {
        updateRowIndexes(rows, startIndex);
    }

    protected void updateRowIndexes(List<T> rows, int startIndex) {
        for (int i=startIndex; i<rows.size();i++) {
            rows.get(i).setIndex(i);
        }
    }

    /*********************************************************
     *                 Listener notifiers                    *
     *********************************************************/
    public void notifyCellUpdated(int rowIndex, int columnIndex) {
        TableModelEvent tableModelEvent = new TableModelEvent(this, rowIndex, rowIndex, columnIndex);
        ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, rowIndex, rowIndex);
        notifyListeners(listDataEvent, tableModelEvent);
    }

    public void notifyRowUpdated(int rowIndex) {
        TableModelEvent tableModelEvent = new TableModelEvent(this, rowIndex, rowIndex);
        ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, rowIndex, rowIndex);
        notifyListeners(listDataEvent, tableModelEvent);
    }

    public void notifyRowsDeleted(int fromRowIndex, int toRowIndex) {
        TableModelEvent tableModelEvent = new TableModelEvent(this, fromRowIndex, toRowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, fromRowIndex, toRowIndex);
        notifyListeners(listDataEvent, tableModelEvent);
    }

    public void notifyRowsUpdated(int fromRowIndex, int toRowIndex) {
        TableModelEvent tableModelEvent = new TableModelEvent(this, fromRowIndex, toRowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);
        ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, fromRowIndex, toRowIndex);
        notifyListeners(listDataEvent, tableModelEvent);
    }

    public void notifyRowsInserted(int fromRowIndex, int toRowIndex) {
        TableModelEvent tableModelEvent = new TableModelEvent(this, fromRowIndex, toRowIndex, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT);
        ListDataEvent listDataEvent = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, fromRowIndex, toRowIndex);
        notifyListeners(listDataEvent, tableModelEvent);
    }

    private void notifyListeners(final ListDataEvent listDataEvent, final TableModelEvent event) {
        new ConditionalLaterInvocator() {
            public void run() {
                for (ListDataListener listDataListener : listDataListeners) {
                    listDataListener.contentsChanged(listDataEvent);
                }

                for (TableModelListener tableModelListener: tableModelListeners) {
                    tableModelListener.tableChanged(event);
                }

                for (DataModelListener tableModelListener: dataModelListeners) {
                    tableModelListener.modelChanged();
                }
            }
        }.start();
    }

    /*********************************************************
     *                   ListModel (for gutter)              *
     *********************************************************/
    public int getSize() {
        return getRowCount();
    }

    public Object getElementAt(int index) {
        return getRowAtIndex(index);
    }

    public void addListDataListener(ListDataListener l) {
        listDataListeners.add(l);
    }

    public void removeListDataListener(ListDataListener l) {
        listDataListeners.remove(l);
    }

    /*********************************************************
     *                     DataModel                        *
     *********************************************************/
    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return getHeader().getColumnCount();
    }

    public String getColumnName(int columnIndex) {
        return getHeader().getColumnName(columnIndex);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        // model may be reloading when this is called, hence
        // IndexOutOfBoundsException is thrown if the range is not checked
        return rows.size() > rowIndex ? rows.get(rowIndex).getCellAtIndex(columnIndex) : null;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {}

    public void addTableModelListener(TableModelListener listener) {
        tableModelListeners.add(listener);
    }

    public void removeTableModelListener(TableModelListener listener) {
        tableModelListeners.remove(listener);
    }

    public void addDataModelListener(DataModelListener listener) {
        dataModelListeners.add(listener);
    }

    @Override
    public void removeDataModelListener(DataModelListener listener) {
        dataModelListeners.remove(listener);
    }

    @Override
    public DataSearchResult getSearchResult() {
        if (searchResult == null) {
            searchResult = new DataSearchResult();
        }
        return searchResult;
    }
    
    @Override
    public boolean hasSearchResult() {
        return searchResult != null && !searchResult.isEmpty();
    }

    public boolean isDisposed() {
        return isDisposed;
    }

    public void dispose() {
        if (!isDisposed) {
            isDisposed = true;
            for (DataModelRow row : rows) {
                row.dispose();
            }
            rows.clear();
            if (header != null) {
                header.dispose();
                header = null;
            }
            tableModelListeners.clear();
            listDataListeners.clear();
            searchResult = null;
        }
    }
}
