package io.fabric.sdk.android;

import android.util.Log;

public class DefaultLogger implements Logger {
    private int a;

    public DefaultLogger(int i) {
        this.a = i;
    }

    public DefaultLogger() {
        this.a = 4;
    }

    public boolean isLoggable(String str, int i) {
        return this.a <= i;
    }

    public int getLogLevel() {
        return this.a;
    }

    public void setLogLevel(int i) {
        this.a = i;
    }

    public void d(String str, String str2, Throwable th) {
        if (isLoggable(str, 3)) {
            Log.d(str, str2, th);
        }
    }

    public void v(String str, String str2, Throwable th) {
        if (isLoggable(str, 2)) {
            Log.v(str, str2, th);
        }
    }

    public void i(String str, String str2, Throwable th) {
        if (isLoggable(str, 4)) {
            Log.i(str, str2, th);
        }
    }

    public void w(String str, String str2, Throwable th) {
        if (isLoggable(str, 5)) {
            Log.w(str, str2, th);
        }
    }

    public void e(String str, String str2, Throwable th) {
        if (isLoggable(str, 6)) {
            Log.e(str, str2, th);
        }
    }

    public void d(String str, String str2) {
        d(str, str2, null);
    }

    public void v(String str, String str2) {
        v(str, str2, null);
    }

    public void i(String str, String str2) {
        i(str, str2, null);
    }

    public void w(String str, String str2) {
        w(str, str2, null);
    }

    public void e(String str, String str2) {
        e(str, str2, null);
    }

    public void log(int i, String str, String str2) {
        log(i, str, str2, false);
    }

    public void log(int i, String str, String str2, boolean z) {
        if (z || isLoggable(str, i)) {
            Log.println(i, str, str2);
        }
    }
}
