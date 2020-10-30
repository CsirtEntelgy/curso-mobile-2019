package org.bouncycastle.crypto.paddings;

import com.google.common.primitives.UnsignedBytes;
import java.security.SecureRandom;
import org.bouncycastle.crypto.InvalidCipherTextException;

public class X923Padding implements BlockCipherPadding {
    SecureRandom a = null;

    public int addPadding(byte[] bArr, int i) {
        byte length = (byte) (bArr.length - i);
        while (i < bArr.length - 1) {
            if (this.a == null) {
                bArr[i] = 0;
            } else {
                bArr[i] = (byte) this.a.nextInt();
            }
            i++;
        }
        bArr[i] = length;
        return length;
    }

    public String getPaddingName() {
        return "X9.23";
    }

    public void init(SecureRandom secureRandom) {
        this.a = secureRandom;
    }

    public int padCount(byte[] bArr) {
        byte b = bArr[bArr.length - 1] & UnsignedBytes.MAX_VALUE;
        if (b <= bArr.length) {
            return b;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}
