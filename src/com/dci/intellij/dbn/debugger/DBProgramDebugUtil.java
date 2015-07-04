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

package com.dci.intellij.dbn.debugger;

import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.dci.intellij.dbn.vfs.DatabaseEditableObjectFile;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.xdebugger.XSourcePosition;
import org.jetbrains.annotations.Nullable;

public class DBProgramDebugUtil {

    public static @Nullable DBSchemaObject getObject(@Nullable XSourcePosition sourcePosition) {
        if (sourcePosition != null) {
            VirtualFile virtualFile = sourcePosition.getFile();
            if (virtualFile instanceof DatabaseEditableObjectFile) {
                DatabaseEditableObjectFile databaseFile = (DatabaseEditableObjectFile) virtualFile;
                return databaseFile.getObject();
            }

            if (virtualFile instanceof SourceCodeFile) {
                SourceCodeFile sourceCodeFile = (SourceCodeFile) virtualFile;
                return sourceCodeFile.getDatabaseFile().getObject();
            }
        }
        return null;
    }

    public static SourceCodeFile getSourceCodeFile(XSourcePosition sourcePosition) {
        if (sourcePosition != null) {
            VirtualFile virtualFile = sourcePosition.getFile();
            if (virtualFile instanceof DatabaseEditableObjectFile) {
                DatabaseEditableObjectFile databaseFile = (DatabaseEditableObjectFile) virtualFile;
                return (SourceCodeFile) databaseFile.getMainContentFile();
            }

            if (virtualFile instanceof SourceCodeFile) {
                return (SourceCodeFile) virtualFile;
            }
        }
        return null;
    }
}
