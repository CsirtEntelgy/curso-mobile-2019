package cz.msebera.android.httpclient.ssl;

public class SSLInitializationException extends IllegalStateException {
    private static final long serialVersionUID = -8243587425648536702L;

    public SSLInitializationException(String str, Throwable th) {
        super(str, th);
    }
}
