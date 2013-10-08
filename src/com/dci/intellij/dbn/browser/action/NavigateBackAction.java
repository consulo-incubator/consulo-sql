package com.dci.intellij.dbn.browser.action;

import com.dci.intellij.dbn.browser.DatabaseBrowserManager;
import com.dci.intellij.dbn.browser.ui.DatabaseBrowserTree;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;

public class NavigateBackAction extends DumbAwareAction
{
	public NavigateBackAction()
	{
		super("Back");
	}

	public void actionPerformed(AnActionEvent e)
	{
		Project project = ActionUtil.getProject(e);
		if(project != null)
		{
			DatabaseBrowserManager browserManager = DatabaseBrowserManager.getInstance(project);
			DatabaseBrowserTree activeTree = browserManager.getActiveBrowserTree();
			if(activeTree != null && activeTree.getNavigationHistory().hasPrevious())
			{
				activeTree.navigateBack();
			}
		}
	}
}
