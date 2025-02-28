package org.bouncycastle.crypto.tls;

import java.io.InputStream;
import java.io.OutputStream;

public class DigitallySigned {
    protected SignatureAndHashAlgorithm algorithm;
    protected byte[] signature;

    public DigitallySigned(SignatureAndHashAlgorithm signatureAndHashAlgorithm, byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("'signature' cannot be null");
        }
        this.algorithm = signatureAndHashAlgorithm;
        this.signature = bArr;
    }

    public static DigitallySigned parse(TlsContext tlsContext, InputStream inputStream) {
        return new DigitallySigned(TlsUtils.isTLSv12(tlsContext) ? SignatureAndHashAlgorithm.parse(inputStream) : null, TlsUtils.readOpaque16(inputStream));
    }

    public void encode(OutputStream outputStream) {
        if (this.algorithm != null) {
            this.algorithm.encode(outputStream);
        }
        TlsUtils.writeOpaque16(this.signature, outputStream);
    }

    public SignatureAndHashAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    public byte[] getSignature() {
        return this.signature;
    }
}
