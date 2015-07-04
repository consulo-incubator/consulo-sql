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

package com.dci.intellij.dbn.execution.method.result.ui;

import com.dci.intellij.dbn.execution.method.ArgumentValue;
import com.dci.intellij.dbn.object.common.DBObject;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class ArgumentValuesTreeNode implements TreeNode{
    private Object userValue;
    private ArgumentValuesTreeNode parent;
    private List<ArgumentValuesTreeNode> children = new ArrayList<ArgumentValuesTreeNode>();

    protected ArgumentValuesTreeNode(ArgumentValuesTreeNode parent, Object userValue) {
        this.parent = parent;
        if (parent != null) {
            parent.getChildren().add(this);
        }
        this.userValue = userValue;
    }

    public Object getUserValue() {
        return userValue;
    }

    public List<ArgumentValuesTreeNode> getChildren() {
        return children;
    }

    public void dispose() {
        for (ArgumentValuesTreeNode treeNode : children) {
            treeNode.dispose();
        }
    }

    @Override
    public String toString() {
        if (userValue instanceof ArgumentValue) {
            ArgumentValue argumentValue = (ArgumentValue) userValue;
            return "" + argumentValue.getValue();
        }

        if (userValue instanceof DBObject) {
            DBObject object = (DBObject) userValue;
            return object.getName();
        }

        return userValue.toString();
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
