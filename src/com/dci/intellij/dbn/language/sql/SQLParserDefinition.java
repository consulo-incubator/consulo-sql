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
