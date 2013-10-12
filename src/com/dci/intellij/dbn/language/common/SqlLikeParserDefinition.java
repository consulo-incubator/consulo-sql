package com.dci.intellij.dbn.language.common;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageVersionableParserDefinition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;

public abstract class SqlLikeParserDefinition extends LanguageVersionableParserDefinition
{
	@NotNull
	public PsiElement createElement(ASTNode astNode)
	{
		IElementType et = astNode.getElementType();
		if(et instanceof ElementType)
		{
			ElementType elementType = (ElementType) et;
			//SQLFile file = lookupFile(astNode);
			return elementType.createPsiElement(astNode);
		}
		return new ASTWrapperPsiElement(astNode);
	}

	public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right)
	{
		return SpaceRequirements.MAY;
	}
}
