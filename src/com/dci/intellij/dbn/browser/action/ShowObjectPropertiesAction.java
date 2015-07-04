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

package com.dci.intellij.dbn.browser.action;

import com.dci.intellij.dbn.browser.DatabaseBrowserManager;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;

public class ShowObjectPropertiesAction extends ToggleAction implements DumbAware {
    public ShowObjectPropertiesAction() {
        super("Show properties", null, Icons.BROWSER_OBJECT_PROPERTIES);
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        Project project = ActionUtil.getProject(e);
        return project != null &&
                DatabaseBrowserManager.getInstance(project).getShowObjectProperties().value();
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        Project project = ActionUtil.getProject(e);
        DatabaseBrowserManager.getInstance(project).showObjectProperties(state);
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        e.getPresentation().setText(isSelected(e) ? "Hide object properties" : "Show object properties");
    }
}
