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

package com.dci.intellij.dbn.common.environment;

import java.util.Set;

import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.common.AbstractProjectComponent;
import com.dci.intellij.dbn.common.environment.options.EnvironmentSettings;
import com.dci.intellij.dbn.common.event.EventManager;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.editor.DBEditorTabColorProvider;
import com.dci.intellij.dbn.options.general.GeneralProjectSettings;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorsSplitters;
import com.intellij.openapi.fileEditor.impl.FileEditorManagerImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.JDOMExternalizable;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.openapi.vfs.VirtualFile;

public class EnvironmentManager extends AbstractProjectComponent implements JDOMExternalizable, Disposable, EnvironmentChangeListener {
    private EnvironmentManager(Project project) {
        super(project);
        EventManager.subscribe(project, EnvironmentChangeListener.TOPIC, this);

    }

    public static EnvironmentManager getInstance(Project project) {
        return project.getComponent(EnvironmentManager.class);
    }

    @NonNls
    @NotNull
    public String getComponentName() {
        return "DBNavigator.Project.EnvironmentManager";
    }

    @Override
    public void environmentConfigChanged(String environmentTypeId) {
        FileEditorManagerImpl fileEditorManager = (FileEditorManagerImpl) FileEditorManager.getInstance(getProject());
        VirtualFile[] openFiles = fileEditorManager.getOpenFiles();
        Set<EditorsSplitters> splitters = fileEditorManager.getAllSplitters();
        for (VirtualFile virtualFile : openFiles) {
            ConnectionHandler connectionHandler = DBEditorTabColorProvider.getConnectionHandler(virtualFile, getProject());
            if (connectionHandler != null && connectionHandler.getSettings().getDetailSettings().getEnvironmentTypeId().equals(environmentTypeId)) {
                for (EditorsSplitters splitter : splitters) {
					updateFileBackgroundColor(splitter, virtualFile);
                }
            }
        }
    }

    @Override
    public void environmentVisibilitySettingsChanged() {
        FileEditorManagerImpl fileEditorManager = (FileEditorManagerImpl) FileEditorManager.getInstance(getProject());
        VirtualFile[] openFiles = fileEditorManager.getOpenFiles();
        Set<EditorsSplitters> splitters = fileEditorManager.getAllSplitters();
        for (VirtualFile virtualFile : openFiles) {
            for (EditorsSplitters splitter : splitters) {
				updateFileBackgroundColor(splitter, virtualFile);
            }
        }
    }

	private static void updateFileBackgroundColor(@NotNull EditorsSplitters editorsSplitters, @NotNull VirtualFile file) {
		final EditorWindow[] windows = editorsSplitters.getWindows();
		for (int i = 0; i != windows.length; ++ i) {
			windows [i].updateFileBackgroundColor(file);
		}
	}

    /****************************************
    *            JDOMExternalizable         *
    *****************************************/
    public void readExternal(Element element) throws InvalidDataException {

    }

    public void writeExternal(Element element) throws WriteExternalException {

    }

    public void dispose() {
        EventManager.unsubscribe(this);
    }

    public EnvironmentType getEnvironmentType(String id) {
        EnvironmentSettings environmentSettings = GeneralProjectSettings.getInstance(getProject()).getEnvironmentSettings();
        return environmentSettings.getEnvironmentType(id);
    }
}
