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
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.filter.name.ObjectNameFilterSettings;
import com.dci.intellij.dbn.object.filter.name.ui.ObjectNameFilterSettingsForm;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;

import java.awt.*;

public class CreateFilterAction extends ObjectNameFilterAction{
    private static final DBObjectType[] OBJECT_TYPES = new DBObjectType[] {
            DBObjectType.SCHEMA,
            DBObjectType.USER,
            DBObjectType.ROLE,
            DBObjectType.PRIVILEGE,
            DBObjectType.TABLE,
            DBObjectType.VIEW,
            DBObjectType.MATERIALIZED_VIEW,
            DBObjectType.NESTED_TABLE,
            DBObjectType.INDEX,
            DBObjectType.CONSTRAINT,
            DBObjectType.TRIGGER,
            DBObjectType.SYNONYM,
            DBObjectType.SEQUENCE,
            DBObjectType.PROCEDURE,
            DBObjectType.FUNCTION,
            DBObjectType.PACKAGE,
            DBObjectType.TYPE,
            DBObjectType.DIMENSION,
            DBObjectType.CLUSTER,
            DBObjectType.DBLINK,
    };

    public CreateFilterAction(ObjectNameFilterSettingsForm settingsForm) {
        super("New Filter", Icons.DATASET_FILTER_NEW, settingsForm);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {

        DefaultActionGroup actionGroup = new DefaultActionGroup();
        ObjectNameFilterSettings settings = (ObjectNameFilterSettings) getFiltersTree().getModel();

        for (DBObjectType objectType : OBJECT_TYPES) {
            if (!settings.containsFilter(objectType)) {
                actionGroup.add(new CreateFilterForObjectTypeAction(objectType, settingsForm));
            }
        }
        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                "Select object type",
                actionGroup,
                e.getDataContext(),
                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                true, null, 10);

        Component component = (Component) e.getInputEvent().getSource();
        popup.showUnderneathOf(component);
    }

    @Override
    public void update(AnActionEvent e) {

    }
}
