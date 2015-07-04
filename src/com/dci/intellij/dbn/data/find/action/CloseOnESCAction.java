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

import com.dci.intellij.dbn.common.ui.KeyUtil;
import com.dci.intellij.dbn.data.find.DataSearchComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import com.intellij.openapi.actionSystem.KeyboardShortcut;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.project.DumbAware;

import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CloseOnESCAction extends DataSearchHeaderAction implements DumbAware {
    public CloseOnESCAction(DataSearchComponent searchComponent, JComponent component) {
        super(searchComponent);

        ArrayList<Shortcut> shortcuts = new ArrayList<Shortcut>();
        if (KeyUtil.isEmacsKeymap()) {
            shortcuts.add(new KeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK), null));
            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    CloseOnESCAction.this.actionPerformed(null);
                }
            };
            component.registerKeyboardAction(
                    actionListener,
                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                    JComponent.WHEN_FOCUSED);
        } else {
            shortcuts.add(new KeyboardShortcut(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), null));
        }

        registerCustomShortcutSet(new CustomShortcutSet(shortcuts.toArray(new Shortcut[shortcuts.size()])), component);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        getSearchComponent().close();
    }
}
