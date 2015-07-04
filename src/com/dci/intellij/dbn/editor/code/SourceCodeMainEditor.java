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

import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.project.Project;

/**
 * Only main editor extends TextEditor to force navigation of breakpoints to the right editor (e.g. package body)
 */
public class SourceCodeMainEditor extends SourceCodeEditor implements TextEditor {
    public SourceCodeMainEditor(Project project, SourceCodeFile sourceCodeFile, String name) {
        super(project, sourceCodeFile, name);
    }
}
