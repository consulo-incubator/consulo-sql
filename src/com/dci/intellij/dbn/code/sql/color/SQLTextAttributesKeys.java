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

package com.dci.intellij.dbn.code.sql.color;

import com.dci.intellij.dbn.language.sql.SQLLanguage;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;

public interface SQLTextAttributesKeys
{
	TextAttributesKey LINE_COMMENT = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.LINE_COMMENT);
	TextAttributesKey BLOCK_COMMENT = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.BLOCK_COMMENT);
	TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.STRING);
	TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.NUMBER);
	TextAttributesKey DATA_TYPE = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.KEYWORD);
	TextAttributesKey ALIAS = TextAttributesKey.createTextAttributesKey("SQL_ALIAS", HighlighterColors.TEXT);
	TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.IDENTIFIER);
	TextAttributesKey QUOTED_IDENTIFIER = TextAttributesKey.createTextAttributesKey("SQL_QUOTED_IDENTIFIER",
			IDENTIFIER);
	TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.KEYWORD);
	TextAttributesKey FUNCTION = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.INSTANCE_METHOD);
	TextAttributesKey PARAMETER = TextAttributesKey.createTextAttributesKey("SQL_PARAMETER",
			DefaultLanguageHighlighterColors.KEYWORD);
	TextAttributesKey OPERATOR = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.OPERATION_SIGN);
	TextAttributesKey PARENTHESIS = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.PARENTHESES);
	TextAttributesKey BRACKET = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.BRACKETS);
	TextAttributesKey CHAMELEON = TextAttributesKey.createTextAttributesKey("SQL_CHAMELEON", new TextAttributes(null,
			null, null, null, 0));
	TextAttributesKey VARIABLE = TextAttributesKey.createTextAttributesKey(SQLLanguage.INSTANCE,
			DefaultLanguageHighlighterColors.INSTANCE_FIELD);
}