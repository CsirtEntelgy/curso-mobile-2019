package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import java.util.concurrent.ScheduledFuture;

@GwtIncompatible
@Beta
public interface ListenableScheduledFuture<V> extends ListenableFuture<V>, ScheduledFuture<V> {
}
