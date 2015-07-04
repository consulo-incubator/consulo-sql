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

package com.dci.intellij.dbn.browser.ui;

import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.connection.ModuleConnectionBundle;
import com.dci.intellij.dbn.connection.ProjectConnectionBundle;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;

import javax.swing.JList;

public class ModuleListCellRenderer extends ColoredListCellRenderer {

    protected void customizeCellRenderer(JList list, Object value, int index, boolean selected, boolean hasFocus) {
        BrowserTreeNode treeNode = (BrowserTreeNode) value;
        setIcon(treeNode.getIcon(0));

        String displayName;
        if (treeNode instanceof ModuleConnectionBundle) {
            ModuleConnectionBundle connectionManager = (ModuleConnectionBundle) treeNode;
            displayName = connectionManager.getModule().getName();
        } else if (treeNode instanceof ProjectConnectionBundle) {
            displayName = "PROJECT";
        } else {
            displayName = treeNode.getPresentableText();
        }

        append(displayName, SimpleTextAttributes.REGULAR_ATTRIBUTES);
    }
}
