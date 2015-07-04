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

package com.dci.intellij.dbn.generator;

import com.dci.intellij.dbn.common.AbstractProjectComponent;
import com.dci.intellij.dbn.object.DBTable;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StatementGenerationManager extends AbstractProjectComponent {

    private StatementGenerationManager(Project project) {
        super(project);
    }

    public static StatementGenerationManager getInstance(Project project) {
        return project.getComponent(StatementGenerationManager.class);
    }

    public StatementGeneratorResult generateSelectStatement(List<DBObject> objects, boolean enforceAliasUsage) {
        SelectStatementGenerator generator = new SelectStatementGenerator(objects, enforceAliasUsage);
        return generator.generateStatement();
    }

    public StatementGeneratorResult generateInsert(DBTable table) {
        InsertStatementGenerator generator = new InsertStatementGenerator(table);
        return generator.generateStatement();
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "DBNavigator.Project.StatementGenerationManager";
    }
}
