package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
@CanIgnoreReturnValue
public interface ListeningExecutorService extends ExecutorService {
    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection);

    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> collection, long j, TimeUnit timeUnit);

    ListenableFuture<?> submit(Runnable runnable);

    <T> ListenableFuture<T> submit(Runnable runnable, T t);

    <T> ListenableFuture<T> submit(Callable<T> callable);
}
