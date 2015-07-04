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

package com.dci.intellij.dbn.object.factory.ui.common;

import com.dci.intellij.dbn.common.ui.DBNForm;
import com.dci.intellij.dbn.common.ui.DBNFormImpl;
import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.factory.ObjectFactoryInput;

import javax.swing.JPanel;

public abstract class ObjectFactoryInputForm<T extends ObjectFactoryInput> extends DBNFormImpl implements DBNForm {
    private int index;
    private ConnectionHandler connectionHandler;
    private DBObjectType objectType;

    protected ObjectFactoryInputForm(ConnectionHandler connectionHandler, DBObjectType objectType, int index) {
        this.connectionHandler = connectionHandler;
        this.objectType = objectType;
        this.index = index;
    }

    public abstract JPanel getComponent();

    public ConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }

    public DBObjectType getObjectType() {
        return objectType;
    }

    public abstract T createFactoryInput(ObjectFactoryInput parent);

    public abstract void focus();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void dispose() {
        super.dispose();
        connectionHandler = null;
        objectType = null;
    }
}
