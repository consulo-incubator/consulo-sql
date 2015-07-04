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

package com.dci.intellij.dbn.object.common.ui;

import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.ui.SpeedSearchBase;
import com.intellij.util.ui.tree.TreeUtil;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class ObjectTreeSpeedSearch extends SpeedSearchBase {

    public ObjectTreeSpeedSearch(ObjectTree objectTree) {
        super(objectTree);
    }

    @Override
    public ObjectTree getComponent() {
        return (ObjectTree) super.getComponent();
    }

    @Override
    protected int getSelectedIndex() {
        TreePath selectionPath = getComponent().getSelectionPath();
        if (selectionPath != null) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
            for (int i=0; i<getAllElements().length; i++) {
                if (getAllElements()[i] == node) {
                    return i;
                }
            }
        }
        return 0;
    }

    @Override
    protected Object[] getAllElements() {
        return getComponent().getModel().getAllElements();
    }

    @Override
    protected String getElementText(Object obj) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
        if (node.getUserObject() instanceof DBObject) {
            DBObject object = (DBObject) node.getUserObject();
            return object.getName();
        }
        return node.getUserObject().toString();
    }

    @Override
    protected void selectElement(Object obj, String s) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) obj;
        TreeUtil.selectPath(getComponent(), new TreePath(node.getPath()), true);
    }
}
