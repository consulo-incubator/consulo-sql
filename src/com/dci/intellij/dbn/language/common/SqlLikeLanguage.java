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

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.code.common.style.options.CodeStyleCustomSettings;
import com.intellij.lang.BaseLanguageVersion;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageVersion;
import com.intellij.openapi.project.Project;

public abstract class SqlLikeLanguage extends Language
{
	private SharedTokenTypeBundle sharedTokenTypes;
	private LanguageVersion<SqlLikeLanguage> myAnyVersion = new BaseLanguageVersion<SqlLikeLanguage>("ANY", this);

	protected SqlLikeLanguage(final @NonNls String id, final @NonNls String... mimeTypes)
	{
		super(id, mimeTypes);
	}

	public SharedTokenTypeBundle getSharedTokenTypes()
	{
		if(sharedTokenTypes == null)
		{
			sharedTokenTypes = new SharedTokenTypeBundle(this, myAnyVersion);
		}
		return sharedTokenTypes;
	}

	@NotNull
	public SqlLikeLanguageVersion<?> getFirstVersion()
	{
		return (SqlLikeLanguageVersion<?>) getVersions()[0];
	}

	public LanguageVersion<SqlLikeLanguage> getAnyVersion()
	{
		return myAnyVersion;
	}

	public abstract CodeStyleCustomSettings getCodeStyleSettings(Project project);
}
