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

package com.dci.intellij.dbn.execution.method.action;

import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.execution.method.MethodExecutionInput;
import com.dci.intellij.dbn.object.DBSchema;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.DumbAwareAction;

public class SetExecutionSchemaAction extends DumbAwareAction {
    private MethodExecutionInput executionInput;
    private DBSchema schema;

    public SetExecutionSchemaAction(MethodExecutionInput executionInput, DBSchema schema) {
        super(schema.getName(), null, schema.getIcon());
        this.executionInput = executionInput;
        this.schema = schema;
    }

    public void actionPerformed(AnActionEvent e) {
        executionInput.setExecutionSchema(schema);
    }

    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        presentation.setText(NamingUtil.enhanceUnderscoresForDisplay(schema.getName()));
        presentation.setIcon(schema.getIcon());
        presentation.setDescription(schema.getDescription());
    }
}
