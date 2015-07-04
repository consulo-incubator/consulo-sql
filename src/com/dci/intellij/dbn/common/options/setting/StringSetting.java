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
import com.dci.intellij.dbn.common.util.StringUtil;
import com.intellij.openapi.options.ConfigurationException;
import org.jdom.Element;

import javax.swing.text.JTextComponent;

public class StringSetting extends Setting<String, JTextComponent> implements PersistentConfiguration {
    public StringSetting(String name, String value) {
        super(name, value);
    }
    
    @Override
    public void readConfiguration(Element parent) {
        setValue(SettingsUtil.getString(parent, getName(), this.value()));
    }

    @Override
    public void writeConfiguration(Element parent) {
        SettingsUtil.setString(parent, getName(), this.value());
    }

    @Override
    public boolean applyChanges(JTextComponent component) throws ConfigurationException {
        return setValue(StringUtil.trim(component.getText()));
    }

    @Override
    public void resetChanges(JTextComponent component) {
        component.setText(value());
    }

}
