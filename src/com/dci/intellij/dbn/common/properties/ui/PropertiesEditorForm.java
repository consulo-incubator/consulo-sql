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

package com.dci.intellij.dbn.common.properties.ui;

import com.dci.intellij.dbn.common.ui.DBNForm;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PropertiesEditorForm extends DBNFormImpl implements DBNForm {
    private JPanel mainPanel;
    private JButton addButton;
    private JButton removeButton;
    private JButton moveUpButton;
    private JButton moveDownButton;
    private JScrollPane propertiesTableScrollPane;
    private PropertiesEditorTable propertiesTable;

    public PropertiesEditorForm(Map<String, String> properties) {
        propertiesTable = new PropertiesEditorTable(properties);
        propertiesTableScrollPane.setViewportView(propertiesTable);
        propertiesTableScrollPane.setPreferredSize(new Dimension(200, 80));

        propertiesTable.getSelectionModel().addListSelectionListener(selectionListener);
        updateButtons();
        addButton.addActionListener(actionListener);
        removeButton.addActionListener(actionListener);
        moveUpButton.addActionListener(actionListener);
        moveDownButton.addActionListener(actionListener);

        propertiesTableScrollPane.getViewport().setBackground(propertiesTable.getBackground());
    }

    public void setMoveButtonsVisible(boolean visible) {
        moveUpButton.setVisible(visible);
        moveDownButton.setVisible(visible);
    }
    
    public void setProperties(Map<String, String> properties) {
        propertiesTable.setProperties(properties);
    } 

    private ListSelectionListener selectionListener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            updateButtons();
        }
    };

    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                propertiesTable.insertRow();
            } else if (e.getSource() == removeButton) {
                propertiesTable.removeRow();
            }  else if (e.getSource() == moveUpButton) {
                propertiesTable.moveRowUp();
            } else if  (e.getSource() == moveDownButton) {
                propertiesTable.moveRowDown();
            }
            updateButtons();
        }
    };

    private void updateButtons() {
        removeButton.setEnabled(
                propertiesTable.getModel().getRowCount()  > 0 &&
                propertiesTable.getSelectedRowCount() > 0);
        moveUpButton.setEnabled(
                propertiesTable.getSelectedRowCount() > 0 &&
                propertiesTable.getSelectedRow() > 0);
        moveDownButton.setEnabled(
                propertiesTable.getSelectedRowCount() > 0 &&
                propertiesTable.getSelectedRow() < propertiesTable.getModel().getRowCount() - 1);
    }




    public JComponent getComponent() {
        return mainPanel;
    }

    public void dispose() {
        super.dispose();
    }

    public Map<String, String> getProperties() {
        return propertiesTable.getModel().exportProperties();
    }
}
