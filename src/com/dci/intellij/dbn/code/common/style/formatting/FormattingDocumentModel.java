package com.dci.intellij.dbn.code.common.style.formatting;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;

public class FormattingDocumentModel implements com.intellij.formatting.FormattingDocumentModel {
    public int getLineNumber(int offset) {
        return 0;
    }

    public int getLineStartOffset(int line) {
        return 0;
    }

    public CharSequence getText(final TextRange textRange) {
        return null;
    }

    public int getTextLength() {
        return 0;
    }

    public Document getDocument() {
        return null;
    }

    public boolean containsWhiteSpaceSymbolsOnly(int startOffset, int endOffset) {
        return false;
    }

    @NotNull
    public CharSequence adjustWhiteSpaceIfNecessary(@NotNull CharSequence charSequence, int i, int i1, boolean b) {
        return null;
    }

    @NotNull
    public CharSequence adjustWhiteSpaceIfNecessary(@NotNull CharSequence whiteSpaceText, int startOffset, int endOffset) {
        return null;
    }
}
