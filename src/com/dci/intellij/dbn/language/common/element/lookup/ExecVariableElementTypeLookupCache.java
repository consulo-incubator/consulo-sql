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
import com.dci.intellij.dbn.language.common.element.ExecVariableElementType;
import com.dci.intellij.dbn.language.common.element.LeafElementType;
import com.dci.intellij.dbn.language.common.element.path.PathNode;

public class ExecVariableElementTypeLookupCache extends LeafElementTypeLookupCache<ExecVariableElementType>{
    public ExecVariableElementTypeLookupCache(ExecVariableElementType elementType) {
        super(elementType);
    }

    public void init() {
        SharedTokenTypeBundle sharedTokenTypes = getElementType().getLanguage().getSharedTokenTypes();
        TokenType variable = sharedTokenTypes.getVariable();
        allPossibleTokens.add(variable);
        firstPossibleTokens.add(variable);
        firstRequiredTokens.add(variable);
    }

    @Override
    public boolean containsToken(TokenType tokenType) {
        SharedTokenTypeBundle sharedTokenTypes = getElementType().getLanguage().getSharedTokenTypes();
        return sharedTokenTypes.getVariable() == tokenType;
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
        return false;
    }    
}
