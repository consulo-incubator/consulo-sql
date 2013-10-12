package com.dci.intellij.dbn.vfs;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.intellij.psi.PsiFile;

public interface DatabaseFile {
    ConnectionHandler getConnectionHandler();

    PsiFile initializePsiFile(DatabaseFileViewProvider fileViewProvider, SqlLikeLanguage language);
}
