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

import com.dci.intellij.dbn.data.type.ui.DataTypeEditor;
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObjectType;
import com.dci.intellij.dbn.object.factory.ArgumentFactoryInput;
import com.dci.intellij.dbn.object.factory.MethodFactoryInput;
import com.dci.intellij.dbn.object.factory.ObjectFactoryInput;

public class FunctionFactoryInputForm extends MethodFactoryInputForm {

    public FunctionFactoryInputForm(DBSchema schema, DBObjectType objectType, int index) {
        super(schema, objectType, index);
    }

    public MethodFactoryInput createFactoryInput(ObjectFactoryInput parent) {
        MethodFactoryInput methodFactoryInput = super.createFactoryInput(parent);

        DataTypeEditor returnTypeEditor = (DataTypeEditor) returnArgumentDataTypeEditor;

        ArgumentFactoryInput returnArgument = new ArgumentFactoryInput(
                methodFactoryInput, 0, "return",
                returnTypeEditor.getDataTypeRepresentation(),
                false, true);

        methodFactoryInput.setReturnArgument(returnArgument);
        return methodFactoryInput;
    }

    public boolean hasReturnArgument() {
        return true;
    }

    public void dispose() {
        super.dispose();
    }
}
