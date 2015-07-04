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

import com.dci.intellij.dbn.browser.model.BrowserTreeNode;
import com.dci.intellij.dbn.browser.model.LoadInProgressTreeNode;
import com.dci.intellij.dbn.browser.options.DatabaseBrowserSettings;
import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.connection.ModuleConnectionBundle;
import com.dci.intellij.dbn.connection.ProjectConnectionBundle;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.common.list.DBObjectList;
import com.intellij.openapi.project.Project;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.util.ui.UIUtil;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.TreeCellRenderer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

public class DatabaseBrowserTreeCellRenderer implements TreeCellRenderer {
    private DefaultTreeCellRenderer cellRenderer = new DefaultTreeCellRenderer();
    //private static final LoaderCellRendererComponent LOADER_COMPONENT = new LoaderCellRendererComponent();
    private DatabaseBrowserSettings browserSettings;

    public DatabaseBrowserTreeCellRenderer(Project project) {
        browserSettings = DatabaseBrowserSettings.getInstance(project);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof LoadInProgressTreeNode) {
            return new LoaderCellRendererComponent((LoadInProgressTreeNode) value);
        } else {
            return cellRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        }
    }

    private static class LoaderCellRendererComponent extends JPanel {
        LoadInProgressTreeNode treeElement;
        private LoaderCellRendererComponent(LoadInProgressTreeNode treeElement) {
            setLayout(new BorderLayout());
            add(new JLabel("Loading...", treeElement.getIcon(0), SwingConstants.LEFT), BorderLayout.WEST);
            setBackground(UIUtil.getTreeTextBackground());
        }
    }

    private class DefaultTreeCellRenderer extends ColoredTreeCellRenderer {
        public Font getFont() {
            Font font = super.getFont();
            return font == null ? UIUtil.getTreeFont() : font;
        }

        public void customizeCellRenderer(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            BrowserTreeNode treeNode = (BrowserTreeNode) value;
            setIcon(treeNode.getIcon(0));

            String displayName;
            if (treeNode instanceof ModuleConnectionBundle) {
                ModuleConnectionBundle connectionManager = (ModuleConnectionBundle) treeNode;
                displayName = connectionManager.getModule().getName();
            } else if (treeNode instanceof ProjectConnectionBundle) {
                displayName = "PROJECT";
            } else {
                displayName = treeNode.getPresentableText();
            }

            if (treeNode instanceof DBObjectList) {
                DBObjectList objectsList = (DBObjectList) treeNode;
                SimpleTextAttributes textAttributes =
                        //objectsList.isDirty() ? SimpleTextAttributes.ERROR_ATTRIBUTES :
                        objectsList.getTreeChildCount() == 0 ? SimpleTextAttributes.REGULAR_ATTRIBUTES :
                        SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES;

                append(displayName, textAttributes);

                // todo display load error
                /*
                            SimpleTextAttributes descriptionTA = objectsList.getErrorMessage() == null ?
                                    SimpleTextAttributes.GRAY_ATTRIBUTES : SimpleTextAttributes.ERROR_ATTRIBUTES;
                            append(" " + displayDetails, descriptionTA);

                            if (objectsList.getErrorMessage() != null) {
                                String msg = "Could not load " + displayName + ". Cause: " + objectsList.getErrorMessage();
                                setToolTipText(msg);
                            }  else {
                                setToolTipText(null);
                            }
                */
            } else {
                boolean showBold = false;
                boolean isError = false;
                if (treeNode instanceof DBObject) {
                    DBObject object = (DBObject) treeNode;
                    if (object.isOfType(DBObjectType.SCHEMA)) {
                        DBSchema schema = (DBSchema) object;
                        showBold = schema.isUserSchema();
                    }

                    isError = !object.isValid();
                }

                SimpleTextAttributes textAttributes =
                        isError ? SimpleTextAttributes.ERROR_ATTRIBUTES :
                        showBold ? SimpleTextAttributes.REGULAR_BOLD_ATTRIBUTES :
                        SimpleTextAttributes.REGULAR_ATTRIBUTES;

                if (displayName == null) displayName = "displayName null!!";

                append(displayName, textAttributes);
            }
            String displayDetails = treeNode.getPresentableTextDetails();
            if (!StringUtil.isEmptyOrSpaces(displayDetails)) {
                append(" " + displayDetails, SimpleTextAttributes.GRAY_ATTRIBUTES);
            }

            if (browserSettings.getGeneralSettings().getShowObjectDetails().value()) {
                String conditionalDetails = treeNode.getPresentableTextConditionalDetails();
                if (!StringUtil.isEmptyOrSpaces(conditionalDetails)) {
                    append(" - " + conditionalDetails, SimpleTextAttributes.GRAY_ATTRIBUTES);
                }

            }
        }
    }
}
