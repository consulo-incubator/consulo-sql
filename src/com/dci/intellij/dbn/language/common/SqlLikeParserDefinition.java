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

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.language.common.element.ElementType;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageVersion;
import com.intellij.lang.LanguageVersionableParserDefinition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

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

	@NotNull
	@Override
	public TokenSet getCommentTokens(@NotNull LanguageVersion languageVersion)
	{
		if(languageVersion.getName().equals("ANY"))
		{
			SqlLikeLanguage sqlLikeLanguage = (SqlLikeLanguage) languageVersion.getLanguage();
			return sqlLikeLanguage.getSharedTokenTypes().getCommentTokens();
		}
		return super.getCommentTokens(languageVersion);
	}

	public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right)
	{
		return SpaceRequirements.MAY;
	}
}
