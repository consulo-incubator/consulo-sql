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

package com.dci.intellij.dbn.language.sql.dialect.mysql;

import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.language.common.ChameleonTokenType;
import com.dci.intellij.dbn.language.common.DBLanguageDialectIdentifier;
import com.dci.intellij.dbn.language.common.DBLanguageSyntaxHighlighter;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.sql.SQLLanguage;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;

public class MysqlSQLLanguageDialect extends SqlLikeLanguageVersion<SQLLanguage>
{
	public MysqlSQLLanguageDialect(SQLLanguage sqlLanguage)
	{
		super(DBLanguageDialectIdentifier.MYSQL_SQL, sqlLanguage, "mysql_sql_parser_tokens.xml", "mysql_sql_parser_elements.xml", "mysql_sql_element_tokens.idx", "sql_block");
	}

	@Override
	protected Set<ChameleonTokenType> createChameleonTokenTypes()
	{
		return null;
	}

	protected DBLanguageSyntaxHighlighter createSyntaxHighlighter()
	{
		return new MysqlSQLSyntaxHighlighter(this);
	}

	@NotNull
	@Override
	public Lexer createLexer(@Nullable Project project)
	{
		return new FlexAdapter(new MysqlSQLParserFlexLexer(getTokenTypes()));
	}
}