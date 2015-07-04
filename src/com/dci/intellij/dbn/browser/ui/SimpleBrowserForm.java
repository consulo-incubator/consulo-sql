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

package com.dci.intellij.dbn.browser.ui;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;
import javax.swing.border.EmptyBorder;

import com.dci.intellij.dbn.browser.model.BrowserTreeModel;
import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.browser.model.SimpleBrowserTreeModel;
import com.dci.intellij.dbn.browser.options.ObjectDisplaySettingsListener;
import com.dci.intellij.dbn.common.event.EventManager;
import com.dci.intellij.dbn.common.ui.tree.TreeEventType;
import com.dci.intellij.dbn.connection.ConnectionManager;
import com.intellij.openapi.project.Project;

public class SimpleBrowserForm extends DatabaseBrowserForm{
    private JPanel mainPanel;
    private JScrollPane browserScrollPane;
    private DatabaseBrowserTree browserTree;

    public SimpleBrowserForm(Project project) {
        this(new SimpleBrowserTreeModel(project, ConnectionManager.getInstance(project).getConnectionBundles()));
    }

    private SimpleBrowserForm(BrowserTreeModel treeModel) {
        super(treeModel.getProject());
        Project project = getProject();

        browserTree = new DatabaseBrowserTree(treeModel);
        browserScrollPane.setViewportView(browserTree);
        browserScrollPane.setBorder(new EmptyBorder(1,0,0,0));
        ToolTipManager.sharedInstance().registerComponent(browserTree);

        EventManager.subscribe(project, ObjectDisplaySettingsListener.TOPIC, objectDisplaySettingsListener);
    }
    
    public JComponent getComponent() {
        return mainPanel;
    }

    public DatabaseBrowserTree getBrowserTree() {
        return browserTree;
    }

    public void selectElement(BrowserTreeNode treeNode, boolean requestFocus) {
        browserTree.selectElement(treeNode, requestFocus);
    }

    public void updateTree() {
        browserTree.getModel().getRoot().rebuildTreeChildren();
    }

    public void rebuild() {
        BrowserTreeModel treeModel = browserTree.getModel();
        BrowserTreeNode rootNode = treeModel.getRoot();
        treeModel.notifyListeners(rootNode, TreeEventType.STRUCTURE_CHANGED);
    }

    public void dispose() {
        EventManager.unsubscribe(objectDisplaySettingsListener);
        browserScrollPane.getViewport().remove(browserTree);
        browserTree.dispose();
        browserTree = null;
        super.dispose();
    }

    /********************************************************
     *                       Listeners                      *
     ********************************************************/
    private ObjectDisplaySettingsListener objectDisplaySettingsListener = new ObjectDisplaySettingsListener() {
        @Override
        public void displayDetailsChanged() {
            browserTree.repaint();
        }
    };
}
