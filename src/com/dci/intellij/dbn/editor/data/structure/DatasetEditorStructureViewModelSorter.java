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
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.language.psql.structure.PSQLStructureViewElement;
import com.intellij.ide.util.treeView.smartTree.ActionPresentation;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.util.Comparator;

public class DatasetEditorStructureViewModelSorter implements Sorter {

    public Comparator getComparator() {
        return COMPARATOR;    
    }

    public boolean isVisible() {
        return true;
    }

    @NotNull
    public ActionPresentation getPresentation() {
        return ACTION_PRESENTATION;
    }

    @NotNull
    public String getName() {
        return "Sort by Name";
    }

    private static final ActionPresentation ACTION_PRESENTATION = new ActionPresentation() {
        public String getText() {
            return "Sort by Name";
        }

        public String getDescription() {
            return "Sort elements alphabetically by name";
        }

        public Icon getIcon() {
            return Icons.ACTION_SORT_ALPHA;
        }
    };

    private static final Comparator COMPARATOR = new Comparator() {
        public int compare(Object object1, Object object2) {
            if (object1 instanceof DatasetEditorStructureViewElement && object2 instanceof DatasetEditorStructureViewElement) {
                DatasetEditorStructureViewElement structureViewElement1 = (DatasetEditorStructureViewElement) object1;
                DatasetEditorStructureViewElement structureViewElement2 = (DatasetEditorStructureViewElement) object2;
                BrowserTreeNode treeNode1 = structureViewElement1.getValue();
                BrowserTreeNode treeNode2 = structureViewElement2.getValue();
                return treeNode1.getName().compareTo(treeNode2.getName());
            } else {
                return object1 instanceof PSQLStructureViewElement ? 1 : -1;
            }
        }
    };
}
