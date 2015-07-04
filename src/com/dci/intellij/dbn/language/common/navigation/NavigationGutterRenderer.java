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

package com.dci.intellij.dbn.language.common.navigation;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class NavigationGutterRenderer extends GutterIconRenderer {
    private NavigationAction action;
    public NavigationGutterRenderer(NavigationAction action) {
        this.action = action;
    }

    @NotNull
    public Icon getIcon() {
        return action.getTemplatePresentation().getIcon();
    }

    public boolean isNavigateAction() {
        return true;
    }

    @Nullable
    public synchronized AnAction getClickAction() {
        return action;
    }

    @Nullable
    public String getTooltipText() {
        return action.getTemplatePresentation().getText();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NavigationGutterRenderer) {
            NavigationGutterRenderer renderer = (NavigationGutterRenderer) obj;
            return action.equals(renderer.action);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return action.hashCode();
    }
}