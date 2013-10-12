package com.dci.intellij.dbn.language.sql;

import com.dci.intellij.dbn.language.common.DBLanguageFile;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;

public class SQLFile extends DBLanguageFile {
    public SQLFile(FileViewProvider fileViewProvider) {
        super(fileViewProvider, SQLFileType.INSTANCE, SQLLanguage.INSTANCE);
    }

    private SQLFile(Project project) {
        super(project, SQLFileType.INSTANCE, SQLLanguage.INSTANCE);
    }

    public static SQLFile createEmptyFile(Project project){
        return new SQLFile(project);
    }
}