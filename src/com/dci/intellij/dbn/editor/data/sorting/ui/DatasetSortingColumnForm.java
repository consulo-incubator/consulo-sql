package com.dci.intellij.dbn.editor.data.sorting.ui;

import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.data.sorting.SortingInstruction;
import com.dci.intellij.dbn.editor.data.sorting.action.ChangeSortingDirectionAction;
import com.dci.intellij.dbn.editor.data.sorting.action.DeleteSortingCriteriaAction;
import com.dci.intellij.dbn.object.DBColumn;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatasetSortingColumnForm extends DBNFormImpl {
    private JComboBox columnComboBox;
    private JPanel actionsPanel;
    private JPanel mainPanel;

    private DatasetSortingForm parentForm;
    private SortingInstruction<DBColumn> sortingInstruction;

    public DatasetSortingColumnForm(DatasetSortingForm parentForm, SortingInstruction<DBColumn> sortingInstruction) {
        this.parentForm = parentForm;
        this.sortingInstruction = sortingInstruction;
        DBColumn sortingColumn = sortingInstruction.getColumn();
        DBDataset dataset = sortingColumn.getDataset();
        columnComboBox.setRenderer(cellRenderer);
        List<DBColumn> columns = new ArrayList<DBColumn>(dataset.getColumns());
        Collections.sort(columns);
        for (DBColumn column : columns) {
            columnComboBox.addItem(column);
        }

        ActionToolbar actionToolbar = ActionUtil.createActionToolbar(
                "DBNavigator.DataEditor.Sorting.Instruction", true,
                new ChangeSortingDirectionAction(this),
                new DeleteSortingCriteriaAction(this));
        actionsPanel.add(actionToolbar.getComponent(), BorderLayout.CENTER);

        columnComboBox.setSelectedItem(sortingColumn);

    }

    @Override
    public JComponent getComponent() {
        return mainPanel;
    }

    public SortingInstruction<DBColumn> getSortingInstruction() {
        return sortingInstruction;
    }

    public void remove() {
        //parentForm.removeConditionPanel(this);
    }

    private ListCellRenderer cellRenderer = new ColoredListCellRenderer() {
        protected void customizeCellRenderer(JList list, Object value, int index, boolean selected, boolean hasFocus) {
            DBColumn column = (DBColumn) value;
            if (column != null) {
                setIcon(column.getIcon());
                append(column.getName(), SimpleTextAttributes.REGULAR_ATTRIBUTES);
            }
        }
    };
}
