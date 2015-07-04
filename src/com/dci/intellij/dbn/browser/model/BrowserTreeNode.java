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

package com.dci.intellij.dbn.browser.model;

import com.dci.intellij.dbn.browser.ui.ToolTipProvider;
import com.dci.intellij.dbn.connection.GenericDatabaseElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.Disposable;

import javax.swing.Icon;
import java.util.ArrayList;
import java.util.List;

public interface BrowserTreeNode extends NavigationItem, ItemPresentation, ToolTipProvider, Disposable, GenericDatabaseElement {

    List<BrowserTreeNode> EMPTY_LIST = new ArrayList<BrowserTreeNode>();

    void initTreeElement();

    boolean canExpand();

    int getTreeDepth();

    boolean isTreeStructureLoaded();

    BrowserTreeNode getTreeChild(int index);

    BrowserTreeNode getTreeParent();

    List<? extends BrowserTreeNode> getTreeChildren();

    void rebuildTreeChildren();

    int getTreeChildCount();

    boolean isLeafTreeElement();

    int getIndexOfTreeChild(BrowserTreeNode child);

    Icon getIcon(int flags);

    String getPresentableText();

    String getPresentableTextDetails();

    String getPresentableTextConditionalDetails();

    boolean isDisposed();
}
