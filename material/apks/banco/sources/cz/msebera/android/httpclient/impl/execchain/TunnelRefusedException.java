package cz.msebera.android.httpclient.impl.execchain;

import cz.msebera.android.httpclient.HttpException;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.annotation.Immutable;

@Immutable
public class TunnelRefusedException extends HttpException {
    private static final long serialVersionUID = -8646722842745617323L;
    private final HttpResponse a;

    public TunnelRefusedException(String str, HttpResponse httpResponse) {
        super(str);
        this.a = httpResponse;
    }

    public HttpResponse getResponse() {
        return this.a;
    }
}
