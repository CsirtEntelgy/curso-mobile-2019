package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;

public class CTSBlockCipher extends BufferedBlockCipher {
    private int a;

    public CTSBlockCipher(BlockCipher blockCipher) {
        if ((blockCipher instanceof OFBBlockCipher) || (blockCipher instanceof CFBBlockCipher) || (blockCipher instanceof SICBlockCipher)) {
            throw new IllegalArgumentException("CTSBlockCipher can only accept ECB, or CBC ciphers");
        }
        this.cipher = blockCipher;
        this.a = blockCipher.getBlockSize();
        this.buf = new byte[(this.a * 2)];
        this.bufOff = 0;
    }

    public int doFinal(byte[] bArr, int i) {
        if (this.bufOff + i > bArr.length) {
            throw new DataLengthException("output buffer to small in doFinal");
        }
        int blockSize = this.cipher.getBlockSize();
        int i2 = this.bufOff - blockSize;
        byte[] bArr2 = new byte[blockSize];
        if (this.forEncryption) {
            if (this.bufOff < blockSize) {
                throw new DataLengthException("need at least one block of input for CTS");
            }
            this.cipher.processBlock(this.buf, 0, bArr2, 0);
            if (this.bufOff > blockSize) {
                for (int i3 = this.bufOff; i3 != this.buf.length; i3++) {
                    this.buf[i3] = bArr2[i3 - blockSize];
                }
                for (int i4 = blockSize; i4 != this.bufOff; i4++) {
                    byte[] bArr3 = this.buf;
                    bArr3[i4] = (byte) (bArr3[i4] ^ bArr2[i4 - blockSize]);
                }
                (this.cipher instanceof CBCBlockCipher ? ((CBCBlockCipher) this.cipher).getUnderlyingCipher() : this.cipher).processBlock(this.buf, blockSize, bArr, i);
                System.arraycopy(bArr2, 0, bArr, i + blockSize, i2);
                int i5 = this.bufOff;
                reset();
                return i5;
            }
        } else if (this.bufOff < blockSize) {
            throw new DataLengthException("need at least one block of input for CTS");
        } else {
            byte[] bArr4 = new byte[blockSize];
            if (this.bufOff > blockSize) {
                (this.cipher instanceof CBCBlockCipher ? ((CBCBlockCipher) this.cipher).getUnderlyingCipher() : this.cipher).processBlock(this.buf, 0, bArr2, 0);
                for (int i6 = blockSize; i6 != this.bufOff; i6++) {
                    int i7 = i6 - blockSize;
                    bArr4[i7] = (byte) (bArr2[i7] ^ this.buf[i6]);
                }
                System.arraycopy(this.buf, blockSize, bArr2, 0, i2);
                this.cipher.processBlock(bArr2, 0, bArr, i);
                System.arraycopy(bArr4, 0, bArr, i + blockSize, i2);
                int i52 = this.bufOff;
                reset();
                return i52;
            }
            this.cipher.processBlock(this.buf, 0, bArr2, 0);
        }
        System.arraycopy(bArr2, 0, bArr, i, blockSize);
        int i522 = this.bufOff;
        reset();
        return i522;
    }

    public int getOutputSize(int i) {
        return i + this.bufOff;
    }

    public int getUpdateOutputSize(int i) {
        int i2 = i + this.bufOff;
        int length = i2 % this.buf.length;
        return length == 0 ? i2 - this.buf.length : i2 - length;
    }

    public int processByte(byte b, byte[] bArr, int i) {
        int i2;
        if (this.bufOff == this.buf.length) {
            i2 = this.cipher.processBlock(this.buf, 0, bArr, i);
            System.arraycopy(this.buf, this.a, this.buf, 0, this.a);
            this.bufOff = this.a;
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
                System.arraycopy(this.buf, blockSize, this.buf, 0, blockSize);
                this.bufOff = blockSize;
                i2 -= length;
                i += length;
                while (i2 > blockSize) {
                    System.arraycopy(bArr, i, this.buf, this.bufOff, blockSize);
                    processBlock += this.cipher.processBlock(this.buf, 0, bArr2, i3 + processBlock);
                    System.arraycopy(this.buf, blockSize, this.buf, 0, blockSize);
                    i2 -= blockSize;
                    i += blockSize;
                }
                i4 = processBlock;
            }
            System.arraycopy(bArr, i, this.buf, this.bufOff, i2);
            this.bufOff += i2;
            return i4;
        }
        throw new DataLengthException("output buffer too short");
    }
}
