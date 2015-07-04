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

package com.dci.intellij.dbn.language.psql.structure;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.intellij.ide.IconDescriptorUpdaters;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.psi.PsiElement;

public class PSQLStructureViewElement implements StructureViewTreeElement {
    PsiElement psiElement;

    PSQLStructureViewElement(PsiElement psiElement) {
        this.psiElement = psiElement;
    }

    public Object getValue() {
        return psiElement;
    }

    public PsiElement getPsiElement() {
        return psiElement;
    }

    public ItemPresentation getPresentation() {
        if (psiElement instanceof BasePsiElement) return (ItemPresentation) psiElement;
        return new ItemPresentation() {
            public String getPresentableText() {
                if (psiElement instanceof DBLanguageFile) {
                    DBLanguageFile file = (DBLanguageFile) psiElement;
                    return file.getName();
                }
                return psiElement.getText();
            }

            @Nullable
            public String getLocationString() {
                return null;
            }

            @Nullable
            public Icon getIcon(boolean open) {
                return IconDescriptorUpdaters.getIcon(psiElement, 0);
            }
        };
    }

    public StructureViewTreeElement[] getChildren() {
        List<PSQLStructureViewElement> elements = getChildren(psiElement, null);
        return elements == null ?
                EMPTY_ARRAY :
                elements.toArray(new StructureViewTreeElement[elements.size()]);
    }

    private List<PSQLStructureViewElement> getChildren(PsiElement parent, List<PSQLStructureViewElement> elements) {
        PsiElement child = parent.getFirstChild();
        while (child != null) {
            if (child instanceof BasePsiElement) {
                BasePsiElement basePsiElement = (BasePsiElement) child;
                if (basePsiElement.getElementType().is(ElementTypeAttribute.STRUCTURE)) {
                    if (elements == null) {
                        elements = new ArrayList<PSQLStructureViewElement>();
                    }
                    elements.add(new PSQLStructureViewElement(child));
                } else {
                    elements = getChildren(basePsiElement, elements);
                }
            }
            child = child.getNextSibling();
        }
        return elements;
    }

    public void navigate(boolean requestFocus) {
        if (psiElement instanceof NavigationItem) {
            NavigationItem navigationItem = (NavigationItem) psiElement;
            navigationItem.navigate(requestFocus);
        }
    }

    public boolean canNavigate() {
        return true;
    }

    public boolean canNavigateToSource() {
        return true;
    }
}
