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

package com.dci.intellij.dbn.execution.statement;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.execution.statement.processor.StatementExecutionProcessor;
import com.dci.intellij.dbn.language.common.psi.ExecutableBundlePsiElement;
import com.dci.intellij.dbn.language.common.psi.ExecutablePsiElement;
import com.dci.intellij.dbn.language.sql.SQLLanguage;
import com.dci.intellij.dbn.object.DBSchema;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;

public class StatementExecutionInput implements Disposable {
    private StatementExecutionProcessor executionProcessor;
    private ConnectionHandler connectionHandler;
    private DBSchema schema;

    private ExecutablePsiElement originalPsiElement;
    private String originalStatement;
    private String executeStatement;

    public StatementExecutionInput(String originalStatement, String executeStatement, StatementExecutionProcessor executionProcessor) {
        this.executionProcessor = executionProcessor;
        this.connectionHandler = executionProcessor.getActiveConnection();
        this.schema = executionProcessor.getCurrentSchema();
        this.originalStatement = originalStatement;
        this.executeStatement = executeStatement;
    }


    public void setExecuteStatement(String executeStatement) {
        this.executeStatement = executeStatement;
    }

    public String getExecuteStatement() {
        return executeStatement;
    }

    public ExecutablePsiElement getExecutablePsiElement() {
        if (originalPsiElement == null) {
            PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(executionProcessor.getProject());
            PsiFile previewFile = psiFileFactory.createFileFromText("preview.sql", SQLLanguage.INSTANCE, connectionHandler.getLanguageDialect(SQLLanguage.INSTANCE), originalStatement);

            PsiElement firstChild = previewFile.getFirstChild();
            if (firstChild instanceof ExecutableBundlePsiElement) {
                ExecutableBundlePsiElement rootPsiElement = (ExecutableBundlePsiElement) firstChild;
                originalPsiElement = rootPsiElement.getExecutablePsiElements().get(0);
            }
        }
        return originalPsiElement;
    }

    public PsiFile getPreviewFile() {
        PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(executionProcessor.getProject());
        return psiFileFactory.createFileFromText("preview.sql", SQLLanguage.INSTANCE, connectionHandler.getLanguageDialect(SQLLanguage.INSTANCE), executeStatement);
    }

    public boolean isObsolete() {
        return  executionProcessor == null || executionProcessor.isOrphan() ||
                executionProcessor.getActiveConnection() != connectionHandler || // connection changed since execution
                executionProcessor.getCurrentSchema() != schema || // current schema changed since execution
                (executionProcessor.getExecutablePsiElement() != null &&
                        executionProcessor.getExecutablePsiElement().matches(getExecutablePsiElement()) &&
                        !executionProcessor.getExecutablePsiElement().equals(getExecutablePsiElement()));
    }

    public StatementExecutionProcessor getExecutionProcessor() {
        return executionProcessor;
    }

    public Project getProject() {
        return executionProcessor.getProject();
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public DBSchema getSchema() {
        return schema;
    }

    public void dispose() {
        executionProcessor.reset();
        executionProcessor = null;
        connectionHandler = null;

    }
}
