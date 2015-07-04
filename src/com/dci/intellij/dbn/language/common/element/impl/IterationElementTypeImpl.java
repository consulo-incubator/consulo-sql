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

import com.dci.intellij.dbn.code.common.style.formatting.FormattingDefinition;
import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.element.IterationElementType;
import com.dci.intellij.dbn.language.common.element.TokenElementType;
import com.dci.intellij.dbn.language.common.element.lookup.IterationElementTypeLookupCache;
import com.dci.intellij.dbn.language.common.element.parser.IterationElementTypeParser;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeDefinitionException;
import com.dci.intellij.dbn.language.common.psi.SequencePsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class IterationElementTypeImpl extends AbstractElementType implements IterationElementType {

    protected ElementType iteratedElementType;
    protected TokenElementType[] separatorTokens;
    private int[] elementsCountVariants;

    public IterationElementTypeImpl(ElementTypeBundle bundle, ElementType parent, String id, Element def) throws ElementTypeDefinitionException {
        super(bundle, parent, id, def);
    }

    @Override
    protected IterationElementTypeLookupCache createLookupCache() {
        return new IterationElementTypeLookupCache(this);
    }

    @Override
    protected IterationElementTypeParser createParser() {
        return new IterationElementTypeParser(this);
    }

    protected void loadDefinition(Element def) throws ElementTypeDefinitionException {
        super.loadDefinition(def);
        ElementTypeBundle bundle = getElementBundle();
        String separatorTokenIds = def.getAttributeValue("separator");
        if (separatorTokenIds != null) {
            StringTokenizer tokenizer = new StringTokenizer(separatorTokenIds, ",");
            List<TokenElementType> separators = new ArrayList<TokenElementType>();
            while (tokenizer.hasMoreTokens()) {
                String separatorTokenId = tokenizer.nextToken().trim();
                TokenElementType separatorToken = new TokenElementTypeImpl(bundle, this, separatorTokenId, TokenElementType.SEPARATOR);
                        //bundle.getTokenElementType(separatorTokenId);
                separatorToken.setDefaultFormatting(separatorToken.isCharacter() ?
                        FormattingDefinition.NO_SPACE_BEFORE :
                        FormattingDefinition.ONE_SPACE_BEFORE);
                separators.add(separatorToken);
            }
            separatorTokens = separators.toArray(new TokenElementType[separators.size()]);
        }

        List children = def.getChildren();
        if (children.size() != 1) {
            throw new ElementTypeDefinitionException("Invalid iteration definition. Element should contain exactly one child.");
        }
        Element child = (Element) children.get(0);
        String type = child.getName();
        iteratedElementType = bundle.resolveElementDefinition(child, type, this);

        String elementsCountDef = def.getAttributeValue("elements-count");
        if (elementsCountDef != null) {
            List<Integer> variants = new ArrayList<Integer>();
            StringTokenizer tokenizer = new StringTokenizer(elementsCountDef, ",");
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                int index = token.indexOf('-');
                if (index > -1) {
                    int start = Integer.parseInt(token.substring(0, index).trim());
                    int end  = Integer.parseInt(token.substring(index + 1).trim());
                    for (int i=start; i<=end; i++) {
                        variants.add(i);
                    }
                } else {
                    variants.add(Integer.parseInt(token.trim()));
                }
            }

            elementsCountVariants = new int[variants.size()];
            for (int i=0; i< elementsCountVariants.length; i++) {
                elementsCountVariants[i] = variants.get(i);
            }
        }
    }

    public boolean isLeaf() {
        return false;
    }

    public String getDebugName() {
        return "iteration (" + getId() + ")";
    }

    public PsiElement createPsiElement(ASTNode astNode) {
        return new SequencePsiElement(astNode, this);
    }

    public ElementType getIteratedElementType() {
        return iteratedElementType;
    }

    public TokenElementType[] getSeparatorTokens() {
        return separatorTokens;
    }

    public int[] getElementsCountVariants() {
        return elementsCountVariants;
    }

    public boolean isSeparator(TokenElementType tokenElementType) {
        if (separatorTokens != null) {
            for (TokenElementType separatorToken: separatorTokens) {
                if (separatorToken == tokenElementType) return true;
            }
        }
        return false;
    }

    public boolean isSeparator(TokenType tokenType) {
        if (separatorTokens != null) {
            for (TokenElementType separatorToken: separatorTokens) {
                if (separatorToken.getTokenType() == tokenType) return true;
            }
        }
        return false;
    }
}
