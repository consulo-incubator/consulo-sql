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

package com.dci.intellij.dbn.editor.data.ui.table;

import com.dci.intellij.dbn.common.thread.ConditionalLaterInvocator;
import com.dci.intellij.dbn.common.thread.ModalTask;
import com.dci.intellij.dbn.common.thread.SimpleLaterInvocator;
import com.dci.intellij.dbn.common.ui.MouseUtil;
import com.dci.intellij.dbn.common.util.ActionUtil;
import com.dci.intellij.dbn.common.util.EditorUtil;
import com.dci.intellij.dbn.common.util.MessageUtil;
import com.dci.intellij.dbn.data.model.ColumnInfo;
import com.dci.intellij.dbn.data.model.DataModelCell;
import com.dci.intellij.dbn.data.preview.LargeValuePreviewPopup;
import com.dci.intellij.dbn.data.record.RecordViewInfo;
import com.dci.intellij.dbn.data.sorting.SortDirection;
import com.dci.intellij.dbn.data.ui.table.basic.BasicTableGutter;
import com.dci.intellij.dbn.data.ui.table.resultSet.ResultSetTable;
import com.dci.intellij.dbn.data.value.LazyLoadedValue;
import com.dci.intellij.dbn.editor.data.DatasetEditor;
import com.dci.intellij.dbn.editor.data.action.DatasetEditorTableActionGroup;
import com.dci.intellij.dbn.editor.data.model.DatasetEditorModel;
import com.dci.intellij.dbn.editor.data.model.DatasetEditorModelCell;
import com.dci.intellij.dbn.editor.data.options.DataEditorGeneralSettings;
import com.dci.intellij.dbn.editor.data.ui.table.cell.DatasetTableCellEditor;
import com.dci.intellij.dbn.editor.data.ui.table.cell.DatasetTableCellEditorFactory;
import com.dci.intellij.dbn.editor.data.ui.table.listener.DatasetEditorHeaderMouseListener;
import com.dci.intellij.dbn.editor.data.ui.table.listener.DatasetEditorKeyListener;
import com.dci.intellij.dbn.editor.data.ui.table.listener.DatasetEditorMouseListener;
import com.dci.intellij.dbn.editor.data.ui.table.renderer.DatasetEditorTableCellRenderer;
import com.dci.intellij.dbn.object.DBColumn;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPopupMenu;
import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.ui.awt.RelativePoint;
import org.jetbrains.annotations.NotNull;

import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.EventObject;

public class DatasetEditorTable extends ResultSetTable {
    private DatasetTableCellEditorFactory cellEditorFactory = new DatasetTableCellEditorFactory();
    private TableCellRenderer cellRenderer;
    private DatasetEditor datasetEditor;
    private boolean isEditingEnabled = true;
    private DatasetEditorMouseListener tableMouseListener = new DatasetEditorMouseListener(this);

    public DatasetEditorTable(DatasetEditor datasetEditor) throws SQLException {
        super(createModel(datasetEditor), false,
                new RecordViewInfo(
                    datasetEditor.getDataset().getQualifiedName(),
                    datasetEditor.getDataset().getIcon()));
        this.datasetEditor = datasetEditor;
        cellRenderer = new DatasetEditorTableCellRenderer(datasetEditor.getProject());

        getModel().setEditorTable(this);
        getSelectionModel().addListSelectionListener(getModel());
        addKeyListener(new DatasetEditorKeyListener(this));
        addMouseListener(tableMouseListener);

        getTableHeader().addMouseListener(new DatasetEditorHeaderMouseListener(this));

        DataProvider dataProvider = datasetEditor.getDataProvider();
        ActionUtil.registerDataProvider(this, dataProvider, false);
        ActionUtil.registerDataProvider(getTableGutter(), dataProvider, false);
        ActionUtil.registerDataProvider(getTableHeader(), dataProvider, false);
    }

    public Project getProject() {
        return datasetEditor.getProject();
    }


    private static DatasetEditorModel createModel(DatasetEditor datasetEditor) throws SQLException {
        return new DatasetEditorModel(datasetEditor.getDataset());
    }

    public boolean isEditingEnabled() {
        return isEditingEnabled;
    }

    public void setEditingEnabled(boolean editingEnabled) {
        isEditingEnabled = editingEnabled;
    }

    public DBDataset getDataset() {
        return getModel().getDataset();
    }

    public String getName() {
        return getModel().isDisposed() ? "Disposed" : getDataset().getName();
    }

    @Override
    public BasicTableGutter createTableGutter() {
        return new DatasetEditorTableGutter(this);
    }

    public DatasetEditorModel getModel() {
        return (DatasetEditorModel) super.getModel();
    }


    public boolean isInserting() {
        return getModel().isInserting();
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        return cellRenderer;
    }

