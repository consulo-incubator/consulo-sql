package com.dci.intellij.dbn.language.sql;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.code.sql.style.options.SQLCodeStyleSettings;
import com.dci.intellij.dbn.code.sql.style.options.SQLCustomCodeStyleSettings;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.sql.dialect.iso92.Iso92SQLLanguageDialect;
import com.dci.intellij.dbn.language.sql.dialect.mysql.MysqlSQLLanguageDialect;
import com.dci.intellij.dbn.language.sql.dialect.oracle.OracleSQLLanguageDialect;
import com.intellij.lang.LanguageVersion;
import com.intellij.openapi.project.Project;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;

public class SQLLanguage extends SqlLikeLanguage
{
	public static final SQLLanguage INSTANCE = new SQLLanguage();


	private SQLLanguage()
	{
		super("DBN-SQL", "text/sql");
	}

	@NotNull
	@Override
	protected LanguageVersion[] findVersions()
	{
		LanguageVersion oracleSQLLanguageDialect = new OracleSQLLanguageDialect(this);
		LanguageVersion mysqlSQLLanguageDialect = new MysqlSQLLanguageDialect(this);
		LanguageVersion iso92SQLLanguageDialect = new Iso92SQLLanguageDialect(this);
		return new LanguageVersion[]{
				oracleSQLLanguageDialect,
				mysqlSQLLanguageDialect,
				iso92SQLLanguageDialect
		};
	}

	public SQLCodeStyleSettings getCodeStyleSettings(Project project)
	{
		CodeStyleSettings codeStyleSettings = CodeStyleSettingsManager.getSettings(project);
		SQLCustomCodeStyleSettings customCodeStyleSettings = codeStyleSettings.getCustomSettings(SQLCustomCodeStyleSettings.class);
		return customCodeStyleSettings.getCodeStyleSettings();
	}
}
