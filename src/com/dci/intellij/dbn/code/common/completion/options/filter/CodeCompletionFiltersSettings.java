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

package com.dci.intellij.dbn.code.common.completion.options.filter;

import com.dci.intellij.dbn.code.common.completion.options.filter.ui.CodeCompletionFiltersSettingsForm;
import com.dci.intellij.dbn.common.options.CompositeConfiguration;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.language.common.TokenTypeIdentifier;
import com.dci.intellij.dbn.object.common.DBObjectType;

public class CodeCompletionFiltersSettings extends CompositeConfiguration<CodeCompletionFiltersSettingsForm> {
    private CodeCompletionFilterSettings basicFilterSettings;
    private CodeCompletionFilterSettings extendedFilterSettings;

    public CodeCompletionFiltersSettings() {
        basicFilterSettings = new CodeCompletionFilterSettings(false);
        extendedFilterSettings = new CodeCompletionFilterSettings(true);

    }

    public String getDisplayName() {
        return "Code completion filter";
    }

   /*********************************************************
    *                         Custom                        *
    *********************************************************/
    public CodeCompletionFilterSettings getFilterSettings(boolean extended) {
        return extended ? extendedFilterSettings : basicFilterSettings;
    }

    public CodeCompletionFilterSettings getBasicFilterSettings() {
        return basicFilterSettings;
    }

    public CodeCompletionFilterSettings getExtendedFilterSettings() {
        return extendedFilterSettings;
    }

    boolean acceptRootObjects(boolean extended, DBObjectType objectType) {
        return getFilterSettings(extended).acceptsRootObject(objectType);
    }

    boolean showReservedWords(boolean extended, TokenTypeIdentifier tokenTypeIdentifier) {
        return getFilterSettings(extended).acceptReservedWord(tokenTypeIdentifier);
    }

    boolean showUserSchemaObjects(boolean extended, DBObjectType objectType) {
        return getFilterSettings(extended).acceptsCurrentSchemaObject(objectType);
    }

    boolean acceptPublicSchemaObjects(boolean extended, DBObjectType objectType) {
        return getFilterSettings(extended).acceptsPublicSchemaObject(objectType);
    }

    boolean acceptAnySchemaObjects(boolean extended, DBObjectType objectType) {
        return getFilterSettings(extended).acceptsAnySchemaObject(objectType);
    }

    /*********************************************************
    *                   Configuration                       *
    *********************************************************/
    protected CodeCompletionFiltersSettingsForm createConfigurationEditor() {
        return new CodeCompletionFiltersSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "filters";
    }

    protected Configuration[] createConfigurations() {
        return new Configuration[] {
                basicFilterSettings,
                extendedFilterSettings};
    }
}