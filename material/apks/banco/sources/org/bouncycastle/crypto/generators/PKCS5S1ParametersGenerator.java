package org.bouncycastle.crypto.generators;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class PKCS5S1ParametersGenerator extends PBEParametersGenerator {
    private Digest a;

    public PKCS5S1ParametersGenerator(Digest digest) {
        this.a = digest;
    }

    private byte[] a() {
        byte[] bArr = new byte[this.a.getDigestSize()];
        this.a.update(this.password, 0, this.password.length);
        this.a.update(this.salt, 0, this.salt.length);
        this.a.doFinal(bArr, 0);
        for (int i = 1; i < this.iterationCount; i++) {
            this.a.update(bArr, 0, bArr.length);
            this.a.doFinal(bArr, 0);
        }
        return bArr;
    }

    public CipherParameters generateDerivedMacParameters(int i) {
        return generateDerivedParameters(i);
    }

    public CipherParameters generateDerivedParameters(int i) {
        int i2 = i / 8;
        if (i2 <= this.a.getDigestSize()) {
            return new KeyParameter(a(), 0, i2);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Can't generate a derived key ");
        sb.append(i2);
        sb.append(" bytes long.");
        throw new IllegalArgumentException(sb.toString());
    }

    public CipherParameters generateDerivedParameters(int i, int i2) {
        int i3 = i / 8;
        int i4 = i2 / 8;
        int i5 = i3 + i4;
        if (i5 > this.a.getDigestSize()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Can't generate a derived key ");
            sb.append(i5);
            sb.append(" bytes long.");
            throw new IllegalArgumentException(sb.toString());
        }
        byte[] a2 = a();
        return new ParametersWithIV(new KeyParameter(a2, 0, i3), a2, i3, i4);
    }
}
