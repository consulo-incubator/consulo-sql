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

import com.dci.intellij.dbn.common.ui.tree.TreeEventType;
import com.dci.intellij.dbn.common.ui.tree.TreeUtil;
import com.dci.intellij.dbn.execution.statement.StatementExecutionMessage;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.tree.TreePath;

public class StatementExecutionMessagesFileNode extends BundleTreeNode {
    private VirtualFile virtualFile;

    public StatementExecutionMessagesFileNode(StatementExecutionMessagesNode parent, VirtualFile virtualFile) {
        super(parent);
        this.virtualFile = virtualFile;
    }

    public VirtualFile getVirtualFile() {
        return virtualFile;
    }

    public TreePath addExecutionMessage(StatementExecutionMessage executionMessage) {
        StatementExecutionMessageNode execMessageNode = new StatementExecutionMessageNode(this, executionMessage);
        children.add(execMessageNode);
        getTreeModel().notifyTreeModelListeners(this, TreeEventType.STRUCTURE_CHANGED);
        return TreeUtil.createTreePath(execMessageNode);
    }
}
