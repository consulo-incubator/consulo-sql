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

package com.dci.intellij.dbn.execution.common.message.ui.tree;

import javax.swing.tree.TreeNode;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Collections;

public abstract class BundleTreeNode implements MessagesTreeNode{
    protected MessagesTreeNode parent;
    protected List<MessagesTreeNode> children = new ArrayList<MessagesTreeNode>();

    protected BundleTreeNode(MessagesTreeNode parent) {
        this.parent = parent;
    }

    public void dispose() {
        for (MessagesTreeNode treeNode : children) {
            treeNode.dispose();
        }
    }

    public MessagesTreeModel getTreeModel() {
        return parent.getTreeModel();
    }

    /*********************************************************
     *                        TreeNode                       *
     *********************************************************/
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    public int getChildCount() {
        return children.size();
    }

    public TreeNode getParent() {
        return parent;
    }

    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    public Enumeration children() {
        return Collections.enumeration(children);
    }
}
