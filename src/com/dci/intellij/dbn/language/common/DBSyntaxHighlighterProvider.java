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

import org.consulo.lombok.annotations.Logger;
import org.jetbrains.annotations.Nullable;
import com.intellij.lang.LanguageVersion;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.PlainSyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.LanguageVersionUtil;

@Logger
public class DBSyntaxHighlighterProvider implements SyntaxHighlighterProvider
{
	@Override
	public SyntaxHighlighter create(FileType fileType, @Nullable Project project, @Nullable VirtualFile virtualFile)
	{
		DBLanguageFileType dbFileType = (DBLanguageFileType) (virtualFile == null ? fileType : virtualFile.getFileType());
		SqlLikeLanguage language = (SqlLikeLanguage) dbFileType.getLanguage();

		LanguageVersion<SqlLikeLanguage> languageVersion = LanguageVersionUtil.findLanguageVersion(language, project, virtualFile);
		if(languageVersion instanceof SqlLikeLanguageVersion)
		{
			return ((SqlLikeLanguageVersion) languageVersion).createSyntaxHighlighter();
		}

		LOGGER.warn("Cant find syntax highlighter: " + dbFileType.getName() + ":" + language.getID());
		return new PlainSyntaxHighlighter();
	}
}
