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

package com.dci.intellij.dbn.code.common.completion;

import com.dci.intellij.dbn.common.ui.KeyUtil;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class CodeCompletionContributor extends CompletionContributor {
    public static final String DUMMY_TOKEN = "DBN_DUMMY_TOKEN";

    public CodeCompletionContributor() {
        final PsiElementPattern.Capture<PsiElement> everywhere = PlatformPatterns.psiElement();
        extend(CompletionType.BASIC, everywhere, CodeCompletionProvider.INSTANCE);
        extend(CompletionType.SMART, everywhere, CodeCompletionProvider.INSTANCE);

    }

    @Override
    public void beforeCompletion(@NotNull CompletionInitializationContext context) {
        if (context.getPositionLanguage() instanceof SqlLikeLanguage) {
            context.setDummyIdentifier(DUMMY_TOKEN);
        }
    }

    @Override
    public String advertise(@NotNull CompletionParameters parameters) {
        return super.advertise(parameters);
    }

    @Override
    public String handleEmptyLookup(@NotNull CompletionParameters parameters, Editor editor) {
        if (parameters.getCompletionType() == CompletionType.BASIC && parameters.getInvocationCount() == 1) {
            Shortcut[] basicShortcuts = KeyUtil.getShortcuts(IdeActions.ACTION_CODE_COMPLETION);

            return "No suggestions. Press " + KeymapUtil.getShortcutsText(basicShortcuts) + " again to invoke extended completion";
        }

        return null;
    }
}
