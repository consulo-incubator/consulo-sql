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

package com.dci.intellij.dbn.object.common;

import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBFictiveObject extends DBObjectImpl implements PsiReference {
    private DBObjectType objectType;
    public DBFictiveObject(DBObjectType objectType, String name) {
        super(null, name);
        this.objectType = objectType;
    }

    public boolean isValid() {
        return true;
    }

    @Override
    protected void initObject(ResultSet resultSet) throws SQLException {
    }

    public String getQualifiedNameWithType() {
        return getName();
    }

    public ConnectionHandler getConnectionHandler() {
        return null;
    }

    public DBObjectType getObjectType() {
        return objectType;
    }

    @NotNull
    public List<BrowserTreeNode> buildAllPossibleTreeChildren() {
        return BrowserTreeNode.EMPTY_LIST;
    }

    public void navigate(boolean requestFocus) {

    }

    /*********************************************************
     *                       PsiReference                    *
     *********************************************************/
    public PsiElement getElement() {
        return null;
    }

    public TextRange getRangeInElement() {
        return new TextRange(0, getTextLength());
    }

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

    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        return null;
    }

    public boolean isReferenceTo(PsiElement element) {
        return false;
    }

    @NotNull
    public Object[] getVariants() {
        return new Object[0];
    }

    public boolean isSoft() {
        return false;
    }

}
