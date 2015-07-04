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

package com.dci.intellij.dbn.common.compatibility;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JTextField;

import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.find.editorHeaderActions.Utils;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.ui.components.JBList;
import com.intellij.util.ui.UIUtil;

public class CompatibilityUtil {
    public static Color getEditorBackgroundColor(EditorEx editorEx) {
        return editorEx.getBackgroundColor();
    }

    public static void stripDocumentTrailingSpaces(Project project, Document document) {
        if (document instanceof DocumentImpl) {
            DocumentImpl documentImpl = (DocumentImpl) document;
            //documentImpl.stripTrailingSpaces(true);
            documentImpl.stripTrailingSpaces(project);
        }
        
    }

    public static void showSearchCompletionPopup(boolean byClickingToolbarButton, JComponent toolbarComponent, JBList list, String title, JTextField textField) {
        //Utils.showCompletionPopup(byClickingToolbarButton ? toolbarComponent : null, list, title, textField);
        Utils.showCompletionPopup(byClickingToolbarButton ? toolbarComponent : null, list, title, textField, "");
    }

    public static void setSmallerFontForChildren(JComponent component) {
        Utils.setSmallerFontForChildren(component);
    }

    public static void setSmallerFont(JComponent component) {
        Utils.setSmallerFont(component);
    }

    public static boolean isUnderGTKLookAndFeel() {
        return UIUtil.isUnderGTKLookAndFeel();
    }

    public static String getParseRootId(VirtualFile virtualFile) {
        if (virtualFile instanceof SourceCodeFile) {
            SourceCodeFile sourceCodeFile = (SourceCodeFile) virtualFile;
            return sourceCodeFile.getParseRootId();
        } else if (virtualFile instanceof LightVirtualFile) {
            LightVirtualFile lightVirtualFile = (LightVirtualFile) virtualFile;
            VirtualFile originalFile = lightVirtualFile.getOriginalFile();
            return getParseRootId(originalFile);
        }
        return null;
    }

}
