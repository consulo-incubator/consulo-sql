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

package com.dci.intellij.dbn.editor.data.sorting;

import com.dci.intellij.dbn.common.AbstractProjectComponent;
import com.dci.intellij.dbn.editor.data.sorting.ui.DatasetSortingDialog;
import com.dci.intellij.dbn.object.DBDataset;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class DatasetSortingManager extends AbstractProjectComponent implements JDOMExternalizable {
    private DatasetSortingManager(Project project) {
        super(project);
    }


    public static DatasetSortingManager getInstance(Project project) {
        return project.getComponent(DatasetSortingManager.class);
    }

    public void openSortingDialog(DBDataset dataset, DatasetSortingState sortingState) {
        DatasetSortingDialog dialog = new DatasetSortingDialog(dataset, sortingState);
        dialog.show();
    }

    /***************************************
    *            ProjectComponent           *
    ****************************************/
    @NonNls
    @NotNull
    public String getComponentName() {
        return "DBNavigator.Project.DatasetSortingManager";
    }
    public void disposeComponent() {
        super.disposeComponent();
    }

    /*************************************************
    *               JDOMExternalizable              *
    *************************************************/
    public void readExternal(Element element) throws InvalidDataException {
    }

    public void writeExternal(Element element) throws WriteExternalException {
    }

}
