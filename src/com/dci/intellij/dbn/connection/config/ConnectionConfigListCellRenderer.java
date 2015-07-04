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

package com.dci.intellij.dbn.connection.config;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.connection.ConnectivityStatus;
import com.dci.intellij.dbn.connection.config.ui.GenericDatabaseSettingsForm;
import com.intellij.ui.DottedBorder;
import com.intellij.util.ui.UIUtil;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Component;

public class ConnectionConfigListCellRenderer extends DefaultListCellRenderer{
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ConnectionSettings connectionSettings = (ConnectionSettings) value;
        ConnectionDatabaseSettings databaseSettings = connectionSettings.getDatabaseSettings();
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus );
        GenericDatabaseSettingsForm settingsEditor = databaseSettings.getSettingsEditor();
        String name = settingsEditor == null ?
                databaseSettings.getName() :
                settingsEditor.getConnectionName();

        ConnectivityStatus connectivityStatus = settingsEditor == null ?
                databaseSettings.getConnectivityStatus() :
                settingsEditor.getConnectivityStatus();

        boolean active = settingsEditor == null ?
                databaseSettings.isActive() :
                settingsEditor.isConnectionActive();

        Icon icon = Icons.CONNECTION_DISABLED;
        if (active) {
            icon = connectionSettings.getDatabaseSettings().isNew() ? Icons.CONNECTION_NEW :
                   connectivityStatus == ConnectivityStatus.VALID ? Icons.CONNECTION_ACTIVE :
                   connectivityStatus == ConnectivityStatus.INVALID ? Icons.CONNECTION_INVALID : Icons.CONNECTION_INACTIVE;
        }

        label.setIcon(icon);
        label.setText(name);
        if (!cellHasFocus && isSelected) {
            label.setForeground(list.getForeground());
            label.setBackground(list.hasFocus() ? list.getBackground() : UIUtil.getFocusedFillColor());
            label.setBorder(new DottedBorder(Color.BLACK));
        }
        return label;
    }
}
