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
import com.intellij.ui.CheckedTreeNode;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class CodeCompletionFilterTreeModel implements TreeModel {
    private CodeCompletionFilterTreeNode root;

    public CodeCompletionFilterTreeModel(CodeCompletionFilterSettings setup) {
        root = (CodeCompletionFilterTreeNode) setup.createCheckedTreeNode();
    }

    public void applyChanges() {
        root.applyChanges();
    }

    public void resetChanges() {
        root.resetChanges();        
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object o, int i) {
        CheckedTreeNode node = (CheckedTreeNode) o;
        return node.getChildAt(i);
    }

    public int getChildCount(Object o) {
        CheckedTreeNode node = (CheckedTreeNode) o;
        return node.getChildCount();
    }

    public boolean isLeaf(Object o) {
        CheckedTreeNode node = (CheckedTreeNode) o;
        return node.isLeaf();
    }


    public int getIndexOfChild(Object o, Object o1) {
        CheckedTreeNode node = (CheckedTreeNode) o;
        return node.getIndex((TreeNode) o1);
    }

    public void addTreeModelListener(TreeModelListener treeModelListener) {}
    public void removeTreeModelListener(TreeModelListener treeModelListener) {}
    public void valueForPathChanged(TreePath treePath, Object o) {}

}
