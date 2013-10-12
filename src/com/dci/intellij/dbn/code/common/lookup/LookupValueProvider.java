package com.dci.intellij.dbn.code.common.lookup;

import com.dci.intellij.dbn.language.common.SqlLikeLanguage;

public interface LookupValueProvider {

    LookupItemFactory getLookupItemFactory(SqlLikeLanguage language);

}
