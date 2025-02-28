package com.google.android.gms.common.stats;

import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.text.TextUtils;
import java.util.List;

public class zzf {
    static String a(String str) {
        if ("com.google.android.gms".equals(str)) {
            return null;
        }
        return str;
    }

    static List<String> a(List<String> list) {
        if (list == null || list.size() != 1 || !"com.google.android.gms".equals(list.get(0))) {
            return list;
        }
        return null;
    }

    public static String zza(WakeLock wakeLock, String str) {
        String valueOf = String.valueOf(String.valueOf((((long) Process.myPid()) << 32) | ((long) System.identityHashCode(wakeLock))));
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        String valueOf2 = String.valueOf(str);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }
}
