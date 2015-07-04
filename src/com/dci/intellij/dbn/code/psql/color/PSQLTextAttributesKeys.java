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

import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.ui.SimpleTextAttributes;

import java.awt.Color;

public interface PSQLTextAttributesKeys {
    TextAttributesKey LINE_COMMENT       = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.LineComment",       SyntaxHighlighterColors.LINE_COMMENT.getDefaultAttributes());
    TextAttributesKey BLOCK_COMMENT      = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.BlockComment",      SyntaxHighlighterColors.DOC_COMMENT.getDefaultAttributes());
    TextAttributesKey STRING             = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.String",            SyntaxHighlighterColors.STRING.getDefaultAttributes());
    TextAttributesKey NUMBER             = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.Number",            SyntaxHighlighterColors.NUMBER.getDefaultAttributes());
    TextAttributesKey DATA_TYPE          = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.DataType",          SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    TextAttributesKey ALIAS              = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.Alias",             HighlighterColors.TEXT.getDefaultAttributes());
    TextAttributesKey IDENTIFIER         = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.Identifier",        HighlighterColors.TEXT.getDefaultAttributes());
    TextAttributesKey QUOTED_IDENTIFIER  = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.QuotedIdentifier",  HighlighterColors.TEXT.getDefaultAttributes());
    TextAttributesKey KEYWORD            = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.Keyword",           SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    TextAttributesKey FUNCTION           = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.Function",          SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES.toTextAttributes());
    TextAttributesKey PARAMETER          = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.Parameter",         SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    TextAttributesKey EXCEPTION          = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.Exception",         SyntaxHighlighterColors.KEYWORD.getDefaultAttributes());
    TextAttributesKey OPERATOR           = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.Operator",          SyntaxHighlighterColors.OPERATION_SIGN.getDefaultAttributes());
    TextAttributesKey PARENTHESIS        = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.Parenthesis",       SyntaxHighlighterColors.PARENTHS.getDefaultAttributes());
    TextAttributesKey BRACKET            = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.Brackets",          SyntaxHighlighterColors.BRACKETS.getDefaultAttributes());
    TextAttributesKey UNKNOWN_IDENTIFIER = TextAttributesKey.createTextAttributesKey("DBNavigator.TextAttributes.PSQL.UnknownIdentifier", new TextAttributes(Color.RED, null, null, null, 0));
    TextAttributesKey BAD_CHARACTER      = HighlighterColors.BAD_CHARACTER;
}
