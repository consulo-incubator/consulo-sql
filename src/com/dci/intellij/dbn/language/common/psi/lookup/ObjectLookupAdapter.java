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

import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.element.util.IdentifierRole;
import com.dci.intellij.dbn.language.common.element.util.IdentifierType;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.LeafPsiElement;
import com.dci.intellij.dbn.object.common.DBObjectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ObjectLookupAdapter extends IdentifierLookupAdapter {
    public ObjectLookupAdapter(LeafPsiElement lookupIssuer, DBObjectType objectType) {
        super(lookupIssuer, IdentifierType.OBJECT, IdentifierRole.ALL, objectType, null);
    }

    public ObjectLookupAdapter(LeafPsiElement lookupIssuer, DBObjectType objectType, CharSequence identifierName) {
        super(lookupIssuer, IdentifierType.OBJECT, IdentifierRole.ALL, objectType, identifierName);
    }

    public ObjectLookupAdapter(LeafPsiElement lookupIssuer, IdentifierRole identifierRole, DBObjectType objectType) {
        super(lookupIssuer, IdentifierType.OBJECT, identifierRole, objectType, null);
    }

    public ObjectLookupAdapter(LeafPsiElement lookupIssuer, IdentifierRole identifierRole, DBObjectType objectType, CharSequence identifierName) {
        super(lookupIssuer, IdentifierType.OBJECT, identifierRole, objectType, identifierName);
    }

    public ObjectLookupAdapter(LeafPsiElement lookupIssuer, @Nullable IdentifierRole identifierRole, @NotNull DBObjectType objectType, CharSequence identifierName, ElementTypeAttribute attribute) {
        super(lookupIssuer, IdentifierType.OBJECT, identifierRole, objectType, identifierName, attribute);
    }

    @Override
    public boolean matches(BasePsiElement basePsiElement) {
        if (super.matches(basePsiElement)) return true;

        DBObjectType virtualObjectType = basePsiElement.getElementType().getVirtualObjectType();
        return virtualObjectType != null && virtualObjectType.matches(getObjectType());
    }
}
