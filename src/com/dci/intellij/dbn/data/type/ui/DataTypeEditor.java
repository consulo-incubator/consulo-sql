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

package com.dci.intellij.dbn.data.type.ui;

import com.dci.intellij.dbn.code.common.style.options.CodeStyleCaseOption;
import com.dci.intellij.dbn.code.psql.style.options.PSQLCodeStyleSettings;
import com.dci.intellij.dbn.data.editor.ui.TextFieldWithPopup;
import com.dci.intellij.dbn.data.type.DataTypeDefinition;
import com.dci.intellij.dbn.connection.ConnectionHandler;

import java.util.ArrayList;
import java.util.List;

public class DataTypeEditor extends TextFieldWithPopup {
    public DataTypeEditor(ConnectionHandler connectionHandler) {
        super(connectionHandler.getProject());
        PSQLCodeStyleSettings codeStyleSettings =
                PSQLCodeStyleSettings.getInstance(connectionHandler.getProject());
        CodeStyleCaseOption caseOption = codeStyleSettings.getCaseSettings().getDatatypeCaseOption();

        List<DataTypeDefinition> nativeDataTypes = connectionHandler.getInterfaceProvider().getNativeDataTypes().list();
        List<String> nativeDataTypeNames = new ArrayList<String>();
        for (int i=0; i<nativeDataTypes.size(); i++) {
            String typeName = nativeDataTypes.get(i).getName();
            typeName = caseOption.changeCase(typeName);
            nativeDataTypeNames.add(typeName);
        }
        createValuesListPopup(nativeDataTypeNames, false);
    }



    public String getDataTypeRepresentation() {
        return getTextField().getText();
    }
}
