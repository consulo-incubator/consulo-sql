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

package com.dci.intellij.dbn.data.ui.table.basic;

import com.dci.intellij.dbn.common.ui.table.DBNTable;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;

public class BasicTableGutter extends JList implements ListSelectionListener {
    private BasicTable table;

    public BasicTableGutter(BasicTable table) {
        super(table.getModel());
        this.table = table;
        setCellRenderer(BasicTableGutterCellRenderer.INSTANCE);
        addListSelectionListener(this);
        int rowHeight = table.getRowHeight();
        if (rowHeight != 0) setFixedCellHeight(rowHeight);
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        if (getModel().getSize() == 0) {
            setFixedCellWidth(10);
        }
    }

    public DBNTable getTable() {
        return table;
    }

    public void scrollRectToVisible(Rectangle rect) {
        super.scrollRectToVisible(rect);
        Rectangle tableRect = table.getVisibleRect();

        tableRect.y = rect.y;
        tableRect.height = rect.height;
        table.scrollRectToVisible(tableRect);
    }

    boolean justGainedFocus = false;

    @Override
    protected void processFocusEvent(FocusEvent e) {
        super.processFocusEvent(e);
        if (e.getComponent() == this) {
            justGainedFocus = e.getID() == FocusEvent.FOCUS_GAINED;
        }
    }

    /*********************************************************
     *                ListSelectionListener                  *
     *********************************************************/
    public void valueChanged(ListSelectionEvent e) {
        if (hasFocus()) {
            int lastColumnIndex = table.getModel().getColumnCount() - 1;
            if (justGainedFocus) {
                justGainedFocus = false;
                if (table.isEditing()) table.getCellEditor().cancelCellEditing();
                table.clearSelection();
                table.setColumnSelectionInterval(0, lastColumnIndex);
            }

            for (int i = e.getFirstIndex(); i <= e.getLastIndex(); i++) {
                if (isSelectedIndex(i)) 
                    table.getSelectionModel().addSelectionInterval(i, i); else
                    table.getSelectionModel().removeSelectionInterval(i, i);
            }
        }
    }
}
