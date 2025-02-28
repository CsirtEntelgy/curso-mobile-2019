package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Comparator;

@GwtCompatible
@Beta
public final class UnsignedInts {

    enum LexicographicalComparator implements Comparator<int[]> {
        INSTANCE;

        public String toString() {
            return "UnsignedInts.lexicographicalComparator()";
        }

        /* renamed from: a */
        public int compare(int[] iArr, int[] iArr2) {
            int min = Math.min(iArr.length, iArr2.length);
            for (int i = 0; i < min; i++) {
                if (iArr[i] != iArr2[i]) {
                    return UnsignedInts.compare(iArr[i], iArr2[i]);
                }
            }
            return iArr.length - iArr2.length;
        }
    }

    static int a(int i) {
        return i ^ Integer.MIN_VALUE;
    }

    public static long toLong(int i) {
        return ((long) i) & 4294967295L;
    }

    private UnsignedInts() {
    }

    public static int compare(int i, int i2) {
        return Ints.compare(a(i), a(i2));
    }

    public static int min(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        int a = a(iArr[0]);
        for (int i = 1; i < iArr.length; i++) {
            int a2 = a(iArr[i]);
            if (a2 < a) {
                a = a2;
            }
        }
        return a(a);
    }

    public static int max(int... iArr) {
        Preconditions.checkArgument(iArr.length > 0);
        int a = a(iArr[0]);
        for (int i = 1; i < iArr.length; i++) {
            int a2 = a(iArr[i]);
            if (a2 > a) {
                a = a2;
            }
        }
        return a(a);
    }

    public static String join(String str, int... iArr) {
        Preconditions.checkNotNull(str);
        if (iArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(iArr.length * 5);
        sb.append(toString(iArr[0]));
        for (int i = 1; i < iArr.length; i++) {
            sb.append(str);
            sb.append(toString(iArr[i]));
        }
        return sb.toString();
    }

    public static Comparator<int[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static int divide(int i, int i2) {
        return (int) (toLong(i) / toLong(i2));
    }

    public static int remainder(int i, int i2) {
        return (int) (toLong(i) % toLong(i2));
    }

    @CanIgnoreReturnValue
    public static int decode(String str) {
        ParseRequest a = ParseRequest.a(str);
        try {
            return parseUnsignedInt(a.a, a.b);
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
    public static int parseUnsignedInt(String str) {
        return parseUnsignedInt(str, 10);
    }

    @CanIgnoreReturnValue
    public static int parseUnsignedInt(String str, int i) {
        Preconditions.checkNotNull(str);
        long parseLong = Long.parseLong(str, i);
        if ((parseLong & 4294967295L) == parseLong) {
            return (int) parseLong;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Input ");
        sb.append(str);
        sb.append(" in base ");
        sb.append(i);
        sb.append(" is not in the range of an unsigned integer");
        throw new NumberFormatException(sb.toString());
    }

    public static String toString(int i) {
        return toString(i, 10);
    }

    public static String toString(int i, int i2) {
        return Long.toString(((long) i) & 4294967295L, i2);
    }
}
