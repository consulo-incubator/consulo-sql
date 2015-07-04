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

package com.dci.intellij.dbn.ddl.options;

import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.common.options.setting.BooleanSetting;
import com.dci.intellij.dbn.common.options.setting.StringSetting;
import com.dci.intellij.dbn.ddl.options.ui.DDLFileGeneralSettingsForm;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

public class DDLFileGeneralSettings extends Configuration<DDLFileGeneralSettingsForm> {
    private StringSetting statementPostfix = new StringSetting("statement-postfix", "/");
    private BooleanSetting lookupDDLFilesEnabled = new BooleanSetting("lookup-ddl-files", true);
    private BooleanSetting createDDLFilesEnabled = new BooleanSetting("create-ddl-files", false);

    public String getDisplayName() {
        return "DDL file general settings";
    }

    public StringSetting getStatementPostfix() {
        return statementPostfix;
    }

    public BooleanSetting getLookupDDLFilesEnabled() {
        return lookupDDLFilesEnabled;
    }

    public BooleanSetting getCreateDDLFilesEnabled() {
        return createDDLFilesEnabled;
    }

    /*********************************************************
     *                     Configuration                     *
     *********************************************************/
    public DDLFileGeneralSettingsForm createConfigurationEditor() {
        return new DDLFileGeneralSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "general";
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        statementPostfix.readConfiguration(element);
        lookupDDLFilesEnabled.readConfiguration(element);
        createDDLFilesEnabled.readConfiguration(element);
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        statementPostfix.writeConfiguration(element);
        lookupDDLFilesEnabled.writeConfiguration(element);
        createDDLFilesEnabled.writeConfiguration(element);
    }
}
