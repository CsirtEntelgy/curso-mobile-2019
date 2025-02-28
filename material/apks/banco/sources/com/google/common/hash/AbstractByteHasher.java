package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@CanIgnoreReturnValue
abstract class AbstractByteHasher extends AbstractHasher {
    private final ByteBuffer a = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);

    /* access modifiers changed from: protected */
    public abstract void a(byte b);

    AbstractByteHasher() {
    }

    /* access modifiers changed from: protected */
    public void a(byte[] bArr) {
        a(bArr, 0, bArr.length);
    }

    /* access modifiers changed from: protected */
    public void a(byte[] bArr, int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            a(bArr[i3]);
        }
    }

    public Hasher putByte(byte b) {
        a(b);
        return this;
    }

    public Hasher putBytes(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        a(bArr);
        return this;
    }

    public Hasher putBytes(byte[] bArr, int i, int i2) {
        Preconditions.checkPositionIndexes(i, i + i2, bArr.length);
        a(bArr, i, i2);
        return this;
    }

    private Hasher a(int i) {
        try {
            a(this.a.array(), 0, i);
            return this;
        } finally {
            this.a.clear();
        }
    }

    public Hasher putShort(short s) {
        this.a.putShort(s);
        return a(2);
    }

    public Hasher putInt(int i) {
        this.a.putInt(i);
        return a(4);
    }

    public Hasher putLong(long j) {
        this.a.putLong(j);
        return a(8);
    }

    public Hasher putChar(char c) {
        this.a.putChar(c);
        return a(2);
    }

    public <T> Hasher putObject(T t, Funnel<? super T> funnel) {
        funnel.funnel(t, this);
        return this;
    }
}
