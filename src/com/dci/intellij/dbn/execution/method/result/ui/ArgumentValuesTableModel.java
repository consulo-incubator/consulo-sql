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

package com.dci.intellij.dbn.execution.method.result.ui;

import com.dci.intellij.dbn.execution.method.ArgumentValue;
import com.intellij.openapi.project.Project;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

@Deprecated
public class ArgumentValuesTableModel implements TableModel {
    private List<ArgumentValue> argumentValues;
    private Project project;


    public ArgumentValuesTableModel(Project project, List<ArgumentValue> argumentValues) {
        this.argumentValues = argumentValues;
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public int getRowCount() {
        int count = 0;
        for (ArgumentValue argumentValue : argumentValues) {
            if (!argumentValue.isCursor()) {
                count++;
            }
        }
        return count;
    }

    public int getColumnCount() {
        return 2;
    }

    public String getColumnName(int columnIndex) {
        return
            columnIndex == 0 ? "Argument" :
            columnIndex == 1 ? "Value" : null ;
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        int index = 0;
        ArgumentValue resultArgumentValue = null;
        for (ArgumentValue argumentValue : argumentValues) {
            if (!argumentValue.isCursor()) {
                if (index == rowIndex) {
                    resultArgumentValue = argumentValue;
                    break;
                }
                index++;
            }
        }
        return
            resultArgumentValue == null ? null :
            columnIndex == 0 ? resultArgumentValue :
            columnIndex == 1 ? resultArgumentValue.getValue() : null;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
    public void addTableModelListener(TableModelListener l) {}
    public void removeTableModelListener(TableModelListener l) {}
}
