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

package com.dci.intellij.dbn.ddl.ui;

import com.dci.intellij.dbn.ddl.DDLFileNameProvider;
import com.intellij.ui.ColoredListCellRenderer;
import com.intellij.ui.SimpleTextAttributes;

import javax.swing.*;

public class DDLFileNameListCellRenderer extends ColoredListCellRenderer {
    protected void customizeCellRenderer(JList list, Object value, int index, boolean selected, boolean hasFocus) {
        DDLFileNameProvider fileNameProvider = (DDLFileNameProvider) value;

        append(fileNameProvider.getFileName(), SimpleTextAttributes.REGULAR_ATTRIBUTES);
        append(" (" + fileNameProvider.getDdlFileType().getDescription() + ") ", SimpleTextAttributes.GRAY_ATTRIBUTES);

        //Module module = ProjectRootManager.getInstance(psiFile.getProject()).getFileIndex().getModuleForFile(virtualFile);
        //append(" - module " + module.getName(), SimpleTextAttributes.GRAYED_ATTRIBUTES);

        setIcon(fileNameProvider.getDdlFileType().getLanguageFileType().getIcon());
    }
}