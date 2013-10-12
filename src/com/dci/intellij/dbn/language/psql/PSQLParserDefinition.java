package com.dci.intellij.dbn.language.psql;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.language.common.SqlLikeParserDefinition;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;


public final class PSQLParserDefinition extends SqlLikeParserDefinition
{
	private static final IFileElementType FILE_TYPE = new PSQLFileElementType(PSQLLanguage.INSTANCE);

	@NotNull
	@Override
	public IFileElementType getFileNodeType()
	{
		return FILE_TYPE;
	}

	public PsiFile createFile(FileViewProvider viewProvider)
	{
		return new PSQLFile(viewProvider);
	}
}