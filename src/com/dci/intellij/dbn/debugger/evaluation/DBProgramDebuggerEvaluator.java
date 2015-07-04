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

package com.dci.intellij.dbn.debugger.evaluation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.debugger.frame.DBProgramDebugStackFrame;
import com.dci.intellij.dbn.debugger.frame.DBProgramDebugValue;
import com.dci.intellij.dbn.language.common.psi.IdentifierPsiElement;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.evaluation.XDebuggerEvaluator;

public class DBProgramDebuggerEvaluator extends XDebuggerEvaluator {
    private DBProgramDebugStackFrame frame;

    public DBProgramDebuggerEvaluator(DBProgramDebugStackFrame frame) {
        this.frame = frame;
    }

	@Override
	public void evaluate(@NotNull String s, @NotNull XEvaluationCallback xEvaluationCallback, @Nullable XSourcePosition xSourcePosition)
	{

	}

	public void evaluate(@NotNull String expression, XEvaluationCallback callback) {
        DBProgramDebugValue value = frame.getValue(expression);
        if (value == null) {
            value = new DBProgramDebugValue(frame.getDebugProcess(), expression, null, frame.getIndex());
            frame.setValue(expression, value);
        }

        String errorMessage = value.getErrorMessage();
        if (errorMessage != null) {
            callback.errorOccurred(errorMessage);
        } else {
            callback.evaluated(value);
        }

    }

    @Nullable
    public TextRange getExpressionRangeAtOffset(Project project, Document document, int offset) {
        PsiFile psiFile = PsiUtil.getPsiFile(project, document);
        if (psiFile != null) {
            PsiElement psiElement = psiFile.findElementAt(offset);
            if (psiElement != null && psiElement.getParent() instanceof IdentifierPsiElement) {
                return psiElement.getTextRange();
            }
        }
        return null;
    }
}
