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

package com.dci.intellij.dbn.data.find.action;

import com.dci.intellij.dbn.data.find.DataSearchComponent;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.IconLoader;

public class FindAllAction extends DataSearchHeaderAction implements DumbAware {
    public FindAllAction(DataSearchComponent searchComponent) {
        super(searchComponent);
        getTemplatePresentation().setIcon(IconLoader.getIcon("/actions/export.png"));
        getTemplatePresentation().setDescription("Export matches to Find tool window");
        getTemplatePresentation().setText("Find All");
        registerCustomShortcutSet(ActionManager.getInstance().getAction(IdeActions.ACTION_FIND_USAGES).getShortcutSet(),
                searchComponent.getSearchField());
    }

    @Override
    public void update(final AnActionEvent e) {
        super.update(e);
/*
        Editor editor = getEditorSearchComponent().getEditor();
        Project project = editor.getProject();
        if (project != null) {
            e.getPresentation().setEnabled(getEditorSearchComponent().hasMatches() &&
                    PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument()) != null);
        }
*/
    }

    @Override
    public void actionPerformed(final AnActionEvent e) {
/*
        Editor editor = getEditorSearchComponent().getEditor();
        Project project = editor.getProject();
        if (project != null && !project.isDisposed()) {
            final FindModel model = FindManager.getInstance(project).getFindInFileModel();
            final FindModel realModel = (FindModel) model.clone();
            String text = getEditorSearchComponent().getTextInField();
            if (StringUtil.isEmptyOrSpaces(text)) return;
            realModel.setStringToFind(text);
            FindUtil.findAllAndShow(project, editor, realModel);
        }
*/
    }
}
