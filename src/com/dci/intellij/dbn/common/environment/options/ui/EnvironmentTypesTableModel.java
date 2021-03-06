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

package com.dci.intellij.dbn.common.environment.options.ui;

import com.dci.intellij.dbn.common.environment.EnvironmentType;
import com.dci.intellij.dbn.common.environment.EnvironmentTypeBundle;
import com.dci.intellij.dbn.common.environment.options.EnvironmentPresentationChangeListener;
import com.dci.intellij.dbn.common.event.EventManager;
import com.dci.intellij.dbn.common.ui.DBNColor;
import com.dci.intellij.dbn.common.util.CommonUtil;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class EnvironmentTypesTableModel implements TableModel, Disposable {
    private EnvironmentTypeBundle environmentTypes;
    private Set<TableModelListener> tableModelListeners = new HashSet<TableModelListener>();
    private Project project;

    public EnvironmentTypesTableModel(Project project, EnvironmentTypeBundle environmentTypes) {
        this.project = project;
        this.environmentTypes = new EnvironmentTypeBundle(environmentTypes);
        addTableModelListener(defaultModelListener);
    }

    public EnvironmentTypeBundle getEnvironmentTypes() {
        return environmentTypes;
    }

    public void setEnvironmentTypes(EnvironmentTypeBundle environmentTypes) {
        this.environmentTypes = new EnvironmentTypeBundle(environmentTypes);
        notifyListeners(0, environmentTypes.size(), -1);
    }

    public int getRowCount() {
        return environmentTypes.size();
    }
    
    TableModelListener defaultModelListener = new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            EnvironmentPresentationChangeListener listener = EventManager.notify(project, EnvironmentPresentationChangeListener.TOPIC);
            listener.settingsChanged(environmentTypes);
        }
    };    

    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int columnIndex) {
        return columnIndex == 0 ? "Name" :
               columnIndex == 1 ? "Description" :
               columnIndex == 2 ? "Color" : null;
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;

    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        EnvironmentType environmentType = getEnvironmentType(rowIndex);
        return
           columnIndex == 0 ? environmentType.getName() :
           columnIndex == 1 ? environmentType.getDescription() :
           columnIndex == 2 ? environmentType.getColor() : null;
    }

    public void setValueAt(Object o, int rowIndex, int columnIndex) {
        Object actualValue = getValueAt(rowIndex, columnIndex);
        if (!CommonUtil.safeEqual(actualValue, o)) {
            EnvironmentType environmentType = environmentTypes.get(rowIndex);
            if (columnIndex == 0) {
                environmentType.setName((String) o);

            } else if (columnIndex == 1) {
                environmentType.setDescription((String) o);
            } else if (columnIndex == 2) {
                Color color = (Color) o;
                DBNColor environmentColor = environmentType.getColor();
                if (environmentColor == null) {
                    environmentColor = new DBNColor(color, color);
                } else {
                    environmentColor = environmentColor.set(color);
                }
                environmentType.setColor(environmentColor);
            }

            notifyListeners(rowIndex, rowIndex, columnIndex);
        }
    }

    public void addTableModelListener(TableModelListener l) {
        tableModelListeners.add(l);
    }

    public void removeTableModelListener(TableModelListener l) {
        tableModelListeners.remove(l);
    }

    private EnvironmentType getEnvironmentType(int rowIndex) {
        while (environmentTypes.size() <= rowIndex) {
            environmentTypes.add(new EnvironmentType());
        }
        return environmentTypes.get(rowIndex);
    }

    public void insertRow(int rowIndex) {
        environmentTypes.add(rowIndex, new EnvironmentType());
        notifyListeners(rowIndex, environmentTypes.size()-1, -1);
    }

    public void removeRow(int rowIndex) {
        if (environmentTypes.size() > rowIndex) {
            environmentTypes.remove(rowIndex);
            notifyListeners(rowIndex, environmentTypes.size()-1, -1);
        }
    }

    public void notifyListeners(int firstRowIndex, int lastRowIndex, int columnIndex) {
        TableModelEvent modelEvent = new TableModelEvent(this, firstRowIndex, lastRowIndex, columnIndex);
        for (TableModelListener listener : tableModelListeners) {
            listener.tableChanged(modelEvent);
        }
    }
    
    public void validate() throws ConfigurationException {
        for (EnvironmentType environmentType : environmentTypes) {
            if (StringUtil.isEmpty(environmentType.getName())) {
                throw new ConfigurationException("Please provide names for all environment types.");
            }
        }
    }

    @Override
    public void dispose() {
        tableModelListeners.clear();
    }
}
