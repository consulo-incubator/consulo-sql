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

package com.dci.intellij.dbn.code.sql.style.options;

import com.dci.intellij.dbn.code.common.style.options.CodeStyleCaseSettings;
import com.dci.intellij.dbn.code.common.style.options.CodeStyleCustomSettings;
import com.dci.intellij.dbn.code.common.style.options.CodeStyleFormattingSettings;
import com.dci.intellij.dbn.code.common.style.options.ProjectCodeStyleSettings;
import com.dci.intellij.dbn.code.sql.style.options.ui.SQLCodeStyleSettingsEditorForm;
import com.dci.intellij.dbn.common.Icons;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class SQLCodeStyleSettings extends CodeStyleCustomSettings<SQLCodeStyleSettingsEditorForm> {

    public static SQLCodeStyleSettings getInstance(Project project) {
        return ProjectCodeStyleSettings.getInstance(project).getSQLCodeStyleSettings();
    }

    @Nls
    public String getDisplayName() {
        return "SQL";
    }

    @Nullable
    public Icon getIcon() {
        return Icons.FILE_SQL;
    }

    protected CodeStyleCaseSettings createCaseSettings() {
        return new SQLCodeStyleCaseSettings();
    }

    protected CodeStyleFormattingSettings createAttributeSettings() {
        return new SQLCodeStyleFormattingSettings();
    }

    @Override
    protected String getElementName() {
        return "DBN-SQL";
    }

    /*********************************************************
    *                     Configuration                     *
    *********************************************************/
    public SQLCodeStyleSettingsEditorForm createConfigurationEditor() {
        return new SQLCodeStyleSettingsEditorForm(this);
    }
}
