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
