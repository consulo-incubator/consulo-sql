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
import com.dci.intellij.dbn.object.DBSchema;
import com.dci.intellij.dbn.object.common.DBObjectType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MethodFactoryInput extends ObjectFactoryInput{
    private List<ArgumentFactoryInput> arguments = new ArrayList<ArgumentFactoryInput>();
    private ArgumentFactoryInput returnArgument;
    private DBSchema schema;

    public MethodFactoryInput(DBSchema schema, String objectName, DBObjectType methodType, int index) {
        super(objectName, methodType, null, index);
        this.schema = schema;
    }

    public DBSchema getSchema() {
        return schema;
    }

    public boolean isFunction() {
        return returnArgument != null;
    }

    public List<ArgumentFactoryInput> getArguments() {
        return arguments;
    }

    public ArgumentFactoryInput getReturnArgument() {
        return returnArgument;
    }

    public void setReturnArgument(ArgumentFactoryInput returnArgument) {
        this.returnArgument = returnArgument;
    }

    public void setArguments(List<ArgumentFactoryInput> arguments) {
        this.arguments = arguments;
    }

    public void validate(List<String> errors) {
        if (getObjectName().length() == 0) {
            String hint = getParent() == null ? "" : " at index " + getIndex();
            errors.add(getObjectType().getName() + " name is not specified" + hint);
            
        } else if (!StringUtil.isWord(getObjectName())) {
            errors.add("invalid " + getObjectType().getName() +" name specified" + ": \"" + getObjectName() + "\"");
        }


        if (returnArgument != null) {
            if (returnArgument.getDataType().length() == 0)
                errors.add("missing data type for return argument");
        }

        Set<String> argumentNames = new HashSet<String>();
        for (ArgumentFactoryInput argument : arguments) {
            argument.validate(errors);
            String argumentName = argument.getObjectName();
            if (argumentName.length() > 0 && argumentNames.contains(argumentName)) {
                String hint = getParent() == null ? "" : " for " + getObjectType().getName() + " \"" + getObjectName() + "\"";
                errors.add("dupplicate argument name \"" + argumentName + "\"" + hint);
            }
            argumentNames.add(argumentName);
        }
    }
}
