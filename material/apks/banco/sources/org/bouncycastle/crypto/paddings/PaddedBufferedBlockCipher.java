package org.bouncycastle.crypto.paddings;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.ParametersWithRandom;

public class PaddedBufferedBlockCipher extends BufferedBlockCipher {
    BlockCipherPadding a;

    public PaddedBufferedBlockCipher(BlockCipher blockCipher) {
        this(blockCipher, new PKCS7Padding());
    }

    public PaddedBufferedBlockCipher(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
        this.cipher = blockCipher;
        this.a = blockCipherPadding;
        this.buf = new byte[blockCipher.getBlockSize()];
        this.bufOff = 0;
    }

    public int doFinal(byte[] bArr, int i) {
        int padCount;
        int i2;
        int blockSize = this.cipher.getBlockSize();
        if (this.forEncryption) {
            if (this.bufOff != blockSize) {
                i2 = 0;
            } else if ((blockSize * 2) + i > bArr.length) {
                reset();
                throw new OutputLengthException("output buffer too short");
            } else {
                i2 = this.cipher.processBlock(this.buf, 0, bArr, i);
                this.bufOff = 0;
            }
            this.a.addPadding(this.buf, this.bufOff);
            padCount = i2 + this.cipher.processBlock(this.buf, 0, bArr, i + i2);
        } else if (this.bufOff == blockSize) {
            int processBlock = this.cipher.processBlock(this.buf, 0, this.buf, 0);
            this.bufOff = 0;
            try {
                padCount = processBlock - this.a.padCount(this.buf);
                System.arraycopy(this.buf, 0, bArr, i, padCount);
            } catch (Throwable th) {
                reset();
                throw th;
            }
        } else {
            reset();
            throw new DataLengthException("last block incomplete in decryption");
        }
        reset();
        return padCount;
    }

    public int getOutputSize(int i) {
        int length;
        int i2 = i + this.bufOff;
        int length2 = i2 % this.buf.length;
        if (length2 != 0) {
            i2 -= length2;
            length = this.buf.length;
        } else if (!this.forEncryption) {
            return i2;
        } else {
            length = this.buf.length;
        }
        return i2 + length;
    }

    public int getUpdateOutputSize(int i) {
        int i2 = i + this.bufOff;
        int length = i2 % this.buf.length;
        return length == 0 ? Math.max(0, i2 - this.buf.length) : i2 - length;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        BlockCipher blockCipher;
        this.forEncryption = z;
        reset();
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.a.init(parametersWithRandom.getRandom());
            blockCipher = this.cipher;
            cipherParameters = parametersWithRandom.getParameters();
        } else {
            this.a.init(null);
            blockCipher = this.cipher;
        }
        blockCipher.init(z, cipherParameters);
    }

    public int processByte(byte b, byte[] bArr, int i) {
        int i2;
        if (this.bufOff == this.buf.length) {
            i2 = this.cipher.processBlock(this.buf, 0, bArr, i);
            this.bufOff = 0;
        } else {
            i2 = 0;
        }
        byte[] bArr2 = this.buf;
        int i3 = this.bufOff;
        this.bufOff = i3 + 1;
        bArr2[i3] = b;
        return i2;
    }

    public int processBytes(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int blockSize = getBlockSize();
        int updateOutputSize = getUpdateOutputSize(i2);
        if (updateOutputSize <= 0 || updateOutputSize + i3 <= bArr2.length) {
            int length = this.buf.length - this.bufOff;
            int i4 = 0;
            if (i2 > length) {
                System.arraycopy(bArr, i, this.buf, this.bufOff, length);
                int processBlock = this.cipher.processBlock(this.buf, 0, bArr2, i3) + 0;
                this.bufOff = 0;
                i2 -= length;
                i += length;
                i4 = processBlock;
                while (i2 > this.buf.length) {
                    i4 += this.cipher.processBlock(bArr, i, bArr2, i3 + i4);
                    i2 -= blockSize;
                    i += blockSize;
                }
            }
            System.arraycopy(bArr, i, this.buf, this.bufOff, i2);
            this.bufOff += i2;
            return i4;
        }
        throw new OutputLengthException("output buffer too short");
    }
}
