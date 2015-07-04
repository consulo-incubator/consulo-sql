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

package com.dci.intellij.dbn.execution.common.message.action;

import com.dci.intellij.dbn.execution.common.message.ui.tree.MessagesTree;
import com.intellij.openapi.project.DumbAwareAction;

import javax.swing.*;

public abstract class ExecutionMessagesAction extends DumbAwareAction {
    private MessagesTree messagesTree;

    public ExecutionMessagesAction(MessagesTree messagesTree, String text, Icon icon) {
        super(text, null, icon);
        this.messagesTree = messagesTree;
    }

    public MessagesTree getMessagesTree() {
        return messagesTree;
    }
}
