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

package com.dci.intellij.dbn.data.editor.color;

import com.dci.intellij.dbn.common.Icons;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.PlainSyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataGridColorSettingsPage implements ColorSettingsPage {
    protected final List<AttributesDescriptor> attributeDescriptors = new ArrayList<AttributesDescriptor>();
    protected final List<ColorDescriptor> colorDescriptors = new ArrayList<ColorDescriptor>();

    public DataGridColorSettingsPage() {
        attributeDescriptors.add(new AttributesDescriptor("Plain Data", DataGridTextAttributesKeys.PLAIN_DATA));
        attributeDescriptors.add(new AttributesDescriptor("Modified Data", DataGridTextAttributesKeys.MODIFIED_DATA));
        attributeDescriptors.add(new AttributesDescriptor("Deleted Data", DataGridTextAttributesKeys.DELETED_DATA));
        attributeDescriptors.add(new AttributesDescriptor("Error Data", DataGridTextAttributesKeys.ERROR_DATA));
        attributeDescriptors.add(new AttributesDescriptor("Readonly Data", DataGridTextAttributesKeys.READONLY_DATA));
        attributeDescriptors.add(new AttributesDescriptor("Loading Data", DataGridTextAttributesKeys.LOADING_DATA));
        attributeDescriptors.add(new AttributesDescriptor("Primary Key", DataGridTextAttributesKeys.PRIMARY_KEY));
        attributeDescriptors.add(new AttributesDescriptor("Foreign Key", DataGridTextAttributesKeys.FOREIGN_KEY));
        attributeDescriptors.add(new AttributesDescriptor("Selection", DataGridTextAttributesKeys.SELECTION));
        colorDescriptors.add(new ColorDescriptor("Caret Row", DataGridTextAttributesKeys.CARET_ROW_BACKGROUND, ColorDescriptor.Kind.BACKGROUND));
    }

    @Override
    public Icon getIcon() {
        return Icons.FILE_SQL;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new PlainSyntaxHighlighter();
    }

    @NonNls
    @NotNull
    public final String getDemoText() {
        return " ";
    }

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return attributeDescriptors.toArray(new AttributesDescriptor[attributeDescriptors.size()]);
    }

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return colorDescriptors.toArray(new ColorDescriptor[colorDescriptors.size()]);
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Data Grid (DBN)";
    }

    @Nullable
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }
}
