package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.util.concurrent.ForwardingListenableFuture.SimpleForwardingListenableFuture;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.Exception;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
@Beta
public abstract class AbstractCheckedFuture<V, X extends Exception> extends SimpleForwardingListenableFuture<V> implements CheckedFuture<V, X> {
    /* access modifiers changed from: protected */
    public abstract X mapException(Exception exc);

    protected AbstractCheckedFuture(ListenableFuture<V> listenableFuture) {
        super(listenableFuture);
    }

    @CanIgnoreReturnValue
    public V checkedGet() {
        try {
            return get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw mapException(e);
        } catch (CancellationException e2) {
            throw mapException(e2);
        } catch (ExecutionException e3) {
            throw mapException(e3);
        }
    }

    @CanIgnoreReturnValue
    public V checkedGet(long j, TimeUnit timeUnit) {
        try {
            return get(j, timeUnit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw mapException(e);
        } catch (CancellationException e2) {
            throw mapException(e2);
        } catch (ExecutionException e3) {
            throw mapException(e3);
        }
    }
}
