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

package com.dci.intellij.dbn.common.editor.structure;

import com.intellij.ide.structureView.FileEditorPositionListener;
import com.intellij.ide.structureView.ModelListener;
import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.util.treeView.smartTree.Grouper;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.ide.util.treeView.smartTree.Filter;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public abstract class DBObjectStructureViewModel implements StructureViewModel {
    protected Set<FileEditorPositionListener> fileEditorPositionListeners = new HashSet<FileEditorPositionListener>();
    protected Set<ModelListener> modelListeners = new HashSet<ModelListener>();

    public void addEditorPositionListener(FileEditorPositionListener listener) {
        fileEditorPositionListeners.add(listener);
    }

    public void removeEditorPositionListener(FileEditorPositionListener listener) {
        fileEditorPositionListeners.remove(listener);
    }

    public void addModelListener(ModelListener modelListener) {
        modelListeners.add(modelListener);
    }

    public void removeModelListener(ModelListener modelListener) {
        modelListeners.remove(modelListener);
    }

    public void dispose() {
    }

    public boolean shouldEnterElement(Object o) {
        return false;
    }

    @NotNull
    public Grouper[] getGroupers() {
        return Grouper.EMPTY_ARRAY;
    }

    @NotNull
    public Sorter[] getSorters() {
        return new Sorter[0];
    }

    @NotNull
    public Filter[] getFilters() {
        return new Filter[0];
    }

    public void rebuild() {
        for (ModelListener modelListener : modelListeners) {
            modelListener.onModelChanged();
        }

        for (FileEditorPositionListener positionListener : fileEditorPositionListeners) {
            positionListener.onCurrentElementChanged();
        }
    }
}
