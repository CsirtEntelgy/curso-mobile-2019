package com.google.android.gms.internal;

public final class zzarn {
    public static final int[] bqF = new int[0];
    public static final long[] bqG = new long[0];
    public static final float[] bqH = new float[0];
    public static final double[] bqI = new double[0];
    public static final boolean[] bqJ = new boolean[0];
    public static final String[] bqK = new String[0];
    public static final byte[][] bqL = new byte[0][];
    public static final byte[] bqM = new byte[0];

    static int a(int i) {
        return i & 7;
    }

    public static int zzahu(int i) {
        return i >>> 3;
    }

    public static int zzaj(int i, int i2) {
        return (i << 3) | i2;
    }

    public static boolean zzb(zzarc zzarc, int i) {
        return zzarc.zzaha(i);
    }

    public static final int zzc(zzarc zzarc, int i) {
        int position = zzarc.getPosition();
        zzarc.zzaha(i);
        int i2 = 1;
        while (zzarc.cw() == i) {
            zzarc.zzaha(i);
            i2++;
        }
        zzarc.zzahe(position);
        return i2;
    }
}
