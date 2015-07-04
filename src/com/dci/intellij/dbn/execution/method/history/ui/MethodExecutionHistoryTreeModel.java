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

package com.dci.intellij.dbn.execution.method.history.ui;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.execution.method.MethodExecutionInput;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.List;

public abstract class MethodExecutionHistoryTreeModel extends DefaultTreeModel {
    protected List<MethodExecutionInput> executionInputs;

    public MethodExecutionHistoryTreeModel(List<MethodExecutionInput> executionInputs) {
        super(new DefaultMutableTreeNode());
        this.executionInputs = executionInputs;
        setRoot(new RootTreeNode());
    }

    public RootTreeNode getRoot() {
        return (RootTreeNode) super.getRoot();
    }

    public abstract List<MethodExecutionInput> getExecutionInputs();

    protected abstract String getMethodName(MethodExecutionInput executionInput);

    public abstract TreePath getTreePath(MethodExecutionInput executionInput);

    /**********************************************************
     *                        TreeNodes                       *
     **********************************************************/
    protected class RootTreeNode extends MethodExecutionHistoryTreeNode {
        RootTreeNode() {
            super(null, NODE_TYPE_ROOT, "ROOT");
        }

        ConnectionTreeNode getConnectionNode(MethodExecutionInput executionInput) {
            if (!isLeaf())
                for (TreeNode node : getChildren()) {
                    ConnectionTreeNode connectionNode = (ConnectionTreeNode) node;
                    if (connectionNode.getConnectionHandler().getId().equals(executionInput.getMethodIdentifier().getConnectionId())) {
                        return connectionNode;
                    }
                }

            return new ConnectionTreeNode(this, executionInput);
        }
    }

    protected class ConnectionTreeNode extends MethodExecutionHistoryTreeNode {
        ConnectionHandler connectionHandler;
        ConnectionTreeNode(MethodExecutionHistoryTreeNode parent, MethodExecutionInput executionInput) {
            super(parent, NODE_TYPE_CONNECTION, null);
            this.connectionHandler = executionInput.getConnectionHandler();
        }

        ConnectionHandler getConnectionHandler() {
            return connectionHandler;
        }

        @Override
        public String getName() {
            return connectionHandler.getName();
        }

        SchemaTreeNode getSchemaNode(MethodExecutionInput executionInput) {
            if (!isLeaf())
                for (TreeNode node : getChildren()) {
                    SchemaTreeNode schemaNode = (SchemaTreeNode) node;
                    if (schemaNode.getName().equalsIgnoreCase(executionInput.getMethodIdentifier().getSchemaName())) {
                        return schemaNode;
                    }
                }
            return new SchemaTreeNode(this, executionInput);
        }
    }

    protected class SchemaTreeNode extends MethodExecutionHistoryTreeNode {
        SchemaTreeNode(MethodExecutionHistoryTreeNode parent, MethodExecutionInput executionInput) {
            super(parent, NODE_TYPE_SCHEMA, executionInput.getMethodIdentifier().getSchemaName());
        }

        ProgramTreeNode getProgramNode(MethodExecutionInput executionInput) {
            String programName = executionInput.getMethodIdentifier().getProgramName();
            if (!isLeaf())
                for (TreeNode node : getChildren()) {
                    if (node instanceof ProgramTreeNode) {
                        ProgramTreeNode programNode = (ProgramTreeNode) node;
                        if (programNode.getName().equalsIgnoreCase(programName)) {
                            return programNode;
                        }
                    }
                }
            return new ProgramTreeNode(this, executionInput);
        }

        MethodTreeNode getMethodNode(MethodExecutionInput executionInput) {
            if (!isLeaf())
                for (TreeNode node : getChildren()) {
                    if (node instanceof MethodTreeNode) {
                        MethodTreeNode methodNode = (MethodTreeNode) node;
                        if (methodNode.getExecutionInput() == executionInput) {
                            return methodNode;
                        }
                    }
                }
            return new MethodTreeNode(this, executionInput);
        }

    }

    protected class ProgramTreeNode extends MethodExecutionHistoryTreeNode {
        ProgramTreeNode(MethodExecutionHistoryTreeNode parent, MethodExecutionInput executionInput) {
            super(parent,
                    getNodeType(executionInput.getMethodIdentifier().getProgramObjectType()),
                    executionInput.getMethodIdentifier().getProgramName());
        }

        MethodTreeNode getMethodNode(MethodExecutionInput executionInput) {
            String methodName = executionInput.getMethodIdentifier().getMethodName();
            if (!isLeaf())
                for (TreeNode node : getChildren()) {
                    MethodTreeNode methodNode = (MethodTreeNode) node;
                    if (methodNode.getName().equalsIgnoreCase(methodName)) {
                        return methodNode;
                    }
                }
            return new MethodTreeNode(this, executionInput);
        }
    }

    protected class MethodTreeNode extends MethodExecutionHistoryTreeNode {
        private MethodExecutionInput executionInput;

        MethodTreeNode(MethodExecutionHistoryTreeNode parent, MethodExecutionInput executionInput) {
            super(parent,
                    getNodeType(executionInput.getMethodIdentifier().getMethodObjectType()),
                    getMethodName(executionInput));
            this.executionInput = executionInput;
        }

        int getOverload() {
            return executionInput.getMethodIdentifier().getOverload();
        }

        MethodExecutionInput getExecutionInput() {
            return executionInput;
        }

        @Override
        public boolean isValid() {
            return !executionInput.isObsolete();
        }
    }
}
