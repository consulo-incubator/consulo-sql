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

package com.dci.intellij.dbn.connection.config.ui;

import com.dci.intellij.dbn.common.event.EventManager;
import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;
import com.dci.intellij.dbn.common.ui.DBNHeaderForm;
import com.dci.intellij.dbn.connection.config.ConnectionFilterSettings;
import com.intellij.util.ui.UIUtil;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

public class ConnectionFilterSettingsForm extends CompositeConfigurationEditorForm<ConnectionFilterSettings> implements ConnectionPresentationChangeListener{
    private JPanel mainPanel;
    private JPanel objectTypesFilterPanel;
    private JPanel objectNameFiltersPanel;
    private JPanel headerPanel;
    private DBNHeaderForm headerForm;

    private String connectionId;

    public ConnectionFilterSettingsForm(ConnectionFilterSettings settings) {
        super(settings);
        objectTypesFilterPanel.add(settings.getObjectTypeFilterSettings().createComponent(), BorderLayout.CENTER);
        objectNameFiltersPanel.add(settings.getObjectNameFilterSettings().createComponent(), BorderLayout.CENTER);
        headerForm = new DBNHeaderForm();
        headerPanel.add(headerForm.getComponent(), BorderLayout.CENTER);

        EventManager.subscribe(settings.getProject(), ConnectionPresentationChangeListener.TOPIC, this);
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    @Override
    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    @Override
    public void presentationChanged(String name, Icon icon, Color color, String connectionId) {
        if (this.connectionId.equals(connectionId)) {
            if (name != null) headerForm.setTitle(name);
            if (icon != null) headerForm.setIcon(icon);
            headerForm.setBackground(color == null ? UIUtil.getPanelBackground() :color);
        }
    }

    @Override
    public void dispose() {
        EventManager.unsubscribe(this);
        super.dispose();
    }
}
