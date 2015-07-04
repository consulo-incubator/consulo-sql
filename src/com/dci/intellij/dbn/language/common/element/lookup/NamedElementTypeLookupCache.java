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

package com.dci.intellij.dbn.language.common.element.lookup;

import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.LeafElementType;
import com.dci.intellij.dbn.language.common.element.NamedElementType;
import com.dci.intellij.dbn.language.common.element.path.BasicPathNode;
import com.dci.intellij.dbn.language.common.element.path.PathNode;

import java.util.Set;

public class NamedElementTypeLookupCache extends SequenceElementTypeLookupCache<NamedElementType>{

    public NamedElementTypeLookupCache(NamedElementType elementType) {
        super(elementType);
    }

    protected void registerLeafInParent(LeafElementType leaf) {
        // walk the tree up for all potential parents
        NamedElementType elementType = getElementType();
        Set<ElementType> parents = elementType.getParents();
        if (parents != null) {
            for (ElementType parentElementType: parents) {
                parentElementType.getLookupCache().registerLeaf(leaf, elementType);
            }
        }
    }

    public boolean containsLandmarkToken(TokenType tokenType, PathNode node) {
        return !isRecursive(node) &&
                super.containsLandmarkToken(tokenType, createRecursionCheckPathNode(node));
    }

    public boolean startsWithIdentifier(PathNode node) {
        return !isRecursive(node) &&
                super.startsWithIdentifier(createRecursionCheckPathNode(node));
    }

    private boolean isRecursive(PathNode node) {
        return node != null && node.isRecursive();
    }

    private PathNode createRecursionCheckPathNode(PathNode parentPathNode) {
        return new BasicPathNode(getElementType(), parentPathNode, 0);
    }


}
