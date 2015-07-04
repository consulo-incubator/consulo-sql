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

import com.dci.intellij.dbn.common.editor.structure.DBObjectStructureViewModel;
import com.dci.intellij.dbn.editor.data.DatasetEditor;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DatasetEditorStructureViewModel extends DBObjectStructureViewModel {
    private Sorter[] sorters = new Sorter[] {new DatasetEditorStructureViewModelSorter()};
    private DatasetEditor datasetEditor;
    private StructureViewTreeElement root;

    public DatasetEditorStructureViewModel(DatasetEditor datasetEditor) {
        this.datasetEditor = datasetEditor;

    }

    @NotNull
    @Override
    public Sorter[] getSorters() {
        return sorters;
    }

    @Nullable
    public Object getCurrentEditorElement() {
        return null;
    }

    @NotNull
    public StructureViewTreeElement getRoot() {
        if (root == null) {
            //DBObjectBundle objectBundle = datasetEditor.getConnectionHandler().getObjectBundle();
            root = new DatasetEditorStructureViewElement(datasetEditor.getDataset(), datasetEditor);
        }
        return root;
    }
}
