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

package com.dci.intellij.dbn.execution.method.browser.action;

import com.dci.intellij.dbn.common.util.NamingUtil;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.execution.method.browser.ui.MethodExecutionBrowserForm;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;

public class SelectConnectionAction extends DumbAwareAction {
    private final ConnectionHandler connectionHandler;
    private MethodExecutionBrowserForm browserComponent;

    public SelectConnectionAction(MethodExecutionBrowserForm browserComponent, ConnectionHandler connectionHandler) {
        super(NamingUtil.enhanceUnderscoresForDisplay(connectionHandler.getQualifiedName()), null, connectionHandler.getIcon());
        this.browserComponent = browserComponent;
        this.connectionHandler = connectionHandler;


    }

    public void actionPerformed(AnActionEvent e) {
        browserComponent.setConnectionHandler(connectionHandler);
    }


}
