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

import com.dci.intellij.dbn.common.editor.BasicTextEditorImpl;
import com.dci.intellij.dbn.common.editor.BasicTextEditorState;
import com.dci.intellij.dbn.vfs.SQLConsoleFile;
import com.intellij.openapi.project.Project;

public class SQLConsoleEditor extends BasicTextEditorImpl<SQLConsoleFile>{
    public SQLConsoleEditor(Project project, SQLConsoleFile sqlConsoleFile, String name) {
        super(project, sqlConsoleFile, name);
    }

    @Override
    protected BasicTextEditorState createEditorState() {
        return new SQLConsoleEditorState();
    }

}
