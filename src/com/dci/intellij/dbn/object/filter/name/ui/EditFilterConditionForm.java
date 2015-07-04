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

package com.dci.intellij.dbn.object.filter.name.ui;

import com.dci.intellij.dbn.common.ui.ComboBoxSelectionKeyListener;
import com.dci.intellij.dbn.common.ui.DBNForm;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.filter.name.CompoundFilterCondition;
import com.dci.intellij.dbn.object.filter.name.ConditionJoinType;
import com.dci.intellij.dbn.object.filter.name.ConditionOperator;
import com.dci.intellij.dbn.object.filter.name.SimpleFilterCondition;
import com.intellij.openapi.ui.ComboBox;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditFilterConditionForm extends DBNFormImpl implements DBNForm {
    private JPanel mainPanel;
    private JTextField textPatternTextField;
    private JLabel objectNameLabel;
    private ComboBox operatorComboBox;
    private JRadioButton joinTypeAndRadioButton;
    private JRadioButton joinTypeOrRadioButton;
    private JPanel joinTypePanel;
    private JLabel wildcardsHintLabel;

    private SimpleFilterCondition condition;
    public enum Operation {CREATE, EDIT, JOIN}

    public EditFilterConditionForm(CompoundFilterCondition parentCondition, SimpleFilterCondition condition, DBObjectType objectType, Operation operation) {
        this.condition = condition == null ? new SimpleFilterCondition(ConditionOperator.EQUAL, "") : condition;
        joinTypePanel.setVisible(false);
        if (operation == Operation.JOIN) {
            joinTypePanel.setVisible(true);
            if (parentCondition!= null && parentCondition.getJoinType() == ConditionJoinType.AND) {
                joinTypeOrRadioButton.setSelected(true);
            } else {
                joinTypeAndRadioButton.setSelected(true);
            }
        } else if (operation == Operation.CREATE) {
            if (parentCondition != null && parentCondition.getConditions().size() == 1) {
                joinTypePanel.setVisible(true);
                joinTypeAndRadioButton.setSelected(true);
            }
        }

        objectNameLabel.setIcon(objectType.getIcon());
        objectNameLabel.setText(objectType.getName().toUpperCase() + "_NAME");

        operatorComboBox.setModel(new DefaultComboBoxModel(ConditionOperator.values()));
        if (operation == Operation.EDIT) {
            textPatternTextField.setText(condition.getText());
            operatorComboBox.setSelectedItem(condition.getOperator());

        }
        textPatternTextField.selectAll();
        textPatternTextField.setToolTipText("<html>While editing, <br> " +
                "press <b>Up/Down</b> keys to change the operator</html>");
        textPatternTextField.addKeyListener(ComboBoxSelectionKeyListener.create(operatorComboBox, false));

        //wildcardsHintLabel.setIcon(Icons.COMMON_INFO);
        //wildcardsHintLabel.setDisabledIcon(Icons.COMMON_INFO_DISABLED);
        showHideWildcardHint();

        operatorComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showHideWildcardHint();
            }
        });
    }

    private void showHideWildcardHint() {
        ConditionOperator operator = (ConditionOperator) operatorComboBox.getSelectedItem();
        wildcardsHintLabel.setEnabled(operator.allowsWildcards());
    }

    public JComponent getFocusComponent() {
        return textPatternTextField;
    }

    public SimpleFilterCondition getCondition() {
        condition.setOperator((ConditionOperator) operatorComboBox.getSelectedItem());
        condition.setText(textPatternTextField.getText().trim());
        return condition;
    }

    public ConditionJoinType getJoinType() {
        return
            joinTypeAndRadioButton.isSelected() ? ConditionJoinType.AND :
            joinTypeOrRadioButton.isSelected() ? ConditionJoinType.OR : null;
    }

    public JComponent getComponent() {
        return mainPanel;
    }

    public void dispose() {
        super.dispose();
        condition = null;
    }
}
