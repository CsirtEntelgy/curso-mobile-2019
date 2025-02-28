package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza implements Creator<PlayLoggerContext> {
    static void a(PlayLoggerContext playLoggerContext, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, playLoggerContext.versionCode);
        zzb.zza(parcel, 2, playLoggerContext.packageName, false);
        zzb.zzc(parcel, 3, playLoggerContext.axu);
        zzb.zzc(parcel, 4, playLoggerContext.axv);
        zzb.zza(parcel, 5, playLoggerContext.axw, false);
        zzb.zza(parcel, 6, playLoggerContext.axx, false);
        zzb.zza(parcel, 7, playLoggerContext.axy);
        zzb.zza(parcel, 8, playLoggerContext.axz, false);
        zzb.zza(parcel, 9, playLoggerContext.axA);
        zzb.zzc(parcel, 10, playLoggerContext.axB);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzrs */
    public PlayLoggerContext createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = com.google.android.gms.common.internal.safeparcel.zza.zzcq(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        boolean z = true;
        boolean z2 = false;
        int i4 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = com.google.android.gms.common.internal.safeparcel.zza.zzcp(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzgv(zzcp)) {
                case 1:
                    i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel2, zzcp);
                    break;
                case 2:
                    str = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 3:
                    i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel2, zzcp);
                    break;
                case 4:
                    i3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel2, zzcp);
                    break;
                case 5:
                    str2 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 6:
                    str3 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 7:
                    z = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel2, zzcp);
                    break;
                case 8:
                    str4 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 9:
                    z2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel2, zzcp);
                    break;
                case 10:
                    i4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel2, zzcp);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel2, zzcp);
                    break;
            }
        }
        if (parcel.dataPosition() != zzcq) {
            StringBuilder sb = new StringBuilder(37);
            sb.append("Overread allowed size end=");
            sb.append(zzcq);
            throw new C0004zza(sb.toString(), parcel2);
        }
        PlayLoggerContext playLoggerContext = new PlayLoggerContext(i, str, i2, i3, str2, str3, z, str4, z2, i4);
        return playLoggerContext;
    }

    /* renamed from: zzzk */
    public PlayLoggerContext[] newArray(int i) {
        return new PlayLoggerContext[i];
    }
}
