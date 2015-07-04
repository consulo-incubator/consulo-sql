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

package com.dci.intellij.dbn.connection.transaction.ui;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.transaction.UncommittedChange;
import com.dci.intellij.dbn.connection.transaction.UncommittedChangeBundle;
import com.intellij.openapi.project.Project;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class UncommittedChangesTableModel implements TableModel {
    private ConnectionHandler connectionHandler;

    public UncommittedChangesTableModel(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public Project getProject() {
        return connectionHandler.getProject();
    }

    public int getRowCount() {
        UncommittedChangeBundle uncommittedChanges = connectionHandler.getUncommittedChanges();
        return uncommittedChanges == null ? 0 : uncommittedChanges.size();
    }

    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int columnIndex) {
        return
            columnIndex == 0 ? "File" :
            columnIndex == 1 ? "Details" : null ;
    }

    public Class<?> getColumnClass(int columnIndex) {
        return UncommittedChange.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return connectionHandler.getUncommittedChanges().getChanges().get(rowIndex);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
    public void addTableModelListener(TableModelListener l) {}
    public void removeTableModelListener(TableModelListener l) {}
}
