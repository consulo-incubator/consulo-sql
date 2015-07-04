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

package com.dci.intellij.dbn.generator.action;

import com.dci.intellij.dbn.generator.StatementGenerationManager;
import com.dci.intellij.dbn.generator.StatementGeneratorResult;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.openapi.project.Project;

import java.util.List;

public class GenerateSelectStatementAction extends GenerateStatementAction {
    private List<DBObject> selectedObjects;

    public GenerateSelectStatementAction(List<DBObject> selectedObjects) {
        super("SELECT Statement");
        this.selectedObjects = selectedObjects;
    }

    @Override
    protected StatementGeneratorResult generateStatement(Project project) {
        StatementGenerationManager statementGenerationManager = StatementGenerationManager.getInstance(project);
        return statementGenerationManager.generateSelectStatement(selectedObjects, true);
    }
}
