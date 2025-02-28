package com.google.common.math;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@GwtCompatible(emulated = true)
public final class BigIntegerMath {
    @VisibleForTesting
    static final BigInteger a = new BigInteger("16a09e667f3bcc908b2fb1366ea957d3e3adec17512775099da2f590b0667322a", 16);
    private static final double b = Math.log(10.0d);
    private static final double c = Math.log(2.0d);

    /* renamed from: com.google.common.math.BigIntegerMath$1 reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[RoundingMode.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|(3:15|16|18)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|18) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0040 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x004b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0056 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                java.math.RoundingMode[] r0 = java.math.RoundingMode.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                a = r0
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0014 }
                java.math.RoundingMode r1 = java.math.RoundingMode.UNNECESSARY     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x001f }
                java.math.RoundingMode r1 = java.math.RoundingMode.DOWN     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x002a }
                java.math.RoundingMode r1 = java.math.RoundingMode.FLOOR     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0035 }
                java.math.RoundingMode r1 = java.math.RoundingMode.UP     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0040 }
                java.math.RoundingMode r1 = java.math.RoundingMode.CEILING     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x004b }
                java.math.RoundingMode r1 = java.math.RoundingMode.HALF_DOWN     // Catch:{ NoSuchFieldError -> 0x004b }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x004b }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x004b }
            L_0x004b:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0056 }
                java.math.RoundingMode r1 = java.math.RoundingMode.HALF_UP     // Catch:{ NoSuchFieldError -> 0x0056 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0056 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0056 }
            L_0x0056:
                int[] r0 = a     // Catch:{ NoSuchFieldError -> 0x0062 }
                java.math.RoundingMode r1 = java.math.RoundingMode.HALF_EVEN     // Catch:{ NoSuchFieldError -> 0x0062 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0062 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0062 }
            L_0x0062:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.math.BigIntegerMath.AnonymousClass1.<clinit>():void");
        }
    }

    @Beta
    public static BigInteger ceilingPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(log2(bigInteger, RoundingMode.CEILING));
    }

    @Beta
    public static BigInteger floorPowerOfTwo(BigInteger bigInteger) {
        return BigInteger.ZERO.setBit(log2(bigInteger, RoundingMode.FLOOR));
    }

    public static boolean isPowerOfTwo(BigInteger bigInteger) {
        Preconditions.checkNotNull(bigInteger);
        return bigInteger.signum() > 0 && bigInteger.getLowestSetBit() == bigInteger.bitLength() - 1;
    }

    public static int log2(BigInteger bigInteger, RoundingMode roundingMode) {
        MathPreconditions.a("x", (BigInteger) Preconditions.checkNotNull(bigInteger));
        int bitLength = bigInteger.bitLength() - 1;
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.a(isPowerOfTwo(bigInteger));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                if (!isPowerOfTwo(bigInteger)) {
                    bitLength++;
                }
                return bitLength;
            case 6:
            case 7:
            case 8:
                if (bitLength < 256) {
                    return bigInteger.compareTo(a.shiftRight(256 - bitLength)) <= 0 ? bitLength : bitLength + 1;
                }
                if (bigInteger.pow(2).bitLength() - 1 >= (bitLength * 2) + 1) {
                    bitLength++;
                }
                return bitLength;
            default:
                throw new AssertionError();
        }
        return bitLength;
    }

    @GwtIncompatible
    public static int log10(BigInteger bigInteger, RoundingMode roundingMode) {
        int i;
        MathPreconditions.a("x", bigInteger);
        if (a(bigInteger)) {
            return LongMath.log10(bigInteger.longValue(), roundingMode);
        }
        int log2 = (int) ((((double) log2(bigInteger, RoundingMode.FLOOR)) * c) / b);
        BigInteger pow = BigInteger.TEN.pow(log2);
        int compareTo = pow.compareTo(bigInteger);
        if (compareTo > 0) {
            do {
                log2--;
                pow = pow.divide(BigInteger.TEN);
                i = pow.compareTo(bigInteger);
            } while (i > 0);
        } else {
            BigInteger multiply = BigInteger.TEN.multiply(pow);
            int compareTo2 = multiply.compareTo(bigInteger);
            BigInteger bigInteger2 = multiply;
            int i2 = compareTo;
            int i3 = compareTo2;
            BigInteger bigInteger3 = bigInteger2;
            while (i3 <= 0) {
                log2++;
                BigInteger multiply2 = BigInteger.TEN.multiply(bigInteger3);
                int compareTo3 = multiply2.compareTo(bigInteger);
                BigInteger bigInteger4 = bigInteger3;
                bigInteger3 = multiply2;
                pow = bigInteger4;
                int i4 = compareTo3;
                i2 = i3;
                i3 = i4;
            }
            i = i2;
        }
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.a(i == 0);
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                if (!pow.equals(bigInteger)) {
                    log2++;
                }
                return log2;
            case 6:
            case 7:
            case 8:
                if (bigInteger.pow(2).compareTo(pow.pow(2).multiply(BigInteger.TEN)) > 0) {
                    log2++;
                }
                return log2;
            default:
                throw new AssertionError();
        }
        return log2;
    }

    @GwtIncompatible
    public static BigInteger sqrt(BigInteger bigInteger, RoundingMode roundingMode) {
        MathPreconditions.b("x", bigInteger);
        if (a(bigInteger)) {
            return BigInteger.valueOf(LongMath.sqrt(bigInteger.longValue(), roundingMode));
        }
        BigInteger b2 = b(bigInteger);
        switch (AnonymousClass1.a[roundingMode.ordinal()]) {
            case 1:
                MathPreconditions.a(b2.pow(2).equals(bigInteger));
                break;
            case 2:
            case 3:
                break;
            case 4:
            case 5:
                int intValue = b2.intValue();
                if (!(intValue * intValue == bigInteger.intValue() && b2.pow(2).equals(bigInteger))) {
                    b2 = b2.add(BigInteger.ONE);
                }
                return b2;
            case 6:
            case 7:
            case 8:
                if (b2.pow(2).add(b2).compareTo(bigInteger) < 0) {
                    b2 = b2.add(BigInteger.ONE);
                }
                return b2;
            default:
                throw new AssertionError();
        }
        return b2;
    }

    @GwtIncompatible
    private static BigInteger b(BigInteger bigInteger) {
        BigInteger bigInteger2;
        int log2 = log2(bigInteger, RoundingMode.FLOOR);
        if (log2 < 1023) {
            bigInteger2 = c(bigInteger);
        } else {
            int i = (log2 - 52) & -2;
            bigInteger2 = c(bigInteger.shiftRight(i)).shiftLeft(i >> 1);
        }
        BigInteger shiftRight = bigInteger2.add(bigInteger.divide(bigInteger2)).shiftRight(1);
        if (bigInteger2.equals(shiftRight)) {
            return bigInteger2;
        }
        while (true) {
            BigInteger shiftRight2 = shiftRight.add(bigInteger.divide(shiftRight)).shiftRight(1);
            if (shiftRight2.compareTo(shiftRight) >= 0) {
                return shiftRight;
            }
            shiftRight = shiftRight2;
        }
    }

    @GwtIncompatible
    private static BigInteger c(BigInteger bigInteger) {
        return DoubleMath.roundToBigInteger(Math.sqrt(DoubleUtils.a(bigInteger)), RoundingMode.HALF_EVEN);
    }

    @GwtIncompatible
    public static BigInteger divide(BigInteger bigInteger, BigInteger bigInteger2, RoundingMode roundingMode) {
        return new BigDecimal(bigInteger).divide(new BigDecimal(bigInteger2), 0, roundingMode).toBigIntegerExact();
    }

    public static BigInteger factorial(int i) {
        int i2 = i;
        MathPreconditions.b("n", i2);
        if (i2 < LongMath.d.length) {
            return BigInteger.valueOf(LongMath.d[i2]);
        }
        ArrayList arrayList = new ArrayList(IntMath.divide(IntMath.log2(i2, RoundingMode.CEILING) * i2, 64, RoundingMode.CEILING));
        int length = LongMath.d.length;
        long j = LongMath.d[length - 1];
        int numberOfTrailingZeros = Long.numberOfTrailingZeros(j);
        long j2 = j >> numberOfTrailingZeros;
        int log2 = LongMath.log2(j2, RoundingMode.FLOOR) + 1;
        long j3 = (long) length;
        int log22 = LongMath.log2(j3, RoundingMode.FLOOR) + 1;
        int i3 = 1 << (log22 - 1);
        while (j3 <= ((long) i2)) {
            if ((j3 & ((long) i3)) != 0) {
                i3 <<= 1;
                log22++;
            }
            int numberOfTrailingZeros2 = Long.numberOfTrailingZeros(j3);
            long j4 = j3 >> numberOfTrailingZeros2;
            numberOfTrailingZeros += numberOfTrailingZeros2;
            if ((log22 - numberOfTrailingZeros2) + log2 >= 64) {
                arrayList.add(BigInteger.valueOf(j2));
                j2 = 1;
            }
            j2 *= j4;
            log2 = LongMath.log2(j2, RoundingMode.FLOOR) + 1;
            j3++;
        }
        if (j2 > 1) {
            arrayList.add(BigInteger.valueOf(j2));
        }
        return a((List<BigInteger>) arrayList).shiftLeft(numberOfTrailingZeros);
    }

    static BigInteger a(List<BigInteger> list) {
        return a(list, 0, list.size());
    }

    static BigInteger a(List<BigInteger> list, int i, int i2) {
        switch (i2 - i) {
            case 0:
                return BigInteger.ONE;
            case 1:
                return (BigInteger) list.get(i);
            case 2:
                return ((BigInteger) list.get(i)).multiply((BigInteger) list.get(i + 1));
            case 3:
                return ((BigInteger) list.get(i)).multiply((BigInteger) list.get(i + 1)).multiply((BigInteger) list.get(i + 2));
            default:
                int i3 = (i2 + i) >>> 1;
                return a(list, i, i3).multiply(a(list, i3, i2));
        }
    }

    public static BigInteger binomial(int i, int i2) {
        MathPreconditions.b("n", i);
        MathPreconditions.b("k", i2);
        int i3 = 1;
        Preconditions.checkArgument(i2 <= i, "k (%s) > n (%s)", i2, i);
        if (i2 > (i >> 1)) {
            i2 = i - i2;
        }
        if (i2 < LongMath.e.length && i <= LongMath.e[i2]) {
            return BigInteger.valueOf(LongMath.binomial(i, i2));
        }
        BigInteger bigInteger = BigInteger.ONE;
        long j = (long) i;
        long j2 = 1;
        int log2 = LongMath.log2(j, RoundingMode.CEILING);
        while (true) {
            long j3 = j2;
            long j4 = j;
            BigInteger bigInteger2 = bigInteger;
            int i4 = log2;
            while (i3 < i2) {
                int i5 = i - i3;
                i3++;
                i4 += log2;
                if (i4 >= 63) {
                    bigInteger = bigInteger2.multiply(BigInteger.valueOf(j4)).divide(BigInteger.valueOf(j3));
                    j = (long) i5;
                    j2 = (long) i3;
                } else {
                    j4 *= (long) i5;
                    j3 *= (long) i3;
                }
            }
            return bigInteger2.multiply(BigInteger.valueOf(j4)).divide(BigInteger.valueOf(j3));
        }
    }

    @GwtIncompatible
    static boolean a(BigInteger bigInteger) {
        return bigInteger.bitLength() <= 63;
    }

    private BigIntegerMath() {
    }
}
