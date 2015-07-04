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

package com.dci.intellij.dbn.editor.data.ui;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.ui.DBNColor;
import com.dci.intellij.dbn.common.ui.DBNForm;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.editor.data.DatasetEditorError;
import com.dci.intellij.dbn.editor.data.model.DatasetEditorModelCell;
import com.dci.intellij.dbn.editor.data.ui.table.DatasetEditorTable;
import com.intellij.openapi.ui.popup.ComponentPopupBuilder;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class DatasetEditorErrorForm extends DBNFormImpl implements DBNForm, ChangeListener {
    public static final DBNColor BACKGROUND_COLOR = new DBNColor(
            new Color(0xFFCCCC),
            new Color(0x743A3A));
    private JPanel mainPanel;
    private JLabel errorIconLabel;
    private JTextArea errorMessageTextArea;

    private DatasetEditorModelCell cell;
    private JBPopup popup;

    public DatasetEditorErrorForm(DatasetEditorModelCell cell) {
        this.cell = cell;
        DatasetEditorError error = cell.getError();
        error.addChangeListener(this);
        errorIconLabel.setIcon(Icons.EXEC_MESSAGES_ERROR);
        errorIconLabel.setText("");
        errorMessageTextArea.setText(StringUtil.wrap(error.getMessage(), 60, ": ,."));
        Color backgroundColor = BACKGROUND_COLOR;
        errorMessageTextArea.setBackground(backgroundColor);
        errorMessageTextArea.setFont(mainPanel.getFont());
        mainPanel.setBackground(backgroundColor);

        ComponentPopupBuilder popupBuilder = JBPopupFactory.getInstance().createComponentPopupBuilder(mainPanel, mainPanel);
        popup = popupBuilder.createPopup();
    }

    public void show() {
        DatasetEditorTable table = cell.getRow().getModel().getEditorTable();
        Rectangle rectangle = table.getCellRect(cell.getRow().getIndex(), cell.getIndex(), false);

        if (table.isShowing()) {
            Point tableLocation = table.getLocationOnScreen();
            int x = (int) (tableLocation.getX() + rectangle.getLocation().getX() + 4);
            int y = (int) (tableLocation.getY() + rectangle.getLocation().getY() + 20);
            Point cellLocation = new Point(x, y);
            popup.showInScreenCoordinates(table, cellLocation);
        }
    }

    public JBPopup getPopup() {
        return popup;
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public void stateChanged(ChangeEvent e) {
        if (popup.isVisible()) popup.cancel();
    }

    public void dispose() {
        super.dispose();
        popup.dispose();
        popup = null;
        cell = null;
    }
}
