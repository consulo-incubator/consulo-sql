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

package com.dci.intellij.dbn.code.common.color;

import com.dci.intellij.dbn.common.util.CommonUtil;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class DBLColorSettingsPage implements ColorSettingsPage {
    private String demoText;
    protected final List<AttributesDescriptor> attributeDescriptors = new ArrayList<AttributesDescriptor>();

    @NonNls
    @NotNull
    public final String getDemoText() {
        if (demoText == null) {
            InputStream inputStream = getClass().getResourceAsStream(getDemoTextFileName());
            try {
                demoText = CommonUtil.readInputStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return demoText.replace("\r\n", "\n");
    }

    public abstract String getDemoTextFileName();

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return attributeDescriptors.toArray(new AttributesDescriptor[attributeDescriptors.size()]);
    }

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return new ColorDescriptor[0];
    }

    @Nullable
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }
}