    public void editingStopped(ChangeEvent e) {
        TableCellEditor editor = getCellEditor();
        if (editor != null) {
            if (editor instanceof DatasetTableCellEditor) {
                DatasetTableCellEditor cellEditor = (DatasetTableCellEditor) editor;
                if (cellEditor.isEditable()) {
                    try {
                        Object value = cellEditor.getCellEditorValue();
                        setValueAt(value, editingRow, editingColumn);
                    } catch (Throwable t) {
                        Object value = cellEditor.getCellEditorValueLenient();
                        setValueAt(value, t.getMessage(), editingRow, editingColumn);
                    }
                }
            }
            removeEditor();
        }
        updateTableGutter();
    }

    @Override
    public void removeEditor() {
        new ConditionalLaterInvocator() {
            @Override
            public void run() {
                DatasetEditorTable.super.removeEditor();
            }
        }.start();
    }

    public void updateTableGutter() {
        new ConditionalLaterInvocator() {
            @Override
            public void run() {
                getTableGutter().updateUI();
            }
        }.start();
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        int modelRowIndex = rowIndex;//convertRowIndexToModel(rowIndex);
        int modelColumnIndex = convertColumnIndexToModel(columnIndex);
        getModel().setValueAt(value, modelRowIndex, modelColumnIndex);
    }

    public void setValueAt(Object value, String errorMessage, int rowIndex, int columnIndex) {
        int modelRowIndex = rowIndex;//convertRowIndexToModel(rowIndex);
        int modelColumnIndex = convertColumnIndexToModel(columnIndex);
        getModel().setValueAt(value, errorMessage, modelRowIndex, modelColumnIndex);
    }

    @Override
    public Component prepareEditor(TableCellEditor editor, int rowIndex, int columnIndex) {
        Component component = super.prepareEditor(editor, rowIndex, columnIndex);
        selectCell(rowIndex, columnIndex);

        if (editor instanceof DatasetTableCellEditor) {
            DatasetTableCellEditor cellEditor = (DatasetTableCellEditor) editor;
            DatasetEditorModelCell cell = (DatasetEditorModelCell) getCellAtPosition(rowIndex, columnIndex);
            cellEditor.prepareEditor(cell);
        }
        return component;
    }

    @Override
    public boolean editCellAt(final int row, final int column, final EventObject e) {
        return super.editCellAt(row, column, e);
    }

    @Override
    public TableCellEditor getCellEditor(int rowIndex, int columnIndex) {
        if (isLoading()) {
            return null;
        }

        int modelColumnIndex = getModelColumnIndex(columnIndex);
        ColumnInfo columnInfo = getModel().getColumnInfo(modelColumnIndex);
        return cellEditorFactory.getCellEditor(columnInfo, this);
    }

    @Override
    public TableCellEditor getDefaultEditor(Class<?> columnClass) {
        return super.getDefaultEditor(columnClass);
    }

    @Override
    protected void initLargeValuePopup(LargeValuePreviewPopup viewer) {
        super.initLargeValuePopup(viewer);
    }

    @Override
    protected boolean isLargeValuePopupActive() {
        DataEditorGeneralSettings generalSettings = datasetEditor.getSettings().getGeneralSettings();
        return generalSettings.getLargeValuePreviewActive().value();
    }

    @Override
    public String getToolTipText(@NotNull MouseEvent event) {
        DataModelCell cell = getCellAtLocation(event.getPoint());
        if (cell instanceof DatasetEditorModelCell) {
            DatasetEditorModelCell editorTableCell = (DatasetEditorModelCell) cell;
/*            if (event.isControlDown() && isNavigableCellAtMousePosition()) {
                DBColumn column = editorTableCell.getColumnInfo().getColumn();
                DBColumn foreignKeyColumn = column.getForeignKeyColumn();
                if (foreignKeyColumn != null) {
                    StringBuilder text = new StringBuilder("<html>");
                    text.append("Show ");
                    text.append(foreignKeyColumn.getDataset().getName());
                    text.append(" record");
                    text.append("</html>");
                    return text.toString();
                }
            }*/

            if (editorTableCell.hasError()) {
                StringBuilder text = new StringBuilder("<html>");

                if (editorTableCell.hasError()) {
                    text.append(editorTableCell.getError().getMessage());
                    text.append("<br>");
                }

                if (editorTableCell.isModified() && !(editorTableCell.getUserValue() instanceof LazyLoadedValue)) {
                    text.append("<br>Original value: <b>");
                    text.append(editorTableCell.getOriginalUserValue());
                    text.append("</b></html>");
                } else {
                    text.append("</html>");
                }

                return text.toString();
            }

            if (editorTableCell.isModified() && !event.isControlDown()) {
                if (editorTableCell.getUserValue() instanceof LazyLoadedValue) {
                    return "LOB content has changed";
                } else {
                    return "<HTML>Original value: <b>" + editorTableCell.getOriginalUserValue() + "</b></html>";
                }

            }
        }
        return super.getToolTipText(event);
    }

    public void fireEditingCancel() {
        if (isEditing()) {
            new SimpleLaterInvocator() {
                public void run() {
                    cancelEditing();
                }
            }.start();
        }
    }

