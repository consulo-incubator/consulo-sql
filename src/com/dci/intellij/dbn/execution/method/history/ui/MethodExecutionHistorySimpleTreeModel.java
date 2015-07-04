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

import com.dci.intellij.dbn.execution.method.MethodExecutionInput;
import com.dci.intellij.dbn.object.identifier.DBMethodIdentifier;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;

public class MethodExecutionHistorySimpleTreeModel extends MethodExecutionHistoryTreeModel {
    public MethodExecutionHistorySimpleTreeModel(List<MethodExecutionInput> executionInputs) {
        super(executionInputs);
        for (MethodExecutionInput executionInput : executionInputs) {
            RootTreeNode rootNode = getRoot();

            ConnectionTreeNode connectionNode = rootNode.getConnectionNode(executionInput);
            SchemaTreeNode schemaNode = connectionNode.getSchemaNode(executionInput);
            schemaNode.getMethodNode(executionInput);
        }
    }

    public List<MethodExecutionInput> getExecutionInputs() {
        List<MethodExecutionInput> executionInputs = new ArrayList<MethodExecutionInput>();
        for (TreeNode connectionTreeNode : getRoot().getChildren()) {
            ConnectionTreeNode connectionNode = (ConnectionTreeNode) connectionTreeNode;
            for (TreeNode schemaTreeNode : connectionNode.getChildren()) {
                SchemaTreeNode schemaNode = (SchemaTreeNode) schemaTreeNode;
                for (TreeNode node : schemaNode.getChildren()) {
                    MethodTreeNode methodNode = (MethodTreeNode) node;
                    MethodExecutionInput executionInput =
                            getExecutionInput(connectionNode, schemaNode, methodNode);

                    if (executionInput != null) {
                        executionInputs.add(executionInput);
                    }
                }
            }
        }
        return executionInputs;
    }

    private MethodExecutionInput getExecutionInput(
            ConnectionTreeNode connectionNode,
            SchemaTreeNode schemaNode,
            MethodTreeNode methodNode) {
        for (MethodExecutionInput executionInput : executionInputs) {
            DBMethodIdentifier methodIdentifier = executionInput.getMethodIdentifier();
            if (executionInput.getConnectionHandler().getId().equals(connectionNode.getConnectionHandler().getId()) &&
                methodIdentifier.getSchemaName().equalsIgnoreCase(schemaNode.getName()) &&
                methodIdentifier.getQualifiedMethodName().equalsIgnoreCase(methodNode.getName()) &&
                methodIdentifier.getOverload() == methodNode.getOverload() ) {

                return executionInput;
            }
        }
        return null;
    }

    protected String getMethodName(MethodExecutionInput executionInput) {
        return executionInput.getMethodIdentifier().getQualifiedMethodName();
    }

    @Override
    public TreePath getTreePath(MethodExecutionInput executionInput) {
        List<MethodExecutionHistoryTreeNode> path = new ArrayList<MethodExecutionHistoryTreeNode>();
        MethodExecutionHistoryTreeModel.RootTreeNode rootTreeNode = getRoot();
        path.add(rootTreeNode);
        ConnectionTreeNode connectionTreeNode = rootTreeNode.getConnectionNode(executionInput);
        path.add(connectionTreeNode);
        SchemaTreeNode schemaTreeNode = connectionTreeNode.getSchemaNode(executionInput);
        path.add(schemaTreeNode);
        MethodTreeNode methodTreeNode = schemaTreeNode.getMethodNode(executionInput);
        path.add(methodTreeNode);
        
        return new TreePath(path.toArray());
    }
}