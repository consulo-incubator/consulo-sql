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

package com.dci.intellij.dbn.object.factory;

import com.dci.intellij.dbn.common.util.StringUtil;
import com.dci.intellij.dbn.object.common.DBObjectType;

import java.util.List;


public class ArgumentFactoryInput extends ObjectFactoryInput{

    private String dataType;
    private boolean isInput;
    private boolean isOutput;

    public ArgumentFactoryInput(ObjectFactoryInput parent, int index, String objectName, String dataType, boolean input, boolean output) {
        super(objectName, DBObjectType.ARGUMENT, parent, index);
        this.dataType = dataType == null ? "" : dataType.trim();
        this.isInput = input;
        this.isOutput = output;
    }

    public String getDataType() {
        return dataType;
    }

    public boolean isInput() {
        return isInput;
    }

    public boolean isOutput() {
        return isOutput;
    }

    public void validate(List<String> errors) {
        if (getObjectName().length() == 0) {
            errors.add("argument name is not specified at index " + getIndex());

        } else if (!StringUtil.isWord(getObjectName())) {
            errors.add("invalid argument name specified at index " + getIndex() + ": \"" + getObjectName() + "\"");
        }

        if (dataType.length() == 0){
            if (getObjectName().length() > 0) {
                errors.add("missing data type for argument \"" + getObjectName() + "\"");
            } else {
                errors.add("missing data type for argument at index " + getIndex());
            }
        }
    }
}
