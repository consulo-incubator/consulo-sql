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

package com.dci.intellij.dbn.editor.console;

import com.dci.intellij.dbn.common.editor.BasicTextEditorState;
import com.dci.intellij.dbn.common.thread.ConditionalLaterInvocator;
import com.dci.intellij.dbn.common.thread.WriteActionRunner;
import com.dci.intellij.dbn.common.util.DocumentUtil;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.vfs.SQLConsoleFile;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import org.jdom.CDATA;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class SQLConsoleEditorState extends BasicTextEditorState {
    private String content = "";
    private String currentSchema = "";

    @Override
    public void writeState(Element targetElement, Project project) {
        super.writeState(targetElement, project);
        Element contentElement = new Element("content");
        contentElement.setAttribute("current-schema", currentSchema);
        targetElement.addContent(contentElement);
        String content = StringUtil.replace(this.content, "\n", "<br>");
        content = StringUtil.replace(content, "  ", "<sp>");
        CDATA cdata = new CDATA(content);
        contentElement.setContent(cdata);

    }

    @Override
    public void readState(@NotNull Element sourceElement, Project project, Document document) {
        super.readState(sourceElement, project, document);
        Element contentElement = sourceElement.getChild("content");
        if (contentElement != null) {
            currentSchema = contentElement.getAttributeValue("current-schema");
            if (contentElement.getContentSize() > 0) {
                CDATA cdata = (CDATA) contentElement.getContent(0);

                String content = StringUtil.replace(cdata.getText(), "<br>", "\n");
                content = StringUtil.replace(content, "<sp>", "  ");
                this.content = content;
            }
        }
    }

    @Override
    public void loadFromEditor(@NotNull FileEditorStateLevel level, @NotNull TextEditor textEditor) {
        super.loadFromEditor(level, textEditor);
        content = textEditor.getEditor().getDocument().getText();
        SQLConsoleFile file = (SQLConsoleFile) DocumentUtil.getVirtualFile(textEditor.getEditor());
        DBSchema schema = file.getCurrentSchema();
        currentSchema = schema == null ? "" : schema.getName();
    }

    @Override
    public void applyToEditor(@NotNull final TextEditor textEditor) {
        new ConditionalLaterInvocator() {
            @Override
            public void run() {
                new WriteActionRunner() {
                    public void run() {
                        textEditor.getEditor().getDocument().setText(content);
                        SQLConsoleEditorState.super.applyToEditor(textEditor);
                        SQLConsoleFile file = (SQLConsoleFile) DocumentUtil.getVirtualFile(textEditor.getEditor());
                        if (currentSchema != null) {
                            DBSchema schema = file.getConnectionHandler().getObjectBundle().getSchema(currentSchema);
                            if (schema != null) file.setCurrentSchema(schema);
                        }
                    }
                }.start();
            }
        }.start();
    }
}
