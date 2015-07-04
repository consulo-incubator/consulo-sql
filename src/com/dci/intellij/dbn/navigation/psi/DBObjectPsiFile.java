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

package com.dci.intellij.dbn.navigation.psi;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.connection.GenericDatabaseElement;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.list.DBObjectList;
import com.dci.intellij.dbn.vfs.DatabaseFileViewProvider;
import com.intellij.lang.FileASTNode;
import com.intellij.lang.Language;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.UnknownFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiInvalidElementAccessException;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.light.LightElement;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.util.IncorrectOperationException;

public class DBObjectPsiFile extends LightElement implements PsiFile, Disposable
{
	private DBObject object;

	public DBObjectPsiFile(DBObject object)
	{
		super(PsiManager.getInstance(object.getProject()), Language.ANY);
		this.object = object;
	}

	public DBObject getObject()
	{
		return object;
	}

	@Override
	public void dispose()
	{
		object = null;
	}

	@Override
	public FileASTNode getNode()
	{
		return null;
	}

	@Override
	@NotNull
	public String getName()
	{
		return getObject().getName();
	}

	@Override
	public ItemPresentation getPresentation()
	{
		return getObject();
	}

	public FileStatus getFileStatus()
	{
		return FileStatus.NOT_CHANGED;
	}

	@Override
	@NotNull
	public Project getProject() throws PsiInvalidElementAccessException
	{
		return getObject().getProject();
	}


	@Override
	public PsiDirectory getParent()
	{
		GenericDatabaseElement parent = object.getTreeParent();
		if(parent instanceof DBObjectList)
		{
			DBObjectList objectList = (DBObjectList) parent;
			return NavigationPsiCache.getPsiDirectory(objectList);
		}
		return null;
	}


	@Override
	public void navigate(boolean requestFocus)
	{
		getObject().navigate(requestFocus);
	}

	@Override
	public boolean canNavigate()
	{
		return true;
	}

	@Override
	public boolean canNavigateToSource()
	{
		return false;
	}


	/**
	 * ******************************************************
	 * PsiFile                        *
	 * *******************************************************
	 */
	@Override
	@NotNull
	public VirtualFile getVirtualFile()
	{
		return getObject().getVirtualFile();
	}

	@Override
	public boolean processChildren(PsiElementProcessor<PsiFileSystemItem> processor)
	{
		return false;
	}

	@Override
	@NotNull
	public PsiElement setName(@NotNull String name) throws IncorrectOperationException
	{
		throw new IncorrectOperationException("Operation not supported");
	}

	@Override
	public boolean isDirectory()
	{
		return false;
	}

	@Override
	public PsiDirectory getContainingDirectory()
	{
		return getParent();
	}

	@Override
	public long getModificationStamp()
	{
		return 0;
	}

	@Override
	@NotNull
	public PsiFile getOriginalFile()
	{
		return this;
	}

	@Override
	@NotNull
	public FileType getFileType()
	{
		return UnknownFileType.INSTANCE;
	}

	@Override
	@NotNull
	public PsiFile[] getPsiRoots()
	{
		return new PsiFile[0];
	}

	@Override
	@NotNull
	public FileViewProvider getViewProvider()
	{
		return new DatabaseFileViewProvider(PsiManager.getInstance(getProject()), getVirtualFile(), true);
	}

	@Override
	public void checkSetName(String name) throws IncorrectOperationException
	{

	}

	@Override
	public String toString()
	{
		return object.toString();
	}
}
