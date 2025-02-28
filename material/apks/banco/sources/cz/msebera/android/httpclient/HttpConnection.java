package cz.msebera.android.httpclient;

import java.io.Closeable;

public interface HttpConnection extends Closeable {
    void close();

    HttpConnectionMetrics getMetrics();

    int getSocketTimeout();

    boolean isOpen();

    boolean isStale();

    void setSocketTimeout(int i);

    void shutdown();
}
