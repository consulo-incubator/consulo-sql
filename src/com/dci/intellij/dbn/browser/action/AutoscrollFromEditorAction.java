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

public class AutoscrollFromEditorAction extends ToggleAction implements DumbAware {

    public AutoscrollFromEditorAction() {
        super("Autoscroll from editor", "", Icons.BROWSER_AUTOSCROLL_FROM_EDITOR);
    }


    public boolean isSelected(AnActionEvent e) {
        Project project = ActionUtil.getProject(e);
        if (project != null) {
            DatabaseBrowserManager browserManager = DatabaseBrowserManager.getInstance(project);
            return browserManager.getAutoscrollFromEditor().value();
        }
        return false;
    }

    public void setSelected(AnActionEvent e, boolean state) {
        Project project = ActionUtil.getProject(e);
        DatabaseBrowserManager browserManager = DatabaseBrowserManager.getInstance(project);
        browserManager.getAutoscrollFromEditor().setValue(state);
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        e.getPresentation().setText("Autoscroll from editor");
    }

}
