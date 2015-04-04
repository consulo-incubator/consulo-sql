package com.dci.intellij.dbn.language.common.element;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.code.common.style.formatting.FormattingDefinition;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.language.common.TokenTypeIdentifier;
import com.dci.intellij.dbn.language.common.element.lookup.ElementTypeLookupCache;
import com.dci.intellij.dbn.language.common.element.parser.ElementTypeParser;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttributesBundle;
import com.dci.intellij.dbn.language.common.psi.ChameleonPsiElement;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.ILazyParseableElementType;

public class ChameleonElementType extends ILazyParseableElementType implements ElementType, TokenType
{
	private SqlLikeLanguageVersion<?> myLanguageVersion;

	public ChameleonElementType(SqlLikeLanguageVersion languageVersion)
	{
		super("chameleon (" + languageVersion + ")", languageVersion.getLanguage());
		myLanguageVersion = languageVersion;
	}

	@NotNull
	@Override
	public SqlLikeLanguage getLanguage()
	{
		return (SqlLikeLanguage) super.getLanguage();
	}

	@Override
	public SqlLikeLanguageVersion<?> getLanguageVersion()
	{
		return myLanguageVersion;
	}

	@Override
	public String getId()
	{
		return "";
	}

	@Override
	public String getDescription()
	{
		return getDebugName();
	}

	@Override
	public String getDebugName()
	{
		return toString();
	}

	@Override
	public Icon getIcon()
	{
		return null;
	}

	@Override
	public ElementType getParent()
	{
		return null;
	}

	@Override
	public ElementTypeLookupCache getLookupCache()
	{
		return null;
	}

	@Override
	public ElementTypeParser getParser()
	{
		return null;
	}

	@Override
	public FormattingDefinition getFormatting()
	{
		return null;
	}

	@Override
	public void setDefaultFormatting(FormattingDefinition defaults)
	{
	}

	@Override
	public ElementTypeAttributesBundle getAttributes()
	{
		return null;
	}

	@Override
	public boolean is(ElementTypeAttribute attribute)
	{
		return false;
	}

	@Override
	public boolean isLeaf()
	{
		return false;
	}

	@Override
	public boolean isVirtualObject()
	{
		return false;
	}

	@Override
	public boolean isVirtualObjectInsideLookup()
	{
		return false;
	}

	@Override
	public DBObjectType getVirtualObjectType()
	{
		return null;
	}

	@Override
	public PsiElement createPsiElement(ASTNode astNode)
	{
		return new ChameleonPsiElement(astNode, this);
	}

	public String getResolveScopeId()
	{
		return null;
	}

	@Override
	public ElementTypeBundle getElementBundle()
	{
		return null;
	}

	@Override
	public void registerVirtualObject(DBObjectType objectType)
	{
	}

	@Override
	public int getIdx()
	{
		return 0;
	}

	@Override
	public String getValue()
	{
		return null;
	}

	@Override
	public String getTypeName()
	{
		return null;
	}

	@Override
	public boolean isSuppressibleReservedWord()
	{
		return false;
	}

	@Override
	public boolean isIdentifier()
	{
		return false;
	}

	@Override
	public boolean isVariable()
	{
		return false;
	}

	@Override
	public boolean isQuotedIdentifier()
	{
		return false;
	}

	@Override
	public boolean isKeyword()
	{
		return false;
	}

	@Override
	public boolean isFunction()
	{
		return false;
	}

	@Override
	public boolean isParameter()
	{
		return false;
	}

	@Override
	public boolean isDataType()
	{
		return false;
	}

	@Override
	public boolean isCharacter()
	{
		return false;
	}

	@Override
	public boolean isOperator()
	{
		return false;
	}

	@Override
	public boolean isChameleon()
	{
		return true;
	}

	@Override
	public boolean isReservedWord()
	{
		return false;
	}

	@Override
	public boolean isParserLandmark()
	{
		return false;
	}

	@Override
	public TokenTypeIdentifier getTokenTypeIdentifier()
	{
		return null;
	}
}
