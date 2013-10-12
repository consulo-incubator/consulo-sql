package com.dci.intellij.dbn.language.psql.template;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.psi.LeafPsiElement;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.dci.intellij.dbn.language.psql.PSQLLanguage;
import com.intellij.codeInsight.template.TemplateContextType;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.PsiFile;

public class PSQLTemplateContextType extends TemplateContextType {
    protected PSQLTemplateContextType() {
        super("PL/SQL", "PL/SQL (DBN)");
    }

    @Override
    public boolean isInContext(@NotNull PsiFile file, int offset) {
        Language language = file.getLanguage();
        if (language instanceof SqlLikeLanguage) {
            // support PSQL in SQL language
            LeafPsiElement leafPsiElement = PsiUtil.lookupLeafBeforeOffset(file, offset);
            if (leafPsiElement != null) {
                return leafPsiElement.getLanguage() instanceof PSQLLanguage;
            } else {
                return language instanceof PSQLLanguage;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public SyntaxHighlighter createHighlighter() {
        return PSQLLanguage.INSTANCE.getFirstVersion().getSyntaxHighlighter();
    }
}
