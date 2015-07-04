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

import com.dci.intellij.dbn.language.psql.PSQLFileType;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.xdebugger.XSourcePosition;
import com.intellij.xdebugger.evaluation.EvaluationMode;
import com.intellij.xdebugger.evaluation.XDebuggerEditorsProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DBProgramDebuggerEditorsProvider extends XDebuggerEditorsProvider {
    public static final DBProgramDebuggerEditorsProvider INSTANCE = new DBProgramDebuggerEditorsProvider();

    private DBProgramDebuggerEditorsProvider(){}

    @NotNull
    @Override
    public FileType getFileType() {
        return PSQLFileType.INSTANCE;
    }

    @NotNull
    public Document createDocument(@NotNull Project project, @NotNull String text, @Nullable XSourcePosition sourcePosition, @NotNull EvaluationMode evaluationMode) {
        return new DocumentImpl(text);
    }

    @NotNull
    public Document createDocument(@NotNull Project project, @NotNull String text, @Nullable XSourcePosition sourcePosition) {
        return new DocumentImpl(text);
    }


}
