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

import com.dci.intellij.dbn.language.common.element.LeafElementType;

public abstract class LeafElementTypeLookupCache<T extends LeafElementType> extends AbstractElementTypeLookupCache<T> {
    public LeafElementTypeLookupCache(T leafElementType) {
        super(leafElementType);
        T elementType = getElementType();
        allPossibleLeafs.add(elementType);
        firstPossibleLeafs.add(elementType);
        firstRequiredLeafs.add(elementType);
    }

    @Override
    public boolean containsLeaf(LeafElementType leafElementType) {
        return getElementType() == leafElementType;
    }

    @Override
    public boolean canStartWithLeaf(LeafElementType leafElementType) {
        return getElementType() == leafElementType;
    }
}