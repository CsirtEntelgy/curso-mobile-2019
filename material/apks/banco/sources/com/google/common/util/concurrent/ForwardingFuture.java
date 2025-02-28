package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingObject;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
@CanIgnoreReturnValue
public abstract class ForwardingFuture<V> extends ForwardingObject implements Future<V> {

    public static abstract class SimpleForwardingFuture<V> extends ForwardingFuture<V> {
        private final Future<V> a;

        protected SimpleForwardingFuture(Future<V> future) {
            this.a = (Future) Preconditions.checkNotNull(future);
        }

        /* access modifiers changed from: protected */
        public final Future<V> delegate() {
            return this.a;
        }
    }

    /* access modifiers changed from: protected */
    public abstract Future<? extends V> delegate();

    protected ForwardingFuture() {
    }

    public boolean cancel(boolean z) {
        return delegate().cancel(z);
    }

    public boolean isCancelled() {
        return delegate().isCancelled();
    }

    public boolean isDone() {
        return delegate().isDone();
    }

    public V get() {
        return delegate().get();
    }

    public V get(long j, TimeUnit timeUnit) {
        return delegate().get(j, timeUnit);
    }
}
