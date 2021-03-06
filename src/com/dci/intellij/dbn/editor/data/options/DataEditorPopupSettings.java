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
import com.dci.intellij.dbn.editor.data.options.ui.DataEditorPopupSettingsForm;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class DataEditorPopupSettings extends Configuration<DataEditorPopupSettingsForm>{
    private boolean active = false;
    private boolean activeIfEmpty = false;
    private int dataLengthThreshold = 100;
    private int delay = 1000;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActiveIfEmpty() {
        return activeIfEmpty;
    }

    public void setActiveIfEmpty(boolean activeIfEmpty) {
        this.activeIfEmpty = activeIfEmpty;
    }

    public int getDataLengthThreshold() {
        return dataLengthThreshold;
    }

    public void setDataLengthThreshold(int dataLengthThreshold) {
        this.dataLengthThreshold = dataLengthThreshold;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getDisplayName() {
        return null;
    }

    /****************************************************
     *                   Configuration                  *
     ****************************************************/
   public DataEditorPopupSettingsForm createConfigurationEditor() {
       return new DataEditorPopupSettingsForm(this);
   }

    @Override
    public String getConfigElementName() {
        return "text-editor-popup";
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        active = SettingsUtil.getBoolean(element, "active", active);
        activeIfEmpty = SettingsUtil.getBoolean(element, "active-if-empty", activeIfEmpty);
        dataLengthThreshold = SettingsUtil.getInteger(element, "data-length-threshold", dataLengthThreshold);
        delay = SettingsUtil.getInteger(element, "popup-delay", delay);
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        SettingsUtil.setBoolean(element, "active", active);
        SettingsUtil.setBoolean(element, "active-if-empty", activeIfEmpty);
        SettingsUtil.setInteger(element, "data-length-threshold", dataLengthThreshold);
        SettingsUtil.setInteger(element, "popup-delay", delay);
    }

}
