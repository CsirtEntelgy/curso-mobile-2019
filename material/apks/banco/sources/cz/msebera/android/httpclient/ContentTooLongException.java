package cz.msebera.android.httpclient;

import java.io.IOException;

public class ContentTooLongException extends IOException {
    private static final long serialVersionUID = -924287689552495383L;

    public ContentTooLongException(String str) {
        super(str);
    }
}
