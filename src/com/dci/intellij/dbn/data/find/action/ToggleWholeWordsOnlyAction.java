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
import com.intellij.find.FindSettings;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class ToggleWholeWordsOnlyAction extends DataSearchHeaderToggleAction {
    public ToggleWholeWordsOnlyAction(DataSearchComponent searchComponent) {
        super(searchComponent, "W&hole Words");
    }

    @Override
    public boolean isSelected(AnActionEvent e) {
        return getEditorSearchComponent().getFindModel().isWholeWordsOnly();
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        e.getPresentation().setEnabled(!getEditorSearchComponent().getFindModel().isRegularExpressions());
        e.getPresentation().setVisible(true);
    }

    @Override
    public void setSelected(AnActionEvent e, boolean state) {
        FindSettings.getInstance().setLocalWholeWordsOnly(state);
        getEditorSearchComponent().getFindModel().setWholeWordsOnly(state);
    }
}
