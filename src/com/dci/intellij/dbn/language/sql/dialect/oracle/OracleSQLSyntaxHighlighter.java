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

package com.dci.intellij.dbn.language.sql.dialect.oracle;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.sql.SQLSyntaxHighlighter;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;
import com.intellij.lexer.LayeredLexer;
import com.intellij.lexer.Lexer;

public class OracleSQLSyntaxHighlighter extends SQLSyntaxHighlighter
{
	public OracleSQLSyntaxHighlighter(SqlLikeLanguageVersion<? extends SqlLikeLanguage> languageDialect)
	{
		super(languageDialect, "oracle_sql_highlighter_tokens.xml");
	}

	@NotNull
	protected Lexer createLexer()
	{
		FlexLexer flexLexer = new OracleSQLHighlighterFlexLexer(getTokenTypes());
		return new LayeredLexer(new FlexAdapter(flexLexer));
	}
}
