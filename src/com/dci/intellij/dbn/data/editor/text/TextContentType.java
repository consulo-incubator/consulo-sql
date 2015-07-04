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

package com.dci.intellij.dbn.data.editor.text;

import com.dci.intellij.dbn.common.ui.list.Selectable;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TextContentType implements Selectable {
    private String name;
    private FileType fileType;
    private boolean enabled = true;

    public TextContentType(String name, FileType fileType) {
        this.name = name;
        this.fileType = fileType;
    }

    @Nullable
    public static TextContentType create(String name, String fileTypeName) {
        FileType fileType = FileTypeManager.getInstance().getStdFileType(fileTypeName);
        // if returned expected file type
        if (fileType.getName().equals(fileTypeName)) {
            return new TextContentType(name, fileType);
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getError() {
        return null;
    }

    public FileType getFileType() {
        return fileType;
    }

    public Icon getIcon() {
        return fileType.getIcon();
    }

    public boolean isSelected() {
        return enabled;
    }

    public boolean isMasterSelected() {
        return true;
    }

    public void setSelected(boolean enabled) {
        this.enabled = enabled;
    }
}

