package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtIncompatible
@Beta
public interface LineProcessor<T> {
    T getResult();

    @CanIgnoreReturnValue
    boolean processLine(String str);
}
