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

package com.dci.intellij.dbn.language.common.element;

import com.dci.intellij.dbn.language.common.TokenType;

import java.util.Set;

public interface SequenceElementType extends ElementType {
    ElementType[] getElementTypes();

    int elementsCount();

    boolean isOptionalFromIndex(int index);

    boolean isLast(int index);

    boolean isFirst(int index);

    boolean isOptional(int index);

    boolean isOptional(ElementType elementType);

    boolean canStartWithElement(ElementType elementType);

    boolean shouldStartWithElement(ElementType elementType);

    boolean isExitIndex(int index);

    boolean containsLandmarkTokenFromIndex(TokenType tokenType, int index);

    Set<TokenType> getFirstPossibleTokensFromIndex(int index);

    boolean isPossibleTokenFromIndex(TokenType tokenType, int index);

    int indexOf(ElementType elementType, int fromIndex);

    int indexOf(ElementType elementType);

    boolean[] getOptionalElementsMap();
}
