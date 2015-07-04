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

package com.dci.intellij.dbn.navigation;

import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.dci.intellij.dbn.navigation.psi.DBConnectionPsiDirectory;
import com.dci.intellij.dbn.navigation.psi.DBObjectPsiDirectory;
import com.dci.intellij.dbn.navigation.psi.DBObjectPsiFile;
import com.dci.intellij.dbn.navigation.psi.NavigationPsiCache;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.ide.navigationToolbar.NavBarModelExtension;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;

import java.util.Collection;
import java.util.Collections;

public class NavigationBarExtension implements NavBarModelExtension {
    public String getPresentableText(Object object) {
        if (object instanceof DBObject) {
            DBObject dbObject = (DBObject) object;
            return dbObject.getName();
        }
        return null;
    }

    public PsiElement getParent(PsiElement psiElement) {
        if (psiElement instanceof DBObjectPsiFile || psiElement instanceof DBObjectPsiDirectory || psiElement instanceof DBConnectionPsiDirectory) {
            return psiElement.getParent();
        }
        return null;
    }

    public PsiElement adjustElement(PsiElement psiElement) {
        if (psiElement instanceof DBLanguageFile) {
            DBLanguageFile databaseFile = (DBLanguageFile) psiElement;
            DBObject object = databaseFile.getUnderlyingObject();
            if (object != null) {
                NavigationPsiCache psiCache = object.getConnectionHandler().getPsiCache();
                return psiCache.getPsiFile(object);
            }
        }
        return psiElement;
    }

    public Collection<VirtualFile> additionalRoots(Project project) {
        return Collections.emptyList();
    }
}
