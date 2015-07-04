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

import com.dci.intellij.dbn.common.options.CompositeConfiguration;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.common.options.ui.CompositeConfigurationEditorForm;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public abstract class CodeStyleCustomSettings<T extends CompositeConfigurationEditorForm> extends CompositeConfiguration<T>{
    protected CodeStyleCaseSettings caseSettings;
    protected CodeStyleFormattingSettings formattingSettings;

    protected CodeStyleCustomSettings() {
        caseSettings = createCaseSettings();
        formattingSettings = createAttributeSettings();
    }

    protected abstract CodeStyleCaseSettings createCaseSettings();
    protected abstract CodeStyleFormattingSettings createAttributeSettings();

    public CodeStyleCaseSettings getCaseSettings() {
        return caseSettings;
    }

    public CodeStyleFormattingSettings getFormattingSettings() {
        return formattingSettings;
    }

    /*********************************************************
    *                     Configuration                     *
    *********************************************************/
    protected Configuration[] createConfigurations() {
        return new Configuration[] {
                caseSettings,
                formattingSettings};
    }

    protected abstract String getElementName();

    public void readConfiguration(Element element) throws InvalidDataException {
        Element child = element.getChild(getElementName());
        if (child != null) {
            readConfiguration(child, caseSettings);
            readConfiguration(child, formattingSettings);
        }
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
         Element child = new Element(getElementName());
         element.addContent(child);
         writeConfiguration(child, caseSettings);
         writeConfiguration(child, formattingSettings);
     }


}
