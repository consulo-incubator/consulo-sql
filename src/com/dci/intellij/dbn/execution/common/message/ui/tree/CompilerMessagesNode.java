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
import com.dci.intellij.dbn.execution.compiler.CompilerMessage;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.tree.TreePath;

public class CompilerMessagesNode extends BundleTreeNode {
    public CompilerMessagesNode(RootNode parent) {
        super(parent);
    }

    public MessagesTreeNode getChildTreeNode(VirtualFile virtualFile) {
        for (MessagesTreeNode messagesTreeNode : children) {
            if (messagesTreeNode.getVirtualFile().equals(virtualFile)) {
                return messagesTreeNode;
            }
        }
        return null;
    }

    public TreePath addCompilerMessage(CompilerMessage compilerMessage) {
        CompilerMessagesObjectNode objectNode = (CompilerMessagesObjectNode)
                getChildTreeNode(compilerMessage.getDatabaseFile());
        if (objectNode == null) {
            objectNode = new CompilerMessagesObjectNode(this, compilerMessage.getDatabaseFile());
            children.add(objectNode);
            getTreeModel().notifyTreeModelListeners(this, TreeEventType.STRUCTURE_CHANGED);
        }
        return objectNode.addCompilerMessage(compilerMessage);
    }

    public TreePath getTreePath(CompilerMessage compilerMessage) {
        CompilerMessagesObjectNode objectNode = (CompilerMessagesObjectNode)
                getChildTreeNode(compilerMessage.getDatabaseFile());
        return objectNode.getTreePath(compilerMessage);
    }

    public VirtualFile getVirtualFile() {
        return null;
    }
}