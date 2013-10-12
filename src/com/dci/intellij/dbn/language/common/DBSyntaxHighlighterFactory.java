package com.dci.intellij.dbn.language.common;

import org.jetbrains.annotations.NotNull;
import com.intellij.openapi.fileTypes.PlainSyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

public class DBSyntaxHighlighterFactory extends SyntaxHighlighterFactory {
    @NotNull
    @Override
    public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
       /* if (virtualFile != null && virtualFile.getFileType() instanceof DBLanguageFileType) {
            DBLanguageFileType fileType = (DBLanguageFileType) virtualFile.getFileType();
            SqlLikeLanguage language = (SqlLikeLanguage) fileType.getLanguage();

            ConnectionHandler connectionHandler = FileConnectionMappingManager.getInstance(project).getActiveConnection(virtualFile);
            DBLanguageDialect languageDialect = connectionHandler == null ?
                    language.getMainLanguageDialect() :
                    connectionHandler.getLanguageDialect(language);

            return languageDialect.createSyntaxHighlighter();
        }

        return PlainSyntaxHighlighterFactory.getSyntaxHighlighter(Language.ANY, project, virtualFile);*/
		return new PlainSyntaxHighlighter();
    }
}
