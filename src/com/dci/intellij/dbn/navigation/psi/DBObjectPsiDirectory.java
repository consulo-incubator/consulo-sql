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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.mustbe.consulo.RequiredReadAction;
import com.dci.intellij.dbn.connection.GenericDatabaseElement;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.list.DBObjectList;
import com.dci.intellij.dbn.object.common.list.DBObjectListContainer;
import com.intellij.lang.Language;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.light.LightElement;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.util.IncorrectOperationException;

public class DBObjectPsiDirectory extends LightElement implements PsiDirectory, Disposable
{
	private DBObject object;

	public DBObjectPsiDirectory(DBObject object)
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
	public String toString()
	{
		return object.toString();
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

	@RequiredReadAction
	@Override
	@NotNull
	public PsiElement[] getChildren()
	{
		List<PsiElement> children = new ArrayList<PsiElement>();
		DBObjectListContainer childObjects = getObject().getChildObjects();
		if(childObjects != null)
		{
			Collection<DBObjectList<DBObject>> objectLists = childObjects.getObjectLists();
			if(objectLists != null)
			{
				for(DBObjectList objectList : objectLists)
				{
					children.add(NavigationPsiCache.getPsiDirectory(objectList));
				}
				return children.toArray(new PsiElement[children.size()]);
			}
		}

		return PsiElement.EMPTY_ARRAY;
	}

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
		return true;
	}

	@Override
	public void checkSetName(String name) throws IncorrectOperationException
	{

	}

	@Override
	public PsiDirectory getParentDirectory()
	{
		return getParent();
	}

	@NotNull
	@Override
	public PsiDirectory[] getSubdirectories()
	{
		return PsiDirectory.EMPTY_ARRAY;
	}

	@NotNull
	@Override
	public PsiFile[] getFiles()
	{
		return PsiFile.EMPTY_ARRAY;
	}

	@Override
	public PsiDirectory findSubdirectory(@NotNull String s)
	{
		return null;
	}

	@Override
	public PsiFile findFile(@NotNull String s)
	{
		return null;
	}

	@NotNull
	@Override
	public PsiDirectory createSubdirectory(@NotNull String s) throws IncorrectOperationException
	{
		throw new IncorrectOperationException("Operation not supported");
	}

	@Override
	public void checkCreateSubdirectory(@NotNull String s) throws IncorrectOperationException
	{
		throw new IncorrectOperationException("Operation not supported");
	}

	@NotNull
	@Override
	public PsiFile createFile(@NotNull String s) throws IncorrectOperationException
	{
		throw new IncorrectOperationException("Operation not supported");
	}

	@NotNull
	@Override
	public PsiFile copyFileFrom(@NotNull String s, @NotNull PsiFile psiFile) throws IncorrectOperationException
	{
		throw new IncorrectOperationException("Operation not supported");
	}

	@Override
	public void checkCreateFile(@NotNull String s) throws IncorrectOperationException
	{
		throw new IncorrectOperationException("Operation not supported");
	}
}
