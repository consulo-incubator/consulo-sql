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

import com.dci.intellij.dbn.language.common.SharedTokenTypeBundle;
import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.IdentifierElementType;
import com.dci.intellij.dbn.language.common.element.LeafElementType;
import com.dci.intellij.dbn.language.common.element.path.PathNode;

public class IdentifierElementTypeLookupCache extends LeafElementTypeLookupCache<IdentifierElementType>{
    public IdentifierElementTypeLookupCache(IdentifierElementType elementType) {
        super(elementType);
    }

    public void init() {
        SharedTokenTypeBundle sharedTokenTypes = getElementType().getLanguage().getSharedTokenTypes();
        TokenType identifier = sharedTokenTypes.getIdentifier();
        TokenType quotedIdentifier = sharedTokenTypes.getQuotedIdentifier();
        allPossibleTokens.add(identifier);
        allPossibleTokens.add(quotedIdentifier);
        firstPossibleTokens.add(identifier);
        firstPossibleTokens.add(quotedIdentifier);
        firstRequiredTokens.add(identifier);
        firstRequiredTokens.add(quotedIdentifier);
    }

    @Override
    public boolean containsToken(TokenType tokenType) {
        SharedTokenTypeBundle sharedTokenTypes = getElementType().getLanguage().getSharedTokenTypes();
        return sharedTokenTypes.getIdentifier() == tokenType || sharedTokenTypes.getQuotedIdentifier() == tokenType;
    }

    public boolean isFirstPossibleLeaf(LeafElementType leaf, ElementType pathChild) {
        return false;
    }

    public boolean isFirstRequiredLeaf(LeafElementType leaf, ElementType pathChild) {
        return false;
    }

    public boolean containsLandmarkToken(TokenType tokenType, PathNode node) {
        return false;
    }

    public boolean startsWithIdentifier(PathNode node) {
        return true;
    }    
}