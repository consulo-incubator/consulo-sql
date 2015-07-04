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

package com.dci.intellij.dbn.data.model.sortable;

import com.dci.intellij.dbn.data.sorting.SortDirection;
import com.dci.intellij.dbn.data.ui.table.sortable.SortableTable;

import javax.swing.table.JTableHeader;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SortableTableHeaderMouseListener extends MouseAdapter {
    private SortableTable table;

    public SortableTableHeaderMouseListener(SortableTable table) {
        this.table = table;
    }

    public void mouseClicked(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            Point mousePoint = event.getPoint();
            mousePoint.setLocation(mousePoint.getX() - 4, mousePoint.getX());
            JTableHeader tableHeader = table.getTableHeader();
            int columnIndex = tableHeader.columnAtPoint(mousePoint);
            Rectangle colRect = tableHeader.getHeaderRect(columnIndex);
            boolean isEdgeClick = colRect.getMaxX() - 8 < mousePoint.getX();
            if (isEdgeClick) {
                if (event.getClickCount() == 2) {
                    table.accommodateColumnSize(columnIndex, 20);
                }
            } else {
                table.sort(columnIndex, SortDirection.INDEFINITE);
            }
        }
        table.requestFocus();
        //event.consume();
    }
}
