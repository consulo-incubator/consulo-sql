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
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.common.compatibility.CompatibilityUtil;
import com.dci.intellij.dbn.common.util.DocumentUtil;
import com.dci.intellij.dbn.common.util.EditorUtil;
import com.dci.intellij.dbn.common.util.VirtualFileUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.mapping.FileConnectionMappingManager;
import com.dci.intellij.dbn.connection.mapping.FileConnectionMappingProvider;
import com.dci.intellij.dbn.language.common.element.ElementTypeBundle;
import com.dci.intellij.dbn.language.common.psi.NamedPsiElement;
import com.dci.intellij.dbn.navigation.psi.NavigationPsiCache;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.vfs.DatabaseFile;
import com.dci.intellij.dbn.vfs.DatabaseFileSystem;
import com.dci.intellij.dbn.vfs.DatabaseObjectFile;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.ide.util.EditSourceUtil;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiManager;
import com.intellij.psi.SingleRootFileViewProvider;
import com.intellij.testFramework.LightVirtualFile;

public abstract class DBLanguageFile extends PsiFileBase implements FileConnectionMappingProvider {
    private DBLanguageFileType fileType;
    private ParserDefinition parserDefinition;
    private String parseRootId;
    private ConnectionHandler activeConnection;
    private DBSchema currentSchema;
    private DBObject underlyingObject;

    public DBLanguageFile(FileViewProvider viewProvider, DBLanguageFileType fileType, SqlLikeLanguage language) {
        super(viewProvider, language);
		this.fileType = fileType;
        parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language);
        if (parserDefinition == null) {
            throw new RuntimeException("PsiFileBase: language.getParserDefinition() returned null.");
        }
        VirtualFile virtualFile = viewProvider.getVirtualFile();
        if (virtualFile instanceof SourceCodeFile) {
            SourceCodeFile sourceCodeFile = (SourceCodeFile) virtualFile;
            this.underlyingObject = sourceCodeFile.getObject();
        }

        parseRootId = CompatibilityUtil.getParseRootId(virtualFile);
    }

    public void setUnderlyingObject(DBObject underlyingObject) {
        this.underlyingObject = underlyingObject;
    }

    public DBObject getUnderlyingObject() {
        VirtualFile virtualFile = getVirtualFile();
        if (virtualFile instanceof DatabaseObjectFile) {
            DatabaseObjectFile databaseObjectFile = (DatabaseObjectFile) virtualFile;
            return databaseObjectFile.getObject();
        }

        if (virtualFile instanceof SourceCodeFile) {
            SourceCodeFile sourceCodeFile = (SourceCodeFile) virtualFile;
            return sourceCodeFile.getObject();
        }

        return underlyingObject;
    }

    public DBLanguageFile(Project project,  DBLanguageFileType fileType, @NotNull SqlLikeLanguage language) {
        this(createFileViewProvider(project), fileType, language);
    }

    private static SingleRootFileViewProvider createFileViewProvider(Project project) {
        return new SingleRootFileViewProvider(PsiManager.getInstance(project), new LightVirtualFile());
    }

    public void setParseRootId(String parseRootId) {
        this.parseRootId = parseRootId;
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        visitor.visitFile(this);
    }

    @NotNull
    public ParserDefinition getParserDefinition() {
        return parserDefinition;
    }

    public VirtualFile getVirtualFile() {
        DBLanguageFile originalFile = (DBLanguageFile) getOriginalFile();
        return originalFile == null || originalFile == this ?
                super.getVirtualFile() :
                originalFile.getVirtualFile();

    }

    private FileConnectionMappingManager getConnectionMappingManager() {
        return FileConnectionMappingManager.getInstance(getProject());
    }

    @Nullable
    public ConnectionHandler getActiveConnection() {
        VirtualFile file = getVirtualFile();
        if (file != null) {
            if (VirtualFileUtil.isVirtualFileSystem(file)) {
                DBLanguageFile originalFile = (DBLanguageFile) getOriginalFile();
                return originalFile == null || originalFile == this ? activeConnection : originalFile.getActiveConnection();
            } else {
                return getConnectionMappingManager().getActiveConnection(file);
            }
        }
        return null;
    }

    public void setActiveConnection(ConnectionHandler activeConnection) {
        VirtualFile file = getVirtualFile();
        if (file != null) {
            if (VirtualFileUtil.isVirtualFileSystem(file)) {
                this.activeConnection = activeConnection;
            } else {
                getConnectionMappingManager().setActiveConnection(file, activeConnection);
            }
        }
    }

    public DBSchema getCurrentSchema() {
        VirtualFile file = getVirtualFile();
        if (file != null) {
            if (VirtualFileUtil.isVirtualFileSystem(file)) {
                DBLanguageFile originalFile = (DBLanguageFile) getOriginalFile();
                return originalFile == null || originalFile == this ? currentSchema : originalFile.getCurrentSchema();
            } else {
                return getConnectionMappingManager().getCurrentSchema(file);
            }
        }
        return null;
    }

    public void setCurrentSchema(DBSchema schema) {
        VirtualFile file = getVirtualFile();
        if (file != null) {
            if (VirtualFileUtil.isVirtualFileSystem(file)) {
                this.currentSchema = schema;
            } else {
                getConnectionMappingManager().setCurrentSchema(file, schema);
            }
        }
    }

    public boolean contains(NamedPsiElement element, boolean leniant) {
        PsiElement child = getFirstChild();
        while (child != null) {
            if (child instanceof NamedPsiElement) {
                NamedPsiElement namedPsiElement = (NamedPsiElement) child;
                if (namedPsiElement == element) {
                    return true;
                }
            }
            child = child.getNextSibling();
        }
        if (leniant) {
            child = getFirstChild();
            while (child != null) {
                if (child instanceof NamedPsiElement) {
                    NamedPsiElement namedPsiElement = (NamedPsiElement) child;
                    if (namedPsiElement.matches(element)) {
                        return true;
                    }
                }
                child = child.getNextSibling();
            }
        }

        return false;
    }

    @Override
    public void navigate(boolean requestFocus) {
        Editor selectedEditor = EditorUtil.getSelectedEditor(getProject());
        if (selectedEditor != null) {
            Document document = DocumentUtil.getDocument(getContainingFile());
            Editor[] editors = EditorFactory.getInstance().getEditors(document);
            for (Editor editor : editors) {
                if (editor == selectedEditor) {
                    OpenFileDescriptor descriptor = (OpenFileDescriptor) EditSourceUtil.getDescriptor(this);
                    if (descriptor != null) {
                        descriptor.navigateIn(selectedEditor);
                        return;
                    }
                }
            }
        }
        if (!(getVirtualFile() instanceof DatabaseFile)) {
            super.navigate(requestFocus);
        }
    }

    @NotNull
    public DBLanguageFileType getFileType() {
        return fileType;
    }

    public String getParseRootId() {
        return parseRootId;
    }

    public ElementTypeBundle getElementTypeBundle() {
        return ((SqlLikeLanguageVersion)getLanguageVersion()).getElementTypes();
    }

    @Override
    public PsiDirectory getParent() {
        DBObject underlyingObject = getUnderlyingObject();
        if (underlyingObject != null) {
            DBObject parentObject = underlyingObject.getParentObject();
            return NavigationPsiCache.getPsiDirectory(parentObject);

        }
        return super.getParent();
    }

    @Override
    public boolean isValid() {
        VirtualFile virtualFile = getViewProvider().getVirtualFile();
        return virtualFile.getFileSystem() instanceof DatabaseFileSystem ?
                virtualFile.isValid() :
                super.isValid();
    }
}
