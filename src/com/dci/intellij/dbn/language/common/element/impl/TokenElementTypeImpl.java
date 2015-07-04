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

package com.dci.intellij.dbn.language.common.element.impl;

import java.util.Set;

import org.jdom.Element;
import com.dci.intellij.dbn.code.common.completion.options.filter.CodeCompletionFilterSettings;
import com.dci.intellij.dbn.code.common.lookup.LookupValueProvider;
import com.dci.intellij.dbn.code.common.lookup.TokenLookupItemFactory;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.element.IterationElementType;
import com.dci.intellij.dbn.language.common.element.LeafElementType;
import com.dci.intellij.dbn.language.common.element.QualifiedIdentifierElementType;
import com.dci.intellij.dbn.language.common.element.TokenElementType;
import com.dci.intellij.dbn.language.common.element.WrapperElementType;
import com.dci.intellij.dbn.language.common.element.lookup.TokenElementTypeLookupCache;
import com.dci.intellij.dbn.language.common.element.parser.TokenElementTypeParser;
import com.dci.intellij.dbn.language.common.element.path.PathNode;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeDefinitionException;
import com.dci.intellij.dbn.language.common.psi.TokenPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

public class TokenElementTypeImpl extends LeafElementTypeImpl implements LookupValueProvider, TokenElementType {
    private TokenLookupItemFactory lookupItemFactory;

    public TokenElementTypeImpl(ElementTypeBundle bundle, ElementType parent, String id, Element def) throws ElementTypeDefinitionException {
        super(bundle, parent, id, def);
        String typeId = def.getAttributeValue("type-id");
        TokenType tokenType = bundle.getTokenTypeBundle().getTokenType(typeId);
        setTokenType(tokenType);
        setDescription(tokenType.getValue() + " " + tokenType.getTokenTypeIdentifier());

        setDefaultFormatting(tokenType.getFormatting());
    }

    public TokenElementTypeImpl(ElementTypeBundle bundle, ElementType parent, String typeId, String id) throws ElementTypeDefinitionException {
        super(bundle, parent, id, (String)null);
        TokenType tokenType = bundle.getTokenTypeBundle().getTokenType(typeId);
        setTokenType(tokenType);
        setDescription(tokenType.getValue() + " " + tokenType.getTokenTypeIdentifier());

        setDefaultFormatting(tokenType.getFormatting());
    }

    public TokenElementTypeLookupCache createLookupCache() {
        return new TokenElementTypeLookupCache(this);
    }

    public TokenElementTypeParser createParser() {
        return new TokenElementTypeParser(this);
    }

    public String getDebugName() {
        return "token (" + getId() + " - " + getTokenType().getId() + ")";
    }

    public Set<LeafElementType> getNextPossibleLeafs(PathNode pathNode, CodeCompletionFilterSettings filterSettings) {
        ElementType parent = getParent();
        if (isIterationSeparator()) {
            if (parent instanceof IterationElementType) {
                IterationElementType iterationElementType = (IterationElementType) parent;
                /*return codeCompletionSettings.isSmart() ?
                        iterationElementType.getIteratedElementType().getFirstPossibleLeafs() :
                        iterationElementType.getIteratedElementType().getFirstRequiredLeafs();*/
                return iterationElementType.getIteratedElementType().getLookupCache().getFirstPossibleLeafs();
            } else if (parent instanceof QualifiedIdentifierElementType){
                return super.getNextPossibleLeafs(pathNode, filterSettings);
            }
        }
        if (parent instanceof WrapperElementType) {
            WrapperElementType wrapperElementType = (WrapperElementType) parent;
            if (this.equals(wrapperElementType.getBeginTokenElement())) {
                return wrapperElementType.getWrappedElement().getLookupCache().getFirstPossibleLeafs();
            }
        }

        return super.getNextPossibleLeafs(pathNode, filterSettings);
    }

    public Set<LeafElementType> getNextRequiredLeafs(PathNode pathNode) {
        if (isIterationSeparator()) {
            if (getParent() instanceof IterationElementType) {
                IterationElementType iterationElementType = (IterationElementType) getParent();
                return iterationElementType.getIteratedElementType().getLookupCache().getFirstRequiredLeafs();
            } else if (getParent() instanceof QualifiedIdentifierElementType){
                return super.getNextRequiredLeafs(pathNode);
            }
        }
        return super.getNextRequiredLeafs(pathNode);
    }

    public boolean isIterationSeparator() {
        return getId().equals(SEPARATOR);
    }

    public boolean isLeaf() {
        return true;
    }

    public boolean isIdentifier() {
        return false;
    }

    public boolean isSameAs(LeafElementType elementType) {
        if (elementType instanceof TokenElementType) {
            TokenElementType token = (TokenElementType) elementType;
            return token.getTokenType() == getTokenType();
        }
        return false;
    }


    public PsiElement createPsiElement(ASTNode astNode) {
        return new TokenPsiElement(astNode, this);
    }

    public String toString() {
        return getTokenType().getId() + " (" + getId() + ")";
    }

    public boolean isCharacter() {
        return getTokenType().isCharacter();
    }

    public TokenLookupItemFactory getLookupItemFactory(SqlLikeLanguage language) {
        if (lookupItemFactory == null) {
            lookupItemFactory = new TokenLookupItemFactory(this);
        }
        return lookupItemFactory;
    }
}
