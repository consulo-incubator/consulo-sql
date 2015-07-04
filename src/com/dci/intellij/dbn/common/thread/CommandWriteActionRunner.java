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

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.project.Project;

public abstract class CommandWriteActionRunner {
    private Project project;
    private String commandName;

    public CommandWriteActionRunner(Project project, String commandName) {
        this.project = project;
        this.commandName = commandName;
    }

    protected CommandWriteActionRunner(Project project) {
        this.project = project;
        this.commandName = "";
    }

    public final void start() {
        Runnable command = new Runnable() {
            public void run() {
                Runnable writeAction = new Runnable() {
                    public void run() {
                        CommandWriteActionRunner.this.run();
                    }
                };
                ApplicationManager.getApplication().runWriteAction(writeAction);
            }
        };
        CommandProcessor.getInstance().executeCommand(project, command, commandName, null);
    }

    public abstract void run();

}
