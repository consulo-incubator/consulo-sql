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

package com.dci.intellij.dbn.common.content.dependency;

import com.dci.intellij.dbn.common.content.DynamicContent;
import com.dci.intellij.dbn.connection.ConnectionHandler;

public class BasicDependencyAdapter implements ContentDependencyAdapter {
    private ConnectionHandler connectionHandler;

    public BasicDependencyAdapter(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    protected boolean isConnectionValid() {
        return connectionHandler.isValid();
    }

    public boolean shouldLoad() {
        // should not reload just like that
        return false;
    }

    public boolean shouldLoadIfDirty() {
        //should reload if connection is valid
        return connectionHandler == null || isConnectionValid();
    }

    public boolean hasDirtyDependencies() {
        return false;
    }

    public void beforeLoad() {
        // nothing to do before load
    }

    public void afterLoad() {
        // nothing to do after load
    }

    public void beforeReload(DynamicContent dynamicContent) {

    }

    public void afterReload(DynamicContent dynamicContent) {

    }

    public boolean isSourceContentLoaded() {
        return false;
    }

    @Override
    public boolean isSubContent() {
        return false;
    }

    public void dispose() {
        connectionHandler = null;
    }
}
