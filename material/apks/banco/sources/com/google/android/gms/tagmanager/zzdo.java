package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaf;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzai.zza;
import java.util.Map;

class zzdo extends zzam {
    private static final String a = zzaf.UPPERCASE_STRING.toString();
    private static final String b = zzag.ARG0.toString();

    public zzdo() {
        super(a, b);
    }

    public zza zzaw(Map<String, zza> map) {
        return zzdm.zzat(zzdm.zzg((zza) map.get(b)).toUpperCase());
    }

    public boolean zzcds() {
        return true;
    }
}
