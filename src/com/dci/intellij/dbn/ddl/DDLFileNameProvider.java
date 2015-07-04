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

import com.dci.intellij.dbn.object.common.DBObject;

public class DDLFileNameProvider {
    private DBObject object;
    private DDLFileType ddlFileType;
    private String extension;

    public DDLFileNameProvider(DBObject object, DDLFileType ddlFileType, String extension) {
        this.object = object;
        this.ddlFileType = ddlFileType;
        this.extension = extension;
    }

    public DBObject getObject() {
        return object;
    }

    public DDLFileType getDdlFileType() {
        return ddlFileType;
    }

    public String getExtension() {
        return extension;
    }

    public String getFileName() {
        return object.getName().toLowerCase() + "." + extension;
    }
}
