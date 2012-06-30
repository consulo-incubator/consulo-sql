package com.dci.intellij.dbn.debugger.execution.ui;

import com.dci.intellij.dbn.common.ui.UIForm;
import com.dci.intellij.dbn.common.ui.UIFormImpl;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.object.common.DBSchemaObject;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.List;

public class CompileDebugDependenciesForm extends UIFormImpl implements UIForm {
    private JTextArea hintTextArea;
    private JList objectList;
    private JPanel mainPanel;
    private JCheckBox rememberSelectionCheckBox;
    private JPanel headerPanel;
    private JLabel runProgramLabel;

    public CompileDebugDependenciesForm(List<DBSchemaObject> compileList, DBSchemaObject selectedObject) {
        hintTextArea.setText(StringUtil.wrap(
            "The program you are trying to debug or some of its dependencies are not compiled with debug information. " +
            "This may result in breakpoints being ignored during the debug execution, as well as missing information about execution stacks and variables.\n" +
            "In order to achieve full debugging support it is advisable to compile the respective programs in debug mode.\n\n" +
            "Do you want to compile dependencies now?", 90, ": ,."));

        objectList.setCellRenderer(new ObjectListCellRenderer());
        DefaultListModel model = new DefaultListModel();

        for (DBSchemaObject schemaObject : compileList) {
            model.addElement(schemaObject);
        }
        objectList.setModel(model);
        objectList.setSelectedValue(selectedObject, true);
        hintTextArea.setBackground(mainPanel.getBackground());
        hintTextArea.setFont(mainPanel.getFont());

        if (getEnvironmentSettings(selectedObject.getProject()).getVisibilitySettings().getDialogHeaders().value()) {
            headerPanel.setBackground(selectedObject.getEnvironmentType().getColor());
        }
        runProgramLabel.setText(selectedObject.getQualifiedName());
        runProgramLabel.setIcon(selectedObject.getIcon());
    }

    protected boolean rememberSelection() {
        return rememberSelectionCheckBox.isSelected();
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public List<DBSchemaObject> getSelection() {
        List<DBSchemaObject> objects = new ArrayList<DBSchemaObject>();
        for (Object o : objectList.getSelectedValues()) {
            objects.add((DBSchemaObject) o);
        }
        return objects;
    }

    public void selectAll() {
        objectList.setSelectionInterval(0, objectList.getModel().getSize() -1);
    }

    public void selectNone() {
        objectList.clearSelection();
    }

    public void dispose() {
        super.dispose();
    }
}