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

package com.dci.intellij.dbn.object.filter.name.ui;

import com.dci.intellij.dbn.object.filter.name.CompoundFilterCondition;
import com.dci.intellij.dbn.object.filter.name.FilterCondition;
import com.dci.intellij.dbn.object.filter.name.ObjectNameFilter;
import com.dci.intellij.dbn.object.filter.name.SimpleFilterCondition;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.SimpleTextAttributes;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FilterSettingsTreeCellRenderer extends ColoredTreeCellRenderer{
    @Override
    public void customizeCellRenderer(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof ObjectNameFilter) {
            ObjectNameFilter condition = (ObjectNameFilter) value;
            append(condition.getObjectType().getName().toUpperCase(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES);
            setIcon(condition.getObjectType().getIcon());
        }

        if (value instanceof CompoundFilterCondition) {
            CompoundFilterCondition condition = (CompoundFilterCondition) value;
            List<FilterCondition> conditions = condition.getConditions();
            if (conditions.size() > 1) {
                append(" (" + conditions.size() + " conditions joined with " + condition.getJoinType() + ") ", SimpleTextAttributes.GRAY_ATTRIBUTES);
            }
        }

        if (value instanceof SimpleFilterCondition) {
            SimpleFilterCondition condition = (SimpleFilterCondition) value;

            append(condition.getObjectType().getName().toUpperCase() + "_NAME ");
            append(condition.getOperator().getText(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES);
            append(" '" + condition.getText() + "' ", new SimpleTextAttributes(0, Color.BLUE));

        }

        if (value instanceof FilterCondition) {
            FilterCondition condition = (FilterCondition) value;
            CompoundFilterCondition parentCondition = condition.getParent();
            if (parentCondition != null) {
                List<FilterCondition> conditions = parentCondition.getConditions();
                if (conditions.indexOf(condition) < conditions.size() - 1) {
                    append(parentCondition.getJoinType().toString().toLowerCase(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES);
                }
            }
        }

    }
}
