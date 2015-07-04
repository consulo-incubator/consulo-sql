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

package com.dci.intellij.dbn.common.options.ui;

import com.intellij.openapi.options.ConfigurationException;

import javax.swing.JTextField;

public class ConfigurationEditorUtil {
    public static int validateIntegerInputValue(JTextField inputField, String name, int min, int max, String hint) throws ConfigurationException {
        try {
            int integer = Integer.parseInt(inputField.getText());
            if (min > integer || max < integer) throw new NumberFormatException("Number not in range");
            return integer;
        } catch (NumberFormatException e) {
            inputField.grabFocus();
            inputField.selectAll();
            String message = "Input value for \"" + name + "\" must be an integer between " + min + " and " + max + ".";
            if (hint != null) {
                message = message + " " + hint;
            }
            throw new ConfigurationException(message, "Invalid config value");
        }
    }

    public static String validateStringInputValue(JTextField inputField, String name, boolean required) throws ConfigurationException {
        String value = inputField.getText().trim();
        if (required && value.length() == 0) {
            String message = "Input value for \"" + name + "\" must be specified";
            throw new ConfigurationException(message, "Invalid config value");
        }
        return value;
    }
    
}
