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

import com.dci.intellij.dbn.common.DevNullStreams;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.language.sql.SQLFileType;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseConnectionFile extends VirtualFile implements DBVirtualFile {
    private static final byte[] EMPTY_CONTENT = new byte[0];
    private ConnectionHandler connectionHandler;

    private String path;
    private String url;
    private String name;

    public DatabaseConnectionFile(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DatabaseConnectionFile) {
            DatabaseConnectionFile databaseFile = (DatabaseConnectionFile) obj;
            return databaseFile.getConnectionHandler().equals(getConnectionHandler());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return connectionHandler.getQualifiedName().hashCode();
    }

    public Project getProject() {
        return connectionHandler.getProject();
    }

    @Override
    public boolean isInLocalFileSystem() {
        return false;
    }

    /*********************************************************
     *                     VirtualFile                       *
     *********************************************************/
    @NotNull
    @NonNls
    public String getName() {
        if (name == null) {
            name = connectionHandler.getName();
        }
        return name;
    }

    @Override
    public String getPresentableName() {
        return getName();
    }

    @NotNull
    public FileType getFileType() {
        return SQLFileType.INSTANCE;
    }

    @NotNull
    public DatabaseFileSystem getFileSystem() {
        return DatabaseFileSystem.getInstance();
    }

    public String getPath() {
        if (path == null) {
            path = DatabaseFileSystem.createPath(connectionHandler);
        }
        return path;
    }

    @NotNull
    public String getUrl() {
        if (url == null) {
            url = DatabaseFileSystem.createUrl(connectionHandler);
        }
        return url;
    }

    public boolean isWritable() {
        return false;
    }

    public boolean isDirectory() {
        return true;
    }

    public boolean isValid() {
        return true;
    }

    @Nullable
    public VirtualFile getParent() {
        return null;
    }

    public Icon getIcon() {
        return connectionHandler.getIcon();
    }

    public VirtualFile[] getChildren() {
        return VirtualFile.EMPTY_ARRAY;
    }

    @NotNull
    public OutputStream getOutputStream(Object o, long l, long l1) throws IOException {
        return DevNullStreams.OUTPUT_STREAM;
    }

    @NotNull
    public byte[] contentsToByteArray() throws IOException {
        return EMPTY_CONTENT;
    }

    public long getTimeStamp() {
        return 0;
    }

    public long getLength() {
        return 0;
    }

    public void refresh(boolean b, boolean b1, Runnable runnable) {

    }

    public InputStream getInputStream() throws IOException {
        return DevNullStreams.INPUT_STREAM;
    }

    public long getModificationStamp() {
        return 1;
    }

    @Override
    public String getExtension() {
        return null;
    }


    @Override
    public void dispose() {
        connectionHandler = null;
    }
}

