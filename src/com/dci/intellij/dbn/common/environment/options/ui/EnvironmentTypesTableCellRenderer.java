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

import com.dci.intellij.dbn.common.util.StringUtil;
import com.intellij.ui.ColoredSideBorder;
import com.intellij.ui.ColoredTableCellRenderer;

import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class EnvironmentTypesTableCellRenderer extends ColoredTableCellRenderer{
    @Override
    protected void customizeCellRenderer(JTable table, Object value, boolean selected, boolean hasFocus, int row, int column) {
        if (column == 2) {
            Color color = (Color) value;
            if (color == null) color = Color.WHITE;
            setBackground(color);
            setBorder(new CompoundBorder(new LineBorder(Color.WHITE), new ColoredSideBorder(color.brighter(), color.brighter(), color.darker(), color.darker(), 1)));
        } else {
            String stringValue = (String) value;
            if (StringUtil.isNotEmpty(stringValue)) {
                append(stringValue);
            }
        }
    }
}
