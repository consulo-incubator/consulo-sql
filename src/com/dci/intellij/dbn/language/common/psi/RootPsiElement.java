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

import com.dci.intellij.dbn.language.common.element.NamedElementType;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.util.ArrayList;
import java.util.List;

public class RootPsiElement extends NamedPsiElement implements ExecutableBundlePsiElement{

    public RootPsiElement(ASTNode astNode, NamedElementType elementType) {
        super(astNode, elementType);
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

    public NamedElementType getElementType() {
        return (NamedElementType) super.getElementType();
    }

    public boolean hasErrors() {
        return false;
    }

    public Object clone() {
        return super.clone();
    }

    /*********************************************************
     *                    ItemPresentation                   *
     *********************************************************/
    public String getPresentableText() {
        return getElementType().getDescription();
    }

    @Nullable
    public String getLocationString() {
        return null;
    }

    @Nullable
    public Icon getIcon(boolean open) {
        return super.getIcon(open);
    }

    @Nullable
    public TextAttributesKey getTextAttributesKey() {
        return null;
    }
}
