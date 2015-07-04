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

package com.dci.intellij.dbn.editor.code.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.ddl.action.AttachDDLFileAction;
import com.dci.intellij.dbn.ddl.action.CreateDDLFileAction;
import com.dci.intellij.dbn.ddl.action.DetachDDLFileAction;
import com.dci.intellij.dbn.object.common.DBSchemaObject;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;

import java.awt.Component;
import java.awt.Point;

public class DDLFileAction extends AbstractSourceCodeEditorAction {
    DefaultActionGroup actionGroup;
    public DDLFileAction() {
        super("DDL File", null, Icons.CODE_EDITOR_DDL_FILE);
    }

    public void actionPerformed(AnActionEvent e) {
        DBSchemaObject object = getSourcecodeFile(e).getObject();
        actionGroup = new DefaultActionGroup();
        actionGroup.add(new CreateDDLFileAction(object));
        actionGroup.add(new AttachDDLFileAction(object));
        actionGroup.add(new DetachDDLFileAction(object));

        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                "DDL File",
                actionGroup,
                e.getDataContext(),
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                true, null, 10);

        //Project project = (Project) e.getDataContext().getData(DataConstants.PROJECT);
        Component component = (Component) e.getInputEvent().getSource();
        showBelowComponent(popup, component);
    }

    public void update(AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        presentation.setIcon(Icons.CODE_EDITOR_DDL_FILE);
        presentation.setText("DDL Files");
    }

    private static void showBelowComponent(ListPopup popup, Component component) {
        Point locationOnScreen = component.getLocationOnScreen();
        Point location = new Point(
                (int) (locationOnScreen.getX()),
                (int) locationOnScreen.getY() + component.getHeight());
        popup.showInScreenCoordinates(component, location);
    }
}
