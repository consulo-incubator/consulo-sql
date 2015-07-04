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

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.object.common.DBObjectType;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class MethodExecutionHistoryTreeNode extends DefaultMutableTreeNode {
    public static final int NODE_TYPE_ROOT = 0;
    public static final int NODE_TYPE_CONNECTION = 1;
    public static final int NODE_TYPE_SCHEMA = 2;
    public static final int NODE_TYPE_PACKAGE = 3;
    public static final int NODE_TYPE_TYPE = 4;
    public static final int NODE_TYPE_PROCEDURE = 5;
    public static final int NODE_TYPE_FUNCTION = 6;
    private String name;
    private int type;

    public MethodExecutionHistoryTreeNode(MethodExecutionHistoryTreeNode parent, int type, String name) {
        this.name = name;
        this.type = type;
        if (parent != null) {
            parent.add(this);
        }
    }
    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public Icon getIcon() {
        return
            type == NODE_TYPE_CONNECTION? Icons.CONNECTION_ACTIVE :
            type == NODE_TYPE_SCHEMA ? Icons.DBO_SCHEMA :
            type == NODE_TYPE_PACKAGE ? Icons.DBO_PACKAGE :
            type == NODE_TYPE_TYPE ? Icons.DBO_TYPE :
            type == NODE_TYPE_PROCEDURE ? Icons.DBO_PROCEDURE :
            type == NODE_TYPE_FUNCTION ? Icons.DBO_FUNCTION : null;
    }

    public static int getNodeType(DBObjectType objectType) {
        return
            objectType == DBObjectType.SCHEMA ? NODE_TYPE_SCHEMA :
            objectType == DBObjectType.PACKAGE ? NODE_TYPE_PACKAGE :
            objectType == DBObjectType.TYPE ? NODE_TYPE_TYPE :
            objectType == DBObjectType.PROCEDURE ||
                    objectType == DBObjectType.PACKAGE_PROCEDURE ||
                    objectType == DBObjectType.TYPE_PROCEDURE ? NODE_TYPE_PROCEDURE :
            objectType == DBObjectType.FUNCTION ||
                    objectType == DBObjectType.PACKAGE_FUNCTION ||
                    objectType == DBObjectType.TYPE_FUNCTION ? NODE_TYPE_FUNCTION : -1;
    }

    public List<MethodExecutionHistoryTreeNode> getChildren() {
        return children;
    }

    public boolean getAllowsChildren() {
        return
            type != NODE_TYPE_PROCEDURE &&
            type != NODE_TYPE_FUNCTION;
    }

    public boolean isValid() {
        return true;
    }
}
