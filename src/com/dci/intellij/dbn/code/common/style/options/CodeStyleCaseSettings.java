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

package com.dci.intellij.dbn.code.common.style.options;

import com.dci.intellij.dbn.code.common.style.options.ui.CodeStyleCaseSettingsForm;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;

public abstract class CodeStyleCaseSettings extends Configuration<CodeStyleCaseSettingsForm> {
    private List<CodeStyleCaseOption> options = new ArrayList<CodeStyleCaseOption>();
    private boolean enabled = true;

    public CodeStyleCaseSettings() {
        options.add(new CodeStyleCaseOption("KEYWORD_CASE", CodeStyleCase.LOWER));
        options.add(new CodeStyleCaseOption("FUNCTION_CASE", CodeStyleCase.LOWER));
        options.add(new CodeStyleCaseOption("PARAMETER_CASE", CodeStyleCase.LOWER));
        options.add(new CodeStyleCaseOption("DATATYPE_CASE", CodeStyleCase.LOWER));
        options.add(new CodeStyleCaseOption("OBJECT_CASE", CodeStyleCase.PRESERVE));
    }


    public String getDisplayName() {
        return "Case Options";
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CodeStyleCaseOption getKeywordCaseOption() {
        return getCodeStyleCaseOption("KEYWORD_CASE");
    }

    public CodeStyleCaseOption getFunctionCaseOption() {
        return getCodeStyleCaseOption("FUNCTION_CASE");
    }

    public CodeStyleCaseOption getParameterCaseOption() {
        return getCodeStyleCaseOption("PARAMETER_CASE");
    }

    public CodeStyleCaseOption getDatatypeCaseOption() {
        return getCodeStyleCaseOption("DATATYPE_CASE");
    }


    public CodeStyleCaseOption getObjectCaseOption() {
        return getCodeStyleCaseOption("OBJECT_CASE");
    }

    private CodeStyleCaseOption getCodeStyleCaseOption(String name) {
        for (CodeStyleCaseOption option : options) {
            if (option.getName().equals(name)) return option;
        }
        return null;
    }

    /*********************************************************
     *                     Configuration                     *
     *********************************************************/
    public CodeStyleCaseSettingsForm createConfigurationEditor() {
        return new CodeStyleCaseSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "case-options";
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        enabled = SettingsUtil.getBooleanAttribute(element, "enabled", enabled);
        for (Object object : element.getChildren()) {
            Element optionElement = (Element) object;
            String name = optionElement.getAttributeValue("name");
            CodeStyleCaseOption option = getCodeStyleCaseOption(name);
            option.readConfiguration(optionElement);
        }
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        SettingsUtil.setBooleanAttribute(element, "enabled", enabled);
        for (CodeStyleCaseOption option : options) {
            Element optionElement = new Element("option");
            option.writeConfiguration(optionElement);
            element.addContent(optionElement);
        }
    }
}
