package org.bouncycastle.asn1.misc;

import com.google.common.primitives.UnsignedBytes;
import org.bouncycastle.asn1.DERBitString;

public class NetscapeCertType extends DERBitString {
    public static final int objectSigning = 16;
    public static final int objectSigningCA = 1;
    public static final int reserved = 8;
    public static final int smime = 32;
    public static final int smimeCA = 2;
    public static final int sslCA = 4;
    public static final int sslClient = 128;
    public static final int sslServer = 64;

    public NetscapeCertType(int i) {
        super(getBytes(i), getPadBits(i));
    }

    public NetscapeCertType(DERBitString dERBitString) {
        super(dERBitString.getBytes(), dERBitString.getPadBits());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NetscapeCertType: 0x");
        sb.append(Integer.toHexString(this.data[0] & UnsignedBytes.MAX_VALUE));
        return sb.toString();
    }
}
