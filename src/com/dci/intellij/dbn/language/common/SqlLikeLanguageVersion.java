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

import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.jdom.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.common.util.CommonUtil;
import com.dci.intellij.dbn.language.common.element.ChameleonElementType;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.intellij.lang.BaseLanguageVersion;
import com.intellij.lang.LanguageVersionWithParsing;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.project.Project;
import com.intellij.psi.tree.TokenSet;

/**
 * @author VISTALL
 * @since 10.10.13.
 */
public abstract class SqlLikeLanguageVersion<T extends SqlLikeLanguage> extends BaseLanguageVersion<T> implements LanguageVersionWithParsing<T>
{
	private final ElementTypeBundle elementTypes;
	private final TokenTypeBundle tokenTypes;
	private final String defaultParseRootId;
	private Set<ChameleonTokenType> chameleonTokens;
	private ChameleonElementType chameleonElementType;
	private DBLanguageSyntaxHighlighter syntaxHighlighter;

	public SqlLikeLanguageVersion(DBLanguageDialectIdentifier name, T language, String tokenTypesFile, String elementTypesFile, String elementTokenIndexFile, String defaultParseRootId)
	{
		super(name.getValue(), language);
		this.tokenTypes = new TokenTypeBundle(language, this, CommonUtil.loadXmlFile(getClass(), tokenTypesFile));
		Document document = CommonUtil.loadXmlFile(getClass(), elementTypesFile);
		Properties elementTokenIndex = CommonUtil.loadProperties(getClass(), elementTokenIndexFile);
		this.elementTypes = new ElementTypeBundle(language, this, tokenTypes, document, elementTokenIndex);
		this.defaultParseRootId = defaultParseRootId;
	}

	public TokenTypeBundle getHighlighterTokenTypes()
	{
		return getSyntaxHighlighter().getTokenTypes();
	}

	public synchronized DBLanguageSyntaxHighlighter getSyntaxHighlighter()
	{
		if(syntaxHighlighter == null)
		{
			syntaxHighlighter = createSyntaxHighlighter();
		}
		return syntaxHighlighter;
	}

	protected abstract DBLanguageSyntaxHighlighter createSyntaxHighlighter();

	protected Set<ChameleonTokenType> createChameleonTokenTypes()
	{
		return Collections.emptySet();
	}

	public ChameleonElementType getChameleonTokenType(DBLanguageDialectIdentifier dialectIdentifier)
	{
		throw new IllegalArgumentException("Language " + this + " does not support chameleons of type " + dialectIdentifier.getValue());
	}

	@NotNull
	@Override
	public PsiParser createParser(@Nullable Project project)
	{
		return new CommonSqlParser();
	}

	@NotNull
	public TokenSet getWhitespaceTokens()
	{
		return getTokenTypes().getSharedTokenTypes().getWhitespaceTokens();
	}

	@NotNull
	public TokenSet getCommentTokens()
	{
		return getTokenTypes().getSharedTokenTypes().getCommentTokens();
	}

	@NotNull
	public TokenSet getStringLiteralElements()
	{
		return getTokenTypes().getSharedTokenTypes().getStringTokens();
	}

	public ElementTypeBundle getElementTypes()
	{
		return elementTypes;
	}

	public TokenTypeBundle getTokenTypes()
	{
		return tokenTypes;
	}

	public String getDefaultParseRootId()
	{
		return defaultParseRootId;
	}

	public TokenType getInjectedLanguageToken(DBLanguageDialectIdentifier dialectIdentifier)
	{
		if(chameleonTokens == null)
		{
			chameleonTokens = createChameleonTokenTypes();
			if(chameleonTokens == null)
			{
				chameleonTokens = new HashSet<ChameleonTokenType>();
			}
		}
		for(ChameleonTokenType chameleonToken : chameleonTokens)
		{
			if(chameleonToken.getInjectedLanguage().getName().equals(dialectIdentifier.getValue()))
			{
				return chameleonToken;
			}
		}
		return null;
	}

	public boolean isReservedWord(String identifier)
	{
		return getTokenTypes().isReservedWord(identifier);
	}

	public synchronized ChameleonElementType getChameleonElementType()
	{
		if(chameleonElementType == null)
		{
			chameleonElementType = new ChameleonElementType(this);
		}
		return chameleonElementType;
	}
}
