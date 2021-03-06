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

import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.code.common.style.formatting.FormattingDefinition;
import com.dci.intellij.dbn.code.common.style.formatting.FormattingDefinitionFactory;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageVersion;
import com.intellij.psi.tree.IElementType;

public class SimpleTokenType extends IElementType implements TokenType {
    private String id;
    private String value;
    private String description;
    private boolean isSuppressibleReservedWord;
    private TokenTypeIdentifier tokenTypeIdentifier;
    private int idx;
    private int hashCode;
    private FormattingDefinition formatting;
	private LanguageVersion<?> myLanguageVersion;

    public SimpleTokenType(@NotNull @NonNls String debugName, Language language, @Nullable LanguageVersion languageVersion) {
        super(debugName, language);
		myLanguageVersion = languageVersion;
	}

    /*public SimpleTokenType(SimpleTokenType source, Language language) {
        super(source.toString(), language);
        this.id = source.getId();
        this.value = source.getValue();
        this.description = source.getDescription();
        isSuppressibleReservedWord = source.isSuppressibleReservedWord();
        this.tokenTypeIdentifier = source.getTokenTypeIdentifier();
        this.idx = source.getIdx();

        formatting = FormattingDefinitionFactory.cloneDefinition(source.getFormatting());
    }*/

    public SimpleTokenType(Element element, Language language, LanguageVersion languageVersion) {
        super(element.getAttributeValue("id"), language);
        id = element.getAttributeValue("id");
        value = element.getAttributeValue("value");
        description = element.getAttributeValue("description");

        String indexString = element.getAttributeValue("index");
        if (StringUtil.isNotEmptyOrSpaces(indexString)) {
            idx = Integer.parseInt(indexString);
        }

        String type = element.getAttributeValue("type");
        tokenTypeIdentifier = TokenTypeIdentifier.getIdentifier(type);
        isSuppressibleReservedWord = isReservedWord() && !Boolean.parseBoolean(element.getAttributeValue("reserved"));
		try
		{
			hashCode = (language.getDisplayName() + id).hashCode();
		}
		catch(Exception e)
		{
			System.out.println(language + " " + languageVersion);
			e.printStackTrace();
		}

		formatting = FormattingDefinitionFactory.loadDefinition(element);
    }

    public LanguageVersion<?> getLanguageVersion()
    {
        return myLanguageVersion;
    }

    public void setDefaultFormatting(FormattingDefinition defaultFormatting) {
        formatting = FormattingDefinitionFactory.mergeDefinitions(formatting, defaultFormatting);
    }

    public String getId() {
        return id;
    }


    public int getIdx() {
        return idx;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public String getDescription() {
        return description;
    }

    public String getTypeName() {
        return tokenTypeIdentifier.getName();
    }

    public int compareTo(Object o) {
        SimpleTokenType tokenType = (SimpleTokenType) o;
        return getValue().compareTo(tokenType.getValue());
    }

    public boolean isSuppressibleReservedWord() {
        return isReservedWord() && isSuppressibleReservedWord;
    }

    public boolean isIdentifier() {
        return getSharedTokenTypes().isIdentifier(this);
    }

    public boolean isVariable() {
        return getSharedTokenTypes().isVariable(this);
    }

    public boolean isQuotedIdentifier() {
        return this == getSharedTokenTypes().getQuotedIdentifier();
    }

    public boolean isKeyword() {
        return tokenTypeIdentifier == TokenTypeIdentifier.KEYWORD;
    }

    public boolean isFunction() {
        return tokenTypeIdentifier == TokenTypeIdentifier.FUNCTION;
    }

    public boolean isParameter() {
        return tokenTypeIdentifier == TokenTypeIdentifier.PARAMETER;
    }

    public boolean isDataType() {
        return tokenTypeIdentifier == TokenTypeIdentifier.DATATYPE;
    }

    public boolean isCharacter() {
        return tokenTypeIdentifier == TokenTypeIdentifier.CHARACTER;
    }

    public boolean isOperator() {
        return tokenTypeIdentifier == TokenTypeIdentifier.OPERATOR;
    }

    public boolean isChameleon() {
        return tokenTypeIdentifier == TokenTypeIdentifier.CHAMELEON;
    }

    public boolean isReservedWord() {
        return isKeyword() || isFunction() || isParameter() || isDataType();
    }

    public boolean isParserLandmark() {
        return isKeyword() || isFunction() || isParameter() || isCharacter() || isOperator();
        //return isCharacter() || isOperator() || !isSuppressibleReservedWord();
    }

    public TokenTypeIdentifier getTokenTypeIdentifier() {
        return tokenTypeIdentifier;
    }

    @Override
    public FormattingDefinition getFormatting() {
        if (formatting == null) {
            formatting = new FormattingDefinition();
        }
        return formatting;
    }

    private SharedTokenTypeBundle getSharedTokenTypes() {
        Language lang = getLanguage();
        if (lang instanceof SqlLikeLanguage) {
            SqlLikeLanguage language = (SqlLikeLanguage) lang;
            return language.getSharedTokenTypes();
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof SimpleTokenType) {
            SimpleTokenType simpleTokenType = (SimpleTokenType) obj;
            return simpleTokenType.getLanguage().equals(getLanguage()) &&
                    simpleTokenType.getId().equals(getId());
        }
        return false;
    }

    public int hashCode() {
        return hashCode;
    }

}
