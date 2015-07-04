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

import javax.swing.Icon;

public class UncommittedChange {
    private String filePath;
    private String displayFilePath;
    private Icon icon;
    private int changesCount = 0;

    public UncommittedChange(String filePath, String displayFilePath, Icon icon) {
        this.filePath = filePath;
        this.displayFilePath = displayFilePath;
        this.icon = icon;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDisplayFilePath() {
        return displayFilePath;
    }

    public Icon getIcon() {
        return icon;
    }

    public int getChangesCount() {
        return changesCount;
    }

    public void incrementChangesCount() {
        changesCount++;
    }
}
