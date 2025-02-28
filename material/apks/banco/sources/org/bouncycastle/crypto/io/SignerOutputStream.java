package org.bouncycastle.crypto.io;

import java.io.OutputStream;
import org.bouncycastle.crypto.Signer;

public class SignerOutputStream extends OutputStream {
    protected Signer signer;

    public SignerOutputStream(Signer signer2) {
        this.signer = signer2;
    }

    public Signer getSigner() {
        return this.signer;
    }

    public void write(int i) {
        this.signer.update((byte) i);
    }

    public void write(byte[] bArr, int i, int i2) {
        this.signer.update(bArr, i, i2);
    }
}
