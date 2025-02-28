package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.io.FilterOutputStream;
import java.io.OutputStream;

@GwtIncompatible
@Beta
public final class CountingOutputStream extends FilterOutputStream {
    private long a;

    public CountingOutputStream(OutputStream outputStream) {
        super((OutputStream) Preconditions.checkNotNull(outputStream));
    }

    public long getCount() {
        return this.a;
    }

    public void write(byte[] bArr, int i, int i2) {
        this.out.write(bArr, i, i2);
        this.a += (long) i2;
    }

    public void write(int i) {
        this.out.write(i);
        this.a++;
    }

    public void close() {
        this.out.close();
    }
}