    public void cancelEditing() {
        if (isEditing()) {
            TableCellEditor cellEditor = getCellEditor();
            if (cellEditor != null) {
                cellEditor.cancelCellEditing();
            }
        }
    }

    @Override
    public boolean sort(int columnIndex, SortDirection sortDirection) {
        int modelColumnIndex = convertColumnIndexToModel(columnIndex);
        ColumnInfo columnInfo = getModel().getColumnInfo(modelColumnIndex);
        if (columnInfo.isSortable()) {
            if (!isLoading() && super.sort(columnIndex, sortDirection)) {
                if (!getModel().isResultSetExhausted()) {
                    datasetEditor.load(true, true);
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void dispose() {
        cellEditorFactory.dispose();
        super.dispose();
        datasetEditor = null;
    }

    public DatasetEditor getDatasetEditor() {
        return datasetEditor;
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.isControlDown() && isNavigableCellAtMousePosition()) {
            MouseUtil.processMouseEvent(e, tableMouseListener);
        } else {
            super.processMouseEvent(e);
        }
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        if (e.isControlDown() && e.getID() != MouseEvent.MOUSE_DRAGGED && isNavigableCellAtMousePosition()) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            DatasetEditorModelCell cell = (DatasetEditorModelCell) getCellAtMouseLocation();
            DBColumn column = cell.getColumnInfo().getColumn();
            DBColumn foreignKeyColumn = column.getForeignKeyColumn();
            setToolTipText("<html>Show referenced <b>" + foreignKeyColumn.getDataset().getQualifiedName() + "</b> record<html>");
        } else {
            super.processMouseMotionEvent(e);
            setCursor(Cursor.getDefaultCursor());
            setToolTipText(null);
        }
    }

    private boolean isNavigableCellAtMousePosition() {
        DatasetEditorModelCell cell = (DatasetEditorModelCell) getCellAtMouseLocation();
        return cell != null && cell.isNavigable();
    }

    /**********************************************************
     *                  ListSelectionListener                 *
     **********************************************************/
    public void valueChanged(ListSelectionEvent e) {
        super.valueChanged(e);
        DatasetEditorModel model = getModel();

        if (model.isInserting() && !e.getValueIsAdjusting()) {
            int insertRowIndex = getModel().getInsertRowIndex();
            if ((insertRowIndex == e.getFirstIndex() || insertRowIndex == e.getLastIndex()) && getSelectedRow() != insertRowIndex) {
                try {
                    model.postInsertRecord(false, true);
                } catch (SQLException e1) {
                    MessageUtil.showErrorDialog("Could not create row in " + getDataset().getQualifiedNameWithType() + ".", e1);
                }
            }
        }
        startCellEditing(e);
    }

    public void columnSelectionChanged(ListSelectionEvent e) {
        JTableHeader tableHeader = getTableHeader();
        if (tableHeader != null && tableHeader.getDraggedColumn() == null) {
            super.columnSelectionChanged(e);
            if (!e.getValueIsAdjusting()) {
                startCellEditing(e);
            }
        }
    }

    private void startCellEditing(ListSelectionEvent e) {
        if (!isLoading() && isEditingEnabled && getSelectedColumnCount() == 1 && getSelectedRowCount() == 1 && !isEditing() && !e.getValueIsAdjusting() && getDataset().getConnectionHandler().isConnected()) {
            editCellAt(getSelectedRows()[0], getSelectedColumns()[0]);
        }
    }

    public void stopCellEditing() {
        if (isEditing()) {
            getCellEditor().stopCellEditing();
        }
    }

    public RelativePoint getColumnHeaderLocation(DBColumn column) {
        int columnIndex = convertColumnIndexToView(getModel().getHeader().indexOfColumn(column));
        Rectangle rectangle = getTableHeader().getHeaderRect(columnIndex);
        Point point = new Point(
                (int) (rectangle.getX() + rectangle.getWidth() - 20),
                (int) (rectangle.getY() + rectangle.getHeight()) + 20);
        return new RelativePoint(getTableHeader(), point);
    }

    /********************************************************
     *                        Popup                         *
     ********************************************************/
    public void showPopupMenu(
            final MouseEvent event,
            final DatasetEditorModelCell cell,
            final ColumnInfo columnInfo) {
        new ModalTask(getDataset().getProject(), "Loading column information", true) {
            public void run(@NotNull ProgressIndicator progressIndicator) {
                progressIndicator.setIndeterminate(true);
                ActionGroup actionGroup = new DatasetEditorTableActionGroup(datasetEditor, cell, columnInfo);
                if (!progressIndicator.isCanceled()) {
                    String actionPlace = EditorUtil.getEditorActionPlace(getDatasetEditor());
                    ActionPopupMenu actionPopupMenu = ActionManager.getInstance().createActionPopupMenu(actionPlace, actionGroup);
                    final JPopupMenu popupMenu = actionPopupMenu.getComponent();
                    new SimpleLaterInvocator() {
                        public void run() {
                            popupMenu.show((Component) event.getSource(), event.getX(), event.getY());
                        }
                    }.start();
                }
            }
        }.start();
    }
}
