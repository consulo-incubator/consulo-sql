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

package com.dci.intellij.dbn.editor.data.structure;

import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.editor.data.DatasetEditor;
import com.dci.intellij.dbn.editor.data.model.DatasetEditorModel;
import com.dci.intellij.dbn.editor.data.ui.table.DatasetEditorTable;
import com.dci.intellij.dbn.object.DBColumn;
import com.dci.intellij.dbn.object.DBDataset;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObjectBundle;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.util.Arrays;
import java.util.List;

public class DatasetEditorStructureViewElement implements StructureViewTreeElement, Comparable{
    private BrowserTreeNode treeNode;
    private DatasetEditor datasetEditor;
    private StructureViewTreeElement[] children;

    DatasetEditorStructureViewElement(BrowserTreeNode treeNode, DatasetEditor datasetEditor) {
        this.treeNode = treeNode;
        this.datasetEditor = datasetEditor;
    }

    public BrowserTreeNode getValue() {
        return treeNode;
    }

    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            public String getPresentableText() {
                return treeNode.getPresentableText();
            }

            @Nullable
            public String getLocationString() {
                if (treeNode instanceof DBColumn) {
                    DBColumn column  = (DBColumn) treeNode;
                    return column.getPresentableTextDetails();

                }
                return null;
            }

            @Nullable
            public Icon getIcon(boolean open) {
                return treeNode.getIcon(0);
            }

            @Nullable
            public TextAttributesKey getTextAttributesKey() {
                return null;
            }
        };
    }

    public StructureViewTreeElement[] getChildren() {
        if (children == null) {
            if (datasetEditor.isDisposed())  {
                return EMPTY_ARRAY;
            }
            DBDataset dataset = datasetEditor.getDataset();
            if (treeNode instanceof DBObjectBundle) {
                DatasetEditorStructureViewElement schemaStructureElement =
                        new DatasetEditorStructureViewElement(dataset.getSchema(), datasetEditor);
                children = new StructureViewTreeElement[] {schemaStructureElement};
            }
            else if (treeNode instanceof DBSchema) {
                DatasetEditorStructureViewElement datasetStructureElement =
                        new DatasetEditorStructureViewElement(dataset, datasetEditor);
                children = new StructureViewTreeElement[] {datasetStructureElement};
            }
            else if (treeNode instanceof DBDataset) {
                List<DBColumn> columns = dataset.getColumns();
                children = new StructureViewTreeElement[columns.size()];
                for (int i=0; i<children.length; i++) {
                    children[i] = new DatasetEditorStructureViewElement(columns.get(i), datasetEditor);
                }
                Arrays.sort(children);
            }
            else {
                children = EMPTY_ARRAY;
            }
        }
        return children;
    }

    public void navigate(boolean requestFocus) {
        if (!datasetEditor.isDisposed()) {
            DatasetEditorTable table = datasetEditor.getEditorTable();
            table.cancelEditing();
            DatasetEditorModel model = table.getModel();
            if (treeNode instanceof DBColumn &&  model.getSize() > 0) {
                DBColumn column = (DBColumn) treeNode;
                int modelColumnIndex = model.getHeader().indexOfColumn(column);
                int tableColumnIndex = table.convertColumnIndexToView(modelColumnIndex);
                int rowIndex = table.getSelectedRow();
                if (rowIndex == -1)  rowIndex = 0;
                table.selectCell(rowIndex, tableColumnIndex);
                if (requestFocus) {
                    table.requestFocus();
                }
            }
        }
    }

    public boolean canNavigate() {
        return !datasetEditor.isDisposed();
    }

    public boolean canNavigateToSource() {
        return treeNode instanceof DBColumn && !datasetEditor.isDisposed() && datasetEditor.getEditorTable().getRowCount() > 0;
    }

    public int compareTo(@NotNull Object o) {
        DatasetEditorStructureViewElement desve = (DatasetEditorStructureViewElement) o;
        if (treeNode instanceof DBColumn && desve.treeNode instanceof DBColumn) {
            DBColumn thisColumn = (DBColumn) treeNode;
            DBColumn remoteColumn = (DBColumn) desve.treeNode;
            return thisColumn.compareTo(remoteColumn);
        }
        return 0;
    }
}
