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

package com.dci.intellij.dbn.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.Icon;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.common.DevNullStreams;
import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.GenericDatabaseElement;
import com.dci.intellij.dbn.navigation.psi.NavigationPsiCache;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectBundle;
import com.dci.intellij.dbn.object.common.list.DBObjectList;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.UnknownFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ArrayUtilRt;

public class DatabaseObjectListFile<T extends DBObjectList> extends VirtualFile implements DBVirtualFile
{
	protected T objectList;

	protected String name;
	protected String path;
	protected String url;

	public DatabaseObjectListFile(T objectList)
	{
		this.objectList = objectList;
		this.name = NamingUtil.capitalize(objectList.getName());
	}

	public T getObjectList()
	{
		return objectList;
	}

	@Override
	public ConnectionHandler getConnectionHandler()
	{
		return objectList.getConnectionHandler();
	}

	public Project getProject()
	{
		return objectList.getProject();
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof DatabaseObjectListFile)
		{
			DatabaseObjectListFile objectListFile = (DatabaseObjectListFile) obj;
			return objectListFile.getObjectList().equals(getObjectList());
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		GenericDatabaseElement parent = objectList.getParent();
		if(parent instanceof DBObject)
		{
			DBObject object = (DBObject) parent;
			String qualifiedName = object.getQualifiedNameWithType() + "." + getName();
			return qualifiedName.hashCode();
		}

		if(parent instanceof DBObjectBundle)
		{
			DBObjectBundle objectBundle = (DBObjectBundle) parent;
			String qualifiedName = objectBundle.getConnectionHandler().getName() + "." + getName();
			return qualifiedName.hashCode();
		}

		return super.hashCode();
	}

	@Override
	public void dispose()
	{
		objectList = null;
	}

	@Override
	@NotNull
	@NonNls
	public String getName()
	{
		return name;
	}

	@Override
	public String getPresentableName()
	{
		return name;
	}

	@Override
	@NotNull
	public FileType getFileType()
	{
		return UnknownFileType.INSTANCE;
	}

	@Override
	@NotNull
	public DatabaseFileSystem getFileSystem()
	{
		return DatabaseFileSystem.getInstance();
	}

	@NotNull
	@Override
	public String getPath()
	{
		if(path == null)
		{
			//path = DatabaseFileSystem.createPath(object);
			path = "";
		}
		return path;
	}

	@Override
	@NotNull
	public String getUrl()
	{
		if(url == null)
		{
			//url = DatabaseFileSystem.createUrl(object);
			url = "";
		}
		return url;
	}

	@Override
	public boolean isWritable()
	{
		return false;
	}

	@Override
	public boolean isDirectory()
	{
		return true;
	}

	@Override
	public boolean isValid()
	{
		return true;
	}

	@Override
	public boolean isInLocalFileSystem()
	{
		return false;
	}

	@Override
	@Nullable
	public VirtualFile getParent()
	{
		GenericDatabaseElement parent = objectList.getParent();
		if(parent instanceof DBObject)
		{
			DBObject parentObject = (DBObject) parent;
			return NavigationPsiCache.getPsiDirectory(parentObject).getVirtualFile();
		}

		if(parent instanceof DBObjectBundle)
		{
			DBObjectBundle objectBundle = (DBObjectBundle) parent;
			return NavigationPsiCache.getPsiDirectory(objectBundle.getConnectionHandler()).getVirtualFile();
		}

		return null;
	}

	@Override
	public Icon getIcon()
	{
		return null;
	}

	@Override
	public VirtualFile[] getChildren()
	{
		return VirtualFile.EMPTY_ARRAY;
	}

	@Override
	@NotNull
	public OutputStream getOutputStream(Object o, long l, long l1) throws IOException
	{
		return DevNullStreams.OUTPUT_STREAM;
	}

	@Override
	@NotNull
	public byte[] contentsToByteArray() throws IOException
	{
		return ArrayUtilRt.EMPTY_BYTE_ARRAY;
	}

	@Override
	public long getTimeStamp()
	{
		return 0;
	}

	@Override
	public long getLength()
	{
		return 0;
	}

	@Override
	public void refresh(boolean b, boolean b1, Runnable runnable)
	{

	}

	@Override
	public InputStream getInputStream() throws IOException
	{
		return DevNullStreams.INPUT_STREAM;
	}

	@Override
	public long getModificationStamp()
	{
		return 1;
	}

	@Override
	public String getExtension()
	{
		return null;
	}
}

