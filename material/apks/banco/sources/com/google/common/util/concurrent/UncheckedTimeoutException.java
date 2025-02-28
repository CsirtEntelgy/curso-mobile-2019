package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import javax.annotation.Nullable;

@GwtIncompatible
public class UncheckedTimeoutException extends RuntimeException {
    private static final long serialVersionUID = 0;

    public UncheckedTimeoutException() {
    }

    public UncheckedTimeoutException(@Nullable String str) {
        super(str);
    }

    public UncheckedTimeoutException(@Nullable Throwable th) {
        super(th);
    }

    public UncheckedTimeoutException(@Nullable String str, @Nullable Throwable th) {
        super(str, th);
    }
}
