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

package com.dci.intellij.dbn.object.factory.ui;

import com.dci.intellij.dbn.connection.ConnectionHandler;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.factory.ArgumentFactoryInput;
import com.dci.intellij.dbn.object.factory.ui.common.ObjectFactoryInputForm;
import com.dci.intellij.dbn.object.factory.ui.common.ObjectListForm;

public class ArgumentFactoryInputListPanel extends ObjectListForm<ArgumentFactoryInput> {
    private boolean enforceInArguments;
    public ArgumentFactoryInputListPanel(ConnectionHandler connectionHandler, boolean enforceInArguments) {
        super(connectionHandler);
        this.enforceInArguments = enforceInArguments;
    }

    public ObjectFactoryInputForm createObjectDetailsPanel(int index) {
        return new ArgumentFactoryInputForm(getConnectionHandler(), enforceInArguments, index);
    }

    public DBObjectType getObjectType() {
        return DBObjectType.ARGUMENT;
    }
}
