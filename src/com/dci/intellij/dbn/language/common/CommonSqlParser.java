package com.dci.intellij.dbn.language.common;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.element.NamedElementType;
import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageVersion;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;

public class CommonSqlParser implements PsiParser
{
	@NotNull
	public ASTNode parse(IElementType rootElementType, PsiBuilder builder, LanguageVersion languageVersion)
	{
		SqlLikeLanguageVersion sqlLikeLanguageVersion = (SqlLikeLanguageVersion) languageVersion;

		ElementTypeBundle elements = sqlLikeLanguageVersion.getElementTypes();
		long timestamp = System.currentTimeMillis();
		builder.setDebugMode(SettingsUtil.isDebugEnabled);
		PsiBuilder.Marker marker = builder.mark();
		NamedElementType root = elements.getNamedElementType(sqlLikeLanguageVersion.getDefaultParseRootId());
		if(root == null)
		{
			root = elements.getRootElementType();
		}

		boolean advancedLexer = false;

		try
		{
			while(!builder.eof())
			{
				int currentOffset = builder.getCurrentOffset();
				root.getParser().parse(null, builder, true, 0, timestamp);
				if(currentOffset == builder.getCurrentOffset())
				{
					TokenType tokenType = (TokenType) builder.getTokenType();
					/*if (tokenType.isChameleon()) {
                        PsiBuilder.Marker injectedLanguageMarker = builder.mark();
                        builder.advanceLexer();
                        injectedLanguageMarker.done((IElementType) tokenType);
                    }
                    else*/
					if(tokenType instanceof ChameleonTokenType)
					{
						PsiBuilder.Marker injectedLanguageMarker = builder.mark();
						builder.advanceLexer();
						injectedLanguageMarker.done((IElementType) tokenType);
					}
					else
					{
						builder.advanceLexer();
					}
					advancedLexer = true;
				}
			}
		}
		catch(ParseException e)
		{
			while(!builder.eof())
			{
				builder.advanceLexer();
				advancedLexer = true;
			}
		}
		catch(StackOverflowError e)
		{
			marker.rollbackTo();
			marker = builder.mark();
			while(!builder.eof())
			{
				builder.advanceLexer();
				advancedLexer = true;
			}

		}

		if(!advancedLexer)
		{
			builder.advanceLexer();
		}
		marker.done(rootElementType);
		return builder.getTreeBuilt();
	}
}
