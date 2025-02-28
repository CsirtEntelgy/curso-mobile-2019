package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;

@GwtIncompatible
@CanIgnoreReturnValue
public abstract class ForwardingListenableFuture<V> extends ForwardingFuture<V> implements ListenableFuture<V> {

    public static abstract class SimpleForwardingListenableFuture<V> extends ForwardingListenableFuture<V> {
        private final ListenableFuture<V> a;

        protected SimpleForwardingListenableFuture(ListenableFuture<V> listenableFuture) {
            this.a = (ListenableFuture) Preconditions.checkNotNull(listenableFuture);
        }

        /* access modifiers changed from: protected */
        public final ListenableFuture<V> delegate() {
            return this.a;
        }
    }

    /* access modifiers changed from: protected */
    public abstract ListenableFuture<? extends V> delegate();

    protected ForwardingListenableFuture() {
    }

    public void addListener(Runnable runnable, Executor executor) {
        delegate().addListener(runnable, executor);
    }
}
