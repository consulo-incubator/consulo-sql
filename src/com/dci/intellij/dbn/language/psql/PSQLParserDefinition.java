package com.dci.intellij.dbn.language.psql;

import com.dci.intellij.dbn.language.common.DBLanguageParser;
import com.dci.intellij.dbn.language.common.DBLanguageParserDefinition;
import com.dci.intellij.dbn.language.common.TokenTypeBundle;
import com.intellij.lang.LanguageVersion;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;


public class PSQLParserDefinition extends DBLanguageParserDefinition {

    public PSQLParserDefinition() {
        this((PSQLParser) getDefaultParseDefinition().getParser());
    }

    public PSQLParserDefinition(PSQLParser parser) {
        super(parser);
    }

    @NotNull
    public Lexer createLexer(Project project, LanguageVersion languageVersion) {
        return getDefaultParseDefinition().createLexer(project, languageVersion);
    }

    private static DBLanguageParserDefinition getDefaultParseDefinition() {
        return PSQLLanguage.INSTANCE.getMainLanguageDialect().getParserDefinition();
    }

    @NotNull
    public DBLanguageParser createParser(Project project, LanguageVersion languageVersion) {
        return getParser();
    }

    public TokenTypeBundle getTokenTypes() {
        return getParser().getTokenTypes();
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new PSQLFile(viewProvider, PSQLLanguage.INSTANCE);
    }
}