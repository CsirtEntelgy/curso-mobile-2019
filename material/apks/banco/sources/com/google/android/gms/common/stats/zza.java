package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.C0004zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza implements Creator<ConnectionEvent> {
    static void a(ConnectionEvent connectionEvent, Parcel parcel, int i) {
        int zzcr = zzb.zzcr(parcel);
        zzb.zzc(parcel, 1, connectionEvent.a);
        zzb.zza(parcel, 2, connectionEvent.getTimeMillis());
        zzb.zza(parcel, 4, connectionEvent.zzawk(), false);
        zzb.zza(parcel, 5, connectionEvent.zzawl(), false);
        zzb.zza(parcel, 6, connectionEvent.zzawm(), false);
        zzb.zza(parcel, 7, connectionEvent.zzawn(), false);
        zzb.zza(parcel, 8, connectionEvent.zzawo(), false);
        zzb.zza(parcel, 10, connectionEvent.zzaws());
        zzb.zza(parcel, 11, connectionEvent.zzawr());
        zzb.zzc(parcel, 12, connectionEvent.getEventType());
        zzb.zza(parcel, 13, connectionEvent.zzawp(), false);
        zzb.zzaj(parcel, zzcr);
    }

    /* renamed from: zzdb */
    public ConnectionEvent createFromParcel(Parcel parcel) {
        Parcel parcel2 = parcel;
        int zzcq = com.google.android.gms.common.internal.safeparcel.zza.zzcq(parcel);
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        String str6 = null;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzcq) {
            int zzcp = com.google.android.gms.common.internal.safeparcel.zza.zzcp(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzgv(zzcp)) {
                case 1:
                    i = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel2, zzcp);
                    break;
                case 2:
                    j = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel2, zzcp);
                    break;
                case 4:
                    str = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 5:
                    str2 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 6:
                    str3 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 7:
                    str4 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 8:
                    str5 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
                    break;
                case 10:
                    j2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel2, zzcp);
                    break;
                case 11:
                    j3 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel2, zzcp);
                    break;
                case 12:
                    i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel2, zzcp);
                    break;
                case 13:
                    str6 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel2, zzcp);
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
        ConnectionEvent connectionEvent = new ConnectionEvent(i, j, i2, str, str2, str3, str4, str5, str6, j2, j3);
        return connectionEvent;
    }

    /* renamed from: zzhg */
    public ConnectionEvent[] newArray(int i) {
        return new ConnectionEvent[i];
    }
}
