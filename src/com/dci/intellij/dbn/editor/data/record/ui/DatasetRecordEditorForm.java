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

package com.dci.intellij.dbn.editor.data.record.ui;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.common.dispose.DisposeUtil;
import com.dci.intellij.dbn.common.ui.DBNForm;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.common.ui.DBNHeaderForm;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.data.record.ColumnSortingType;
import com.dci.intellij.dbn.editor.data.DatasetEditorManager;
import com.dci.intellij.dbn.editor.data.model.DatasetEditorColumnInfo;
import com.dci.intellij.dbn.editor.data.model.DatasetEditorModelCell;
import com.dci.intellij.dbn.editor.data.model.DatasetEditorModelRow;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.UIUtil;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatasetRecordEditorForm extends DBNFormImpl implements DBNForm {
    private JPanel actionsPanel;
    private JPanel columnsPanel;
    private JPanel mainPanel;
    private JScrollPane columnsPanelScrollPane;
    private JPanel headerPanel;

    private List<DatasetRecordEditorColumnForm> columnForms = new ArrayList<DatasetRecordEditorColumnForm>();

    private DatasetEditorModelRow row;

    public DatasetRecordEditorForm(DatasetEditorModelRow row) {
        this.row = row;
        DBDataset dataset = row.getModel().getDataset();
        Project project = dataset.getProject();

        String headerTitle = dataset.getQualifiedName();
        Icon headerIcon = dataset.getIcon();
        Color headerBackground = UIUtil.getPanelBackground();
        if (getEnvironmentSettings(project).getVisibilitySettings().getDialogHeaders().value()) {
            headerBackground = dataset.getEnvironmentType().getColor();
        }
        DBNHeaderForm headerForm = new DBNHeaderForm(
                headerTitle,
                headerIcon,
                headerBackground);
        headerPanel.add(headerForm.getComponent(), BorderLayout.CENTER);

        ActionToolbar actionToolbar = ActionUtil.createActionToolbar(
                "DBNavigator.Place.DataEditor.TextAreaPopup", true,
                new SortAlphabeticallyAction(),
                ActionUtil.SEPARATOR,
                new FirstRecordAction(),
                new PreviousRecordAction(),
                new NextRecordAction(),
                new LastRecordAction());
        actionsPanel.add(actionToolbar.getComponent(), BorderLayout.WEST);


        columnsPanel.setLayout(new BoxLayout(columnsPanel, BoxLayout.Y_AXIS));

        for (DatasetEditorModelCell cell: row.getCells()) {
            DatasetRecordEditorColumnForm columnForm = new DatasetRecordEditorColumnForm(this, cell);
            columnForms.add(columnForm);
        }
        ColumnSortingType columnSortingType = DatasetEditorManager.getInstance(project).getRecordViewColumnSortingType();
        sortColumns(columnSortingType);

        int[] metrics = new int[]{0, 0};
        for (DatasetRecordEditorColumnForm columnForm : columnForms) {
            metrics = columnForm.getMetrics(metrics);
        }

        for (DatasetRecordEditorColumnForm columnForm : columnForms) {
            columnForm.adjustMetrics(metrics);
        }

        Dimension preferredSize = mainPanel.getPreferredSize();
        int width = (int) preferredSize.getWidth() + 24;
        int height = (int) Math.min(preferredSize.getHeight(), 380);
        mainPanel.setPreferredSize(new Dimension(width, height));

        int scrollUnitIncrement = (int) columnForms.get(0).getComponent().getPreferredSize().getHeight();
        columnsPanelScrollPane.getVerticalScrollBar().setUnitIncrement(scrollUnitIncrement);
    }

    public JComponent getPreferredFocusComponent() {
        return columnForms.get(0).getEditorComponent();
    }

    public JPanel getComponent() {
        return mainPanel;
    }

    public JComponent getColumnsPanel() {
        return columnsPanel;
    }

    public void setRow(DatasetEditorModelRow row) {
        this.row = row;
        for (DatasetEditorModelCell cell : row.getCells()) {
            DatasetRecordEditorColumnForm columnForm = getColumnPanel(cell.getColumnInfo());
            columnForm.setCell(cell);
        }
    }

    private DatasetRecordEditorColumnForm getColumnPanel(DatasetEditorColumnInfo columnInfo) {
        for (DatasetRecordEditorColumnForm columnForm : columnForms) {
            if (columnForm.getCell().getColumnInfo() == columnInfo) {
                return columnForm;
            }
        }
        return null;
    }

    /*********************************************************
     *                   Column sorting                      *
     *********************************************************/
    private void sortColumns(ColumnSortingType columnSortingType) {
        Comparator<DatasetRecordEditorColumnForm> comparator =
                columnSortingType == ColumnSortingType.ALPHABETICAL ? alphabeticComparator :
                columnSortingType == ColumnSortingType.BY_INDEX ? indexedComparator : null;

        if (comparator != null) {
            Collections.sort(columnForms, comparator);
            columnsPanel.removeAll();
            for (DatasetRecordEditorColumnForm columnForm : columnForms) {
                columnsPanel.add(columnForm.getComponent());
            }
            columnsPanel.updateUI();
        }
    }

    private static Comparator<DatasetRecordEditorColumnForm> alphabeticComparator = new Comparator<DatasetRecordEditorColumnForm>() {
        public int compare(DatasetRecordEditorColumnForm columnPanel1, DatasetRecordEditorColumnForm columnPanel2) {
            String name1 = columnPanel1.getCell().getColumnInfo().getName();
            String name2 = columnPanel2.getCell().getColumnInfo().getName();
            return name1.compareTo(name2);
        }
    };

    private static Comparator<DatasetRecordEditorColumnForm> indexedComparator = new Comparator<DatasetRecordEditorColumnForm>() {
        public int compare(DatasetRecordEditorColumnForm columnPanel1, DatasetRecordEditorColumnForm columnPanel2) {
            int index1 = columnPanel1.getCell().getColumnInfo().getColumnIndex();
            int index2 = columnPanel2.getCell().getColumnInfo().getColumnIndex();
            return index1-index2;
        }
    };

    public void focusNextColumnPanel(DatasetRecordEditorColumnForm source) {
        int index = columnForms.indexOf(source);
        if (index < columnForms.size() - 1) {
            DatasetRecordEditorColumnForm columnForm = columnForms.get(index + 1);
            columnForm.getEditorComponent().requestFocus();
        }
    }

    public void focusPreviousColumnPanel(DatasetRecordEditorColumnForm source) {
        int index = columnForms.indexOf(source);
        if (index > 0) {
            DatasetRecordEditorColumnForm columnForm = columnForms.get(index - 1);
            columnForm.getEditorComponent().requestFocus();
        }
    }

    /*********************************************************      
     *                       Actions                         *
     *********************************************************/
    private class SortAlphabeticallyAction extends ToggleAction {
        private SortAlphabeticallyAction() {
            super("Sort columns alphabetically", null, Icons.ACTION_SORT_ALPHA);
        }

        public boolean isSelected(AnActionEvent anActionEvent) {
            Project project = row.getModel().getDataset().getProject();
            ColumnSortingType columnSortingType = DatasetEditorManager.getInstance(project).getRecordViewColumnSortingType();
            return columnSortingType == ColumnSortingType.ALPHABETICAL;
        }

        public void setSelected(AnActionEvent anActionEvent, boolean selected) {
            ColumnSortingType columnSortingType = selected ? ColumnSortingType.ALPHABETICAL : ColumnSortingType.BY_INDEX;
            Project project = row.getModel().getDataset().getProject();
            DatasetEditorManager.getInstance(project).setRecordViewColumnSortingType(columnSortingType);
            sortColumns(columnSortingType);
        }
    }

    private class FirstRecordAction extends AnAction {
        private FirstRecordAction() {
            super("First record", null, Icons.DATA_EDITOR_FIRST_RECORD);
        }

        public void actionPerformed(AnActionEvent e) {
            DatasetEditorModelRow firstRow = row.getModel().getRowAtIndex(0);
            setRow(firstRow);
            row.getModel().getEditorTable().selectRow(0);
        }

        @Override
        public void update(AnActionEvent anactionevent) {
            anactionevent.getPresentation().setEnabled(row.getIndex() > 0);
        }
    }

    private class PreviousRecordAction extends AnAction {
        private PreviousRecordAction() {
            super("Previus record", null, Icons.DATA_EDITOR_PREVIOUS_RECORD);
        }

        public void actionPerformed(AnActionEvent e) {
            if (row.getIndex() > 0) {
                int index = row.getIndex() - 1;
                DatasetEditorModelRow previousRow = row.getModel().getRowAtIndex(index);
                setRow(previousRow);
                row.getModel().getEditorTable().selectRow(index);
            }
        }

        @Override
        public void update(AnActionEvent anactionevent) {
            anactionevent.getPresentation().setEnabled(row.getIndex() > 0);
        }
    }

    private class NextRecordAction extends AnAction {
        private NextRecordAction() {
            super("Next record", null, Icons.DATA_EDITOR_NEXT_RECORD);
        }

        public void actionPerformed(AnActionEvent e) {
            if (row.getIndex() < row.getModel().getSize() -1) {
                int index = row.getIndex() + 1;
                DatasetEditorModelRow nextRow = row.getModel().getRowAtIndex(index);
                setRow(nextRow);
                row.getModel().getEditorTable().selectRow(index);
            }
        }

        @Override
        public void update(AnActionEvent anactionevent) {
            anactionevent.getPresentation().setEnabled(row.getIndex() < row.getModel().getSize() -1);
        }
    }

    private class LastRecordAction extends AnAction {
        private LastRecordAction() {
            super("Last record", null, Icons.DATA_EDITOR_LAST_RECORD);
        }

        public void actionPerformed(AnActionEvent e) {
            int index = row.getModel().getSize() - 1 ;
            DatasetEditorModelRow lastRow = row.getModel().getRowAtIndex(index);
            setRow(lastRow);
            row.getModel().getEditorTable().selectRow(index);
        }

        @Override
        public void update(AnActionEvent anactionevent) {
            anactionevent.getPresentation().setEnabled(row.getIndex() < row.getModel().getSize() -1);
        }
    }

    public void dispose() {
        super.dispose();
        DisposeUtil.disposeCollection(columnForms);
        columnForms = null;
        row = null;
    }
}
