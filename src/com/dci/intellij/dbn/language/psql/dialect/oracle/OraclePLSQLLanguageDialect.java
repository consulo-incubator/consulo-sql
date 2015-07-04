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

package com.dci.intellij.dbn.language.psql.dialect.oracle;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.language.common.DBLanguageDialectIdentifier;
import com.dci.intellij.dbn.language.common.DBLanguageSyntaxHighlighter;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.psql.PSQLLanguage;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;

public class OraclePLSQLLanguageDialect extends SqlLikeLanguageVersion<PSQLLanguage>
{
	public OraclePLSQLLanguageDialect(PSQLLanguage psqlLanguage)
	{
		super(DBLanguageDialectIdentifier.ORACLE_PLSQL, psqlLanguage, "oracle_plsql_parser_tokens.xml", "oracle_plsql_parser_elements.xml", "oracle_plsql_element_tokens.idx", "plsql_block");
	}

	protected DBLanguageSyntaxHighlighter createSyntaxHighlighter()
	{
		return new OraclePLSQLSyntaxHighlighter(this);
	}

	@NotNull
	@Override
	public Lexer createLexer(@Nullable Project project)
	{
		return new FlexAdapter(new OraclePLSQLParserFlexLexer(getTokenTypes()));
	}
}
