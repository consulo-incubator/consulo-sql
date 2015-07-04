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

package com.dci.intellij.dbn.data.ui.table.sortable;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.data.model.sortable.SortableDataModel;
import com.dci.intellij.dbn.data.sorting.SortDirection;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

public class SortableTableHeaderRenderer extends DefaultTableCellRenderer {
    private static final Border BORDER = UIManager.getBorder("TableHeader.cellBorder");
    public static final TableCellRenderer INSTANCE = new SortableTableHeaderRenderer();

    private SortableTableHeaderRenderer() {}

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        SortableDataModel model = (SortableDataModel) table.getModel();
        JTableHeader header = table.getTableHeader();

        setBackground(header.getBackground());
        setFont(header.getFont());
        setBorder(BORDER);
        setIcon(null);
        setHorizontalTextPosition(JLabel.LEADING);
        setHorizontalAlignment(JLabel.CENTER);
        if (column == table.convertColumnIndexToView(model.getSortColumnIndex())) {
            renderer.setIcon(model.getSortDirection() == SortDirection.ASCENDING ? Icons.ACTION_SORT_ASC : Icons.ACTION_SORT_DESC);
        }
        return renderer;
    }
}
