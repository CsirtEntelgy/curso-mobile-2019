package com.google.android.gms.common.server;

import android.os.Parcel;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

public class FavaDiagnosticsEntity extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final zza CREATOR = new zza();
    public final String Dl;
    public final int Dm;
    final int a;

    public FavaDiagnosticsEntity(int i, String str, int i2) {
        this.a = i;
        this.Dl = str;
        this.Dm = i2;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.a(this, parcel, i);
    }
}
