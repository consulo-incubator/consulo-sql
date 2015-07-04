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
import com.dci.intellij.dbn.language.common.element.IdentifierElementType;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public class PsiResolveResult {
    private ConnectionHandler activeConnection;
    private DBSchema currentSchema;
    private IdentifierPsiElement element;
    private BasePsiElement parent;
    private PsiElement referencedElement;
    private CharSequence text;
    private boolean isNew;
    private boolean isResolving;
    private boolean isConnectionValid;
    private long lastResolveInvocation = 0;
    private int executableTextLength;
    private int resolveTrials = 0;
    private int overallResolveTrials = 0;

    PsiResolveResult(IdentifierPsiElement element) {
        this.activeConnection = element.getActiveConnection();
        this.element = element;
        this.isNew = true;
        IdentifierElementType elementType = element.getElementType();
    }

    public void preResolve(IdentifierPsiElement psiElement) {
        this.isResolving = true;
        ConnectionHandler connectionHandler = psiElement.getActiveConnection();
        this.isConnectionValid = connectionHandler != null && !connectionHandler.isVirtual() && connectionHandler.getConnectionStatus().isValid();
        this.referencedElement = null;
        this.parent = null;
        this.text = psiElement.getUnquotedText();
        this.activeConnection = connectionHandler;
        this.currentSchema = psiElement.getCurrentSchema();
        this.executableTextLength = psiElement.getEnclosingScopePsiElement().getTextLength();
    }

    public void postResolve() {
        this.isNew = false;
        this.resolveTrials = referencedElement == null ? resolveTrials + 1 : 0;
        this.overallResolveTrials = referencedElement == null ? overallResolveTrials + 1 : 0;
        this.isResolving = false;
    }

    public boolean isResolving() {
        return isResolving;
    }

    boolean isDirty() {
        //if (isResolving) return false;
        if (isNew) return true;

        if (resolveTrials > 3 && lastResolveInvocation < System.currentTimeMillis() - 3000) {
            lastResolveInvocation = System.currentTimeMillis();
            resolveTrials = 0;
            return true;
        }

        if (conectionChanged()) {
            return true;
        }

        ConnectionHandler activeConnection = element.getActiveConnection();
        if (activeConnection == null || activeConnection.isVirtual()) {
            if (currentSchema != null) return true;
        } else {
            if (connectionBecameValid() || currentSchemaChanged()) return true;
        }

        if (referencedElement == null &&
                resolveTrials > 3 &&
                !elementTextChanged() &&
                !enclosingExecutableChanged()) {
            return false;
        }

        if (referencedElement == null ||
                !referencedElement.isValid() ||
                !element.textMatches(referencedElement.getText())) {
            return true;
        }

        if (parent != null) {
            if (!parent.isValid()) {
                return true;
            } else if (referencedElement instanceof DBObject) {
                DBObject object = (DBObject) referencedElement;
                if (object.getParentObject() != parent.resolveUnderlyingObject()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean elementTextChanged() {
        return !element.textMatches(text);
    }

    private boolean conectionChanged() {
        return activeConnection != element.getActiveConnection();
    }

    private boolean currentSchemaChanged() {
        return currentSchema != element.getCurrentSchema();
    }

    private boolean connectionBecameValid() {
        ConnectionHandler activeConnection = element.getActiveConnection();
        return !isConnectionValid && activeConnection!= null && !activeConnection.isVirtual() && activeConnection.getConnectionStatus().isValid();
    }

    private boolean enclosingExecutableChanged() {
        return executableTextLength != element.getEnclosingScopePsiElement().getTextLength();
    }

    /*********************************************************
     *                   Getters/Setters                     *
     *********************************************************/

    public CharSequence getText() {
        return text;
    }

    public PsiElement getReferencedElement() {
        return referencedElement;
    }

    public ConnectionHandler getActiveConnection() {
        return activeConnection;
    }

    public void setParent(@Nullable BasePsiElement parent) {
        this.parent = parent;
    }

    public void setReferencedElement(PsiElement referencedElement) {
        this.referencedElement = referencedElement;
    }
}
