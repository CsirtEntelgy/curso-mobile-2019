package org.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

public class GF2mField {
    private int a = 0;
    private int b;

    public GF2mField(int i) {
        if (i >= 32) {
            throw new IllegalArgumentException(" Error: the degree of field is too large ");
        } else if (i < 1) {
            throw new IllegalArgumentException(" Error: the degree of field is non-positive ");
        } else {
            this.a = i;
            this.b = PolynomialRingGF2.getIrreduciblePolynomial(i);
        }
    }

    public GF2mField(int i, int i2) {
        if (i != PolynomialRingGF2.degree(i2)) {
            throw new IllegalArgumentException(" Error: the degree is not correct");
        } else if (!PolynomialRingGF2.isIrreducible(i2)) {
            throw new IllegalArgumentException(" Error: given polynomial is reducible");
        } else {
            this.a = i;
            this.b = i2;
        }
    }

    public GF2mField(GF2mField gF2mField) {
        this.a = gF2mField.a;
        this.b = gF2mField.b;
    }

    public GF2mField(byte[] bArr) {
        if (bArr.length != 4) {
            throw new IllegalArgumentException("byte array is not an encoded finite field");
        }
        this.b = LittleEndianConversions.OS2IP(bArr);
        if (!PolynomialRingGF2.isIrreducible(this.b)) {
            throw new IllegalArgumentException("byte array is not an encoded finite field");
        }
        this.a = PolynomialRingGF2.degree(this.b);
    }

    private static String a(int i) {
        String str = "";
        if (i == 0) {
            return "0";
        }
        if (((byte) (i & 1)) == 1) {
            str = "1";
        }
        int i2 = i >>> 1;
        int i3 = 1;
        while (i2 != 0) {
            if (((byte) (i2 & 1)) == 1) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append("+x^");
                sb.append(i3);
                str = sb.toString();
            }
            i2 >>>= 1;
            i3++;
        }
        return str;
    }

    public int add(int i, int i2) {
        return i ^ i2;
    }

    public String elementToStr(int i) {
        StringBuilder sb;
        String str;
        String str2 = "";
        for (int i2 = 0; i2 < this.a; i2++) {
            if ((((byte) i) & 1) == 0) {
                sb = new StringBuilder();
                str = "0";
            } else {
                sb = new StringBuilder();
                str = "1";
            }
            sb.append(str);
            sb.append(str2);
            str2 = sb.toString();
            i >>>= 1;
        }
        return str2;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof GF2mField)) {
            return false;
        }
        GF2mField gF2mField = (GF2mField) obj;
        if (this.a == gF2mField.a && this.b == gF2mField.b) {
            return true;
        }
        return false;
    }

    public int exp(int i, int i2) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        if (i2 < 0) {
            i = inverse(i);
            i2 = -i2;
        }
        int i3 = i;
        int i4 = 1;
        while (i2 != 0) {
            if ((i2 & 1) == 1) {
                i4 = mult(i4, i3);
            }
            i3 = mult(i3, i3);
            i2 >>>= 1;
        }
        return i4;
    }

    public int getDegree() {
        return this.a;
    }

    public byte[] getEncoded() {
        return LittleEndianConversions.I2OSP(this.b);
    }

    public int getPolynomial() {
        return this.b;
    }

    public int getRandomElement(SecureRandom secureRandom) {
        return RandUtils.a(secureRandom, 1 << this.a);
    }

    public int getRandomNonZeroElement() {
        return getRandomNonZeroElement(new SecureRandom());
    }

    public int getRandomNonZeroElement(SecureRandom secureRandom) {
        int a2 = RandUtils.a(secureRandom, 1 << this.a);
        int i = 0;
        while (a2 == 0 && i < 1048576) {
            a2 = RandUtils.a(secureRandom, 1 << this.a);
            i++;
        }
        if (i == 1048576) {
            return 1;
        }
        return a2;
    }

    public int hashCode() {
        return this.b;
    }

    public int inverse(int i) {
        return exp(i, (1 << this.a) - 2);
    }

    public boolean isElementOfThisField(int i) {
        boolean z = false;
        if (this.a == 31) {
            if (i >= 0) {
                z = true;
            }
            return z;
        }
        if (i >= 0 && i < (1 << this.a)) {
            z = true;
        }
        return z;
    }

    public int mult(int i, int i2) {
        return PolynomialRingGF2.modMultiply(i, i2, this.b);
    }

    public int sqRoot(int i) {
        for (int i2 = 1; i2 < this.a; i2++) {
            i = mult(i, i);
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Finite Field GF(2^");
        sb.append(this.a);
        sb.append(") = ");
        sb.append("GF(2)[X]/<");
        sb.append(a(this.b));
        sb.append("> ");
        return sb.toString();
    }
}
