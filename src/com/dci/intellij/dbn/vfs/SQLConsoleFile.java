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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.DocumentUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.sql.SQLFileType;
import com.dci.intellij.dbn.object.DBSchema;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.LanguageVersion;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileSystem;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.PsiDocumentManagerImpl;
import com.intellij.util.LocalTimeCounter;

public class SQLConsoleFile extends VirtualFile implements DatabaseFile, DBVirtualFile {
    private long modificationTimestamp = LocalTimeCounter.currentTime();
    private CharSequence content = "";
    private ConnectionHandler connectionHandler;
    private DBSchema currentSchema;
    protected String name;
    protected String path;
    protected String url;


    public SQLConsoleFile(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
        this.currentSchema = connectionHandler.getUserSchema();
        name = connectionHandler.getName();
        path = DatabaseFileSystem.createPath(connectionHandler) + " CONSOLE";
        url = DatabaseFileSystem.createUrl(connectionHandler);
        setCharset(connectionHandler.getSettings().getDetailSettings().getCharset());
    }

    public PsiFile initializePsiFile(DatabaseFileViewProvider fileViewProvider, SqlLikeLanguage language) {
        ConnectionHandler connectionHandler = getConnectionHandler();
        if (connectionHandler != null) {
			ParserDefinition parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language);
			SqlLikeLanguageVersion languageDialect = connectionHandler.getLanguageDialect(language);
            if (parserDefinition != null && languageDialect != null) {
                DBLanguageFile file = (DBLanguageFile) parserDefinition.createFile(fileViewProvider);
				file.putUserData(LanguageVersion.KEY, languageDialect);
                fileViewProvider.forceCachedPsi(file);
                Document document = DocumentUtil.getDocument(fileViewProvider.getVirtualFile());
                PsiDocumentManagerImpl.cachePsi(document, file);
                return file;
            }
        }
        return null;
    }

    public Icon getIcon() {
        return Icons.FILE_SQL_CONSOLE;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    @Override
    public void dispose() {
        connectionHandler = null;
        currentSchema = null;
    }

    public void setCurrentSchema(DBSchema currentSchema) {
        this.currentSchema = currentSchema;
    }

    public DBSchema getCurrentSchema() {
        return currentSchema;
    }

    @NotNull
    @Override
    public String getName() {
        return name;
    }

    @NotNull
    @Override
    public VirtualFileSystem getFileSystem() {
        return DatabaseFileSystem.getInstance();
    }

    @Override
    public String getPath() {
        return path;
    }

    @NotNull
    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public boolean isWritable() {
        return true;
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean isInLocalFileSystem() {
        return false;
    }

    @Override
    public VirtualFile getParent() {
        return null;
    }

    @Override
    public VirtualFile[] getChildren() {
        return VirtualFile.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return SQLFileType.INSTANCE;
    }

    @NotNull
    public OutputStream getOutputStream(Object requestor, final long modificationTimestamp, long newTimeStamp) throws IOException {
        return new ByteArrayOutputStream() {
            public void close() {
                SQLConsoleFile.this.modificationTimestamp = modificationTimestamp;
                content = toString();
            }
        };
    }

    @NotNull
    public byte[] contentsToByteArray() throws IOException {
        Charset charset = getCharset();
        return content.toString().getBytes(charset.name());
    }

    @Override
    public long getTimeStamp() {
        return 0;
    }

  public long getModificationStamp() {
    return modificationTimestamp;
  }

    @Override
    public long getLength() {
        try {
            return contentsToByteArray().length;
        } catch (IOException e) {
            e.printStackTrace();
            assert false;
            return 0;
        }
    }

    @Override
    public void refresh(boolean asynchronous, boolean recursive, Runnable postRunnable) {
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(contentsToByteArray());
    }

    @Override
    public String getExtension() {
        return "sql";
    }
}
