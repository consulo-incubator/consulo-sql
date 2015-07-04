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

package com.dci.intellij.dbn.language.psql;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.code.psql.style.options.PSQLCodeStyleSettings;
import com.dci.intellij.dbn.code.psql.style.options.PSQLCustomCodeStyleSettings;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.psql.dialect.oracle.OraclePLSQLLanguageDialect;
import com.intellij.lang.LanguageVersion;
import com.intellij.openapi.project.Project;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;

public class PSQLLanguage extends SqlLikeLanguage
{
	public static final PSQLLanguage INSTANCE = new PSQLLanguage();

	private PSQLLanguage()
	{
		super("DBN-PSQL", "text/plsql");
	}

	@NotNull
	@Override
	protected LanguageVersion[] findVersions()
	{
		OraclePLSQLLanguageDialect oraclePLSQLLanguageDialect = new OraclePLSQLLanguageDialect(this);
		return new LanguageVersion[]{oraclePLSQLLanguageDialect};
	}

	public PSQLCodeStyleSettings getCodeStyleSettings(Project project)
	{
		CodeStyleSettings codeStyleSettings = CodeStyleSettingsManager.getSettings(project);
		PSQLCustomCodeStyleSettings customCodeStyleSettings = codeStyleSettings.getCustomSettings(PSQLCustomCodeStyleSettings.class);
		return customCodeStyleSettings.getCodeStyleSettings();
	}
}