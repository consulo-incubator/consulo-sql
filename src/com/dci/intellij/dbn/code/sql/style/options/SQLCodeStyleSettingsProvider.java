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

import com.dci.intellij.dbn.language.sql.SQLLanguage;
import com.intellij.lang.Language;
import com.intellij.openapi.options.Configurable;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider;
import com.intellij.psi.codeStyle.CustomCodeStyleSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SQLCodeStyleSettingsProvider extends CodeStyleSettingsProvider {

    public CustomCodeStyleSettings createCustomSettings(CodeStyleSettings codeStyleSettings) {
        return new SQLCustomCodeStyleSettings(codeStyleSettings);
    }

    @NotNull
    public Configurable createSettingsPage(CodeStyleSettings settings, CodeStyleSettings settings1) {
        SQLCustomCodeStyleSettings settingsProvider = settings.getCustomSettings(SQLCustomCodeStyleSettings.class);
        return settingsProvider.getCodeStyleSettings();
    }

    @NotNull
    public Configurable createSettingsPage(CodeStyleSettings settings) {
        SQLCustomCodeStyleSettings settingsProvider = settings.getCustomSettings(SQLCustomCodeStyleSettings.class);
        return settingsProvider.getCodeStyleSettings();
    }

    public String getConfigurableDisplayName() {
        return "SQL (DBN)";
    }

    @Nullable
    @Override
    public Language getLanguage() {
        return SQLLanguage.INSTANCE;
    }
}
