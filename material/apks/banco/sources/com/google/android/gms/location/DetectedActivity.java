package com.google.android.gms.location;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzab;
import java.util.Comparator;

public class DetectedActivity extends AbstractSafeParcelable {
    public static final DetectedActivityCreator CREATOR = new DetectedActivityCreator();
    public static final int IN_VEHICLE = 0;
    public static final int ON_BICYCLE = 1;
    public static final int ON_FOOT = 2;
    public static final int RUNNING = 8;
    public static final int STILL = 3;
    public static final int TILTING = 5;
    public static final int UNKNOWN = 4;
    public static final int WALKING = 7;
    public static final Comparator<DetectedActivity> agO = new Comparator<DetectedActivity>() {
        /* renamed from: a */
        public int compare(DetectedActivity detectedActivity, DetectedActivity detectedActivity2) {
            int compareTo = Integer.valueOf(detectedActivity2.getConfidence()).compareTo(Integer.valueOf(detectedActivity.getConfidence()));
            return compareTo == 0 ? Integer.valueOf(detectedActivity.getType()).compareTo(Integer.valueOf(detectedActivity2.getType())) : compareTo;
        }
    };
    public static final int[] agP = {9, 10};
    public static final int[] agQ = {0, 1, 2, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14};
    int a;
    int b;
    private final int c;

    public DetectedActivity(int i, int i2) {
        this.c = 1;
        this.a = i;
        this.b = i2;
    }

    public DetectedActivity(int i, int i2, int i3) {
        this.c = i;
        this.a = i2;
        this.b = i3;
    }

    private int a(int i) {
        if (i > 15) {
            return 4;
        }
        return i;
    }

    public static String zztz(int i) {
        switch (i) {
            case 0:
                return "IN_VEHICLE";
            case 1:
                return "ON_BICYCLE";
            case 2:
                return "ON_FOOT";
            case 3:
                return "STILL";
            case 4:
                return "UNKNOWN";
            case 5:
                return "TILTING";
            case 7:
                return "WALKING";
            case 8:
                return "RUNNING";
            default:
                return Integer.toString(i);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DetectedActivity detectedActivity = (DetectedActivity) obj;
        return this.a == detectedActivity.a && this.b == detectedActivity.b;
    }

    public int getConfidence() {
        return this.b;
    }

    public int getType() {
        return a(this.a);
    }

    public int getVersionCode() {
        return this.c;
    }

    public int hashCode() {
        return zzab.hashCode(Integer.valueOf(this.a), Integer.valueOf(this.b));
    }

    public String toString() {
        String valueOf = String.valueOf(zztz(getType()));
        int i = this.b;
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 48);
        sb.append("DetectedActivity [type=");
        sb.append(valueOf);
        sb.append(", confidence=");
        sb.append(i);
        sb.append("]");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        DetectedActivityCreator.a(this, parcel, i);
    }
}
