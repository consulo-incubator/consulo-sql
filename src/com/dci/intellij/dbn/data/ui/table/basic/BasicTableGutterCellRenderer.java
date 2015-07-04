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

import com.dci.intellij.dbn.common.ui.DBNColor;
import com.dci.intellij.dbn.data.model.DataModelRow;
import com.dci.intellij.dbn.data.model.basic.BasicDataModel;
import com.intellij.util.ui.UIUtil;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

public class BasicTableGutterCellRenderer extends JPanel implements ListCellRenderer {
    public interface Colors {
        DBNColor FOREGROUND = new DBNColor(new Color(0x454545), new Color(0x808080));
        DBNColor SELECTED_FOREGROUND = new DBNColor(new Color(0x808080), new Color(0x171717));
    }

    private static final Border BORDER = new CompoundBorder(
            UIManager.getBorder("TableHeader.cellBorder"),
            new EmptyBorder(0, 2, 0, 6));

    private JLabel lText;

    public static final ListCellRenderer INSTANCE = new BasicTableGutterCellRenderer();

    public BasicTableGutterCellRenderer() {
        setForeground(Colors.FOREGROUND);
        setBackground(UIUtil.getPanelBackground());
        setBorder(BORDER);
        setLayout(new BorderLayout());
        lText = new JLabel();
        add(lText, BorderLayout.WEST);
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        BasicDataModel model = (BasicDataModel) list.getModel();
        DataModelRow row = model.getRowAtIndex(index);
        lText.setText("" + row.getIndex());
        //lText.setFont(isSelected ? BOLD_FONT : REGULAR_FONT);
        lText.setForeground(isSelected ? Colors.SELECTED_FOREGROUND : Colors.FOREGROUND);
        return this;
    }
}
