package okhttp3.internal.publicsuffix;

import com.google.common.primitives.UnsignedBytes;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.IDN;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import okhttp3.internal.Util;
import okhttp3.internal.platform.Platform;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Source;

public final class PublicSuffixDatabase {
    public static final String PUBLIC_SUFFIX_RESOURCE = "publicsuffixes.gz";
    private static final byte[] a = {42};
    private static final String[] b = new String[0];
    private static final String[] c = {"*"};
    private static final PublicSuffixDatabase d = new PublicSuffixDatabase();
    private final AtomicBoolean e = new AtomicBoolean(false);
    private final CountDownLatch f = new CountDownLatch(1);
    private byte[] g;
    private byte[] h;

    public static PublicSuffixDatabase get() {
        return d;
    }

    public String getEffectiveTldPlusOne(String str) {
        int i;
        if (str == null) {
            throw new NullPointerException("domain == null");
        }
        String[] split = IDN.toUnicode(str).split("\\.");
        String[] a2 = a(split);
        if (split.length == a2.length && a2[0].charAt(0) != '!') {
            return null;
        }
        if (a2[0].charAt(0) == '!') {
            i = split.length - a2.length;
        } else {
            i = split.length - (a2.length + 1);
        }
        StringBuilder sb = new StringBuilder();
        String[] split2 = str.split("\\.");
        while (i < split2.length) {
            sb.append(split2[i]);
            sb.append('.');
            i++;
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0078 A[LOOP:3: B:37:0x0078->B:41:0x0085, LOOP_START, PHI: r1 
      PHI: (r1v3 int) = (r1v0 int), (r1v4 int) binds: [B:36:0x0076, B:41:0x0085] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00a3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String[] a(java.lang.String[] r8) {
        /*
            r7 = this;
            java.util.concurrent.atomic.AtomicBoolean r0 = r7.e
            boolean r0 = r0.get()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x0016
            java.util.concurrent.atomic.AtomicBoolean r0 = r7.e
            boolean r0 = r0.compareAndSet(r1, r2)
            if (r0 == 0) goto L_0x0016
            r7.a()
            goto L_0x0023
        L_0x0016:
            java.util.concurrent.CountDownLatch r0 = r7.f     // Catch:{ InterruptedException -> 0x001c }
            r0.await()     // Catch:{ InterruptedException -> 0x001c }
            goto L_0x0023
        L_0x001c:
            java.lang.Thread r0 = java.lang.Thread.currentThread()
            r0.interrupt()
        L_0x0023:
            monitor-enter(r7)
            byte[] r0 = r7.g     // Catch:{ all -> 0x00c7 }
            if (r0 != 0) goto L_0x0030
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00c7 }
            java.lang.String r0 = "Unable to load publicsuffixes.gz resource from the classpath."
            r8.<init>(r0)     // Catch:{ all -> 0x00c7 }
            throw r8     // Catch:{ all -> 0x00c7 }
        L_0x0030:
            monitor-exit(r7)     // Catch:{ all -> 0x00c7 }
            int r0 = r8.length
            byte[][] r0 = new byte[r0][]
            r3 = 0
        L_0x0035:
            int r4 = r8.length
            if (r3 >= r4) goto L_0x0045
            r4 = r8[r3]
            java.nio.charset.Charset r5 = okhttp3.internal.Util.UTF_8
            byte[] r4 = r4.getBytes(r5)
            r0[r3] = r4
            int r3 = r3 + 1
            goto L_0x0035
        L_0x0045:
            r8 = 0
        L_0x0046:
            int r3 = r0.length
            r4 = 0
            if (r8 >= r3) goto L_0x0056
            byte[] r3 = r7.g
            java.lang.String r3 = a(r3, r0, r8)
            if (r3 == 0) goto L_0x0053
            goto L_0x0057
        L_0x0053:
            int r8 = r8 + 1
            goto L_0x0046
        L_0x0056:
            r3 = r4
        L_0x0057:
            int r8 = r0.length
            if (r8 <= r2) goto L_0x0075
            java.lang.Object r8 = r0.clone()
            byte[][] r8 = (byte[][]) r8
            r5 = 0
        L_0x0061:
            int r6 = r8.length
            int r6 = r6 - r2
            if (r5 >= r6) goto L_0x0075
            byte[] r6 = a
            r8[r5] = r6
            byte[] r6 = r7.g
            java.lang.String r6 = a(r6, r8, r5)
            if (r6 == 0) goto L_0x0072
            goto L_0x0076
        L_0x0072:
            int r5 = r5 + 1
            goto L_0x0061
        L_0x0075:
            r6 = r4
        L_0x0076:
            if (r6 == 0) goto L_0x0088
        L_0x0078:
            int r8 = r0.length
            int r8 = r8 - r2
            if (r1 >= r8) goto L_0x0088
            byte[] r8 = r7.h
            java.lang.String r8 = a(r8, r0, r1)
            if (r8 == 0) goto L_0x0085
            goto L_0x0089
        L_0x0085:
            int r1 = r1 + 1
            goto L_0x0078
        L_0x0088:
            r8 = r4
        L_0x0089:
            if (r8 == 0) goto L_0x00a3
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "!"
            r0.append(r1)
            r0.append(r8)
            java.lang.String r8 = r0.toString()
            java.lang.String r0 = "\\."
            java.lang.String[] r8 = r8.split(r0)
            return r8
        L_0x00a3:
            if (r3 != 0) goto L_0x00aa
            if (r6 != 0) goto L_0x00aa
            java.lang.String[] r8 = c
            return r8
        L_0x00aa:
            if (r3 == 0) goto L_0x00b3
            java.lang.String r8 = "\\."
            java.lang.String[] r8 = r3.split(r8)
            goto L_0x00b5
        L_0x00b3:
            java.lang.String[] r8 = b
        L_0x00b5:
            if (r6 == 0) goto L_0x00be
            java.lang.String r0 = "\\."
            java.lang.String[] r0 = r6.split(r0)
            goto L_0x00c0
        L_0x00be:
            java.lang.String[] r0 = b
        L_0x00c0:
            int r1 = r8.length
            int r2 = r0.length
            if (r1 <= r2) goto L_0x00c5
            goto L_0x00c6
        L_0x00c5:
            r8 = r0
        L_0x00c6:
            return r8
        L_0x00c7:
            r8 = move-exception
            monitor-exit(r7)     // Catch:{ all -> 0x00c7 }
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.publicsuffix.PublicSuffixDatabase.a(java.lang.String[]):java.lang.String[]");
    }

    private static String a(byte[] bArr, byte[][] bArr2, int i) {
        int i2;
        byte b2;
        int i3;
        byte[] bArr3 = bArr;
        byte[][] bArr4 = bArr2;
        int length = bArr3.length;
        int i4 = 0;
        while (i4 < length) {
            int i5 = (i4 + length) / 2;
            while (i5 > -1 && bArr3[i5] != 10) {
                i5--;
            }
            int i6 = i5 + 1;
            int i7 = 1;
            while (true) {
                i2 = i6 + i7;
                if (bArr3[i2] == 10) {
                    break;
                }
                i7++;
            }
            int i8 = i2 - i6;
            int i9 = i;
            boolean z = false;
            int i10 = 0;
            int i11 = 0;
            while (true) {
                if (z) {
                    z = false;
                    b2 = 46;
                } else {
                    b2 = bArr4[i9][i10] & UnsignedBytes.MAX_VALUE;
                }
                i3 = b2 - (bArr3[i6 + i11] & UnsignedBytes.MAX_VALUE);
                if (i3 == 0) {
                    i11++;
                    i10++;
                    if (i11 == i8) {
                        break;
                    } else if (bArr4[i9].length == i10) {
                        if (i9 == bArr4.length - 1) {
                            break;
                        }
                        i9++;
                        z = true;
                        i10 = -1;
                    }
                } else {
                    break;
                }
            }
            if (i3 >= 0) {
                if (i3 <= 0) {
                    int i12 = i8 - i11;
                    int length2 = bArr4[i9].length - i10;
                    while (true) {
                        i9++;
                        if (i9 >= bArr4.length) {
                            break;
                        }
                        length2 += bArr4[i9].length;
                    }
                    if (length2 >= i12) {
                        if (length2 <= i12) {
                            return new String(bArr3, i6, i8, Util.UTF_8);
                        }
                    }
                }
                i4 = i2 + 1;
            }
            length = i6 - 1;
        }
        return null;
    }

    private void a() {
        boolean z = false;
        while (true) {
            try {
                b();
                break;
            } catch (InterruptedIOException unused) {
                z = true;
            } catch (IOException e2) {
                Platform.get().log(5, "Failed to read public suffix list", e2);
                if (z) {
                    Thread.currentThread().interrupt();
                }
                return;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
    }

    private void b() {
        InputStream resourceAsStream = PublicSuffixDatabase.class.getResourceAsStream(PUBLIC_SUFFIX_RESOURCE);
        if (resourceAsStream != null) {
            BufferedSource buffer = Okio.buffer((Source) new GzipSource(Okio.source(resourceAsStream)));
            try {
                byte[] bArr = new byte[buffer.readInt()];
                buffer.readFully(bArr);
                byte[] bArr2 = new byte[buffer.readInt()];
                buffer.readFully(bArr2);
                synchronized (this) {
                    this.g = bArr;
                    this.h = bArr2;
                }
                this.f.countDown();
            } finally {
                Util.closeQuietly((Closeable) buffer);
            }
        }
    }
}
