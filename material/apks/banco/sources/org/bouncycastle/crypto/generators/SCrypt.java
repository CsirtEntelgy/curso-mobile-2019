package org.bouncycastle.crypto.generators;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.Salsa20Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

public class SCrypt {
    private static void a(byte[] bArr) {
        if (bArr != null) {
            Arrays.fill(bArr, 0);
        }
    }

    private static void a(int[] iArr) {
        if (iArr != null) {
            Arrays.fill(iArr, 0);
        }
    }

    private static void a(int[] iArr, int i, int i2, int i3) {
        int[] iArr2 = iArr;
        int i4 = i;
        int i5 = i2;
        int i6 = i3;
        int i7 = i6 * 32;
        int[] iArr3 = new int[16];
        int[] iArr4 = new int[16];
        int[] iArr5 = new int[i7];
        int[] iArr6 = new int[i7];
        int[][] iArr7 = new int[i5][];
        try {
            System.arraycopy(iArr2, i4, iArr6, 0, i7);
            for (int i8 = 0; i8 < i5; i8++) {
                iArr7[i8] = Arrays.clone(iArr6);
                a(iArr6, iArr3, iArr4, iArr5, i6);
            }
            int i9 = i5 - 1;
            for (int i10 = 0; i10 < i5; i10++) {
                a(iArr6, iArr7[iArr6[i7 - 16] & i9], 0, iArr6);
                a(iArr6, iArr3, iArr4, iArr5, i6);
            }
            System.arraycopy(iArr6, 0, iArr2, i4, i7);
            a(iArr7);
            a(new int[][]{iArr6, iArr3, iArr4, iArr5});
        } catch (Throwable th) {
            Throwable th2 = th;
            a(iArr7);
            a(new int[][]{iArr6, iArr3, iArr4, iArr5});
            throw th2;
        }
    }

    private static void a(int[] iArr, int[] iArr2, int i, int[] iArr3) {
        for (int length = iArr3.length - 1; length >= 0; length--) {
            iArr3[length] = iArr[length] ^ iArr2[i + length];
        }
    }

    private static void a(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int i) {
        System.arraycopy(iArr, iArr.length - 16, iArr2, 0, 16);
        int length = iArr.length >>> 1;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = i * 2; i4 > 0; i4--) {
            a(iArr2, iArr, i2, iArr3);
            Salsa20Engine.salsaCore(8, iArr3, iArr2);
            System.arraycopy(iArr2, 0, iArr4, i3, 16);
            i3 = (length + i2) - i3;
            i2 += 16;
        }
        System.arraycopy(iArr4, 0, iArr, 0, iArr4.length);
    }

    private static void a(int[][] iArr) {
        for (int[] a : iArr) {
            a(a);
        }
    }

    private static byte[] a(byte[] bArr, byte[] bArr2, int i) {
        PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(new SHA256Digest());
        pKCS5S2ParametersGenerator.init(bArr, bArr2, 1);
        return ((KeyParameter) pKCS5S2ParametersGenerator.generateDerivedMacParameters(i * 8)).getKey();
    }

    private static byte[] a(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4) {
        int[] iArr;
        int i5 = i2 * 128;
        byte[] a = a(bArr, bArr2, i3 * i5);
        try {
            int length = a.length >>> 2;
            iArr = new int[length];
            try {
                Pack.littleEndianToInt(a, 0, iArr);
                int i6 = i5 >>> 2;
                for (int i7 = 0; i7 < length; i7 += i6) {
                    a(iArr, i7, i, i2);
                }
                Pack.intToLittleEndian(iArr, a, 0);
                byte[] a2 = a(bArr, a, i4);
                a(a);
                a(iArr);
                return a2;
            } catch (Throwable th) {
                th = th;
                a(a);
                a(iArr);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            iArr = null;
            a(a);
            a(iArr);
            throw th;
        }
    }

    public static byte[] generate(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4) {
        if (bArr == null) {
            throw new IllegalArgumentException("Passphrase P must be provided.");
        } else if (bArr2 == null) {
            throw new IllegalArgumentException("Salt S must be provided.");
        } else if (i <= 1) {
            throw new IllegalArgumentException("Cost parameter N must be > 1.");
        } else if (i2 == 1 && i > 65536) {
            throw new IllegalArgumentException("Cost parameter N must be > 1 and < 65536.");
        } else if (i2 < 1) {
            throw new IllegalArgumentException("Block size r must be >= 1.");
        } else {
            int i5 = SubsamplingScaleImageView.TILE_SIZE_AUTO / ((i2 * 128) * 8);
            if (i3 < 1 || i3 > i5) {
                StringBuilder sb = new StringBuilder();
                sb.append("Parallelisation parameter p must be >= 1 and <= ");
                sb.append(i5);
                sb.append(" (based on block size r of ");
                sb.append(i2);
                sb.append(")");
                throw new IllegalArgumentException(sb.toString());
            } else if (i4 >= 1) {
                return a(bArr, bArr2, i, i2, i3, i4);
            } else {
                throw new IllegalArgumentException("Generated key length dkLen must be >= 1.");
            }
        }
    }
}
