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

import com.dci.intellij.dbn.code.common.completion.options.CodeCompletionSettings;
import com.dci.intellij.dbn.code.common.completion.options.filter.CodeCompletionFilterSettings;
import com.dci.intellij.dbn.code.common.style.options.ProjectCodeStyleSettings;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.dci.intellij.dbn.options.GlobalProjectSettings;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.psi.PsiElement;

public class CodeCompletionContext {
    private boolean extended;
    private DBLanguageFile file;
    private ProjectCodeStyleSettings codeStyleSettings;
    private CodeCompletionSettings codeCompletionSettings;
    private CompletionParameters parameters;
    private CompletionResultSet result;
    private PsiElement elementAtCaret;
    private ConnectionHandler connectionHandler;
    private String userInput;


    public CodeCompletionContext(DBLanguageFile file, CompletionParameters parameters, CompletionResultSet result) {
        this.file = file;
        this.parameters = parameters;
        this.result = result;
        this.extended = parameters.getCompletionType() == CompletionType.SMART;
        this.connectionHandler = file.getActiveConnection();

        PsiElement position = parameters.getPosition();
        if (parameters.getOffset() > position.getTextOffset()) {
            userInput = position.getText().substring(0, parameters.getOffset() - position.getTextOffset());
        }

        GlobalProjectSettings globalSettings = GlobalProjectSettings.getInstance(file.getProject());
        codeStyleSettings = globalSettings.getCodeStyleSettings();
        codeCompletionSettings = globalSettings.getCodeCompletionSettings();

        elementAtCaret = position instanceof BasePsiElement ? (BasePsiElement) position : PsiUtil.lookupLeafAtOffset(file, position.getTextOffset());
        elementAtCaret = elementAtCaret == null ? file : elementAtCaret;
    }

    public String getUserInput() {
        return userInput;
    }

    public CompletionParameters getParameters() {
        return parameters;
    }

    public CompletionResultSet getResult() {
        return result;
    }

    public PsiElement getElementAtCaret() {
        return elementAtCaret;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public boolean isExtended() {
        return extended;
    }

    public ProjectCodeStyleSettings getCodeStyleSettings() {
        return codeStyleSettings;
    }

    public CodeCompletionSettings getCodeCompletionSettings() {
        return codeCompletionSettings;
    }

    public CodeCompletionFilterSettings getCodeCompletionFilterSettings() {
        return codeCompletionSettings.getFilterSettings().getFilterSettings(extended);
    }

    public DBLanguageFile getFile() {
        return file;
    }

    public SqlLikeLanguage getLanguage() {
        return (SqlLikeLanguage) file.getLanguage();
    }
}
