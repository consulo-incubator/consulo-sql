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
import com.dci.intellij.dbn.language.common.element.path.PathNode;
import com.dci.intellij.dbn.language.common.element.util.IdentifierRole;
import com.dci.intellij.dbn.language.common.element.util.IdentifierType;
import com.dci.intellij.dbn.object.common.DBObjectType;

import java.util.Set;

public interface ElementTypeLookupCache<T extends ElementType> {
    void registerLeaf(LeafElementType leaf, ElementType pathChild);

    void registerVirtualObject(DBObjectType objectType);

    boolean containsLeaf(LeafElementType leafElementType);

    boolean containsToken(TokenType tokenType);

    boolean containsIdentifier(DBObjectType objectType, IdentifierType identifierType, IdentifierRole identifierRole);

    boolean containsIdentifier(DBObjectType objectType, IdentifierType identifierType);

    boolean containsVirtualObject(DBObjectType objectType);

    T getElementType();


    Set<LeafElementType> getFirstPossibleLeafs();

    Set<TokenType> getFirstPossibleTokens();

    boolean canStartWithLeaf(LeafElementType leafElementType);

    boolean shouldStartWithLeaf(LeafElementType leafElementType);

    boolean canStartWithToken(TokenType tokenType);

    boolean shouldStartWithToken(TokenType tokenType);

    Set<LeafElementType> getFirstRequiredLeafs();

    Set<TokenType> getFirstRequiredTokens();

    boolean containsLandmarkToken(TokenType tokenType, PathNode node);
    boolean containsLandmarkToken(TokenType tokenType);

    boolean startsWithIdentifier(PathNode node);    
    boolean startsWithIdentifier();

    boolean containsIdentifiers();

    Set<TokenType> getNextPossibleTokens();

    Set<TokenType> getNextRequiredTokens();

    void init();

    boolean isFirstPossibleLeaf(LeafElementType leaf, ElementType pathChild);

    boolean isFirstRequiredLeaf(LeafElementType leaf, ElementType pathChild);
}
