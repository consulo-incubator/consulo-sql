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

package com.dci.intellij.dbn.editor.code;

import com.dci.intellij.dbn.common.editor.BasicTextEditorImpl;
import com.dci.intellij.dbn.common.thread.ConditionalLaterInvocator;
import com.dci.intellij.dbn.language.common.psi.BasePsiElement;
import com.dci.intellij.dbn.language.common.psi.PsiUtil;
import com.dci.intellij.dbn.language.psql.PSQLFile;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.dci.intellij.dbn.object.factory.DatabaseObjectFactory;
import com.dci.intellij.dbn.object.factory.ObjectFactoryListener;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

public class SourceCodeEditor extends BasicTextEditorImpl<SourceCodeFile> implements ObjectFactoryListener{
    private DBSchemaObject object;
    private int headerEndOffset;

    public SourceCodeEditor(Project project, SourceCodeFile sourceCodeFile, String name) {
        super(project, sourceCodeFile, name);

        object = sourceCodeFile.getObject();
        Document document = this.textEditor.getEditor().getDocument();
        if (document.getTextLength() > 0) {
            headerEndOffset = sourceCodeFile.getEditorHeaderEndOffset();
            /*int guardedBlockEndOffset = sourceCodeFile.getGuardedBlockEndOffset();
            if (guardedBlockEndOffset > 0) {
                DocumentUtil.createGuardedBlock(document, 0, guardedBlockEndOffset,
                        "You are not allowed to change the name of the " + object.getTypeName());
            }*/
        }
        DatabaseObjectFactory.getInstance(project).addFactoryListener(this);
    }

    public DBSchemaObject getObject() {
        return object;
    }

    public int getHeaderEndOffset() {
        return headerEndOffset;
    }

    public void navigateTo(DBObject object) {
        PsiFile file = PsiUtil.getPsiFile(getObject().getProject(), getVirtualFile());
        if (file instanceof PSQLFile) {
            PSQLFile psqlFile = (PSQLFile) file;
            BasePsiElement navigable = psqlFile.lookupObjectDeclaration(object.getObjectType(), object.getName());
            if (navigable == null) navigable = psqlFile.lookupObjectSpecification(object.getObjectType(), object.getName());
            if (navigable != null) navigable.navigate(true);
        }
    }

    /********************************************************
     *                ObjectFactoryListener                 *
     *****************33*************************************/
    public void objectCreated(DBSchemaObject object) {
    }

    public void objectDropped(DBSchemaObject object) {
        if (this.object.equals(object)) {
            new ConditionalLaterInvocator() {
                public void run() {
                    FileEditorManager fileEditorManager = FileEditorManager.getInstance(SourceCodeEditor.this.object.getProject());
                    fileEditorManager.closeFile(getVirtualFile().getDatabaseFile());
                }
            }.start();
        }

    }
}
