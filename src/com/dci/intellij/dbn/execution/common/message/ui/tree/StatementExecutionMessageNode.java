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

import com.dci.intellij.dbn.execution.statement.StatementExecutionMessage;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class StatementExecutionMessageNode implements MessagesTreeNode {
    private StatementExecutionMessage executionMessage;
    private StatementExecutionMessagesFileNode parent;

    public StatementExecutionMessageNode(StatementExecutionMessagesFileNode parent, StatementExecutionMessage executionMessage) {
        this.parent = parent;
        this.executionMessage = executionMessage;
    }

    public StatementExecutionMessage getExecutionMessage() {
        return executionMessage;
    }

    public VirtualFile getVirtualFile() {
        return parent.getVirtualFile();
    }

    public void dispose() {
        executionMessage.dispose();
    }

    public MessagesTreeModel getTreeModel() {
        return parent.getTreeModel();
    }

    /*********************************************************
     *                        TreeNode                       *
     *********************************************************/
    public TreeNode getChildAt(int childIndex) {
        return null;
    }

    public int getChildCount() {
        return 0;
    }

    public TreeNode getParent() {
        return parent;
    }

    public int getIndex(TreeNode node) {
        return -1;
    }

    public boolean getAllowsChildren() {
        return false;
    }

    public boolean isLeaf() {
        return true;
    }

    public Enumeration children() {
        return null;
    }

    @Override
    public String toString() {
        return
            executionMessage.getText() + " " +
            executionMessage.getCauseMessage() + " - Connection: " +
            executionMessage.getExecutionResult().getConnectionHandler().getName() + ": " +
            executionMessage.getExecutionResult().getExecutionDuration() + "ms";
    }
}
