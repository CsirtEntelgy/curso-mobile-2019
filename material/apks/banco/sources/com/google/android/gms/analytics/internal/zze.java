package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.zzc;

public class zze {
    public static final String VERSION = String.valueOf(zzc.GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000).replaceAll("(\\d+)(\\d)(\\d\\d)", "$1.$2.$3");
    public static final String aK;

    static {
        String str = "ma";
        String valueOf = String.valueOf(VERSION);
        aK = valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
    }
}
