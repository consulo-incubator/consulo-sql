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

package com.dci.intellij.dbn.language.sql;

import com.dci.intellij.dbn.code.sql.color.SQLTextAttributesKeys;
import com.dci.intellij.dbn.language.common.DBLanguageSyntaxHighlighter;
import com.dci.intellij.dbn.language.common.SharedTokenTypeBundle;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.common.TokenTypeBundle;

public abstract class SQLSyntaxHighlighter extends DBLanguageSyntaxHighlighter
{
	public SQLSyntaxHighlighter(SqlLikeLanguageVersion<? extends SqlLikeLanguage> languageDialect, String tokenTypesFile)
	{
		super(languageDialect, tokenTypesFile);
		TokenTypeBundle tt = getTokenTypes();
		SharedTokenTypeBundle stt = tt.getSharedTokenTypes();
		colors.put(stt.getIdentifier(), SQLTextAttributesKeys.IDENTIFIER);
		colors.put(stt.getQuotedIdentifier(), SQLTextAttributesKeys.QUOTED_IDENTIFIER);
		colors.put(tt.getTokenType("LINE_COMMENT"), SQLTextAttributesKeys.LINE_COMMENT);
		colors.put(tt.getTokenType("BLOCK_COMMENT"), SQLTextAttributesKeys.BLOCK_COMMENT);
		colors.put(tt.getTokenType("STRING"), SQLTextAttributesKeys.STRING);
		colors.put(tt.getTokenSet("NUMBERS"), SQLTextAttributesKeys.NUMBER);
		colors.put(tt.getTokenSet("KEYWORDS"), SQLTextAttributesKeys.KEYWORD);
		colors.put(tt.getTokenSet("FUNCTIONS"), SQLTextAttributesKeys.FUNCTION);
		colors.put(tt.getTokenSet("PARAMETERS"), SQLTextAttributesKeys.PARAMETER);
		colors.put(tt.getTokenSet("DATA_TYPES"), SQLTextAttributesKeys.DATA_TYPE);
		colors.put(tt.getTokenSet("VARIABLES"), SQLTextAttributesKeys.VARIABLE);
		colors.put(tt.getTokenSet("PARENTHESES"), SQLTextAttributesKeys.PARENTHESIS);
		colors.put(tt.getTokenSet("BRACKETS"), SQLTextAttributesKeys.BRACKET);
		colors.put(tt.getTokenSet("OPERATORS"), SQLTextAttributesKeys.OPERATOR);

		fillMap(colors, tt.getTokenSet("NUMBERS"), SQLTextAttributesKeys.NUMBER);
		fillMap(colors, tt.getTokenSet("KEYWORDS"), SQLTextAttributesKeys.KEYWORD);
		fillMap(colors, tt.getTokenSet("FUNCTIONS"), SQLTextAttributesKeys.FUNCTION);
		fillMap(colors, tt.getTokenSet("PARAMETERS"), SQLTextAttributesKeys.PARAMETER);
		fillMap(colors, tt.getTokenSet("DATA_TYPES"), SQLTextAttributesKeys.DATA_TYPE);
		fillMap(colors, tt.getTokenSet("VARIABLES"), SQLTextAttributesKeys.VARIABLE);

		fillMap(colors, tt.getTokenSet("PARENTHESES"), SQLTextAttributesKeys.PARENTHESIS);
		fillMap(colors, tt.getTokenSet("BRACKETS"), SQLTextAttributesKeys.BRACKET);
		fillMap(colors, tt.getTokenSet("OPERATORS"), SQLTextAttributesKeys.OPERATOR);
	}
}
