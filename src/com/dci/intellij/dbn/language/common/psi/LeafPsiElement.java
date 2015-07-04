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

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.psi.lookup.ObjectDefinitionLookupAdapter;
import com.dci.intellij.dbn.language.common.psi.lookup.PsiLookupAdapter;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectBundle;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.common.ObjectTypeFilter;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public abstract class LeafPsiElement extends BasePsiElement implements PsiReference {

    public LeafPsiElement(ASTNode astNode, ElementType elementType) {
        super(astNode, elementType);
    }

    public int approximateLength() {
        return getTextLength() + 1;
    }

    @Override
    public PsiReference getReference() {
        return this;
    }

    public CharSequence getChars() {
        return getNode().getFirstChildNode().getChars();
    }

    /*********************************************************
     *                       PsiReference                    *
     *********************************************************/

    public PsiElement getElement() {
        return this;
    }

    @Nullable
    public PsiElement resolve() {
        return null;
    }

    @NotNull
    public String getCanonicalText() {
        return null;
    }

    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return null;
    }

    public PsiElement bindToElement(PsiElement element) throws IncorrectOperationException {
        return null;
    }

    public boolean isReferenceTo(PsiElement element) {
        return false;
    }

    public TextRange getRangeInElement() {                                        
        return new TextRange(0, getTextLength());
    }

    public boolean isSoft() {
        return true;
    }

    @NotNull
    public Object[] getVariants() {
        return PsiElement.EMPTY_ARRAY;
    }

    public static Set<DBObject> identifyPotentialParentObjects(DBObjectType objectType, @Nullable ObjectTypeFilter filter, BasePsiElement sourceScope, LeafPsiElement lookupIssuer) {
        ConnectionHandler connectionHandler = sourceScope.getActiveConnection();
        Set<DBObject> parentObjects = null;
        Set<DBObjectType> parentTypes = objectType.getGenericParents();
        if (parentTypes.size() > 0) {
            if (objectType.isSchemaObject() && connectionHandler != null && !connectionHandler.isVirtual()) {
                DBObjectBundle objectBundle = connectionHandler.getObjectBundle();

                if (filter == null || filter.acceptsCurrentSchemaObject(objectType)) {
                    DBSchema currentSchema = sourceScope.getCurrentSchema();
                    if (currentSchema != null) {
                        parentObjects = new THashSet<DBObject>();
                        parentObjects.add(currentSchema);
                    }
                }

                if (filter == null || filter.acceptsPublicSchemaObject(objectType)) {
                    DBSchema publicSchema = objectBundle.getPublicSchema();
                    if (publicSchema != null) {
                        if (parentObjects == null) parentObjects = new THashSet<DBObject>();
                        parentObjects.add(publicSchema);
                    }
                }
                return parentObjects;
            } else {
                Set<BasePsiElement> parentObjectPsiElements = null;
                for (DBObjectType parentObjectType : parentTypes) {
                    PsiLookupAdapter lookupAdapter = new ObjectDefinitionLookupAdapter(lookupIssuer, parentObjectType, null);
                    parentObjectPsiElements = parentObjectType.isSchemaObject() ?
                            lookupAdapter.collectInScope(sourceScope, parentObjectPsiElements) :
                            lookupAdapter.collectInParentScopeOf(sourceScope, parentObjectPsiElements);
                }

                if (parentObjectPsiElements != null) {
                    for (BasePsiElement parentObjectPsiElement : parentObjectPsiElements) {
                        if (!parentObjectPsiElement.containsPsiElement(sourceScope)) {
                            DBObject parentObject = parentObjectPsiElement.resolveUnderlyingObject();
                            if (parentObject != null) {
                                if (parentObjects == null) parentObjects = new THashSet<DBObject>();
                                parentObjects.add(parentObject);
                            }
                        }
                    }
                }

                DBObject fileObject = sourceScope.getFile().getUnderlyingObject();
                if (fileObject != null && fileObject.getObjectType().isParentOf(objectType)) {
                    if (parentObjects == null) parentObjects = new THashSet<DBObject>();
                    parentObjects.add(fileObject);
                    return parentObjects;
                }
            }
        }
        return parentObjects;
    }

    @Override
    public BasePsiElement lookupPsiElementByAttribute(ElementTypeAttribute attribute) {
        return getElementType().is(attribute) ? this : null;
    }

    public BasePsiElement lookupFirstPsiElement(ElementTypeAttribute attribute) {
        if (this.getElementType().is(attribute)) {
            return this;
        }
        return null;
    }


    public BasePsiElement lookupFirstLeafPsiElement() {
        return this;
    }
}
