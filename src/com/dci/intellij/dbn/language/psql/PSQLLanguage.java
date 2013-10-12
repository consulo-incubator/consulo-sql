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