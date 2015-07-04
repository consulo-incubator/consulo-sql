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
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class NamedPsiElement extends SequencePsiElement {
    public NamedPsiElement(ASTNode astNode, NamedElementType elementType) {
        super(astNode, elementType);
    }

    public boolean hasErrors() {
        PsiElement child = getFirstChild();
        while (child != null) {
            if (child instanceof BasePsiElement && !(child instanceof NamedPsiElement)) {
                BasePsiElement basePsiElement = (BasePsiElement) child;
                if (basePsiElement.hasErrors()) {
                    return true;
                }
            }
            child = child.getNextSibling();
        }
        return false;
    }

    @Override
    public boolean isValid() {
        return super.isValid();
    }

    /*********************************************************
     *                       ItemPresentation                *
     *********************************************************/
    public String getPresentableText() {
        BasePsiElement subject = lookupFirstPsiElement(ElementTypeAttribute.SUBJECT);
        if (subject != null && subject instanceof IdentifierPsiElement && subject.getParent() == this) {
            IdentifierPsiElement identifierPsiElement = (IdentifierPsiElement) subject;
            if (identifierPsiElement.isObject()) {
                return identifierPsiElement.getText();
            }
        }
        return super.getPresentableText();
    }

    @Nullable
    public String getLocationString() {
        return null;
    }

    @Nullable
    public Icon getIcon(boolean open) {
        Icon icon = super.getIcon(open);
        if (icon == null) {
            BasePsiElement subject = lookupFirstPsiElement(ElementTypeAttribute.SUBJECT);
            if (subject != null && subject.getParent() == this) {
                if (subject instanceof IdentifierPsiElement) {
                    IdentifierPsiElement identifierPsiElement = (IdentifierPsiElement) subject;
                    if (identifierPsiElement.isObject() && identifierPsiElement.isValid()) {
                        VirtualFile file = PsiUtil.getVirtualFileForElement(identifierPsiElement);
                        if (file instanceof SourceCodeFile) {
                            SourceCodeFile sourceCodeFile = (SourceCodeFile) file;
                            return identifierPsiElement.getObjectType().getIcon(sourceCodeFile.getContentType());
                        }
                        return identifierPsiElement.getObjectType().getIcon();
                    }
                }
            }
        } else {
            return icon;
        }
        return null;
    }

    @Nullable
    public TextAttributesKey getTextAttributesKey() {
        return null;
    }
}
