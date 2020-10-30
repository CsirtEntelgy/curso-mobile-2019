package org.bouncycastle.crypto.tls;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

public class TlsStreamCipher implements TlsCipher {
    protected TlsContext context;
    protected StreamCipher decryptCipher;
    protected StreamCipher encryptCipher;
    protected TlsMac readMac;
    protected boolean usesNonce;
    protected TlsMac writeMac;

    public TlsStreamCipher(TlsContext tlsContext, StreamCipher streamCipher, StreamCipher streamCipher2, Digest digest, Digest digest2, int i) {
        this(tlsContext, streamCipher, streamCipher2, digest, digest2, i, false);
    }

    public TlsStreamCipher(TlsContext tlsContext, StreamCipher streamCipher, StreamCipher streamCipher2, Digest digest, Digest digest2, int i, boolean z) {
        TlsContext tlsContext2 = tlsContext;
        StreamCipher streamCipher3 = streamCipher;
        StreamCipher streamCipher4 = streamCipher2;
        int i2 = i;
        boolean z2 = z;
        boolean isServer = tlsContext.isServer();
        this.context = tlsContext2;
        this.usesNonce = z2;
        this.encryptCipher = streamCipher3;
        this.decryptCipher = streamCipher4;
        int digestSize = (i2 * 2) + digest.getDigestSize() + digest2.getDigestSize();
        byte[] a = TlsUtils.a(tlsContext2, digestSize);
        TlsContext tlsContext3 = tlsContext2;
        byte[] bArr = a;
        TlsMac tlsMac = new TlsMac(tlsContext3, digest, bArr, 0, digest.getDigestSize());
        int digestSize2 = digest.getDigestSize() + 0;
        TlsMac tlsMac2 = r1;
        TlsMac tlsMac3 = new TlsMac(tlsContext3, digest2, bArr, digestSize2, digest2.getDigestSize());
        int digestSize3 = digestSize2 + digest2.getDigestSize();
        CipherParameters keyParameter = new KeyParameter(a, digestSize3, i2);
        int i3 = digestSize3 + i2;
        CipherParameters keyParameter2 = new KeyParameter(a, i3, i2);
        if (i3 + i2 != digestSize) {
            throw new TlsFatalAlert(80);
        }
        if (isServer) {
            this.writeMac = tlsMac2;
            this.readMac = tlsMac;
            this.encryptCipher = streamCipher4;
            this.decryptCipher = streamCipher3;
            CipherParameters cipherParameters = keyParameter2;
            keyParameter2 = keyParameter;
            keyParameter = cipherParameters;
        } else {
            this.writeMac = tlsMac;
            this.readMac = tlsMac2;
            this.encryptCipher = streamCipher3;
            this.decryptCipher = streamCipher4;
        }
        if (z2) {
            byte[] bArr2 = new byte[8];
            ParametersWithIV parametersWithIV = new ParametersWithIV(keyParameter, bArr2);
            keyParameter2 = new ParametersWithIV(keyParameter2, bArr2);
            keyParameter = parametersWithIV;
        }
        this.encryptCipher.init(true, keyParameter);
        this.decryptCipher.init(false, keyParameter2);
    }

    private void a(long j, short s, byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        if (!Arrays.constantTimeAreEqual(Arrays.copyOfRange(bArr, i, i2), this.readMac.calculateMac(j, s, bArr2, i3, i4))) {
            throw new TlsFatalAlert(20);
        }
    }

    private void a(StreamCipher streamCipher, boolean z, long j) {
        byte[] bArr = new byte[8];
        TlsUtils.writeUint64(j, bArr, 0);
        streamCipher.init(z, new ParametersWithIV(null, bArr));
    }

    public byte[] decodeCiphertext(long j, short s, byte[] bArr, int i, int i2) {
        long j2;
        int i3 = i2;
        if (this.usesNonce) {
            j2 = j;
            a(this.decryptCipher, false, j2);
        } else {
            j2 = j;
        }
        int size = this.readMac.getSize();
        if (i3 < size) {
            throw new TlsFatalAlert(50);
        }
        int i4 = i3 - size;
        byte[] bArr2 = new byte[i3];
        byte[] bArr3 = bArr2;
        this.decryptCipher.processBytes(bArr, i, i3, bArr3, 0);
        a(j2, s, bArr3, i4, i3, bArr2, 0, i4);
        return Arrays.copyOfRange(bArr2, 0, i4);
    }

    public byte[] encodePlaintext(long j, short s, byte[] bArr, int i, int i2) {
        long j2;
        if (this.usesNonce) {
            j2 = j;
            a(this.encryptCipher, true, j2);
        } else {
            j2 = j;
        }
        byte[] bArr2 = new byte[(i2 + this.writeMac.getSize())];
        byte[] bArr3 = bArr;
        int i3 = i;
        int i4 = i2;
        this.encryptCipher.processBytes(bArr3, i3, i4, bArr2, 0);
        byte[] calculateMac = this.writeMac.calculateMac(j2, s, bArr3, i3, i4);
        this.encryptCipher.processBytes(calculateMac, 0, calculateMac.length, bArr2, i2);
        return bArr2;
    }

    public int getPlaintextLimit(int i) {
        return i - this.writeMac.getSize();
    }
}
