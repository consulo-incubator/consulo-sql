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

package com.dci.intellij.dbn.common.ui;

import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.ex.ComboBoxAction;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public abstract class DBNComboBoxAction extends ComboBoxAction {
    public JComponent createCustomComponent(Presentation presentation) {
    JPanel panel=new JPanel(new GridBagLayout());
    ComboBoxButton button = new ComboBoxButton(presentation);
        GridBagConstraints constraints = new GridBagConstraints(
                0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(3, 3, 3, 3), 0, 0);
        panel.add(button, constraints);
        panel.setFocusable(false);
        return panel;
    }
}
