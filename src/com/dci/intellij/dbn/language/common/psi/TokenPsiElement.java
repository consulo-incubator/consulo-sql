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

package com.dci.intellij.dbn.language.common.psi;

import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.language.common.element.TokenElementType;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.psi.lookup.PsiLookupAdapter;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.util.Set;

public class TokenPsiElement extends LeafPsiElement {
    public TokenPsiElement(ASTNode astNode, TokenElementType elementType) {
        super(astNode, elementType);
    }

    public TokenElementType getElementType() {
        return (TokenElementType) super.getElementType();
    }

    public BasePsiElement lookupPsiElement(PsiLookupAdapter lookupAdapter, int scopeCrossCount) {return null;}
    public Set<BasePsiElement> collectPsiElements(PsiLookupAdapter lookupAdapter, Set<BasePsiElement> bucket, int scopeCrossCount) {return bucket;}

    public void collectExecVariablePsiElements(Set<ExecVariablePsiElement> bucket) {}
    public void collectSubjectPsiElements(Set<BasePsiElement> bucket) {}
    public NamedPsiElement lookupNamedPsiElement(String id) {return null;}
    public BasePsiElement lookupPsiElementBySubject(ElementTypeAttribute attribute, CharSequence subjectName, DBObjectType subjectType) {return null;}


    /*********************************************************
     *                       PsiReference                    *
     *********************************************************/
    public boolean isReferenceTo(PsiElement element) {
        return true;
    }

    public boolean isSoft() {
        return false;
    }

    /*********************************************************
     *                       ItemPresentation                *
     *********************************************************/
    public String getPresentableText() {
        return getElementType().getTokenType().getValue();
    }

    @Nullable
    public String getLocationString() {
        return null;
    }

    @Nullable
    public Icon getIcon(boolean open) {
        return null;
    }

    @Nullable
    public TextAttributesKey getTextAttributesKey() {
        return null;
    }

    public boolean hasErrors() {
        return false;
    }

    @Override
    public boolean equals(BasePsiElement basePsiElement) {
        if (this == basePsiElement) {
            return true;
        } else {
            if (basePsiElement instanceof TokenPsiElement) {
                TokenPsiElement remote = (TokenPsiElement) basePsiElement;
                TokenType localTokenType = getElementType().getTokenType();
                TokenType remoteTokenType = remote.getElementType().getTokenType();
                if (localTokenType == remoteTokenType) {
                    return
                        localTokenType.isReservedWord() ||
                        localTokenType.isCharacter() ||
                        localTokenType.isOperator() ||
                        StringUtil.equalsIgnoreCase(getChars(), remote.getChars());
                }
            }
            return false;
        }
    }

    @Override
    public boolean matches(BasePsiElement basePsiElement) {
        if (this == basePsiElement) {
            return true;
        } else {
            if (basePsiElement instanceof TokenPsiElement) {
                TokenPsiElement remote = (TokenPsiElement) basePsiElement;
                TokenType localTokenType = getElementType().getTokenType();
                TokenType remoteTokenType = remote.getElementType().getTokenType();
                return localTokenType == remoteTokenType;
            }
            return false;
        }
    }
}
