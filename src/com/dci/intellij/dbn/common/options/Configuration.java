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

package com.dci.intellij.dbn.common.options;

import com.dci.intellij.dbn.common.options.ui.ConfigurationEditorForm;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import javax.swing.JComponent;

public abstract class Configuration<T extends ConfigurationEditorForm> extends ConfigurationUtil implements SearchableConfigurable, PersistentConfiguration {
    private T configurationEditorForm;
    private boolean isModified;

    public String getHelpTopic() {
        return null;
    }

    @Nls
    public String getDisplayName() {
        return null;
    }

    public Icon getIcon() {
        return null;
    }

    @NotNull
    public String getId() {
        return null;
    }

    public Runnable enableSearch(String option) {
        return null;
    }

    @Nullable
    public final T getSettingsEditor() {
        return configurationEditorForm;
    }

    protected abstract T createConfigurationEditor();

    public JComponent createComponent() {
        configurationEditorForm = createConfigurationEditor();
        return configurationEditorForm == null ? null : configurationEditorForm.getComponent();
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public boolean isModified() {
        return isModified;
    }

    public void apply() throws ConfigurationException {
        if (configurationEditorForm != null && !configurationEditorForm.isDisposed()) configurationEditorForm.applyChanges();
        isModified = false;
    }

    public void reset() {
        if (configurationEditorForm != null && !configurationEditorForm.isDisposed()) configurationEditorForm.resetChanges();
        isModified = false;
    }

    public void disposeUIResources() {
        if (configurationEditorForm != null) {
            configurationEditorForm.dispose();
            configurationEditorForm = null;
        }
    }

    public String getConfigElementName() {
        //throw new UnsupportedOperationException("Element name not defined for this configuration type.");
        return null;
    }
}
