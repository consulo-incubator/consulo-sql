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

import com.dci.intellij.dbn.code.common.completion.options.filter.ui.CheckedTreeNodeProvider;
import com.dci.intellij.dbn.code.common.completion.options.filter.ui.CodeCompletionFilterTreeNode;
import com.dci.intellij.dbn.common.options.setting.SettingsUtil;
import com.dci.intellij.dbn.language.common.TokenTypeIdentifier;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.intellij.ui.CheckedTreeNode;
import org.jdom.Element;

import javax.swing.Icon;

public class CodeCompletionFilterOption implements CheckedTreeNodeProvider {
    private CodeCompletionFilterSettings filterSettings;
    private DBObjectType objectType;
    private TokenTypeIdentifier tokenTypeIdentifier = TokenTypeIdentifier.UNKNOWN;
    private boolean selected;

    public CodeCompletionFilterOption(CodeCompletionFilterSettings filterSettings) {
        this.filterSettings = filterSettings;
    }

    public CodeCompletionFilterSettings getFilterSettings() {
        return filterSettings;
    }

    public DBObjectType getObjectType() {
        return objectType;
    }

    public TokenTypeIdentifier getTokenTypeIdentifier() {
        return tokenTypeIdentifier;
    }

    public String getName() {
        return objectType == null ?
                tokenTypeIdentifier.getName() :
                objectType.getName().toUpperCase();
    }

    public Icon getIcon() {
        return objectType == null ? null : objectType.getIcon();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void readExternal(Element element) {
        if (element != null) {
            String filterElementType = element.getAttributeValue("type");
            if (filterElementType.equals("OBJECT")) {
                String objectTypeName = element.getAttributeValue("id");
                objectType = DBObjectType.getObjectType(objectTypeName);
            } else {
                String tokenTypeName = element.getAttributeValue("id");
                tokenTypeIdentifier = TokenTypeIdentifier.getIdentifier(tokenTypeName);
            }
            selected = SettingsUtil.getBooleanAttribute(element, "selected", selected);
        }

    }

    public void writeExternal(Element element) {
        if (objectType != null) {
            element.setAttribute("type", "OBJECT");
            element.setAttribute("id", objectType.getName());

        } else {
            element.setAttribute("type", "RESERVED_WORD");
            element.setAttribute("id", tokenTypeIdentifier.getName());
        }

        SettingsUtil.setBooleanAttribute(element, "selected", selected);
    }

    public CheckedTreeNode createCheckedTreeNode() {
        return new CodeCompletionFilterTreeNode(this, selected);
    }

    public boolean equals(Object o) {
        CodeCompletionFilterOption option = (CodeCompletionFilterOption) o;
        return
            option.objectType == objectType &&
            option.tokenTypeIdentifier == tokenTypeIdentifier;
    }
}
