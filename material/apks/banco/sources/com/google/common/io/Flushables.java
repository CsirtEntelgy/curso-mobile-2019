package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import java.io.Flushable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtIncompatible
@Beta
public final class Flushables {
    private static final Logger a = Logger.getLogger(Flushables.class.getName());

    private Flushables() {
    }

    public static void flush(Flushable flushable, boolean z) {
        try {
            flushable.flush();
        } catch (IOException e) {
            if (z) {
                a.log(Level.WARNING, "IOException thrown while flushing Flushable.", e);
                return;
            }
            throw e;
        }
    }

    public static void flushQuietly(Flushable flushable) {
        try {
            flush(flushable, true);
        } catch (IOException e) {
            a.log(Level.SEVERE, "IOException should not have been thrown.", e);
        }
    }
}
