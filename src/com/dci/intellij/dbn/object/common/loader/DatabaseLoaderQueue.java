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

package com.dci.intellij.dbn.object.common.loader;

import com.dci.intellij.dbn.common.Constants;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DatabaseLoaderQueue extends Task.Modal implements Disposable {
    boolean isActive = true;
    private List<Runnable> queue = new ArrayList<Runnable>();

    public DatabaseLoaderQueue(@org.jetbrains.annotations.Nullable Project project) {
        super(project, Constants.DBN_TITLE_PREFIX + "Loading data dictionary", false);
    }

    public void queue(Runnable task) {
        queue.add(task);
    }

    public void run(@NotNull ProgressIndicator indicator) {
        while (queue.size() > 0) {
            Runnable task = queue.remove(0);
            task.run();
        }
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void dispose() {
        queue.clear();
    }
}
