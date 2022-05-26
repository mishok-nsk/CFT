package ru.cft.shift.task6.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class UIString {
    public static final Charset SYSTEM_CHARSET = Charset.defaultCharset();

    public static String encoding(String text) {
        byte[] bytes = text.getBytes(SYSTEM_CHARSET);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
