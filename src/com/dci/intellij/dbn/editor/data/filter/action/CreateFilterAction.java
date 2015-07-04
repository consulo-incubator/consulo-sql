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

package com.dci.intellij.dbn.editor.data.filter.action;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.editor.data.filter.ui.DatasetFilterList;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;

import java.awt.Component;
import java.awt.Point;

public class CreateFilterAction extends AbstractFilterListAction {
    private DefaultActionGroup actionGroup;

    public CreateFilterAction(DatasetFilterList filterList) {
        super(filterList, "Create filter", Icons.ACTION_ADD);
        actionGroup = new DefaultActionGroup();
        actionGroup.add(new CreateBasicFilterAction(filterList));
        actionGroup.add(new CreateCustomFilterAction(filterList));
    }

    public void actionPerformed(AnActionEvent e) {
        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                "Create filter",
                actionGroup,
                e.getDataContext(),
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                true, null, 10);

        //Project project = (Project) e.getDataContext().getData(DataConstants.PROJECT);
        Component component = (Component) e.getInputEvent().getSource();
        showBelowComponent(popup, component);
    }

    private static void showBelowComponent(ListPopup popup, Component component) {
        Point locationOnScreen = component.getLocationOnScreen();
        Point location = new Point(
                (int) (locationOnScreen.getX()),
                (int) locationOnScreen.getY() + component.getHeight());
        popup.showInScreenCoordinates(component, location);
    }
}
