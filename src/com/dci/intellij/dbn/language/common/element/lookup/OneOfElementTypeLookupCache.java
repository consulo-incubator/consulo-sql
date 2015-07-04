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
import com.dci.intellij.dbn.language.common.element.OneOfElementType;
import com.dci.intellij.dbn.language.common.element.path.PathNode;

public class OneOfElementTypeLookupCache extends AbstractElementTypeLookupCache<OneOfElementType> {
    public OneOfElementTypeLookupCache(OneOfElementType elementType) {
        super(elementType);
    }

    public boolean isFirstPossibleLeaf(LeafElementType leaf, ElementType pathChild) {
        return pathChild.getLookupCache().canStartWithLeaf(leaf) && !canStartWithLeaf(leaf);
    }

    public boolean isFirstRequiredLeaf(LeafElementType leaf, ElementType pathChild) {
        return pathChild.getLookupCache().shouldStartWithLeaf(leaf) && !shouldStartWithLeaf(leaf);
    }

    public boolean containsLandmarkToken(TokenType tokenType, PathNode node) {
        for (ElementType elementType : getElementType().getPossibleElementTypes()) {
            if (elementType.getLookupCache().containsLandmarkToken(tokenType, node)) return true;
        }
        return false;
    }

    public boolean startsWithIdentifier(PathNode node) {
        for(ElementType elementType : getElementType().getPossibleElementTypes()){
            if (elementType.getLookupCache().startsWithIdentifier(node)) return true;
        }
        return false;
    }
}