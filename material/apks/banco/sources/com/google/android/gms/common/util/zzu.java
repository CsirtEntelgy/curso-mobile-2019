package com.google.android.gms.common.util;

public final class zzu {
    private static int a(StackTraceElement[] stackTraceElementArr, StackTraceElement[] stackTraceElementArr2) {
        int length = stackTraceElementArr2.length;
        int length2 = stackTraceElementArr.length;
        int i = 0;
        while (true) {
            length2--;
            if (length2 < 0) {
                break;
            }
            length--;
            if (length < 0 || !stackTraceElementArr2[length].equals(stackTraceElementArr[length2])) {
                break;
            }
            i++;
        }
        return i;
    }

    public static String zzaxz() {
        StringBuilder sb = new StringBuilder();
        Throwable th = new Throwable();
        StackTraceElement[] stackTrace = th.getStackTrace();
        sb.append("Async stack trace:");
        for (StackTraceElement stackTraceElement : stackTrace) {
            sb.append("\n\tat ");
            sb.append(stackTraceElement);
        }
        Throwable cause = th.getCause();
        while (cause != null) {
            sb.append("\nCaused by: ");
            sb.append(cause);
            StackTraceElement[] stackTrace2 = cause.getStackTrace();
            int a = a(stackTrace2, stackTrace);
            for (int i = 0; i < stackTrace2.length - a; i++) {
                String valueOf = String.valueOf(stackTrace2[i]);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 5);
                sb2.append("\n\tat ");
                sb2.append(valueOf);
                sb.append(sb2.toString());
            }
            if (a > 0) {
                StringBuilder sb3 = new StringBuilder(22);
                sb3.append("\n\t... ");
                sb3.append(a);
                sb3.append(" more");
                sb.append(sb3.toString());
            }
            cause = cause.getCause();
            stackTrace = stackTrace2;
        }
        return sb.toString();
    }
}
