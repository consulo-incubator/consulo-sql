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

package com.dci.intellij.dbn.data.model;

import com.dci.intellij.dbn.data.find.DataSearchResult;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.ListModel;
import javax.swing.table.TableModel;
import java.util.List;

public interface DataModel<T extends DataModelRow> extends TableModel, ListModel, Disposable {
    boolean isReadonly();

    Project getProject();

    List<T> getRows();

    int indexOfRow(T row);

    T getRowAtIndex(int index);

    DataModelHeader getHeader();

    int getColumnCount();

    ColumnInfo getColumnInfo(int columnIndex);

    @NotNull
    DataModelState getState();

    void setState(DataModelState state);

    DataSearchResult getSearchResult();

    void addDataModelListener(DataModelListener listener);

    void removeDataModelListener(DataModelListener listener);

    boolean hasSearchResult();
}
