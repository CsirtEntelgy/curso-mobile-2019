package cz.msebera.android.httpclient.impl.conn.tsccm;

import java.util.concurrent.TimeUnit;

@Deprecated
public interface PoolEntryRequest {
    void abortRequest();

    BasicPoolEntry getPoolEntry(long j, TimeUnit timeUnit);
}
