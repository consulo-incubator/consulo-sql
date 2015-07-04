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

package com.dci.intellij.dbn.editor.data.ui.table;

import com.dci.intellij.dbn.data.ui.table.basic.BasicTableGutter;
import com.dci.intellij.dbn.editor.data.ui.table.renderer.DatasetEditorTableGutterRenderer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DatasetEditorTableGutter extends BasicTableGutter {
    public DatasetEditorTableGutter(DatasetEditorTable table) {
        super(table);
        setCellRenderer(DatasetEditorTableGutterRenderer.INSTANCE);
        setFixedCellWidth(48);
        addMouseListener(mouseListener);
    }

    MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                getTable().getDatasetEditor().openRecordEditor(getSelectedIndex());
            }
        }
    };

    @Override
    public DatasetEditorTable getTable() {
        return (DatasetEditorTable) super.getTable();
    }
}
