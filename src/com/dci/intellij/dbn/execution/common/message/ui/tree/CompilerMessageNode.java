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

import com.dci.intellij.dbn.execution.compiler.CompilerMessage;
import com.dci.intellij.dbn.vfs.DatabaseContentFile;

import javax.swing.tree.TreeNode;
import java.util.Enumeration;

public class CompilerMessageNode implements MessagesTreeNode {
    private CompilerMessage compilerMessage;
    private CompilerMessagesObjectNode parent;

    public CompilerMessageNode(CompilerMessagesObjectNode parent, CompilerMessage compilerMessage) {
        this.parent = parent;
        this.compilerMessage = compilerMessage;
    }

    public CompilerMessage getCompilerMessage() {
        return compilerMessage;
    }

    public DatabaseContentFile getVirtualFile() {
        return compilerMessage.getContentFile();
    }

    public void dispose() {

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
}