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

import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.generator.StatementGeneratorResult;
import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.openapi.project.Project;

import java.sql.SQLException;

public class GenerateDDLStatementAction extends GenerateStatementAction {
    private DBObject object;
    public GenerateDDLStatementAction(DBObject object) {
        super("DDL Statement");
        this.object = object;

    }

    @Override
    protected StatementGeneratorResult generateStatement(Project project) {
        StatementGeneratorResult result = new StatementGeneratorResult();
        try {
            String statement = object.extractDDL();
            if (StringUtil.isEmptyOrSpaces(statement)) {
                String message =
                        "Could not extract DDL statement for " + object.getQualifiedNameWithType() + ".\n" +
                        "You may not have enough rights to perform this action. Please contact your database administrator for more details.";
                result.getMessages().addErrorMessage(message);
            }

            result.setStatement(statement);
        } catch (SQLException e) {
            result.getMessages().addErrorMessage(
                    "Could not extract DDL statement for " + object.getQualifiedNameWithType() + ".\n" +
                    "Cause: " + e.getMessage());
        }
       return result;
    }
}
