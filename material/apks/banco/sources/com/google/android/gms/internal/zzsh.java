package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import com.google.android.gms.common.util.zzs;

public class zzsh {
    protected final Context mContext;

    public zzsh(Context context) {
        this.mContext = context;
    }

    public int checkPermission(String str, String str2) {
        return this.mContext.getPackageManager().checkPermission(str, str2);
    }

    public ApplicationInfo getApplicationInfo(String str, int i) {
        return this.mContext.getPackageManager().getApplicationInfo(str, i);
    }

    public PackageInfo getPackageInfo(String str, int i) {
        return this.mContext.getPackageManager().getPackageInfo(str, i);
    }

    @TargetApi(19)
    public boolean zzg(int i, String str) {
        if (zzs.zzaxr()) {
            try {
                ((AppOpsManager) this.mContext.getSystemService("appops")).checkPackage(i, str);
                return true;
            } catch (SecurityException unused) {
                return false;
            }
        } else {
            String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
            if (!(str == null || packagesForUid == null)) {
                for (String equals : packagesForUid) {
                    if (str.equals(equals)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public CharSequence zzik(String str) {
        return this.mContext.getPackageManager().getApplicationLabel(this.mContext.getPackageManager().getApplicationInfo(str, 0));
    }
}
