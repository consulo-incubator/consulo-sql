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

package com.dci.intellij.dbn.data.ui.table.sortable;

import com.dci.intellij.dbn.common.LoggerFactory;
import com.dci.intellij.dbn.common.locale.options.RegionalSettings;
import com.dci.intellij.dbn.data.model.ColumnInfo;
import com.dci.intellij.dbn.data.model.sortable.SortableDataModel;
import com.dci.intellij.dbn.data.model.sortable.SortableTableHeaderMouseListener;
import com.dci.intellij.dbn.data.model.sortable.SortableTableMouseListener;
import com.dci.intellij.dbn.data.sorting.SortDirection;
import com.dci.intellij.dbn.data.ui.table.basic.BasicTable;
import com.dci.intellij.dbn.data.ui.table.basic.BasicTableCellRenderer;
import com.dci.intellij.dbn.data.ui.table.basic.BasicTableSpeedSearch;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

import javax.swing.JTable;

public class SortableTable extends BasicTable {
    protected Logger logger = LoggerFactory.createLogger();

    public SortableTable(SortableDataModel dataModel, boolean enableSpeedSearch) {
        super(dataModel.getProject(), dataModel);
        Project project = dataModel.getProject();
        this.cellRenderer = new BasicTableCellRenderer(project);
        regionalSettings = RegionalSettings.getInstance(project);
        addMouseListener(new SortableTableMouseListener(this));
        getTableHeader().setDefaultRenderer(SortableTableHeaderRenderer.INSTANCE);
        getTableHeader().addMouseListener(new SortableTableHeaderMouseListener(this));

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setCellSelectionEnabled(true);
        accommodateColumnsSize();
        if (enableSpeedSearch) {
            new BasicTableSpeedSearch(this);
        }
    }

    @Override
    public SortableDataModel getModel() {
        return (SortableDataModel) super.getModel();
    }

    public boolean sort(int columnIndex, SortDirection sortDirection) {
        SortableDataModel model = getModel();
        int modelColumnIndex = convertColumnIndexToModel(columnIndex);
        ColumnInfo columnInfo = getModel().getColumnInfo(modelColumnIndex);
        if (columnInfo.isSortable()) {
            boolean sorted = model.sort(modelColumnIndex, sortDirection);
            if (sorted) getTableHeader().repaint();
            return sorted;
        }
        return false;
    }

}
