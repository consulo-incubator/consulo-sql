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

package com.dci.intellij.dbn.language.sql.template;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.language.common.psi.LeafPsiElement;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.dci.intellij.dbn.language.sql.SQLLanguage;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.PsiFile;

public class SQLTemplateContextType extends TemplateContextType {
    protected SQLTemplateContextType() {
        super("SQL", "SQL (DBN)");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        Language language = file.getLanguage();
        if (language instanceof SQLLanguage) {
            LeafPsiElement leafPsiElement = PsiUtil.lookupLeafBeforeOffset(file, offset);
            return leafPsiElement == null || leafPsiElement.getLanguage() instanceof SQLLanguage;
        }
        return false;
    }

    @Nullable
    @Override
    public SyntaxHighlighter createHighlighter() {
        return SQLLanguage.INSTANCE.getFirstVersion().getSyntaxHighlighter();
    }
}
