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

package com.dci.intellij.dbn.language.sql;

import com.dci.intellij.dbn.common.Icons;
import com.dci.intellij.dbn.editor.DBContentType;
import com.dci.intellij.dbn.language.common.DBLanguageFileType;
import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;

public class SQLFileType extends DBLanguageFileType {

    public static final SQLFileType INSTANCE = new SQLFileType(
            SQLLanguage.INSTANCE,
            "sql", "SQL files (DBN)", DBContentType.CODE);

    public static final SQLFileType DATA_FILE_TYPE = new SQLFileType(
            SQLLanguage.INSTANCE,
            "ds", "Dataset", DBContentType.DATA);

    public SQLFileType(@NotNull Language language, String extension, String description, DBContentType contentType) {
        super(language, extension, description, contentType);
    }


    @NotNull
    public String getName() {
        return "DBN-SQL";
    }

    public Icon getIcon() {
        return Icons.FILE_SQL;
    }


}
