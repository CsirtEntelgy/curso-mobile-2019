package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ForwardingObject;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
@CanIgnoreReturnValue
public abstract class ForwardingExecutorService extends ForwardingObject implements ExecutorService {
    /* access modifiers changed from: protected */
    public abstract ExecutorService delegate();

    protected ForwardingExecutorService() {
    }

    public boolean awaitTermination(long j, TimeUnit timeUnit) {
        return delegate().awaitTermination(j, timeUnit);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection) {
        return delegate().invokeAll(collection);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) {
        return delegate().invokeAll(collection, j, timeUnit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> collection) {
        return delegate().invokeAny(collection);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit) {
        return delegate().invokeAny(collection, j, timeUnit);
    }

    public boolean isShutdown() {
        return delegate().isShutdown();
    }

    public boolean isTerminated() {
        return delegate().isTerminated();
    }

    public void shutdown() {
        delegate().shutdown();
    }

    public List<Runnable> shutdownNow() {
        return delegate().shutdownNow();
    }

    public void execute(Runnable runnable) {
        delegate().execute(runnable);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return delegate().submit(callable);
    }

    public Future<?> submit(Runnable runnable) {
        return delegate().submit(runnable);
    }

    public <T> Future<T> submit(Runnable runnable, T t) {
        return delegate().submit(runnable, t);
    }
}
