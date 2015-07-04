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

package com.dci.intellij.dbn.data.editor.text.actions;

import com.dci.intellij.dbn.data.editor.text.TextContentType;
import com.dci.intellij.dbn.data.editor.text.ui.TextEditorForm;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class TextContentTypeSelectAction extends AnAction {
    private TextEditorForm editorForm;
    private TextContentType contentType;

    public TextContentTypeSelectAction(TextEditorForm editorForm, TextContentType contentType) {
        super(contentType.getName(), null, contentType.getIcon());
        this.contentType = contentType;
        this.editorForm = editorForm;
    }

    public TextContentType getContentType() {
        return contentType;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        editorForm.setContentType(contentType);

    }
}
