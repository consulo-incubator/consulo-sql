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

import com.dci.intellij.dbn.data.model.ColumnInfo;
import com.dci.intellij.dbn.editor.data.ui.table.DatasetEditorTable;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DatasetEditorHeaderMouseListener extends MouseAdapter {
    private DatasetEditorTable table;

    public DatasetEditorHeaderMouseListener(DatasetEditorTable table) {
        this.table = table;
    }

    public void mouseReleased(final MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON3) {
            Point mousePoint = event.getPoint();
            int tableColumnIndex = table.getTableHeader().columnAtPoint(mousePoint);
            int modelColumnIndex = table.convertColumnIndexToModel(tableColumnIndex);
            ColumnInfo columnInfo = table.getModel().getColumnInfo(modelColumnIndex);

            table.showPopupMenu(event, null, columnInfo);
        }
    }
}
