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

package com.dci.intellij.dbn.common.editor.document;

import com.dci.intellij.dbn.common.util.MessageUtil;
import com.dci.intellij.dbn.vfs.SourceCodeFile;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.ReadOnlyFragmentModificationException;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.ReadonlyFragmentModificationHandler;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;

public class OverrideReadonlyFragmentModificationHandler implements
        ReadonlyFragmentModificationHandler {

    public static final Key<String> GUARDED_BLOCK_REASON = Key.create("GUARDED_BLOCK_REASON");

    private static ReadonlyFragmentModificationHandler originalHandler = EditorActionManager.getInstance().getReadonlyFragmentModificationHandler();
    public static final ReadonlyFragmentModificationHandler INSTANCE = new OverrideReadonlyFragmentModificationHandler();
    private OverrideReadonlyFragmentModificationHandler() {

    }

    public void handle(ReadOnlyFragmentModificationException e) {
        Document document = e.getGuardedBlock().getDocument();
        String message = document.getUserData(GUARDED_BLOCK_REASON);
        if (message != null) {
            MessageUtil.showErrorDialog(message, "Action denied");
        } else {
            VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);
            if (virtualFile instanceof SourceCodeFile || virtualFile instanceof LightVirtualFile) {
                //Messages.showErrorDialog("You're not allowed to change name and type of the edited component.", "Action denied");
            } else {
                originalHandler.handle(e);
            }
        }
    }
}
