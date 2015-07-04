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

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.code.common.style.formatting.FormattingAttributes;
import com.dci.intellij.dbn.code.common.style.formatting.FormattingProviderPsiElement;
import com.dci.intellij.dbn.code.common.style.presets.CodeStylePreset;
import com.dci.intellij.dbn.language.common.element.ChameleonElementType;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.formatting.Indent;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

public class ChameleonPsiElement extends ASTWrapperPsiElement implements ExecutableBundlePsiElement, FormattingProviderPsiElement {
    public static final FormattingAttributes FORMATTING_ATTRIBUTES = new FormattingAttributes(null, Indent.getAbsoluteNoneIndent(), CodeStylePreset.SPACING_MIN_ONE_LINE, null);


    private ChameleonElementType elementType;
    public ChameleonPsiElement(@NotNull ASTNode node, ChameleonElementType elementType) {
        super(node);
        this.elementType = elementType;
    }

    public List<ExecutablePsiElement> getExecutablePsiElements() {
        List<ExecutablePsiElement> bucket = new ArrayList<ExecutablePsiElement>();
        collectExecutablePsiElements(bucket, this);
        return bucket;
    }

    private void collectExecutablePsiElements(List<ExecutablePsiElement> bucket, PsiElement element) {
        PsiElement child = element.getFirstChild();
        while (child != null) {
            if (child instanceof ExecutablePsiElement) {
                ExecutablePsiElement executablePsiElement = (ExecutablePsiElement) child;
                bucket.add(executablePsiElement);
            } else {
                collectExecutablePsiElements(bucket, child);
            }
            child = child.getNextSibling();
        }
    }

    @Override
    public String toString() {
        return elementType.getDebugName();
    }

    @Override
    public FormattingAttributes getFormattingAttributes() {
        return FORMATTING_ATTRIBUTES;
    }

    @Override
    public FormattingAttributes getFormattingAttributesRecursive(boolean left) {
        return FORMATTING_ATTRIBUTES;
    }
}
