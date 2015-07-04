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

package com.dci.intellij.dbn.browser;

import java.util.ArrayList;
import java.util.List;

import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.browser.options.DatabaseBrowserSettings;

public class TreeNavigationHistory{
    private List<BrowserTreeNode> history = new ArrayList<BrowserTreeNode>();
    private int offset;

    public void add(BrowserTreeNode treeNode) {
        if (history.size() > 0 && treeNode == history.get(offset)) {
            return;
        }
        while (history.size() - 1  > offset) {
            history.remove(offset);
        }

        DatabaseBrowserSettings browserSettings = DatabaseBrowserSettings.getInstance(treeNode.getProject());

        int historySize = browserSettings.getGeneralSettings().getNavigationHistorySize().value();
        while (history.size() > historySize) {
            history.remove(0);
        }
        history.add(treeNode);
        offset = history.size() -1;
    }

    public void clear() {
        history.clear();
    }

    public boolean hasNext() {
        return offset < history.size()-1;
    }

    public boolean hasPrevious() {
        return offset > 0;
    }

    public BrowserTreeNode next() {
        offset = offset + 1;
        return history.get(offset);
    }

    public BrowserTreeNode previous() {
        offset = offset -1;
        return history.get(offset);
    }

}
