package com.dci.intellij.dbn.language.common;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.element.lookup.ElementTypeLookupCache;
import com.dci.intellij.dbn.language.common.element.parser.ElementTypeParser;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttribute;
import com.dci.intellij.dbn.language.common.element.util.ElementTypeAttributesBundle;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

public class ChameleonTokenType extends SimpleTokenType implements ElementType
{
	private SqlLikeLanguageVersion injectedLanguage;

	public ChameleonTokenType(@Nullable SqlLikeLanguageVersion hostLanguage, SqlLikeLanguageVersion injectedLanguage)
	{
		super(injectedLanguage + " block", hostLanguage.getLanguage(), hostLanguage);
		this.injectedLanguage = injectedLanguage;
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
		return (SqlLikeLanguageVersion<?>) super.getLanguageVersion();
	}

	public SqlLikeLanguageVersion getInjectedLanguage()
	{
		return injectedLanguage;
	}

	public String getDebugName()
	{
		return toString();
	}

	public Icon getIcon()
	{
		return null;
	}

	public ElementType getParent()
	{
		return null;
	}

	public ElementTypeLookupCache getLookupCache()
	{
		return null;
	}

	public ElementTypeParser getParser()
	{
		return null;
	}

	public boolean is(ElementTypeAttribute attribute)
	{
		return false;
	}

	public boolean isLeaf()
	{
		return false;
	}

	public boolean isVirtualObject()
	{
		return false;
	}

	public boolean isVirtualObjectInsideLookup()
	{
		return false;
	}

	public DBObjectType getVirtualObjectType()
	{
		return null;
	}

	public PsiElement createPsiElement(ASTNode astNode)
	{
		return new ASTWrapperPsiElement(astNode);
	}

	public String getResolveScopeId()
	{
		return null;
	}

	public ElementTypeBundle getElementBundle()
	{
		return null;
	}

	public void registerVirtualObject(DBObjectType objectType)
	{

	}

	@Override
	public ElementTypeAttributesBundle getAttributes()
	{
		return null;
	}
}
