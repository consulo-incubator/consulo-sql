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

package com.dci.intellij.dbn.code.common.style.presets.iteration;

import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.IterationElementType;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.NamedPsiElement;
import com.intellij.formatting.Spacing;
import com.intellij.formatting.Wrap;
import com.intellij.psi.codeStyle.CodeStyleSettings;

public class IterationChopDownIfLongStatementPreset extends IterationAbstractPreset {
    public IterationChopDownIfLongStatementPreset() {
        super("chop_down_if_statement_long", "Chop down if statement long");
    }

    public Wrap getWrap(BasePsiElement psiElement, CodeStyleSettings settings) {
        BasePsiElement parentPsiElement = getParentPsiElement(psiElement);
        IterationElementType iterationElementType = (IterationElementType) parentPsiElement.getElementType();
        ElementType elementType = psiElement.getElementType();

        NamedPsiElement namedPsiElement = (NamedPsiElement) parentPsiElement.lookupEnclosingPsiElement(ElementTypeAttribute.EXECUTABLE);
        boolean shouldWrap = namedPsiElement.approximateLength() > settings.RIGHT_MARGIN;
        return getWrap(elementType, iterationElementType, shouldWrap);
    }

    public Spacing getSpacing(BasePsiElement psiElement, CodeStyleSettings settings) {
        BasePsiElement parentPsiElement = getParentPsiElement(psiElement);
        IterationElementType iterationElementType = (IterationElementType) parentPsiElement.getElementType();
        ElementType elementType = psiElement.getElementType();

        NamedPsiElement namedPsiElement = (NamedPsiElement) parentPsiElement.lookupEnclosingPsiElement(ElementTypeAttribute.EXECUTABLE);
        boolean shouldWrap = namedPsiElement.approximateLength() > settings.RIGHT_MARGIN;
        return getSpacing(iterationElementType, elementType, shouldWrap);
    }
}