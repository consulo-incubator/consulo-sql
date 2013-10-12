package com.dci.intellij.dbn.code.common.style.formatting;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.code.common.style.DBLCodeStyleManager;
import com.dci.intellij.dbn.code.common.style.options.CodeStyleCustomSettings;
import com.dci.intellij.dbn.common.util.CommonUtil;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.intellij.formatting.Block;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.FormattingModelBuilder;
import com.intellij.formatting.FormattingModelProvider;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.impl.source.codeStyle.CodeFormatterFacade;

public class DBLFormattingModelBuilder implements FormattingModelBuilder {

    @NotNull
    public FormattingModel createModel(final PsiElement element, final CodeStyleSettings codeStyleSettings) {
        SqlLikeLanguage language = (SqlLikeLanguage) PsiUtil.getLanguage(element);

        PsiFile psiFile = element.getContainingFile();
        CodeStyleCustomSettings settings = language.getCodeStyleSettings(element.getProject());

        boolean deliberate = CommonUtil.isCalledThrough(CodeFormatterFacade.class);
        if (deliberate && settings.getCaseSettings().isEnabled()) {
            DBLCodeStyleManager.getInstance(element.getProject()).formatCase(element.getContainingFile());
        }

        Block rootBlock = deliberate && settings.getFormattingSettings().isEnabled() ?
                new FormattingBlock(codeStyleSettings, settings, element, null, 0) :
                new PassiveFormattingBlock(element);
        return FormattingModelProvider.createFormattingModelForPsiFile(psiFile, rootBlock, codeStyleSettings);
    }

    public TextRange getRangeAffectingIndent(PsiFile psiFile, int i, ASTNode astNode) {
        return astNode.getTextRange();
    }
}
