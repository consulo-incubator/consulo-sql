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

package com.dci.intellij.dbn.data.find.action;

import com.dci.intellij.dbn.data.find.DataSearchComponent;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.IconLoader;
import com.intellij.util.containers.ContainerUtil;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ShowHistoryAction extends DataSearchHeaderAction implements DumbAware {
    private JTextField searchField;


    public ShowHistoryAction(final JTextField searchField, DataSearchComponent searchComponent) {
        super(searchComponent);
        this.searchField = searchField;
        getTemplatePresentation().setIcon(IconLoader.getIcon("/actions/search.png"));
        getTemplatePresentation().setDescription("Search history");
        getTemplatePresentation().setText("Search History");

        ArrayList<Shortcut> shortcuts = new ArrayList<Shortcut>();
        ContainerUtil.addAll(shortcuts, ActionManager.getInstance().getAction("IncrementalSearch").getShortcutSet().getShortcuts());
        shortcuts.add(new KeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK), null));

        registerCustomShortcutSet(new CustomShortcutSet(shortcuts.toArray(new Shortcut[shortcuts.size()])), searchField);
        searchField.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (searchField.getText().isEmpty()) {
                    getSearchComponent().showHistory(false, searchField);
                }
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), JComponent.WHEN_FOCUSED);
    }

    @Override
    public void actionPerformed(final AnActionEvent e) {
        getSearchComponent().showHistory(e.getInputEvent() instanceof MouseEvent, searchField);
    }


}
