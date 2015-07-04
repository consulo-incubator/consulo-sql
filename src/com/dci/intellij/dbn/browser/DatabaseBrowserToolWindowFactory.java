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

package com.dci.intellij.dbn.browser;

import com.dci.intellij.dbn.browser.action.AutoscrollFromEditorAction;
import com.dci.intellij.dbn.browser.action.AutoscrollToEditorAction;
import com.dci.intellij.dbn.browser.action.CollapseTreeAction;
import com.dci.intellij.dbn.browser.action.ExpandTreeAction;
import com.dci.intellij.dbn.browser.action.NavigateBackAction;
import com.dci.intellij.dbn.browser.action.NavigateForwardAction;
import com.dci.intellij.dbn.browser.action.OpenSettingsAction;
import com.dci.intellij.dbn.browser.action.ShowObjectPropertiesAction;
import com.dci.intellij.dbn.browser.ui.BrowserToolWindowForm;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ex.ToolWindowEx;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

public class DatabaseBrowserToolWindowFactory implements ToolWindowFactory, DumbAware
{
	@Override
	public void createToolWindowContent(Project project, ToolWindow toolWindow)
	{
		BrowserToolWindowForm toolWindowForm = DatabaseBrowserManager.getInstance(project).getToolWindowForm();

		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

		Content content = contentFactory.createContent(toolWindowForm.getComponent(), null, false);

		toolWindow.getContentManager().addContent(content);

		ToolWindowEx toolWindowEx = (ToolWindowEx) toolWindow;

		OpenSettingsAction openSettingsAction = new OpenSettingsAction();
		openSettingsAction.getTemplatePresentation().setIcon(AllIcons.General.EditItemInSection);

		ShowObjectPropertiesAction showObjectPropertiesAction = new ShowObjectPropertiesAction();
		showObjectPropertiesAction.getTemplatePresentation().setIcon(AllIcons.Actions.Properties);

		ExpandTreeAction expandTreeAction = new ExpandTreeAction();
		expandTreeAction.getTemplatePresentation().setIcon(AllIcons.General.ExpandAll);
		expandTreeAction.getTemplatePresentation().setHoveredIcon(AllIcons.General.ExpandAllHover);

		CollapseTreeAction collapseTreeAction = new CollapseTreeAction();
		collapseTreeAction.getTemplatePresentation().setIcon(AllIcons.General.CollapseAll);
		collapseTreeAction.getTemplatePresentation().setHoveredIcon(AllIcons.General.CollapseAllHover);

		NavigateBackAction backAction = new NavigateBackAction();
		backAction.getTemplatePresentation().setIcon(AllIcons.General.ComboArrowLeft);
		backAction.getTemplatePresentation().setHoveredIcon(AllIcons.General.ComboArrowLeftPassive);

		NavigateForwardAction forwardAction = new NavigateForwardAction();
		forwardAction.getTemplatePresentation().setIcon(AllIcons.General.ComboArrowRight);
		forwardAction.getTemplatePresentation().setHoveredIcon(AllIcons.General.ComboArrowRightPassive);

		toolWindowEx.setTitleActions(
				backAction, forwardAction,
				openSettingsAction, showObjectPropertiesAction,
				expandTreeAction, collapseTreeAction);

		AutoscrollToEditorAction autoscrollToEditorAction = new AutoscrollToEditorAction();
		AutoscrollFromEditorAction autoscrollFromEditorAction = new AutoscrollFromEditorAction();

		toolWindowEx.setAdditionalGearActions(new DefaultActionGroup(autoscrollToEditorAction, autoscrollFromEditorAction));
	}
}
