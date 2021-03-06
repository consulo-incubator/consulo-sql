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

package com.dci.intellij.dbn.data.type.ui;

import com.dci.intellij.dbn.code.common.style.options.CodeStyleCaseOption;
import com.dci.intellij.dbn.code.psql.style.options.PSQLCodeStyleSettings;
import com.dci.intellij.dbn.data.type.DataTypeDefinition;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.util.ui.UIUtil;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import java.awt.Insets;
import java.util.List;

/**
 * @deprecated use DataTypeEditor
 */
public class DataTypePanel {
    private JComboBox typeComboBox;
    private JPanel mainPanel;

    public DataTypePanel(ConnectionHandler connectionHandler) {
        List<DataTypeDefinition> nativeDataTypes = connectionHandler.getInterfaceProvider().getNativeDataTypes().list();
        String[] nativeDataTypeNames = new String[nativeDataTypes.size()];
        PSQLCodeStyleSettings codeStyleSettings =
                PSQLCodeStyleSettings.getInstance(connectionHandler.getProject());

        CodeStyleCaseOption caseOption = codeStyleSettings.getCaseSettings().getDatatypeCaseOption();
        for (int i=0; i<nativeDataTypes.size(); i++) {
            String typeName = nativeDataTypes.get(i).getName();
            typeName = caseOption.changeCase(typeName);
            nativeDataTypeNames[i] = typeName;
        }

        typeComboBox.setModel(new DefaultComboBoxModel(nativeDataTypeNames));
        typeComboBox.setSelectedItem(null);
        typeComboBox.setRenderer(new CellRenderer());
        typeComboBox.setEditor(new ComboBoxEditor());
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public String getDataType() {
        Object value = typeComboBox.getSelectedItem();
        return value == null ? "" : value.toString();
    }

    private class CellRenderer extends ColoredListCellRenderer {
        protected void customizeCellRenderer(JList jList, Object o, int i, boolean b, boolean b1) {
            append(o.toString(), SimpleTextAttributes.REGULAR_ATTRIBUTES);
        }
    }

    private class ComboBoxEditor extends BasicComboBoxEditor {
        private ComboBoxEditor() {
            JTextField textField = (JTextField) this.getEditorComponent();
            textField.setMargin(new Insets(1, 2, 1, 1));
            textField.setBorder(new LineBorder(UIUtil.getPanelBackgound()));

        }
    }
}
