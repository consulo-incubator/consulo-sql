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

package com.dci.intellij.dbn.editor.data.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.editor.data.model.DatasetEditorModelCell;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

public class RevertChangesAction extends AnAction{
    private DatasetEditorModelCell cell;

    public RevertChangesAction(DatasetEditorModelCell cell) {
        super("Revert changes", null, Icons.ACTION_REVERT_CHANGES);
        this.cell = cell;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        cell.revertChanges();
    }


}
