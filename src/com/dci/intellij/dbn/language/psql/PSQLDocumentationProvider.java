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

import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.IdentifierPsiElement;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.lang.documentation.DocumentationProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PSQLDocumentationProvider implements DocumentationProvider {

    @Nullable
    public String getQuickNavigateInfo(PsiElement element) {
        if (element instanceof DBObject) {
            DBObject object = (DBObject) element;
            return object.getNavigationTooltipText();
        } else if (element instanceof IdentifierPsiElement) {
            IdentifierPsiElement identifierPsiElement = (IdentifierPsiElement) element;
             if (identifierPsiElement.isAlias()) {
                if (identifierPsiElement.isDefinition()) {
                    BasePsiElement aliasedObjectElement = PsiUtil.resolveAliasedEntityElement(identifierPsiElement);
                    if (aliasedObjectElement == null) {
                        return "unknown alias";
                    } else {
                        DBObject aliasedObject = aliasedObjectElement.resolveUnderlyingObject();
                        if (aliasedObject == null) {
                            return "alias of " + aliasedObjectElement.getReferenceQualifiedName();
                        } else {
                            return "alias of " + aliasedObject.getQualifiedNameWithType();
                        }
                    }

                }
             } else if (identifierPsiElement.isObject()) {
                 if (identifierPsiElement.isDefinition()) {
                     return identifierPsiElement.getObjectType().getName() + ":\n" + identifierPsiElement.lookupEnclosingNamedPsiElement().getText();
                 }
             }

             else if (identifierPsiElement.isVariable()) {
                 String prefix = identifierPsiElement.getObjectType() == DBObjectType.ANY ? "variable" : identifierPsiElement.getObjectType().getName();
                 return prefix + ":\n " + identifierPsiElement.lookupEnclosingNamedPsiElement().getText() ;
            }
        }
        return null;
    }

    public String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        return getQuickNavigateInfo(element);
    }

    @Nullable
    public List<String> getUrlFor(PsiElement psiElement, PsiElement psiElement1) {
        return null;
    }

    @Nullable
    public String generateDoc(PsiElement psiElement, PsiElement psiElement1) {
        return null;
    }

    @Nullable
    public PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object o, PsiElement psiElement) {
        return null;
    }

    @Nullable
    public PsiElement getDocumentationElementForLink(PsiManager psiManager, String s, PsiElement psiElement) {
        return null;
    }
}