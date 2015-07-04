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
import com.dci.intellij.dbn.language.common.element.WrapperElementType;
import com.dci.intellij.dbn.language.common.element.path.PathNode;

public class WrapperElementTypeLookupCache extends AbstractElementTypeLookupCache<WrapperElementType> {

    public WrapperElementTypeLookupCache(WrapperElementType elementType) {
        super(elementType);
    }

    public boolean isFirstPossibleLeaf(LeafElementType leaf, ElementType pathChild) {
        ElementTypeLookupCache startTokenLC = getElementType().getBeginTokenElement().getLookupCache();
        ElementTypeLookupCache wrappedTokenLC = getElementType().getWrappedElement().getLookupCache();
        return startTokenLC.canStartWithLeaf(leaf) ||
               (getElementType().isWrappingOptional() && wrappedTokenLC.canStartWithLeaf(leaf));
    }

    public boolean isFirstRequiredLeaf(LeafElementType leaf, ElementType pathChild) {
        ElementTypeLookupCache startTokenLC = getElementType().getBeginTokenElement().getLookupCache();
        ElementTypeLookupCache wrappedTokenLC = getElementType().getWrappedElement().getLookupCache();

        return (!getElementType().isWrappingOptional() && startTokenLC.shouldStartWithLeaf(leaf)) ||
               (getElementType().isWrappingOptional() && wrappedTokenLC.shouldStartWithLeaf(leaf));
    }

    public boolean containsLandmarkToken(TokenType tokenType, PathNode node) {
        return
            getElementType().getBeginTokenElement().getLookupCache().containsLandmarkToken(tokenType) ||
            getElementType().getEndTokenElement().getLookupCache().containsLandmarkToken(tokenType) ||
            getElementType().getWrappedElement().getLookupCache().containsLandmarkToken(tokenType, node);
    }

    public boolean startsWithIdentifier(PathNode node) {
        return getElementType().isWrappingOptional() &&
                getElementType().getWrappedElement().getLookupCache().startsWithIdentifier(node);
    }

}