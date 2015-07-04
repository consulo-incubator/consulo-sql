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

package com.dci.intellij.dbn.language.common.psi.lookup;

import com.dci.intellij.dbn.language.common.element.IdentifierElementType;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.element.util.IdentifierRole;
import com.dci.intellij.dbn.language.common.element.util.IdentifierType;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.IdentifierPsiElement;
import com.dci.intellij.dbn.language.common.psi.LeafPsiElement;
import com.dci.intellij.dbn.object.common.DBObjectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IdentifierLookupAdapter extends PsiLookupAdapter {
    private IdentifierType identifierType;
    private IdentifierRole identifierRole;
    private DBObjectType objectType;
    private CharSequence identifierName;
    private ElementTypeAttribute attribute;
    private LeafPsiElement lookupIssuer;

    protected IdentifierLookupAdapter(LeafPsiElement lookupIssuer, IdentifierType identifierType, IdentifierRole identifierRole, DBObjectType objectType, CharSequence identifierName) {
        this(lookupIssuer, identifierType, identifierRole, objectType, identifierName, null);
    }

    protected IdentifierLookupAdapter(LeafPsiElement lookupIssuer, @Nullable IdentifierType identifierType, @Nullable IdentifierRole identifierRole, @NotNull DBObjectType objectType, CharSequence identifierName, ElementTypeAttribute attribute) {
        this.lookupIssuer = lookupIssuer;
        this.identifierType = identifierType;
        this.objectType = objectType;
        this.identifierName = identifierName;
        this.identifierRole = identifierRole;
        this.attribute = attribute;
    }

    public boolean matches(BasePsiElement basePsiElement) {
        if (basePsiElement instanceof IdentifierPsiElement && basePsiElement != lookupIssuer) {
            IdentifierPsiElement identifierPsiElement = (IdentifierPsiElement) basePsiElement;
            return
                matchesType(identifierPsiElement) &&
                matchesObjectType(identifierPsiElement) &&
                matchesRole(identifierPsiElement) &&
                matchesAttribute(identifierPsiElement) &&
                matchesName(identifierPsiElement);
        }
        return false;
    }

    private boolean matchesType(IdentifierPsiElement identifierPsiElement) {
        return identifierType == null ||identifierType == identifierPsiElement.getElementType().getIdentifierType();
    }

    private boolean matchesObjectType(IdentifierPsiElement identifierPsiElement) {
        return identifierPsiElement.isObjectOfType(objectType);
    }

    public boolean matchesName(IdentifierPsiElement identifierPsiElement) {
        return identifierName == null || identifierPsiElement.textMatches(identifierName);

    }

    private boolean matchesRole(IdentifierPsiElement identifierPsiElement) {
        IdentifierElementType elementType = identifierPsiElement.getElementType();
        IdentifierRole role = elementType.getIdentifierRole();
        switch (identifierRole) {
            case ALL: return true;
            case DEFINITION: return role == IdentifierRole.DEFINITION || identifierPsiElement.isReferenceable();
            case REFERENCE: return role == IdentifierRole.REFERENCE;
        }
        return false;
    }

    private boolean matchesAttribute(IdentifierPsiElement identifierPsiElement) {
        return attribute == null || identifierPsiElement.getElementType().is(attribute);
    }

    @Override
    public boolean accepts(BasePsiElement element) {
        return true;
    }

    public IdentifierType getIdentifierType() {
        return identifierType;
    }

    public IdentifierRole getIdentifierRole() {
        return identifierRole;
    }

    public DBObjectType getObjectType() {
        return objectType;
    }

    public CharSequence getIdentifierName() {
        return identifierName;
    }

    public ElementTypeAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(ElementTypeAttribute attribute) {
        this.attribute = attribute;
    }

    public String toString() {
        return "IdentifierLookupAdapter{" +
                "identifierType=" + identifierType +
                ", identifierRole=" + identifierRole +
                ", objectType=" + objectType +
                ", identifierName='" + identifierName + '\'' +
                ", attribute=" + attribute +
                '}';
    }
}
