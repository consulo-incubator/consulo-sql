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

package com.dci.intellij.dbn.common.editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.openapi.fileEditor.TextEditor;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.Navigatable;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import java.beans.PropertyChangeListener;

public abstract class BasicTextEditorImpl<T extends VirtualFile> implements BasicTextEditor<T>{
    protected TextEditor textEditor;
    private T virtualFile;
    private String name;
    private BasicTextEditorState editorState;

    public BasicTextEditorImpl(Project project, T virtualFile, String name) {
        this.name = name;
        this.virtualFile = virtualFile;
        textEditor = (TextEditor) TextEditorProvider.getInstance().createEditor(project, virtualFile);

    }

    public T getVirtualFile() {
        return virtualFile;
    }

    public <T> T getUserData(@NotNull Key<T> key) {
        return textEditor.getUserData(key);
    }

    public <T> void putUserData(@NotNull Key<T> key, T value) {
        textEditor.putUserData(key, value);
    }

    public boolean isModified() {
        return textEditor.isModified();
    }

    public boolean isValid() {
        return textEditor.isValid();
    }

    public void selectNotify() {
        textEditor.selectNotify();
    }

    public void deselectNotify() {
        textEditor.deselectNotify();
    }

    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {
        textEditor.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {
        textEditor.removePropertyChangeListener(listener);
    }

    @Nullable
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return textEditor.getBackgroundHighlighter();
    }

    public FileEditorLocation getCurrentLocation() {
        return textEditor.getCurrentLocation();
    }

    @NotNull
    public Editor getEditor() {
        return textEditor.getEditor();
    }

    public boolean canNavigateTo(@NotNull final Navigatable navigatable) {
        return textEditor.canNavigateTo(navigatable);
    }

    public void navigateTo(@NotNull final Navigatable navigatable) {
        textEditor.navigateTo(navigatable);
    }

    @NotNull
    public JComponent getComponent() {
        return textEditor.getComponent();
    }

    @Nullable
    public JComponent getPreferredFocusedComponent() {
        return textEditor.getPreferredFocusedComponent();
    }

    protected BasicTextEditorState createEditorState() {
        return new BasicTextEditorState();
    }

    @NotNull
    public FileEditorState getState(@NotNull FileEditorStateLevel level) {
        if (editorState == null) editorState = createEditorState();
        editorState.loadFromEditor(level, textEditor);
        return editorState;
    }

    public void setState(@NotNull FileEditorState fileEditorState) {
        if (fileEditorState instanceof BasicTextEditorState) {
            BasicTextEditorState state = (BasicTextEditorState) fileEditorState;
            state.applyToEditor(textEditor);
        }
    }

    public void dispose() {
        TextEditorProvider.getInstance().disposeEditor(textEditor);
    }

    @NonNls
    @NotNull
    public String getName() {
        return name;
    }

    @Nullable
    public StructureViewBuilder getStructureViewBuilder() {
        return textEditor.getStructureViewBuilder();
    }
}
