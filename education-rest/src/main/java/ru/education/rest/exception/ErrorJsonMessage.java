package ru.education.rest.exception;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class ErrorJsonMessage implements Supplier<String> {

    private static final String BODY = "\"method\": \"{0}\", \"url\": \"{1}\", \"message\": \"{2}\"";
    private final String[] values;

    public ErrorJsonMessage(String method, String url, String message) {
        this.values = new String[]{method, url, message};
    }

    @Override
    public String get() {
        return "{" + MessageFormat.format(BODY, (Object[]) values) + "}";
    }
}
