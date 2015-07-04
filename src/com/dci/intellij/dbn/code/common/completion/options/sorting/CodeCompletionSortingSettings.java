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

package com.dci.intellij.dbn.code.common.completion.options.sorting;

import com.dci.intellij.dbn.code.common.completion.options.sorting.ui.CodeCompletionSortingSettingsForm;
import com.dci.intellij.dbn.code.common.lookup.AliasLookupItemFactory;
import com.dci.intellij.dbn.code.common.lookup.DBObjectLookupItemFactory;
import com.dci.intellij.dbn.code.common.lookup.LookupItemFactory;
import com.dci.intellij.dbn.code.common.lookup.TokenLookupItemFactory;
import com.dci.intellij.dbn.common.options.Configuration;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;

public class CodeCompletionSortingSettings extends Configuration<CodeCompletionSortingSettingsForm> {
    private boolean enabled = true;
    private List<CodeCompletionSortingItem> sortingItems = new ArrayList<CodeCompletionSortingItem>();

    public int getSortingIndexFor(LookupItemFactory lookupItemFactory) {
        if (lookupItemFactory instanceof AliasLookupItemFactory) {
            return -1;
        }
        if (lookupItemFactory instanceof DBObjectLookupItemFactory) {
            DBObjectLookupItemFactory objectLookupItemFactory = (DBObjectLookupItemFactory) lookupItemFactory;
            DBObjectType objectType = objectLookupItemFactory.getObject().getObjectType();
            return getSortingIndexFor(objectType);
        }

        if (lookupItemFactory instanceof TokenLookupItemFactory) {
            TokenLookupItemFactory tokenLookupItemFactory = (TokenLookupItemFactory) lookupItemFactory;
            TokenType tokenType = tokenLookupItemFactory.getTokenType();
            return getSortingIndexFor(tokenType);
        }
        return 0;
    }

    public int getSortingIndexFor(DBObjectType objectType) {
        for (int i=0; i<sortingItems.size(); i++) {
            if (sortingItems.get(i).getObjectType() == objectType) {
                return sortingItems.size() - i;
            }
        }
        return 0;
    }

    public int getSortingIndexFor(TokenType tokenType) {
        for (int i=0; i<sortingItems.size(); i++) {
            if (sortingItems.get(i).getTokenTypeIdentifier() == tokenType.getTokenTypeIdentifier()) {
                return sortingItems.size() - i;
            }
        }
        return 0;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }


    public List<CodeCompletionSortingItem> getSortingItems() {
        return sortingItems;
    }

    public String getDisplayName() {
        return "Code completion sorting";
    }

    /*********************************************************
     *                     Configuration                     *
     *********************************************************/
    public CodeCompletionSortingSettingsForm createConfigurationEditor() {
        return new CodeCompletionSortingSettingsForm(this);
    }

    @Override
    public String getConfigElementName() {
        return "sorting";
    }

    public void readConfiguration(Element element) throws InvalidDataException {
        enabled = SettingsUtil.getBooleanAttribute(element, "enabled", enabled);
        for (Object child : element.getChildren()) {
            Element childElement = (Element) child;
            CodeCompletionSortingItem sortingItem = new CodeCompletionSortingItem();
            sortingItem.readConfiguration(childElement);
            if (sortingItems.contains(sortingItem)) {
                sortingItems.remove(sortingItem);
            }
            sortingItems.add(sortingItem);
        }
    }

    public void writeConfiguration(Element element) throws WriteExternalException {
        SettingsUtil.setBooleanAttribute(element, "enabled", enabled);
        for (CodeCompletionSortingItem sortingItem : sortingItems) {
            writeConfiguration(element, sortingItem);
        }
    }

}
