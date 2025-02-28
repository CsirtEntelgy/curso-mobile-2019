package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ElGamalKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.BigIntegers;

public class ElGamalEngine implements AsymmetricBlockCipher {
    private static final BigInteger e = BigInteger.valueOf(0);
    private static final BigInteger f = BigInteger.valueOf(1);
    private static final BigInteger g = BigInteger.valueOf(2);
    private ElGamalKeyParameters a;
    private SecureRandom b;
    private boolean c;
    private int d;

    public int getInputBlockSize() {
        return this.c ? (this.d - 1) / 8 : ((this.d + 7) / 8) * 2;
    }

    public int getOutputBlockSize() {
        return this.c ? ((this.d + 7) / 8) * 2 : (this.d - 1) / 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.a = (ElGamalKeyParameters) parametersWithRandom.getParameters();
            secureRandom = parametersWithRandom.getRandom();
        } else {
            this.a = (ElGamalKeyParameters) cipherParameters;
            secureRandom = new SecureRandom();
        }
        this.b = secureRandom;
        this.c = z;
        this.d = this.a.getParameters().getP().bitLength();
        if (z) {
            if (!(this.a instanceof ElGamalPublicKeyParameters)) {
                throw new IllegalArgumentException("ElGamalPublicKeyParameters are required for encryption.");
            }
        } else if (!(this.a instanceof ElGamalPrivateKeyParameters)) {
            throw new IllegalArgumentException("ElGamalPrivateKeyParameters are required for decryption.");
        }
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) {
        if (this.a == null) {
            throw new IllegalStateException("ElGamal engine not initialised");
        }
        if (i2 > (this.c ? ((this.d - 1) + 7) / 8 : getInputBlockSize())) {
            throw new DataLengthException("input too large for ElGamal cipher.\n");
        }
        BigInteger p = this.a.getParameters().getP();
        if (this.a instanceof ElGamalPrivateKeyParameters) {
            int i3 = i2 / 2;
            byte[] bArr2 = new byte[i3];
            byte[] bArr3 = new byte[i3];
            System.arraycopy(bArr, i, bArr2, 0, bArr2.length);
            System.arraycopy(bArr, i + bArr2.length, bArr3, 0, bArr3.length);
            return BigIntegers.asUnsignedByteArray(new BigInteger(1, bArr2).modPow(p.subtract(f).subtract(((ElGamalPrivateKeyParameters) this.a).getX()), p).multiply(new BigInteger(1, bArr3)).mod(p));
        }
        if (!(i == 0 && i2 == bArr.length)) {
            byte[] bArr4 = new byte[i2];
            System.arraycopy(bArr, i, bArr4, 0, i2);
            bArr = bArr4;
        }
        BigInteger bigInteger = new BigInteger(1, bArr);
        if (bigInteger.compareTo(p) >= 0) {
            throw new DataLengthException("input too large for ElGamal cipher.\n");
        }
        ElGamalPublicKeyParameters elGamalPublicKeyParameters = (ElGamalPublicKeyParameters) this.a;
        int bitLength = p.bitLength();
        BigInteger bigInteger2 = new BigInteger(bitLength, this.b);
        while (true) {
            if (!bigInteger2.equals(e) && bigInteger2.compareTo(p.subtract(g)) <= 0) {
                break;
            }
            bigInteger2 = new BigInteger(bitLength, this.b);
        }
        BigInteger modPow = this.a.getParameters().getG().modPow(bigInteger2, p);
        BigInteger mod = bigInteger.multiply(elGamalPublicKeyParameters.getY().modPow(bigInteger2, p)).mod(p);
        byte[] byteArray = modPow.toByteArray();
        byte[] byteArray2 = mod.toByteArray();
        byte[] bArr5 = new byte[getOutputBlockSize()];
        if (byteArray.length > bArr5.length / 2) {
            System.arraycopy(byteArray, 1, bArr5, (bArr5.length / 2) - (byteArray.length - 1), byteArray.length - 1);
        } else {
            System.arraycopy(byteArray, 0, bArr5, (bArr5.length / 2) - byteArray.length, byteArray.length);
        }
        if (byteArray2.length > bArr5.length / 2) {
            System.arraycopy(byteArray2, 1, bArr5, bArr5.length - (byteArray2.length - 1), byteArray2.length - 1);
            return bArr5;
        }
        System.arraycopy(byteArray2, 0, bArr5, bArr5.length - byteArray2.length, byteArray2.length);
        return bArr5;
    }
}
