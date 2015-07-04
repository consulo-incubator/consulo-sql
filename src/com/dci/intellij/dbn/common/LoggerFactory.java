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

package com.dci.intellij.dbn.common;

import com.intellij.openapi.diagnostic.Logger;

public class LoggerFactory {

    /**
     * Creates or returns the instance of a Logger for the class within this method is issued.
     * The name of the class is dynamically determined by inquiring the
     * stacktrace of the method execution.
     * NOTE: do not use this method but for static loggers in class initialisations.  
     */
    public static Logger createLogger() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = stackTraceElements[0];
        for (int i=0; i<stackTraceElements.length; i++) {
            if (stackTraceElements[i].getMethodName().equals("createLogger")) {
                stackTraceElement = i+1 <stackTraceElements.length ? stackTraceElements[i+1] : stackTraceElements[i];
                break;
            }
        }

        String className = stackTraceElement.getClassName();
        return Logger.getInstance(className);
    }

    /**
     * Creates or returns the instance of a Logger for the given class.
     */
    public static Logger createLogger(Class clazz) {
        return Logger.getInstance(clazz.getName());
    }

    /**
     * Creates or returns the instance of a Logger for the given name.
     */
    public static Logger createLogger(String name) {
        return Logger.getInstance(name);
    }
}
