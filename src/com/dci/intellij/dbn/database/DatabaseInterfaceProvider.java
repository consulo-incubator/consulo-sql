package com.dci.intellij.dbn.database;

import com.dci.intellij.dbn.connection.DatabaseType;
import com.dci.intellij.dbn.database.common.DatabaseNativeDataTypes;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;
import com.dci.intellij.dbn.language.common.SqlLikeLanguageVersion;

public interface DatabaseInterfaceProvider {
    DatabaseType getDatabaseType();

	SqlLikeLanguageVersion<? extends SqlLikeLanguage> getLanguageDialect(SqlLikeLanguage language);

    DatabaseNativeDataTypes getNativeDataTypes();

    DatabaseMessageParserInterface getMessageParserInterface();

    DatabaseCompatibilityInterface getCompatibilityInterface();

    DatabaseMetadataInterface getMetadataInterface();

    DatabaseDebuggerInterface getDebuggerInterface();

    DatabaseDDLInterface getDDLInterface();

    DatabaseExecutionInterface getDatabaseExecutionInterface();
}
