package com.dci.intellij.dbn.language.sql;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.language.common.SqlLikeParserDefinition;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;


public final class SQLParserDefinition extends SqlLikeParserDefinition
{
	private static final SQLFileElementType FILE_TYPE = new SQLFileElementType(SQLLanguage.INSTANCE);

	@NotNull
	@Override
	public IFileElementType getFileNodeType()
	{
		return FILE_TYPE;
	}

	@Override
	public PsiFile createFile(FileViewProvider viewProvider)
	{
		return new SQLFile(viewProvider);
	}
}
