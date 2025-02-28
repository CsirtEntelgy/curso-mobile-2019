package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.math.BigInteger;
import java.util.Comparator;

@GwtCompatible
@Beta
public final class UnsignedLongs {
    public static final long MAX_VALUE = -1;
    private static final long[] a = new long[37];
    private static final int[] b = new int[37];
    private static final int[] c = new int[37];

    enum LexicographicalComparator implements Comparator<long[]> {
        INSTANCE;

        public String toString() {
            return "UnsignedLongs.lexicographicalComparator()";
        }

        /* renamed from: a */
        public int compare(long[] jArr, long[] jArr2) {
            int min = Math.min(jArr.length, jArr2.length);
            for (int i = 0; i < min; i++) {
                if (jArr[i] != jArr2[i]) {
                    return UnsignedLongs.compare(jArr[i], jArr2[i]);
                }
            }
            return jArr.length - jArr2.length;
        }
    }

    private static long a(long j) {
        return j ^ Long.MIN_VALUE;
    }

    private UnsignedLongs() {
    }

    public static int compare(long j, long j2) {
        return Longs.compare(a(j), a(j2));
    }

    public static long min(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        long a2 = a(jArr[0]);
        for (int i = 1; i < jArr.length; i++) {
            long a3 = a(jArr[i]);
            if (a3 < a2) {
                a2 = a3;
            }
        }
        return a(a2);
    }

    public static long max(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        long a2 = a(jArr[0]);
        for (int i = 1; i < jArr.length; i++) {
            long a3 = a(jArr[i]);
            if (a3 > a2) {
                a2 = a3;
            }
        }
        return a(a2);
    }

    public static String join(String str, long... jArr) {
        Preconditions.checkNotNull(str);
        if (jArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(jArr.length * 5);
        sb.append(toString(jArr[0]));
        for (int i = 1; i < jArr.length; i++) {
            sb.append(str);
            sb.append(toString(jArr[i]));
        }
        return sb.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static long divide(long j, long j2) {
        if (j2 < 0) {
            return compare(j, j2) < 0 ? 0 : 1;
        }
        if (j >= 0) {
            return j / j2;
        }
        int i = 1;
        long j3 = ((j >>> 1) / j2) << 1;
        if (compare(j - (j3 * j2), j2) < 0) {
            i = 0;
        }
        return j3 + ((long) i);
    }

    public static long remainder(long j, long j2) {
        if (j2 < 0) {
            return compare(j, j2) < 0 ? j : j - j2;
        }
        if (j >= 0) {
            return j % j2;
        }
        long j3 = j - ((((j >>> 1) / j2) << 1) * j2);
        if (compare(j3, j2) < 0) {
            j2 = 0;
        }
        return j3 - j2;
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String str) {
        return parseUnsignedLong(str, 10);
    }

    @CanIgnoreReturnValue
    public static long decode(String str) {
        ParseRequest a2 = ParseRequest.a(str);
        try {
            return parseUnsignedLong(a2.a, a2.b);
        } catch (NumberFormatException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Error parsing value: ");
            sb.append(str);
            NumberFormatException numberFormatException = new NumberFormatException(sb.toString());
            numberFormatException.initCause(e);
            throw numberFormatException;
        }
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String str, int i) {
        Preconditions.checkNotNull(str);
        if (str.length() == 0) {
            throw new NumberFormatException("empty string");
        } else if (i < 2 || i > 36) {
            StringBuilder sb = new StringBuilder();
            sb.append("illegal radix: ");
            sb.append(i);
            throw new NumberFormatException(sb.toString());
        } else {
            int i2 = c[i] - 1;
            long j = 0;
            int i3 = 0;
            while (i3 < str.length()) {
                int digit = Character.digit(str.charAt(i3), i);
                if (digit == -1) {
                    throw new NumberFormatException(str);
                } else if (i3 <= i2 || !a(j, digit, i)) {
                    i3++;
                    j = (j * ((long) i)) + ((long) digit);
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Too large for unsigned long: ");
                    sb2.append(str);
                    throw new NumberFormatException(sb2.toString());
                }
            }
            return j;
        }
    }

    private static boolean a(long j, int i, int i2) {
        boolean z = true;
        if (j < 0) {
            return true;
        }
        if (j < a[i2]) {
            return false;
        }
        if (j > a[i2]) {
            return true;
        }
        if (i <= b[i2]) {
            z = false;
        }
        return z;
    }

    public static String toString(long j) {
        return toString(j, 10);
    }

    public static String toString(long j, int i) {
        long j2;
        Preconditions.checkArgument(i >= 2 && i <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", i);
        if (j == 0) {
            return "0";
        }
        if (j > 0) {
            return Long.toString(j, i);
        }
        char[] cArr = new char[64];
        int length = cArr.length;
        int i2 = i - 1;
        if ((i & i2) == 0) {
            int numberOfTrailingZeros = Integer.numberOfTrailingZeros(i);
            do {
                length--;
                cArr[length] = Character.forDigit(((int) j) & i2, i);
                j >>>= numberOfTrailingZeros;
            } while (j != 0);
        } else {
            if ((i & 1) == 0) {
                j2 = (j >>> 1) / ((long) (i >>> 1));
            } else {
                j2 = divide(j, (long) i);
            }
            long j3 = (long) i;
            length--;
            cArr[length] = Character.forDigit((int) (j - (j2 * j3)), i);
            while (j2 > 0) {
                length--;
                cArr[length] = Character.forDigit((int) (j2 % j3), i);
                j2 /= j3;
            }
        }
        return new String(cArr, length, cArr.length - length);
    }

    static {
        BigInteger bigInteger = new BigInteger("10000000000000000", 16);
        for (int i = 2; i <= 36; i++) {
            long j = (long) i;
            a[i] = divide(-1, j);
            b[i] = (int) remainder(-1, j);
            c[i] = bigInteger.toString(i).length() - 1;
        }
    }
}
