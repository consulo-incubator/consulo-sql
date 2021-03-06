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

package com.dci.intellij.dbn.language.common.element.path;

import com.dci.intellij.dbn.language.common.element.ElementType;

public class BasicPathNode implements PathNode {
    private int indexInParent;
    private PathNode parent;
    private ElementType elementType;

    public BasicPathNode(ElementType elementType, PathNode parent, int indexInParent) {
        this.elementType = elementType;
        this.parent = parent;
        this.indexInParent = indexInParent;
    }

    public PathNode getParent() {
        return parent;
    }

    public void setParent(PathNode parent) {
        this.parent = parent;
    }

    public int getIndexInParent() {
        return indexInParent;
    }

    public void setIndexInParent(int indexInParent) {
        this.indexInParent = indexInParent;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public PathNode getRootPathNode() {
        PathNode pathNode = parent;
        while (pathNode != null) {
            PathNode parentPathNode = pathNode.getParent();
            if (parentPathNode == null) {
                return pathNode;
            }
            pathNode = parentPathNode;
        }
        return this;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public boolean isRecursive() {
        PathNode node = this.getParent();
        while (node != null) {
            if (node.getElementType() == getElementType()) {
                return true;
            }
            node = node.getParent();
        }
        return false;
    }

    public boolean isRecursive(ElementType elementType) {
        if (getElementType() == elementType) {
            return true;
        }
        PathNode node = this.getParent();
        while (node != null) {
            if (node.getElementType() == elementType) {
                return true;
            }
            node = node.getParent();
        }
        return false;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        PathNode parent = this;
        while (parent != null) {
            buffer.insert(0, '/');
            buffer.insert(0, parent.getElementType().getId());
            parent = parent.getParent();
        }
        return buffer.toString();
    }

    public void detach() {
        parent = null;
    }
}
