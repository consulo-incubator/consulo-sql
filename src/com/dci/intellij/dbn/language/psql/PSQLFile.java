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

package com.dci.intellij.dbn.language.psql;

import gnu.trove.THashSet;

import java.util.Set;

import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.dci.intellij.dbn.language.common.psi.lookup.IdentifierDefinitionLookupAdapter;
import com.dci.intellij.dbn.language.common.psi.lookup.ObjectDefinitionLookupAdapter;
import com.dci.intellij.dbn.language.common.psi.lookup.PsiLookupAdapter;
import com.dci.intellij.dbn.language.common.psi.lookup.VariableDefinitionLookupAdapter;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;

public class PSQLFile extends DBLanguageFile {

    public PSQLFile(FileViewProvider fileViewProvider) {
        super(fileViewProvider, PSQLFileType.INSTANCE, PSQLLanguage.INSTANCE);
    }

    public BasePsiElement lookupObjectSpecification(DBObjectType objectType, CharSequence objectName) {
        for (PsiElement psiElement : getChildren()) {
            if (psiElement instanceof BasePsiElement) {
                BasePsiElement basePsiElement = (BasePsiElement) psiElement;
                PsiLookupAdapter lookupAdapter = new ObjectDefinitionLookupAdapter(null, objectType, objectName, ElementTypeAttribute.SUBJECT);
                BasePsiElement specObject = lookupAdapter.findInScope(basePsiElement);
                if (specObject != null) {
                    return specObject.lookupEnclosingPsiElement(ElementTypeAttribute.OBJECT_SPECIFICATION);
                }
            }
        }
        return null;
    }

    public BasePsiElement lookupObjectDeclaration(DBObjectType objectType, CharSequence objectName) {
        for (PsiElement psiElement : getChildren()) {
            if (psiElement instanceof BasePsiElement) {
                BasePsiElement basePsiElement = (BasePsiElement) psiElement;
                PsiLookupAdapter lookupAdapter = new ObjectDefinitionLookupAdapter(null, objectType, objectName, ElementTypeAttribute.SUBJECT);
                BasePsiElement specObject = lookupAdapter.findInScope(basePsiElement);
                if (specObject != null) {
                    return specObject.lookupEnclosingPsiElement(ElementTypeAttribute.OBJECT_DECLARATION);
                }
            }
        }
        return null;
    }

    public Set<BasePsiElement> lookupVariableDefinition(int offset) {
        BasePsiElement scope = PsiUtil.lookupElementAtOffset(this, ElementTypeAttribute.SCOPE_DEMARCATION, offset);
        Set<BasePsiElement> variableDefinitions = new THashSet<BasePsiElement>();
        while (scope != null) {
            PsiLookupAdapter lookupAdapter = new IdentifierDefinitionLookupAdapter(null, DBObjectType.ARGUMENT, null);
            variableDefinitions = scope.collectPsiElements(lookupAdapter, variableDefinitions, 0);

            lookupAdapter = new VariableDefinitionLookupAdapter(null, DBObjectType.ANY, null);
            variableDefinitions = scope.collectPsiElements(lookupAdapter, variableDefinitions, 0);

            PsiElement parent = scope.getParent();
            if (parent instanceof BasePsiElement) {
                BasePsiElement basePsiElement = (BasePsiElement) parent;
                scope = basePsiElement.lookupEnclosingPsiElement(ElementTypeAttribute.SCOPE_DEMARCATION);
                if (scope == null) scope = basePsiElement.lookupEnclosingPsiElement(ElementTypeAttribute.SCOPE_ISOLATION);
            } else {
                scope = null;
            }
        }
        return variableDefinitions;
    }
}
