package com.dci.intellij.dbn.language.common.element;

import com.dci.intellij.dbn.code.common.lookup.LookupItemFactory;
import com.dci.intellij.dbn.language.common.SqlLikeLanguage;

public interface TokenElementType extends LeafElementType {
    String SEPARATOR = "SEPARATOR";

    boolean isCharacter();

    LookupItemFactory getLookupItemFactory(SqlLikeLanguage language);
}
