package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;

public class StreetViewPanoramaLink extends AbstractSafeParcelable {
    public static final zzl CREATOR = new zzl();
    private final int a;
    public final float bearing;
    public final String panoId;

    StreetViewPanoramaLink(int i, String str, float f) {
        this.a = i;
        this.panoId = str;
        if (((double) f) <= 0.0d) {
            f = (f % 360.0f) + 360.0f;
        }
        this.bearing = f % 360.0f;
    }

    /* access modifiers changed from: 0000 */
    public int a() {
        return this.a;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StreetViewPanoramaLink)) {
            return false;
        }
        StreetViewPanoramaLink streetViewPanoramaLink = (StreetViewPanoramaLink) obj;
        return this.panoId.equals(streetViewPanoramaLink.panoId) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(streetViewPanoramaLink.bearing);
    }

    public int hashCode() {
        return zzab.hashCode(this.panoId, Float.valueOf(this.bearing));
    }

    public String toString() {
        return zzab.zzx(this).zzg("panoId", this.panoId).zzg("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzl.a(this, parcel, i);
    }
}
