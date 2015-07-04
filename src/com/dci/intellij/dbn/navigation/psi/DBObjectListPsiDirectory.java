package com.dci.intellij.dbn.navigation.psi;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.mustbe.consulo.RequiredReadAction;
import com.dci.intellij.dbn.common.dispose.DisposeUtil;
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.connection.GenericDatabaseElement;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectBundle;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.dci.intellij.dbn.object.common.list.DBObjectList;
import com.dci.intellij.dbn.vfs.DatabaseObjectListFile;
import com.intellij.lang.Language;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.vcs.FileStatus;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.light.LightElement;
import com.intellij.psi.search.PsiElementProcessor;
import com.intellij.util.IncorrectOperationException;

public class DBObjectListPsiDirectory extends LightElement implements PsiDirectory, Disposable
{
	private DatabaseObjectListFile virtualFile;

	public DBObjectListPsiDirectory(DBObjectList objectList)
	{
		super(PsiManager.getInstance(objectList.getProject()), Language.ANY);
		virtualFile = new DatabaseObjectListFile(objectList);
	}

	public DBObjectList getObjectList()
	{
		return virtualFile.getObjectList();
	}

	@Override
	@NotNull
	public VirtualFile getVirtualFile()
	{
		return virtualFile;
	}

	@Override
	public void dispose()
	{
		DisposeUtil.dispose(virtualFile);
		virtualFile = null;
	}

	/**
	 * ******************************************************
	 * PsiElement                       *
	 * *******************************************************
	 */
	@Override
	@NotNull
	public String getName()
	{
		return NamingUtil.capitalize(getObjectList().getName());
	}

	@Override
	public ItemPresentation getPresentation()
	{
		return getObjectList().getPresentation();
	}

	public FileStatus getFileStatus()
	{
		return FileStatus.NOT_CHANGED;
	}

	@Override
	public String toString()
	{
		return getObjectList().toString();
	}

	@Override
	public PsiDirectory getParent()
	{
		GenericDatabaseElement parent = getObjectList().getTreeParent();
		if(parent instanceof DBObject)
		{
			DBObject parentObject = (DBObject) parent;
			return NavigationPsiCache.getPsiDirectory(parentObject);
		}

		if(parent instanceof DBObjectBundle)
		{
			DBObjectBundle objectBundle = (DBObjectBundle) parent;
			return NavigationPsiCache.getPsiDirectory(objectBundle.getConnectionHandler());
		}

		return null;
	}

	@Override
	public void navigate(boolean requestFocus)
	{
		getObjectList().navigate(requestFocus);
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
		for(Object obj : getObjectList().getObjects())
		{
			DBObject object = (DBObject) obj;
			if(object instanceof DBSchemaObject)
			{
				children.add(NavigationPsiCache.getPsiFile(object));
			}
			else
			{
				children.add(NavigationPsiCache.getPsiDirectory(object));
			}
		}
		return children.toArray(new PsiElement[children.size()]);
	}

	/**
	 * ******************************************************
	 * PsiDirectory                   *
	 * *******************************************************
	 */
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
		return new PsiDirectory[0];
	}

	@NotNull
	@Override
	public PsiFile[] getFiles()
	{
		return new PsiFile[0];
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
