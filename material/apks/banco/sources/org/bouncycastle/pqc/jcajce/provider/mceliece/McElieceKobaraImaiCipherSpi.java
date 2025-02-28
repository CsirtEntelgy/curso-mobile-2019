package org.bouncycastle.pqc.jcajce.provider.mceliece;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2KeyParameters;
import org.bouncycastle.pqc.crypto.mceliece.McElieceKobaraImaiCipher;
import org.bouncycastle.pqc.jcajce.provider.util.AsymmetricHybridCipher;

public class McElieceKobaraImaiCipherSpi extends AsymmetricHybridCipher implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private Digest a;
    private McElieceKobaraImaiCipher b;
    private ByteArrayOutputStream c;

    public static class McElieceKobaraImai extends McElieceKobaraImaiCipherSpi {
        public McElieceKobaraImai() {
            super(new SHA1Digest(), new McElieceKobaraImaiCipher());
        }
    }

    public static class McElieceKobaraImai224 extends McElieceKobaraImaiCipherSpi {
        public McElieceKobaraImai224() {
            super(new SHA224Digest(), new McElieceKobaraImaiCipher());
        }
    }

    public static class McElieceKobaraImai256 extends McElieceKobaraImaiCipherSpi {
        public McElieceKobaraImai256() {
            super(new SHA256Digest(), new McElieceKobaraImaiCipher());
        }
    }

    public static class McElieceKobaraImai384 extends McElieceKobaraImaiCipherSpi {
        public McElieceKobaraImai384() {
            super(new SHA384Digest(), new McElieceKobaraImaiCipher());
        }
    }

    public static class McElieceKobaraImai512 extends McElieceKobaraImaiCipherSpi {
        public McElieceKobaraImai512() {
            super(new SHA512Digest(), new McElieceKobaraImaiCipher());
        }
    }

    public McElieceKobaraImaiCipherSpi() {
        this.c = new ByteArrayOutputStream();
        this.c = new ByteArrayOutputStream();
    }

    protected McElieceKobaraImaiCipherSpi(Digest digest, McElieceKobaraImaiCipher mcElieceKobaraImaiCipher) {
        this.c = new ByteArrayOutputStream();
        this.a = digest;
        this.b = mcElieceKobaraImaiCipher;
        this.c = new ByteArrayOutputStream();
    }

    private byte[] a() {
        this.c.write(1);
        byte[] byteArray = this.c.toByteArray();
        this.c.reset();
        return byteArray;
    }

    private byte[] a(byte[] bArr) {
        int length = bArr.length - 1;
        while (length >= 0 && bArr[length] == 0) {
            length--;
        }
        if (bArr[length] != 1) {
            throw new BadPaddingException("invalid ciphertext");
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        return bArr2;
    }

    /* access modifiers changed from: protected */
    public int decryptOutputSize(int i) {
        return 0;
    }

    public byte[] doFinal(byte[] bArr, int i, int i2) {
        update(bArr, i, i2);
        if (this.opMode == 1) {
            try {
                return this.b.messageEncrypt(a());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (this.opMode == 2) {
                byte[] byteArray = this.c.toByteArray();
                this.c.reset();
                return a(this.b.messageDecrypt(byteArray));
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public int encryptOutputSize(int i) {
        return 0;
    }

    public int getKeySize(Key key) {
        AsymmetricKeyParameter generatePrivateKeyParameter;
        if (key instanceof PublicKey) {
            generatePrivateKeyParameter = McElieceCCA2KeysToParams.generatePublicKeyParameter((PublicKey) key);
        } else if (key instanceof PrivateKey) {
            generatePrivateKeyParameter = McElieceCCA2KeysToParams.generatePrivateKeyParameter((PrivateKey) key);
        } else {
            throw new InvalidKeyException();
        }
        return this.b.getKeySize((McElieceCCA2KeyParameters) generatePrivateKeyParameter);
    }

    public String getName() {
        return "McElieceKobaraImaiCipher";
    }

    /* access modifiers changed from: protected */
    public void initCipherDecrypt(Key key, AlgorithmParameterSpec algorithmParameterSpec) {
        this.c.reset();
        AsymmetricKeyParameter generatePrivateKeyParameter = McElieceCCA2KeysToParams.generatePrivateKeyParameter((PrivateKey) key);
        this.a.reset();
        this.b.init(false, generatePrivateKeyParameter);
    }

    /* access modifiers changed from: protected */
    public void initCipherEncrypt(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
        this.c.reset();
        ParametersWithRandom parametersWithRandom = new ParametersWithRandom(McElieceCCA2KeysToParams.generatePublicKeyParameter((PublicKey) key), secureRandom);
        this.a.reset();
        this.b.init(true, parametersWithRandom);
    }

    public byte[] messageDecrypt() {
        byte[] byteArray = this.c.toByteArray();
        this.c.reset();
        try {
            return a(this.b.messageDecrypt(byteArray));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] messageEncrypt() {
        try {
            return this.b.messageEncrypt(a());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] update(byte[] bArr, int i, int i2) {
        this.c.write(bArr, i, i2);
        return new byte[0];
    }
}
