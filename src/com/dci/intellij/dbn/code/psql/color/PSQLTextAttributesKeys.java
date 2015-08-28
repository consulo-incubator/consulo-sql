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

package com.dci.intellij.dbn.code.psql.color;

import com.dci.intellij.dbn.language.psql.PSQLLanguage;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;

public interface PSQLTextAttributesKeys
{
	TextAttributesKey LINE_COMMENT = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.LINE_COMMENT);
	TextAttributesKey BLOCK_COMMENT = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.BLOCK_COMMENT);
	TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.STRING);
	TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.NUMBER);
	TextAttributesKey DATA_TYPE = TextAttributesKey.createTextAttributesKey("PSQL_DATA_TYPE",
			DefaultLanguageHighlighterColors.KEYWORD);
	TextAttributesKey ALIAS = TextAttributesKey.createTextAttributesKey("PSQL_ALIAS",
			DefaultLanguageHighlighterColors.IDENTIFIER);
	TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.IDENTIFIER);
	TextAttributesKey QUOTED_IDENTIFIER = TextAttributesKey.createTextAttributesKey("PSQL_QUOTED_IDENTIFIER",
			IDENTIFIER);
	TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.KEYWORD);
	TextAttributesKey FUNCTION = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.INSTANCE_METHOD);
	TextAttributesKey PARAMETER = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.PARAMETER);
	TextAttributesKey EXCEPTION = TextAttributesKey.createTextAttributesKey("PSQL_EXCEPTION",
			DefaultLanguageHighlighterColors.KEYWORD);
	TextAttributesKey OPERATOR = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.OPERATION_SIGN);
	TextAttributesKey PARENTHESIS = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.PARENTHESES);
	TextAttributesKey BRACKET = TextAttributesKey.createTextAttributesKey(PSQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.BRACKETS);
}
