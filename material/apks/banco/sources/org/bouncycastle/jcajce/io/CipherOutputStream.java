package org.bouncycastle.jcajce.io;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import javax.crypto.Cipher;

public class CipherOutputStream extends FilterOutputStream {
    private final Cipher a;
    private final byte[] b = new byte[1];

    public CipherOutputStream(OutputStream outputStream, Cipher cipher) {
        super(outputStream);
        this.a = cipher;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x003a, code lost:
        if (r1 != null) goto L_0x003d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void close() {
        /*
            r4 = this;
            javax.crypto.Cipher r0 = r4.a     // Catch:{ GeneralSecurityException -> 0x0028, Exception -> 0x0010 }
            byte[] r0 = r0.doFinal()     // Catch:{ GeneralSecurityException -> 0x0028, Exception -> 0x0010 }
            if (r0 == 0) goto L_0x000d
            java.io.OutputStream r1 = r4.out     // Catch:{ GeneralSecurityException -> 0x0028, Exception -> 0x0010 }
            r1.write(r0)     // Catch:{ GeneralSecurityException -> 0x0028, Exception -> 0x0010 }
        L_0x000d:
            r0 = 0
            r1 = r0
            goto L_0x0030
        L_0x0010:
            r0 = move-exception
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Error closing stream: "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            goto L_0x0030
        L_0x0028:
            r0 = move-exception
            org.bouncycastle.crypto.io.InvalidCipherTextIOException r1 = new org.bouncycastle.crypto.io.InvalidCipherTextIOException
            java.lang.String r2 = "Error during cipher finalisation"
            r1.<init>(r2, r0)
        L_0x0030:
            r4.flush()     // Catch:{ IOException -> 0x0039 }
            java.io.OutputStream r0 = r4.out     // Catch:{ IOException -> 0x0039 }
            r0.close()     // Catch:{ IOException -> 0x0039 }
            goto L_0x003d
        L_0x0039:
            r0 = move-exception
            if (r1 != 0) goto L_0x003d
            goto L_0x003e
        L_0x003d:
            r0 = r1
        L_0x003e:
            if (r0 == 0) goto L_0x0041
            throw r0
        L_0x0041:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jcajce.io.CipherOutputStream.close():void");
    }

    public void flush() {
        this.out.flush();
    }

    public void write(int i) {
        this.b[0] = (byte) i;
        write(this.b, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) {
        byte[] update = this.a.update(bArr, i, i2);
        if (update != null) {
            this.out.write(update);
        }
    }
}
