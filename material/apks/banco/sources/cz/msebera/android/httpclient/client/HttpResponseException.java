package cz.msebera.android.httpclient.client;

import cz.msebera.android.httpclient.annotation.Immutable;

@Immutable
public class HttpResponseException extends ClientProtocolException {
    private static final long serialVersionUID = -7186627969477257933L;
    private final int a;

    public HttpResponseException(int i, String str) {
        super(str);
        this.a = i;
    }

    public int getStatusCode() {
        return this.a;
    }
}
