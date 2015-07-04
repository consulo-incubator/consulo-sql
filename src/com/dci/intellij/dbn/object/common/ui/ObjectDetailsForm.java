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

package com.dci.intellij.dbn.object.common.ui;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.object.common.DBObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ObjectDetailsForm {
    private JLabel connectionLabel;
    private JPanel objectPanel;
    private JPanel mainPanel;
    private DBObject object;

    public ObjectDetailsForm(DBObject object) {
        this.object = object;
        objectPanel.setLayout(new BoxLayout(objectPanel, BoxLayout.X_AXIS));
        ConnectionHandler connectionHandler = object.getConnectionHandler();
        connectionLabel.setText(connectionHandler.getName());
        connectionLabel.setIcon(connectionHandler.getIcon());
        

        java.util.List<DBObject> chain = new ArrayList<DBObject>();
        while (object != null) {
            chain.add(0, object);
            object = object.getParentObject();
        }

        for (int i=0; i<chain.size(); i++) {
            object = chain.get(i);
            if ( i > 0) objectPanel.add(new JLabel(" > "));

            JLabel objectLabel = new JLabel(object.getName(), object.getIcon(), SwingConstants.LEFT);
            if (object == this.object) {
                Font font = objectLabel.getFont().deriveFont(Font.BOLD);
                objectLabel.setFont(font);
            }
            objectPanel.add(objectLabel);
        }

    }

    public JPanel getComponent() {
        return mainPanel;
    }
}
