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

package com.dci.intellij.dbn.object.filter.name.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.object.filter.name.CompoundFilterCondition;
import com.dci.intellij.dbn.object.filter.name.FilterCondition;
import com.dci.intellij.dbn.object.filter.name.ObjectNameFilter;
import com.dci.intellij.dbn.object.filter.name.ObjectNameFilterManager;
import com.dci.intellij.dbn.object.filter.name.ui.ObjectNameFilterSettingsForm;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;

import java.util.List;

public class MoveConditionUpAction extends ObjectNameFilterAction{

    public MoveConditionUpAction(ObjectNameFilterSettingsForm settingsForm) {
        super("Move up", Icons.ACTION_MOVE_UP, settingsForm);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Object selection = getSelection();
        if (selection instanceof FilterCondition) {
            FilterCondition condition = (FilterCondition) selection;
            Project project = ActionUtil.getProject(e);
            ObjectNameFilterManager filterManager = ObjectNameFilterManager.getInstance(project);
            filterManager.moveFilterConditionUp(condition, settingsForm);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        Object selection = getSelection();
        Presentation presentation = e.getPresentation();
        if (selection instanceof FilterCondition) {
            FilterCondition condition = (FilterCondition) selection;

            if (condition instanceof ObjectNameFilter) {
                ObjectNameFilter filter = (ObjectNameFilter) condition;
                List<ObjectNameFilter> filters = filter.getSettings().getFilters();
                int index = filters.indexOf(filter);
                presentation.setEnabled(index > 0);
            } else {
                CompoundFilterCondition parentCondition = condition.getParent();
                List<FilterCondition> conditions = parentCondition.getConditions();
                int index = conditions.indexOf(condition);
                presentation.setEnabled(index > 0);
            }

        } else {
            presentation.setEnabled(false);
        }
    }
}
