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

package com.dci.intellij.dbn.database.common;

import com.dci.intellij.dbn.database.DatabaseInterfaceProvider;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;
import com.dci.intellij.dbn.language.psql.PSQLLanguage;
import com.dci.intellij.dbn.language.sql.SQLLanguage;

public abstract class DatabaseInterfaceProviderImpl implements DatabaseInterfaceProvider {
    private SqlLikeLanguageVersion<SQLLanguage> sqlLanguageDialect;
    private SqlLikeLanguageVersion<PSQLLanguage> psqlLanguageDialect;

    protected DatabaseInterfaceProviderImpl(Class<? extends SqlLikeLanguageVersion<SQLLanguage>> csql, Class<? extends SqlLikeLanguageVersion<PSQLLanguage>> psql) {
        this.sqlLanguageDialect = csql == null ? null : SQLLanguage.INSTANCE.findVersionByClass(csql);
        this.psqlLanguageDialect = psql == null ? null : PSQLLanguage.INSTANCE.findVersionByClass(psql);
    }

    public SqlLikeLanguageVersion<? extends SqlLikeLanguage> getLanguageDialect(SqlLikeLanguage language) {
        if (language == SQLLanguage.INSTANCE) return sqlLanguageDialect;
        if (language == PSQLLanguage.INSTANCE) return psqlLanguageDialect;
        return null;
    }
}
