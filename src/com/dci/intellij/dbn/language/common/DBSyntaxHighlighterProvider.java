package com.dci.intellij.dbn.language.common;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.connection.mapping.FileConnectionMappingManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

public class DBSyntaxHighlighterProvider implements SyntaxHighlighterProvider {
    public SyntaxHighlighter create(FileType fileType, @Nullable Project project, @Nullable VirtualFile virtualFile) {
        DBLanguageFileType dbFileType = (DBLanguageFileType) (virtualFile == null ? fileType : virtualFile.getFileType());
        DBLanguage language = (DBLanguage) dbFileType.getLanguage();

        DBLanguageDialect mainLanguageDialect = language.getMainLanguageDialect();
        if (project != null && virtualFile != null) {
            ConnectionHandler connectionHandler = FileConnectionMappingManager.getInstance(project).getActiveConnection(virtualFile);
            DBLanguageDialect languageDialect = connectionHandler == null ?
                    mainLanguageDialect :
                    connectionHandler.getLanguageDialect(language);
            return languageDialect == null ?
                    mainLanguageDialect.createSyntaxHighlighter() :
                    languageDialect.createSyntaxHighlighter();
        }

        return mainLanguageDialect.getSyntaxHighlighter();

    }
}
