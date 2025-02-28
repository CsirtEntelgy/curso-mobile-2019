package okio;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.UnsupportedEncodingException;

final class Base64 {
    private static final byte[] a = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] b = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};

    private Base64() {
    }

    public static byte[] a(String str) {
        int i;
        int length = str.length();
        while (length > 0) {
            char charAt = str.charAt(length - 1);
            if (charAt != '=' && charAt != 10 && charAt != 13 && charAt != ' ' && charAt != 9) {
                break;
            }
            length--;
        }
        byte[] bArr = new byte[((int) ((((long) length) * 6) / 8))];
        int i2 = 0;
        byte b2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            char charAt2 = str.charAt(i4);
            if (charAt2 >= 'A' && charAt2 <= 'Z') {
                i = charAt2 - 'A';
            } else if (charAt2 >= 'a' && charAt2 <= 'z') {
                i = charAt2 - 'G';
            } else if (charAt2 >= '0' && charAt2 <= '9') {
                i = charAt2 + 4;
            } else if (charAt2 == '+' || charAt2 == '-') {
                i = 62;
            } else if (charAt2 == '/' || charAt2 == '_') {
                i = 63;
            } else {
                if (!(charAt2 == 10 || charAt2 == 13 || charAt2 == ' ' || charAt2 == 9)) {
                    return null;
                }
            }
            b2 = (b2 << 6) | ((byte) i);
            i2++;
            if (i2 % 4 == 0) {
                int i5 = i3 + 1;
                bArr[i3] = (byte) (b2 >> Ascii.DLE);
                int i6 = i5 + 1;
                bArr[i5] = (byte) (b2 >> 8);
                int i7 = i6 + 1;
                bArr[i6] = (byte) b2;
                i3 = i7;
            }
        }
        int i8 = i2 % 4;
        if (i8 == 1) {
            return null;
        }
        if (i8 == 2) {
            int i9 = i3 + 1;
            bArr[i3] = (byte) ((b2 << Ascii.FF) >> 16);
            i3 = i9;
        } else if (i8 == 3) {
            int i10 = b2 << 6;
            int i11 = i3 + 1;
            bArr[i3] = (byte) (i10 >> 16);
            i3 = i11 + 1;
            bArr[i11] = (byte) (i10 >> 8);
        }
        if (i3 == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, 0, bArr2, 0, i3);
        return bArr2;
    }

    public static String a(byte[] bArr) {
        return a(bArr, a);
    }

    public static String b(byte[] bArr) {
        return a(bArr, b);
    }

    private static String a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[(((bArr.length + 2) / 3) * 4)];
        int length = bArr.length - (bArr.length % 3);
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 3) {
            int i3 = i + 1;
            bArr3[i] = bArr2[(bArr[i2] & UnsignedBytes.MAX_VALUE) >> 2];
            int i4 = i3 + 1;
            int i5 = i2 + 1;
            bArr3[i3] = bArr2[((bArr[i2] & 3) << 4) | ((bArr[i5] & UnsignedBytes.MAX_VALUE) >> 4)];
            int i6 = i4 + 1;
            int i7 = i2 + 2;
            bArr3[i4] = bArr2[((bArr[i5] & Ascii.SI) << 2) | ((bArr[i7] & UnsignedBytes.MAX_VALUE) >> 6)];
            i = i6 + 1;
            bArr3[i6] = bArr2[bArr[i7] & 63];
        }
        switch (bArr.length % 3) {
            case 1:
                int i8 = i + 1;
                bArr3[i] = bArr2[(bArr[length] & UnsignedBytes.MAX_VALUE) >> 2];
                int i9 = i8 + 1;
                bArr3[i8] = bArr2[(bArr[length] & 3) << 4];
                int i10 = i9 + 1;
                bArr3[i9] = 61;
                bArr3[i10] = 61;
                break;
            case 2:
                int i11 = i + 1;
                bArr3[i] = bArr2[(bArr[length] & UnsignedBytes.MAX_VALUE) >> 2];
                int i12 = i11 + 1;
                int i13 = length + 1;
                bArr3[i11] = bArr2[((bArr[length] & 3) << 4) | ((bArr[i13] & UnsignedBytes.MAX_VALUE) >> 4)];
                int i14 = i12 + 1;
                bArr3[i12] = bArr2[(bArr[i13] & Ascii.SI) << 2];
                bArr3[i14] = 61;
                break;
        }
        try {
            return new String(bArr3, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
