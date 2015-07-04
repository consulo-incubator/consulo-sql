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

package com.dci.intellij.dbn.editor.data.options;

import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.dci.intellij.dbn.data.record.navigation.RecordNavigationTarget;
import com.dci.intellij.dbn.editor.data.options.ui.DataEditorRecordNavigationSettingsForm;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class DataEditorRecordNavigationSettings extends Configuration<DataEditorRecordNavigationSettingsForm> {
    private RecordNavigationTarget navigationTarget = RecordNavigationTarget.VIEWER;

    @Override
    public DataEditorRecordNavigationSettingsForm createConfigurationEditor() {
        return new DataEditorRecordNavigationSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "record-navigation";
    }

    public RecordNavigationTarget getNavigationTarget() {
        return navigationTarget;
    }

    public void setNavigationTarget(RecordNavigationTarget navigationTarget) {
        this.navigationTarget = navigationTarget;
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        navigationTarget = SettingsUtil.getEnum(element, "navigation-target", RecordNavigationTarget.VIEWER);
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        SettingsUtil.setEnum(element, "navigation-target", navigationTarget);
    }
}
