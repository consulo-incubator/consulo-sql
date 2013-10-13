package com.dci.intellij.dbn.language.common;

import org.jdom.Document;
import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.language.common.element.ChameleonElementType;
import com.intellij.psi.tree.TokenSet;

public class TokenTypeBundle extends DBLanguageTokenTypeBundle
{
	public TokenTypeBundle(SqlLikeLanguage language, SqlLikeLanguageVersion<? extends SqlLikeLanguage> languageVersion, Document document)
	{
		super(language, languageVersion, document);
	}

	public SharedTokenTypeBundle getSharedTokenTypes()
	{
		return language.getSharedTokenTypes();
	}

	@Override
	public SimpleTokenType getCharacterTokenType(int index)
	{
		return getSharedTokenTypes().getCharacterTokenType(index);
	}

	@Override
	public SimpleTokenType getOperatorTokenType(int index)
	{
		return getSharedTokenTypes().getOperatorTokenType(index);
	}

	public SimpleTokenType getTokenType(String id)
	{
		SimpleTokenType tokenType = super.getTokenType(id);
		if(tokenType == null)
		{
			tokenType = getSharedTokenTypes().getTokenType(id);
			if(tokenType == null)
			{
				System.out.println("DEBUG - [" + getLanguage().getID() + "] undefined token type: " + id);
				//log.info("[DBN-WARNING] Undefined token type: " + id);
				return getSharedTokenTypes().getIdentifier();
			}
		}
		return tokenType;
	}

	public TokenSet getTokenSet(String id)
	{
		TokenSet tokenSet = super.getTokenSet(id);
		if(tokenSet == null)
		{
			tokenSet = getSharedTokenTypes().getTokenSet(id);
			if(tokenSet == null)
			{
				System.out.println("DEBUG - [" + getLanguage().getID() + "] undefined token set: " + id);
				//log.info("[DBN-WARNING] Undefined token set '" + id + "'");
				tokenSet = super.getTokenSet("UNDEFINED");
			}
		}
		return tokenSet;
	}

	@NotNull
	@Override
	public TokenSet getRequiredTokenSet(String id)
	{
		TokenSet tokenSet = super.getTokenSet(id);
		if(tokenSet.getTypes().length == 0)
		{
			tokenSet = getSharedTokenTypes().getTokenSet(id);
			if(tokenSet.getTypes().length == 0)
			{
				System.out.println("DEBUG - [" + getLanguage().getID() + "] undefined token set: " + id);
				//log.info("[DBN-WARNING] Undefined token set '" + id + "'");
				tokenSet = super.getTokenSet("UNDEFINED");
			}
		}
		return tokenSet;
	}

	public SimpleTokenType getIdentifier()
	{
		return getSharedTokenTypes().getIdentifier();
	}

	public SimpleTokenType getVariable()
	{
		return getSharedTokenTypes().getVariable();
	}

	public SimpleTokenType getString()
	{
		return getSharedTokenTypes().getString();
	}

	public ChameleonElementType getChameleon(DBLanguageDialectIdentifier dialectIdentifier)
	{
		return getLanguageVersion().getChameleonTokenType(dialectIdentifier);
	}

}
