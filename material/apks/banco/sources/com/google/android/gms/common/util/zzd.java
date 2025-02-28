package com.google.android.gms.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.internal.zzsi;

public class zzd {
    public static int zza(PackageInfo packageInfo) {
        if (packageInfo == null || packageInfo.applicationInfo == null) {
            return -1;
        }
        Bundle bundle = packageInfo.applicationInfo.metaData;
        if (bundle == null) {
            return -1;
        }
        return bundle.getInt("com.google.android.gms.version", -1);
    }

    public static boolean zzact() {
        return false;
    }

    public static int zzv(Context context, String str) {
        return zza(zzw(context, str));
    }

    @Nullable
    public static PackageInfo zzw(Context context, String str) {
        try {
            return zzsi.zzcr(context).getPackageInfo(str, 128);
        } catch (NameNotFoundException unused) {
            return null;
        }
    }

    @TargetApi(12)
    public static boolean zzx(Context context, String str) {
        boolean z = false;
        if (!zzs.zzaxl()) {
            return false;
        }
        if ("com.google.android.gms".equals(str) && zzact()) {
            return false;
        }
        try {
            if ((zzsi.zzcr(context).getApplicationInfo(str, 0).flags & 2097152) != 0) {
                z = true;
            }
        } catch (NameNotFoundException unused) {
        }
        return z;
    }
}
