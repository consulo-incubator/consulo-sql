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
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.vfs.DatabaseContentFile;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageVersion;
import com.intellij.lang.LanguageVersionResolver;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

/**
 * @author VISTALL
 * @since 11.10.13.
 */
public class SqlLanguageVersionResolver implements LanguageVersionResolver<SqlLikeLanguage>
{
	@SuppressWarnings("unchecked")
	private static LanguageVersion<SqlLikeLanguage> getFirstItem(Language language)
	{
		return language.getVersions()[0];
	}

	@NotNull
	@Override
	public LanguageVersion<SqlLikeLanguage> getLanguageVersion(@NotNull Language language, @Nullable PsiElement element)
	{
		if(element == null)
		{
			return getFirstItem(language);
		}
		PsiFile containingFile = element.getContainingFile();
		if(containingFile == null)
		{
			return getFirstItem(language);
		}

		if(containingFile instanceof DBLanguageFile)
		{
			SqlLikeLanguage sqlLikeLanguage = (SqlLikeLanguage) language;
			ConnectionHandler connectionHandler = ((DBLanguageFile) containingFile).getActiveConnection();
			if(connectionHandler != null)
			{
				SqlLikeLanguageVersion<?> languageDialect = connectionHandler.getLanguageDialect(sqlLikeLanguage);
				if(languageDialect != null)
				{
					return (LanguageVersion<SqlLikeLanguage>) languageDialect;
				}
			}
			else
			{
				return getFirstItem(language);
			}
		}
		VirtualFile virtualFile = containingFile.getVirtualFile();
		if(virtualFile == null)
		{
			return getFirstItem(language);
		}
		return getLanguageVersion(language, element.getProject(), virtualFile);
	}

	@Override
	public LanguageVersion<SqlLikeLanguage> getLanguageVersion(@NotNull Language language, @Nullable Project project, @Nullable VirtualFile virtualFile)
	{
		if(virtualFile instanceof DatabaseContentFile)
		{
			DatabaseContentFile contentFile = (DatabaseContentFile) virtualFile;
			return (LanguageVersion<SqlLikeLanguage>) contentFile.getLanguageDialect();
		}

		return getFirstItem(language);
	}
}
