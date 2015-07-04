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

package com.dci.intellij.dbn.code.psql.color;

import javax.swing.Icon;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.dci.intellij.dbn.code.common.color.DBLColorSettingsPage;
import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.language.psql.PSQLLanguage;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;

public class PSQLColorSettingsPage extends DBLColorSettingsPage {
    public PSQLColorSettingsPage() {
        attributeDescriptors.add(new AttributesDescriptor("Line Comment", PSQLTextAttributesKeys.LINE_COMMENT));
        attributeDescriptors.add(new AttributesDescriptor("Block Comment", PSQLTextAttributesKeys.BLOCK_COMMENT));
        attributeDescriptors.add(new AttributesDescriptor("String Literal", PSQLTextAttributesKeys.STRING));
        attributeDescriptors.add(new AttributesDescriptor("Numeric Literal", PSQLTextAttributesKeys.NUMBER));
        attributeDescriptors.add(new AttributesDescriptor("Alias", PSQLTextAttributesKeys.ALIAS));
        attributeDescriptors.add(new AttributesDescriptor("Identifier", PSQLTextAttributesKeys.IDENTIFIER));
        attributeDescriptors.add(new AttributesDescriptor("Quoted Identifier", PSQLTextAttributesKeys.QUOTED_IDENTIFIER));
        attributeDescriptors.add(new AttributesDescriptor("Keyword", PSQLTextAttributesKeys.KEYWORD));
        attributeDescriptors.add(new AttributesDescriptor("Function", PSQLTextAttributesKeys.FUNCTION));
        attributeDescriptors.add(new AttributesDescriptor("DataType", PSQLTextAttributesKeys.DATA_TYPE));
        attributeDescriptors.add(new AttributesDescriptor("Parenthesis", PSQLTextAttributesKeys.PARENTHESIS));
        attributeDescriptors.add(new AttributesDescriptor("Exception", PSQLTextAttributesKeys.EXCEPTION));
        attributeDescriptors.add(new AttributesDescriptor("Bracket", PSQLTextAttributesKeys.BRACKET));
        attributeDescriptors.add(new AttributesDescriptor("Operator", PSQLTextAttributesKeys.OPERATOR));
    }

    @NotNull
    public String getDisplayName() {
        return "PL/SQL (DBN)";
    }
    @Nullable
    public Icon getIcon() {
        return Icons.FILE_PLSQL;
    }

    @NotNull
    public SyntaxHighlighter getHighlighter() {
        return PSQLLanguage.INSTANCE.getFirstVersion().getSyntaxHighlighter();
    }

    public String getDemoTextFileName() {
        return "plsql_demo_text.txt";  
    }
}