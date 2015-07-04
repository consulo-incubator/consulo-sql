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

package com.dci.intellij.dbn.code.common.completion.options.filter.ui;

import com.dci.intellij.dbn.code.common.completion.options.filter.CodeCompletionFilterSettings;
import com.dci.intellij.dbn.code.common.completion.options.filter.CodeCompletionFilterOption;
import com.dci.intellij.dbn.code.common.completion.options.filter.CodeCompletionFilterOptionBundle;
import com.intellij.ui.CheckboxTree;
import com.intellij.ui.CheckedTreeNode;
import com.intellij.ui.SimpleTextAttributes;

import javax.swing.JTree;
import javax.swing.Icon;

public class CodeCompletionFilterTreeCellRenderer extends CheckboxTree.CheckboxTreeCellRenderer { //implements TreeCellEditor {
    public static final CodeCompletionFilterTreeCellRenderer CELL_RENDERER = new CodeCompletionFilterTreeCellRenderer();

    public void customizeCellRenderer(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        CheckedTreeNode node = (CheckedTreeNode) value;
        Object userObject = node.getUserObject();

        if (userObject instanceof CodeCompletionFilterOptionBundle) {
            CodeCompletionFilterOptionBundle optionBundle = (CodeCompletionFilterOptionBundle) userObject;
            getTextRenderer().append(optionBundle.getName(), SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES);
        }
        else if(userObject instanceof CodeCompletionFilterOption) {
            CodeCompletionFilterOption option = (CodeCompletionFilterOption) userObject;
            Icon icon = option.getIcon();
            getTextRenderer().append(option.getName(), icon == null ?
                    SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES :
                    SimpleTextAttributes.REGULAR_ATTRIBUTES);
            getTextRenderer().setIcon(icon);
        }
        else if (userObject instanceof CodeCompletionFilterSettings){
            CodeCompletionFilterSettings codeCompletionFilterSettings = (CodeCompletionFilterSettings) userObject;
            getTextRenderer().append(codeCompletionFilterSettings.getDisplayName(), SimpleTextAttributes.REGULAR_ATTRIBUTES);
        }
    }
}

