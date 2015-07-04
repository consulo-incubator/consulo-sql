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

package com.dci.intellij.dbn.language.common;

import java.util.HashMap;
import java.util.Map;

import org.jdom.Document;
import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.common.util.CommonUtil;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;

public abstract class DBLanguageSyntaxHighlighter extends SyntaxHighlighterBase {
    protected Map colors = new HashMap();
    private Map backgrounds = new HashMap();

    private SqlLikeLanguageVersion<? extends SqlLikeLanguage> languageDialect;
    private TokenTypeBundle tokenTypes;

    public DBLanguageSyntaxHighlighter(SqlLikeLanguageVersion<? extends SqlLikeLanguage> languageDialect, String tokenTypesFile) {
        Document document = CommonUtil.loadXmlFile(getClass(), tokenTypesFile);
        tokenTypes = new TokenTypeBundle(languageDialect.getLanguage(), languageDialect, document);
        this.languageDialect = languageDialect;
    }

    public SqlLikeLanguageVersion<? extends SqlLikeLanguage> getLanguageDialect() {
        return languageDialect;
    }

    @NotNull
    protected abstract Lexer createLexer();

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(getAttributeKeys(tokenType, backgrounds), getAttributeKeys(tokenType, colors));
    }

    private TextAttributesKey getAttributeKeys(IElementType tokenType, Map map) {
        return (TextAttributesKey) map.get(tokenType);
    }

    public TokenTypeBundle getTokenTypes() {
        return tokenTypes;
    }

    @NotNull
    public Lexer getHighlightingLexer() {
        return createLexer();
    }
}
