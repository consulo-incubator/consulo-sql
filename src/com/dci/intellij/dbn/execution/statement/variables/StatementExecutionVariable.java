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

package com.dci.intellij.dbn.execution.statement.variables;

import com.dci.intellij.dbn.data.type.BasicDataType;
import com.dci.intellij.dbn.language.common.psi.ExecVariablePsiElement;
import com.intellij.openapi.project.Project;

public class StatementExecutionVariable implements Comparable<StatementExecutionVariable>{
    private BasicDataType dataType;
    private String name;
    private String value;
    private TemporaryValueProvider temporaryValueProvider;
    private transient Project project;

    public StatementExecutionVariable(ExecVariablePsiElement variablePsiElement) {
        this.name = variablePsiElement.getText();
        this.project = variablePsiElement.getProject();
    }

    public Project getProject() {
        return project;
    }

    public String getName() {
        return name;
    }

    public BasicDataType getDataType() {
        return dataType;
    }

    public void setDataType(BasicDataType dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TemporaryValueProvider getTemporaryValueProvider() {
        return temporaryValueProvider;
    }

    public void setTemporaryValueProvider(TemporaryValueProvider temporaryValueProvider) {
        this.temporaryValueProvider = temporaryValueProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatementExecutionVariable that = (StatementExecutionVariable) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(StatementExecutionVariable o) {
        return o.name.length()-name.length();
    }

    public interface TemporaryValueProvider {
        String getValue();
        BasicDataType getDataType();
    }
}
