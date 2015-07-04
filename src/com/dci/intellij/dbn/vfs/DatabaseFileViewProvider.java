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

package com.dci.intellij.dbn.vfs;

import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.navigation.psi.NavigationPsiCache;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.SingleRootFileViewProvider;
import com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.annotations.NotNull;

public class DatabaseFileViewProvider extends SingleRootFileViewProvider {
    public DatabaseFileViewProvider(@NotNull PsiManager manager, @NotNull VirtualFile virtualFile, boolean physical) {
        super(manager, virtualFile, physical);
    }

    public DatabaseFileViewProvider(@NotNull PsiManager psiManager, @NotNull VirtualFile virtualFile, boolean physical, @NotNull Language language) {
        super(psiManager, virtualFile, physical, language);
    }

    @Override
    public boolean isPhysical() {
        return true;
    }

    @Override
    protected PsiFile getPsiInner(@NotNull Language language) {
        if (language instanceof SqlLikeLanguage) {
            VirtualFile virtualFile = getVirtualFile();
            if (virtualFile instanceof DatabaseObjectFile) {
                DatabaseObjectFile objectFile = (DatabaseObjectFile) virtualFile;
                DBObject object = objectFile.getObject();
                NavigationPsiCache psiCache = object.getConnectionHandler().getPsiCache();
                return psiCache.getPsiFile(object);
            }

            SqlLikeLanguage baseLanguage = (SqlLikeLanguage) getBaseLanguage();
            PsiFile psiFile = super.getPsiInner(baseLanguage);
            if (psiFile == null) {
                DatabaseFile databaseFile = getDatabaseFile(virtualFile);
                if (databaseFile != null) {
                    return databaseFile.initializePsiFile(this, (SqlLikeLanguage) language);
                }
            } else {
                return psiFile;
            }
        }

        return super.getPsiInner(language);
    }

    private DatabaseFile getDatabaseFile(VirtualFile virtualFile) {
        if (virtualFile instanceof DatabaseFile) {
            return (DatabaseFile) virtualFile;
        }

        if (virtualFile instanceof LightVirtualFile) {
            LightVirtualFile lightVirtualFile = (LightVirtualFile) virtualFile;
            VirtualFile originalFile = lightVirtualFile.getOriginalFile();
            if (originalFile != null && originalFile != virtualFile) {
                return getDatabaseFile(originalFile);
            }
        }
        return null;
    }

    @NotNull
    @Override
    public SingleRootFileViewProvider createCopy(@NotNull VirtualFile copy) {
        return new DatabaseFileViewProvider(getManager(), copy, false, getBaseLanguage());
    }

    @NotNull
    @Override
    public VirtualFile getVirtualFile() {
        VirtualFile virtualFile = super.getVirtualFile();
/*
        if (virtualFile instanceof SourceCodeFile)  {
            SourceCodeFile sourceCodeFile = (SourceCodeFile) virtualFile;
            return sourceCodeFile.getDatabaseFile();
        }
*/
        return virtualFile;
    }
}
