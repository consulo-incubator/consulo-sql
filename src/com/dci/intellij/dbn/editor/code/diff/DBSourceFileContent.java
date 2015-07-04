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

package com.dci.intellij.dbn.editor.code.diff;

import com.dci.intellij.dbn.common.util.DocumentUtil;
import com.intellij.openapi.diff.FileContent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class DBSourceFileContent extends FileContent {
    private Document document;
    public DBSourceFileContent(Project project, @NotNull VirtualFile file) {
        super(project, file);
    }

    public Document getDocument() {
        if (document == null) {
            document = DocumentUtil.getDocument(getFile());
        }
        return document; 
    }

    @Override
    public FileType getContentType() {
        return getFile().getFileType();
    }
}
