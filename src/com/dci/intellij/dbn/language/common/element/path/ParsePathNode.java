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

public class ParsePathNode extends BasicPathNode {
    private int startOffset;

    public ParsePathNode(ElementType elementType, ParsePathNode parent, int startOffset, int indexInParent) {
        super(elementType, parent, indexInParent);
        this.startOffset = startOffset;
    }

    public ParsePathNode createVariant(int builderOffset, int position) {
        ParsePathNode variant = new ParsePathNode(getElementType(), getParent(), builderOffset, position);
        detach();
        return variant;
    }

    public ParsePathNode getParent() {
        return (ParsePathNode) super.getParent();
    }

    public int getStartOffset() {
        return startOffset;
    }

    public boolean isRecursive() {
        ParsePathNode parseNode = this.getParent();
        while (parseNode != null) {
            if (parseNode.getElementType() == getElementType() &&
                parseNode.getStartOffset() == getStartOffset()) {
                return true;
            }
            parseNode = parseNode.getParent();
        }
        return false;
    }
}

