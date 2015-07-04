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

package com.dci.intellij.dbn.ddl;

public interface DDLFileTypeId {
    public static final String VIEW = "VIEW";
    public static final String TRIGGER = "TRIGGER";
    public static final String PROCEDURE = "PROCEDURE";
    public static final String FUNCTION = "FUNCTION";
    public static final String PACKAGE = "PACKAGE";
    public static final String PACKAGE_SPEC = "PACKAGE_SPEC";
    public static final String PACKAGE_BODY = "PACKAGE_BODY";
    public static final String TYPE = "TYPE";
    public static final String TYPE_SPEC = "TYPE_SPEC";
    public static final String TYPE_BODY = "TYPE_BODY";

}
