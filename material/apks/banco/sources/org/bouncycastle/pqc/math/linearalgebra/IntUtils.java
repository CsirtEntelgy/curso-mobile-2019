package org.bouncycastle.pqc.math.linearalgebra;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.math.BigInteger;

public final class IntUtils {
    private IntUtils() {
    }

    private static int a(int[] iArr, int i, int i2, int i3) {
        int i4 = iArr[i3];
        iArr[i3] = iArr[i2];
        iArr[i2] = i4;
        int i5 = i;
        while (i < i2) {
            if (iArr[i] <= i4) {
                int i6 = iArr[i5];
                iArr[i5] = iArr[i];
                iArr[i] = i6;
                i5++;
            }
            i++;
        }
        int i7 = iArr[i5];
        iArr[i5] = iArr[i2];
        iArr[i2] = i7;
        return i5;
    }

    public static int[] clone(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        return iArr2;
    }

    public static boolean equals(int[] iArr, int[] iArr2) {
        if (iArr.length != iArr2.length) {
            return false;
        }
        boolean z = true;
        for (int length = iArr.length - 1; length >= 0; length--) {
            z &= iArr[length] == iArr2[length];
        }
        return z;
    }

    public static void fill(int[] iArr, int i) {
        for (int length = iArr.length - 1; length >= 0; length--) {
            iArr[length] = i;
        }
    }

    public static void quicksort(int[] iArr) {
        quicksort(iArr, 0, iArr.length - 1);
    }

    public static void quicksort(int[] iArr, int i, int i2) {
        if (i2 > i) {
            int a = a(iArr, i, i2, i2);
            quicksort(iArr, i, a - 1);
            quicksort(iArr, a + 1, i2);
        }
    }

    public static int[] subArray(int[] iArr, int i, int i2) {
        int i3 = i2 - i;
        int[] iArr2 = new int[i3];
        System.arraycopy(iArr, i, iArr2, 0, i3);
        return iArr2;
    }

    public static BigInteger[] toFlexiBigIntArray(int[] iArr) {
        BigInteger[] bigIntegerArr = new BigInteger[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            bigIntegerArr[i] = BigInteger.valueOf((long) iArr[i]);
        }
        return bigIntegerArr;
    }

    public static String toHexString(int[] iArr) {
        return ByteUtils.toHexString(BigEndianConversions.toByteArray(iArr));
    }

    public static String toString(int[] iArr) {
        String str = "";
        for (int append : iArr) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(append);
            sb.append(UtilsCuentas.SEPARAOR2);
            str = sb.toString();
        }
        return str;
    }
}
