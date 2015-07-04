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

package com.dci.intellij.dbn.execution.method.history.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.execution.method.MethodExecutionManager;
import com.dci.intellij.dbn.execution.method.history.ui.MethodExecutionHistoryTree;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.Project;

public class ShowGroupedTreeAction extends ToggleAction {
    MethodExecutionHistoryTree tree;

    public ShowGroupedTreeAction(MethodExecutionHistoryTree tree) {
        super("Group by Program", "Show grouped by program", Icons.ACTION_GROUP);
        this.tree = tree;
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return tree.isGrouped();
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        getTemplatePresentation().setText(state ? "Ungroup" : "Group by Program");
        tree.showGrouped(state);
        Project project = ActionUtil.getProject(e);
        MethodExecutionManager.getInstance(project).setGroupHistoryEntries(state);

    }
}
