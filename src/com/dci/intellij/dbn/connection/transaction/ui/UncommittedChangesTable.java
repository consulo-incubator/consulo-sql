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

import com.dci.intellij.dbn.common.ui.table.DBNTable;
import com.dci.intellij.dbn.connection.transaction.UncommittedChange;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.ui.ColoredTableCellRenderer;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UncommittedChangesTable extends DBNTable {
    private static final Border EMPTY_BORDER = new EmptyBorder(0, 2, 0, 2);

    public UncommittedChangesTable(UncommittedChangesTableModel model) {
        super(model.getProject(), model, false);
        setDefaultRenderer(UncommittedChange.class, new CellRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setCellSelectionEnabled(true);
        setRowHeight(getRowHeight() + 2);
        accommodateColumnsSize();
        addMouseListener(new MouseListener());
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        if (e.getID() != MouseEvent.MOUSE_DRAGGED && getChangeAtMouseLocation() != null) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            super.processMouseMotionEvent(e);
            setCursor(Cursor.getDefaultCursor());
        }
    }

    public UncommittedChange getChangeAtMouseLocation() {
        Point location = MouseInfo.getPointerInfo().getLocation();
        location.setLocation(location.getX() - getLocationOnScreen().getX(), location.getY() - getLocationOnScreen().getY());

        int columnIndex = columnAtPoint(location);
        int rowIndex = rowAtPoint(location);
        if (columnIndex > -1 && rowIndex > -1) {
            return (UncommittedChange) getModel().getValueAt(rowIndex, columnIndex);
        }

        return null;
    }

    public class CellRenderer extends ColoredTableCellRenderer {
        @Override
        protected void customizeCellRenderer(JTable table, Object value, boolean selected, boolean hasFocus, int row, int column) {
            UncommittedChange change = (UncommittedChange) value;
            if (column == 0) {
                setIcon(change.getIcon());
                append(change.getDisplayFilePath());
            } else if (column == 1) {
                append(change.getChangesCount() + " uncommitted changes");
            }
            setBorder(EMPTY_BORDER);

        }
    }

    public class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
                int selectedRow = getSelectedRow();
                UncommittedChange change = (UncommittedChange) getModel().getValueAt(selectedRow, 0);
                FileEditorManager fileEditorManager = FileEditorManager.getInstance(getProject());
                VirtualFile virtualFile = VirtualFileManager.getInstance().findFileByUrl(change.getFilePath());
                if (virtualFile != null) {
                    fileEditorManager.openFile(virtualFile, true);
                }
            }

        }
    }
}
