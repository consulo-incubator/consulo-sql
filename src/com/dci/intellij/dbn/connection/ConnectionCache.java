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

package com.dci.intellij.dbn.connection;

import com.dci.intellij.dbn.common.event.EventManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.impl.ProjectLifecycleListener;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ConnectionCache implements ApplicationComponent{
    private static Map<String, ConnectionHandler> CACHE = new THashMap<String, ConnectionHandler>();

    public static synchronized ConnectionHandler findConnectionHandler(String connectionId) {
        ConnectionHandler connectionHandler = CACHE.get(connectionId);
        if (connectionHandler == null) {
            for (Project project : ProjectManager.getInstance().getOpenProjects()) {
                ConnectionManager connectionManager = ConnectionManager.getInstance(project);
                connectionHandler = connectionManager.getConnectionHandler(connectionId);
                if (connectionHandler != null) {
                    CACHE.put(connectionId, connectionHandler);
                    return connectionHandler;
                }
            }
        }
        return connectionHandler;
    }


    @Override
    public void initComponent() {
        EventManager.subscribe(ProjectLifecycleListener.TOPIC, projectLifecycleListener);
    }

    @Override
    public void disposeComponent() {
        EventManager.unsubscribe(projectLifecycleListener);
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "DBNavigator.ConnectionCache";
    }

    /*********************************************************
     *              ProjectLifecycleListener                 *
     *********************************************************/
    private ProjectLifecycleListener projectLifecycleListener = new ProjectLifecycleListener.Adapter() {

        @Override
        public void projectComponentsInitialized(Project project) {
            Set<ConnectionHandler> connectionHandlers = ConnectionManager.getInstance(project).getConnectionHandlers();
            for (ConnectionHandler connectionHandler : connectionHandlers) {
                CACHE.put(connectionHandler.getId(), connectionHandler);
            }
        }

        @Override
        public void afterProjectClosed(@NotNull Project project) {
            Iterator<String> connectionIds = CACHE.keySet().iterator();
            while (connectionIds.hasNext()) {
                String connectionId = connectionIds.next();
                ConnectionHandler connectionHandler = CACHE.get(connectionId);
                if (connectionHandler.isDisposed() || connectionHandler.getProject() == project) {
                    connectionIds.remove();
                }
            }

        }
    };
}
