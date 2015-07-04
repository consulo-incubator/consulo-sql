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

package com.dci.intellij.dbn.code.common.style.presets.clause;

import com.dci.intellij.dbn.code.common.style.presets.CodeStylePresetImpl;
import com.dci.intellij.dbn.language.common.SharedTokenTypeBundle;
import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.TokenPsiElement;
import com.intellij.formatting.Spacing;
import com.intellij.psi.PsiElement;

public abstract class ClauseAbstractPreset extends CodeStylePresetImpl {
    public ClauseAbstractPreset(String id, String name) {
        super(id, name);
    }

    public boolean accepts(BasePsiElement psiElement) {
        return psiElement.getElementType().is(ElementTypeAttribute.CLAUSE);
    }

    protected Spacing getSpacing(BasePsiElement psiElement, boolean shouldWrap) {
        if (shouldWrap) {
            return SPACING_LINE_BREAK;
        } else {
            PsiElement previousPsiElement = psiElement.getPrevSibling();
            if (previousPsiElement instanceof TokenPsiElement) {
                TokenPsiElement previousToken = (TokenPsiElement) previousPsiElement;
                SharedTokenTypeBundle sharedTokenTypes = psiElement.getLanguage().getSharedTokenTypes();
                TokenType tokenType = previousToken.getElementType().getTokenType();
                return tokenType ==  sharedTokenTypes.getLeftParenthesis() ?
                        SPACING_NO_SPACE :
                        SPACING_ONE_SPACE;

            }
            return SPACING_ONE_SPACE;
        }
    }
}
