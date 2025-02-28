package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.Exception;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
@Beta
public abstract class ForwardingCheckedFuture<V, X extends Exception> extends ForwardingListenableFuture<V> implements CheckedFuture<V, X> {

    @Beta
    public static abstract class SimpleForwardingCheckedFuture<V, X extends Exception> extends ForwardingCheckedFuture<V, X> {
        private final CheckedFuture<V, X> a;

        protected SimpleForwardingCheckedFuture(CheckedFuture<V, X> checkedFuture) {
            this.a = (CheckedFuture) Preconditions.checkNotNull(checkedFuture);
        }

        /* access modifiers changed from: protected */
        public final CheckedFuture<V, X> delegate() {
            return this.a;
        }
    }

    /* access modifiers changed from: protected */
    public abstract CheckedFuture<V, X> delegate();

    @CanIgnoreReturnValue
    public V checkedGet() {
        return delegate().checkedGet();
    }

    @CanIgnoreReturnValue
    public V checkedGet(long j, TimeUnit timeUnit) {
        return delegate().checkedGet(j, timeUnit);
    }
}
