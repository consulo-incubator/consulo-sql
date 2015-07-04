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

import com.dci.intellij.dbn.object.common.DBObject;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public abstract class DBObjectStructureViewElement<T extends DBObject> implements StructureViewTreeElement, Comparable{
    private T object;
    protected DBObjectStructureViewElement[] children;
    private StructureViewTreeElement treeParent;

    public DBObjectStructureViewElement(StructureViewTreeElement treeParent, T object) {
        this.treeParent = treeParent;
        this.object = object;
    }

    public StructureViewTreeElement getTreeParent() {
        return treeParent;
    }

    public Object getValue() {
        return this;
    }

    public T getObject() {
        return object;
    }

    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            public String getPresentableText() {
                return object.getPresentableText();
            }

            @Nullable
            public String getLocationString() {
                return null;
            }

            @Nullable
            public Icon getIcon(boolean open) {
                return object.getIcon(0);
            }

            @Nullable
            public TextAttributesKey getTextAttributesKey() {
                return null;
            }
        };
    }

    public boolean canNavigate() {
        return false;
    }

    public boolean canNavigateToSource() {
        return false;
    }

    public TreeElement[] getChildren() {
        return EMPTY_ARRAY;
    }

    public void navigate(boolean requestFocus) {

    }

    public int compareTo(@NotNull Object o) {
        if (o instanceof DBObject) {
            DBObject remote = (DBObject) o;
            return object.compareTo(remote);
        }
        return 0;
    }

    public void reset() {
        if (children != null) {
            for (DBObjectStructureViewElement child : children) {
                child.reset();
            }
            children = null;
        }
    }
}
