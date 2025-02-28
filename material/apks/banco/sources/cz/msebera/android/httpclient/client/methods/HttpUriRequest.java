package cz.msebera.android.httpclient.client.methods;

import cz.msebera.android.httpclient.HttpRequest;
import java.net.URI;

public interface HttpUriRequest extends HttpRequest {
    void abort();

    String getMethod();

    URI getURI();

    boolean isAborted();
}
