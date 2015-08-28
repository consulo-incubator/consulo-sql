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

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;

public interface SQLTextAttributesKeys
{
	TextAttributesKey LINE_COMMENT = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL" +
			".LineComment", DefaultLanguageHighlighterColors.LINE_COMMENT);
	TextAttributesKey BLOCK_COMMENT = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL" +
			".BlockComment", DefaultLanguageHighlighterColors.DOC_COMMENT);
	TextAttributesKey STRING = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL.String",
			DefaultLanguageHighlighterColors.STRING);
	TextAttributesKey NUMBER = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL.Number",
			DefaultLanguageHighlighterColors.NUMBER);
	TextAttributesKey DATA_TYPE = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL.DataType",
			DefaultLanguageHighlighterColors.KEYWORD);
	TextAttributesKey ALIAS = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL.Alias",
			HighlighterColors.TEXT);
	TextAttributesKey IDENTIFIER = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL" +
			".Identifier", HighlighterColors.TEXT);
	TextAttributesKey QUOTED_IDENTIFIER = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL" +
			".QuotedIdentifier", HighlighterColors.TEXT);
	TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL.Keyword",
			DefaultLanguageHighlighterColors.KEYWORD);
	TextAttributesKey FUNCTION = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL.Function",
			DefaultLanguageHighlighterColors.INSTANCE_METHOD);
	TextAttributesKey PARAMETER = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL" +
			".Parameter", DefaultLanguageHighlighterColors.KEYWORD);
	TextAttributesKey OPERATOR = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL.Operator",
			DefaultLanguageHighlighterColors.OPERATION_SIGN);
	TextAttributesKey PARENTHESIS = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL" +
			".Parenthesis", DefaultLanguageHighlighterColors.PARENTHESES);
	TextAttributesKey BRACKET = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL.Brackets",
			DefaultLanguageHighlighterColors.BRACKETS);
	TextAttributesKey CHAMELEON = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL" +
			".Chameleon", new TextAttributes(null, null, null, null, 0));
	TextAttributesKey VARIABLE = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.SQL.Variable",
			DefaultLanguageHighlighterColors.INSTANCE_FIELD);
}