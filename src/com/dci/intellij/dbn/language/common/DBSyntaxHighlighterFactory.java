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

package com.dci.intellij.dbn.language.common;

import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.fileTypes.PlainSyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class DBSyntaxHighlighterFactory extends SyntaxHighlighterFactory {
    @NotNull
    @Override
    public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
       /* if (virtualFile != null && virtualFile.getFileType() instanceof DBLanguageFileType) {
            DBLanguageFileType fileType = (DBLanguageFileType) virtualFile.getFileType();
            SqlLikeLanguage language = (SqlLikeLanguage) fileType.getLanguage();

            ConnectionHandler connectionHandler = FileConnectionMappingManager.getInstance(project).getActiveConnection(virtualFile);
            DBLanguageDialect languageDialect = connectionHandler == null ?
                    language.getMainLanguageDialect() :
                    connectionHandler.getLanguageDialect(language);

            return languageDialect.createSyntaxHighlighter();
        }

        return PlainSyntaxHighlighterFactory.getSyntaxHighlighter(Language.ANY, project, virtualFile);*/
		return new PlainSyntaxHighlighter();
    }
}
