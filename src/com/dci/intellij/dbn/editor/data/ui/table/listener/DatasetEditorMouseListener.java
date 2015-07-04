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

package com.dci.intellij.dbn.editor.data.ui.table.listener;

import com.dci.intellij.dbn.common.ui.MouseUtil;
import com.dci.intellij.dbn.editor.data.DatasetEditorManager;
import com.dci.intellij.dbn.editor.data.filter.DatasetFilterInput;
import com.dci.intellij.dbn.editor.data.model.DatasetEditorModelCell;
import com.dci.intellij.dbn.editor.data.ui.table.DatasetEditorTable;
import com.dci.intellij.dbn.object.DBColumn;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DatasetEditorMouseListener extends MouseAdapter {
    private DatasetEditorTable table;

    public DatasetEditorMouseListener(DatasetEditorTable table) {
        this.table = table;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void mouseReleased(final MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON3) {
            Point mousePoint = event.getPoint();
            DatasetEditorModelCell cell = (DatasetEditorModelCell) table.getCellAtLocation(mousePoint);
            if (cell != null) {

                if (table.getSelectedRowCount() <= 1 && table.getSelectedColumnCount() <= 1) {
                    table.cancelEditing();
                    boolean oldEditingSatus = table.isEditingEnabled();
                    table.setEditingEnabled(false);
                    table.selectCell(table.rowAtPoint(mousePoint), table.columnAtPoint(mousePoint));
                    table.setEditingEnabled(oldEditingSatus);
                }

                table.showPopupMenu(event, cell, cell.getColumnInfo());
            }
        }
    }

    public void mouseClicked(MouseEvent event) {
        if (MouseUtil.isNavigationEvent(event)) {
            DatasetEditorModelCell cell = (DatasetEditorModelCell) table.getCellAtLocation(event.getPoint());
            DBColumn column = cell.getColumnInfo().getColumn();

            if (column.isForeignKey() && cell.getUserValue() != null) {
                table.clearSelection();
                DatasetFilterInput filterInput = table.getModel().resolveForeignKeyRecord(cell);
                if (filterInput.getColumns().size() > 0) {
                    DatasetEditorManager datasetEditorManager = DatasetEditorManager.getInstance(column.getProject());
                    datasetEditorManager.navigateToRecord(filterInput, event);
                    event.consume();
                }
            }
        }
    }
}