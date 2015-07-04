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

package com.dci.intellij.dbn.common.thread;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

public abstract class ModalTask extends Task.Modal{
    public ModalTask(Project project, String title, boolean canBeCancelled) {
        super(project, title, canBeCancelled);
    }

    public ModalTask(Project project, String title) {
        super(project, title, false);
    }


    public void start() {
        final ProgressManager progressManager = ProgressManager.getInstance();
        Application application = ApplicationManager.getApplication();

        if (application.isDispatchThread()) {
            progressManager.run(ModalTask.this);
        } else {
            Runnable runnable = new Runnable() {
                public void run() {
                    progressManager.run(ModalTask.this);
                }
            };
            application.invokeLater(runnable, ModalityState.NON_MODAL);
        }
    }
}
