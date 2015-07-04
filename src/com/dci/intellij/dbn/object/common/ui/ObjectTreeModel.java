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

package com.dci.intellij.dbn.object.common.ui;

import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectType;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectTreeModel extends DefaultTreeModel {
    private TreePath initialSelection;
    private Object[] elements;

    public ObjectTreeModel(DBSchema schema, Set<DBObjectType> objectTypes, DBObject selectedObject) {
        super(new DefaultMutableTreeNode(schema == null ? "No schema selected" : schema));



        if (schema != null) {
            DefaultMutableTreeNode rootNode = getRoot();

            for (DBObjectType objectType : objectTypes) {
                for (DBObject schemaObject :schema.getChildObjects(objectType)) {
                    DefaultMutableTreeNode objectNode = new DefaultMutableTreeNode(schemaObject);
                    rootNode.add(objectNode);
                    if (selectedObject != null && selectedObject.equals(schemaObject)) {
                        initialSelection = new TreePath(objectNode.getPath());
                    }
                }
            }

            for (DBObjectType schemaObjectType : schema.getObjectType().getChildren()) {
                if (hasChild(schemaObjectType, objectTypes)) {
                    for (DBObject schemaObject : schema.getChildObjects(schemaObjectType)) {
                        DefaultMutableTreeNode bundleNode = new DefaultMutableTreeNode(schemaObject);

                        List<DBObject> objects = new ArrayList<DBObject>();
                        for (DBObjectType objectType : objectTypes) {
                            objects.addAll(schemaObject.getChildObjects(objectType));
                        }

                        if (objects.size() > 0) {
                            rootNode.add(bundleNode);
                            for (DBObject object : objects) {
                                DefaultMutableTreeNode objectNode = new DefaultMutableTreeNode(object);
                                bundleNode.add(objectNode);
                                if (selectedObject != null && selectedObject.equals(object)) {
                                    initialSelection = new TreePath(objectNode.getPath());
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public TreePath getInitialSelection() {
        return initialSelection;
    }

    private boolean hasChild(DBObjectType parentObjectType, Set<DBObjectType> objectTypes) {
        for (DBObjectType objectType : objectTypes) {
            if (parentObjectType.hasChild(objectType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public DefaultMutableTreeNode getRoot() {
        return (DefaultMutableTreeNode) super.getRoot();
    }

    public Object[] getAllElements() {
        if (elements == null) {
            List elementList = new ArrayList();
            collect(getRoot(), elementList);
            elements = elementList.toArray();
        }
        return elements;
    }

    private void collect(TreeNode node, List<TreeNode> bucket) {
        bucket.add(node);
        for (int i=0; i<node.getChildCount(); i++) {
            TreeNode childNode = node.getChildAt(i);
            collect(childNode, bucket);
        }
    }
}
