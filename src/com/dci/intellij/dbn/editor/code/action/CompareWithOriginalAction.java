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

package com.dci.intellij.dbn.editor.code.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;

public class CompareWithOriginalAction extends AbstractDiffAction {
    public CompareWithOriginalAction() {
        super("Compare with original", null, Icons.CODE_EDITOR_DIFF);
    }

    public void actionPerformed(AnActionEvent e) {
        Editor editor = getEditor(e);
        SourceCodeFile virtualFile = getSourcecodeFile(e);
        String content = editor.getDocument().getText();
        virtualFile.setContent(content);
        String referenceText = virtualFile.getOriginalContent();

        openDiffWindow(e, referenceText, "Original version", "Local version");
    }

    public void update(AnActionEvent e) {
        SourceCodeFile virtualFile = getSourcecodeFile(e);
        e.getPresentation().setText("Compare with original");
        e.getPresentation().setEnabled(virtualFile != null && (
                virtualFile.getOriginalContent() != null || virtualFile.isModified()));
    }
}
