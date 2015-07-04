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

package com.dci.intellij.dbn.data.editor.ui;

import com.dci.intellij.dbn.data.editor.text.TextContentType;
import com.intellij.openapi.project.Project;

public class UserValueHolderImpl implements UserValueHolder{
    private String name;
    private Project project;
    private Object userValue;

    public UserValueHolderImpl(String name, Project project) {
        this.name = name;
        this.project = project;
    }

    public Object getUserValue() {
        return userValue;
    }

    @Override
    public String getFormattedUserValue() {
        throw new UnsupportedOperationException();
    }

    public void setUserValue(Object userValue) {
        this.userValue = userValue;
    }

    public void updateUserValue(Object userValue, boolean bulk) {
        this.userValue = userValue;
    }

    public TextContentType getContentType() {
        return null;
    }

    public void setContentType(TextContentType contentType) {
    }

    public String getName() {
        return name;
    }

    public Project getProject() {
        return project;
    }
}
