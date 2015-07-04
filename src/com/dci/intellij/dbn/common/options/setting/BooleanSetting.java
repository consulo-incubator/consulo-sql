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

package com.dci.intellij.dbn.common.options.setting;

import com.dci.intellij.dbn.common.options.PersistentConfiguration;
import org.jdom.Element;

import javax.swing.JToggleButton;

public class BooleanSetting extends Setting<Boolean, JToggleButton> implements PersistentConfiguration {
    public BooleanSetting(String name, Boolean value) {
        super(name, value);
    }
    
    @Override
    public void readConfiguration(Element parent) {
        setValue(SettingsUtil.getBoolean(parent, getName(), this.value()));
    }

    public void readConfigurationAttribute(Element parent) {
        setValue(SettingsUtil.getBooleanAttribute(parent, getName(), this.value()));
    }

    @Override
    public void writeConfiguration(Element parent) {
        SettingsUtil.setBoolean(parent, getName(), this.value());
    }

    public void writeConfigurationAttribute(Element parent) {
        SettingsUtil.setBooleanAttribute(parent, getName(), this.value());
    }


    public boolean applyChanges(JToggleButton checkBox) {
        return setValue(checkBox.isSelected());
    }
    
    public void resetChanges(JToggleButton checkBox) {
        checkBox.setSelected(value());
    }
}
