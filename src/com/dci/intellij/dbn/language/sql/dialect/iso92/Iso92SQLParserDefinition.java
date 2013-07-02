package com.dci.intellij.dbn.language.sql.dialect.iso92;

import org.jetbrains.annotations.NotNull;
import com.dci.intellij.dbn.language.sql.SQLParserDefinition;
import com.intellij.lang.LanguageVersion;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;


public class Iso92SQLParserDefinition extends SQLParserDefinition {

    public Iso92SQLParserDefinition(Iso92SQLParser parser) {
        super(parser);
    }

    @NotNull
    public Lexer createLexer(Project project, LanguageVersion languageVersion) {
        return new FlexAdapter(new Iso92SQLParserFlexLexer(getTokenTypes()));
    }

}
