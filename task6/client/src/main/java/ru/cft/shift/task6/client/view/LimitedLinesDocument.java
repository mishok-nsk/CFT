package ru.cft.shift.task6.client.view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

public class LimitedLinesDocument extends DefaultStyledDocument {
    private final int maxChars;

    public LimitedLinesDocument(int maxChars) {
        this.maxChars = maxChars;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet attribute) throws BadLocationException {
        if (str.length() + offs < maxChars)
            super.insertString(offs, str, attribute);
    }
}
