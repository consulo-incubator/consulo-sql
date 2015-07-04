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

import com.dci.intellij.dbn.language.common.element.IdentifierElementType;
import com.dci.intellij.dbn.language.common.element.LeafElementType;
import com.dci.intellij.dbn.language.common.element.TokenElementType;
import com.dci.intellij.dbn.language.common.psi.LeafPsiElement;
import com.dci.intellij.dbn.language.common.psi.QualifiedIdentifierPsiElement;
import com.intellij.psi.PsiElement;

public class QualifiedIdentifierVariant implements Comparable{
    private LeafElementType[] leafs;
    /**@deprecated*/
    private boolean incomplete;
    private int matchedTokens;

    public QualifiedIdentifierVariant(LeafElementType[] leafs, boolean partial) {
        this.leafs = leafs;
        this.incomplete = partial;
    }

    public QualifiedIdentifierVariant(LeafElementType[] leafs, int matchedTokens) {
        this.leafs = leafs;
        this.matchedTokens = matchedTokens;
        this.incomplete = matchedTokens != leafs.length;
    }


    public LeafElementType[] getLeafs() {
        return leafs;
    }

    public LeafElementType getLeaf(int index) {
        if (index > -1 && index < leafs.length) {
            return leafs[index];
        }
        return null; 
    }

    public int getIndexOf(LeafElementType leafElementType) {
        int index = 0;
        for (LeafElementType leaf : leafs) {
            if (leaf == leafElementType) {
                return index;
            } else {
                index++;
            }
        }
        return -1;
    }

    public int length() {
        return leafs.length;
    }

    public int getMatchedTokens() {
        return matchedTokens;
    }

    public boolean isIncomplete() {
        return incomplete;
    }

    public boolean containsNonIdentifierTokens() {
        for (int i=0; i<matchedTokens; i++) {
            if (!leafs[i].getTokenType().isIdentifier()) {
                return true;
            }
        }
        return false;
    }

    public int compareTo(Object o) {
        QualifiedIdentifierVariant variant = (QualifiedIdentifierVariant) o;
        if (variant.isIncomplete() != this.isIncomplete()) {
            return variant.isIncomplete() ? -1 : 1;
        }

        if (variant.leafs.length == leafs.length) {
            return leafs[0].getId().compareTo(variant.leafs[0].getId());
        }
        return variant.leafs.length - leafs.length;  
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (LeafElementType leaf : leafs) {
            if (leaf instanceof IdentifierElementType) {
                IdentifierElementType identifierElementType = (IdentifierElementType) leaf;
                buffer.append(identifierElementType.getObjectTypeName());
            } else if (leaf instanceof TokenElementType) {
                TokenElementType tokenElementType = (TokenElementType) leaf;
                buffer.append(tokenElementType.getTokenType().getValue());
            }
            if (leaf != leafs[leafs.length-1]) {
                buffer.append('.');
            }
        }
        return buffer.toString();
    }

    public boolean matchesPsiElement(QualifiedIdentifierPsiElement psiElement) {
        TokenElementType separatorToken = psiElement.getElementType().getSeparatorToken();
        PsiElement[] children = psiElement.getChildren();
        int index = 0;
        for (PsiElement child : children) {
            if (child instanceof LeafPsiElement) {
                LeafPsiElement leafPsiElement = (LeafPsiElement) child;

                if (leafPsiElement.getElementType() == separatorToken){
                    index++;
                } else {
                    if (leafs.length == index) {
                        // variant is too short for given QualifiedIdentifier
                        return false;
                    }

                    PsiElement reference = leafPsiElement.resolve();
                    LeafElementType leafElementType = (LeafElementType) leafPsiElement.getElementType();
                    if (reference == null) {
                        if (!(leafElementType.isIdentifier() && leafs[index].isIdentifier()) ||
                                !leafElementType.isSameAs(leafs[index])) {
                            if(child != children[children.length-1]) {
                                return false;
                            }
                        }

                    } else {
                        // element is resolved. Must entirely match variant leaf
                        if (!leafElementType.isSameAs(leafs[index])) {
                             if(child != children[children.length-1]) {
                                 return false;
                             }
                        }
                    }
                }
            }
        }
        return true;
    }
}
