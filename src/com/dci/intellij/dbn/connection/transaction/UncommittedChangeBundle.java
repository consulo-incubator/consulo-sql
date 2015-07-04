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

package com.dci.intellij.dbn.connection.transaction;


import com.dci.intellij.dbn.vfs.DatabaseObjectFile;
import com.dci.intellij.dbn.vfs.SQLConsoleFile;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.Icon;
import java.util.ArrayList;
import java.util.List;

public class UncommittedChangeBundle {
    private List<UncommittedChange> changes = new ArrayList<UncommittedChange>();

    public void notifyChange(VirtualFile file){
        Icon icon = file.getFileType().getIcon();
        if (file instanceof DatabaseObjectFile) {
            DatabaseObjectFile databaseObjectFile = (DatabaseObjectFile) file;
            icon = databaseObjectFile.getIcon();
        }
        if (file instanceof SQLConsoleFile) {
            SQLConsoleFile sqlConsoleFile = (SQLConsoleFile) file;
            icon = sqlConsoleFile.getIcon();
        }

        String url = file.getUrl();

        UncommittedChange change = getUncommittedChange(file);
        if (change == null) {
            change = new UncommittedChange(url, file.getPresentableUrl(), icon);
            changes.add(change);
        }
        change.incrementChangesCount();
    }

    public UncommittedChange getUncommittedChange(VirtualFile file) {
        for (UncommittedChange change : changes) {
            if (change.getFilePath().equals(file.getUrl())) {
                return change;
            }
        }
        return null;
    }

    public List<UncommittedChange> getChanges() {
        return changes;
    }

    public int size() {
        return changes.size();
    }

    public boolean isEmpty() {
        return changes.isEmpty();
    }
}
