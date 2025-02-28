package okhttp3;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

public final class CacheControl {
    public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(SubsamplingScaleImageView.TILE_SIZE_AUTO, TimeUnit.SECONDS).build();
    public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();
    @Nullable
    String a;
    private final boolean b;
    private final boolean c;
    private final int d;
    private final int e;
    private final boolean f;
    private final boolean g;
    private final boolean h;
    private final int i;
    private final int j;
    private final boolean k;
    private final boolean l;
    private final boolean m;

    public static final class Builder {
        boolean a;
        boolean b;
        int c = -1;
        int d = -1;
        int e = -1;
        boolean f;
        boolean g;
        boolean h;

        public Builder noCache() {
            this.a = true;
            return this;
        }

        public Builder noStore() {
            this.b = true;
            return this;
        }

        public Builder maxAge(int i, TimeUnit timeUnit) {
            if (i < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("maxAge < 0: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            }
            long seconds = timeUnit.toSeconds((long) i);
            this.c = seconds > 2147483647L ? SubsamplingScaleImageView.TILE_SIZE_AUTO : (int) seconds;
            return this;
        }

        public Builder maxStale(int i, TimeUnit timeUnit) {
            if (i < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("maxStale < 0: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            }
            long seconds = timeUnit.toSeconds((long) i);
            this.d = seconds > 2147483647L ? SubsamplingScaleImageView.TILE_SIZE_AUTO : (int) seconds;
            return this;
        }

        public Builder minFresh(int i, TimeUnit timeUnit) {
            if (i < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("minFresh < 0: ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            }
            long seconds = timeUnit.toSeconds((long) i);
            this.e = seconds > 2147483647L ? SubsamplingScaleImageView.TILE_SIZE_AUTO : (int) seconds;
            return this;
        }

        public Builder onlyIfCached() {
            this.f = true;
            return this;
        }

        public Builder noTransform() {
            this.g = true;
            return this;
        }

        public Builder immutable() {
            this.h = true;
            return this;
        }

        public CacheControl build() {
            return new CacheControl(this);
        }
    }

    private CacheControl(boolean z, boolean z2, int i2, int i3, boolean z3, boolean z4, boolean z5, int i4, int i5, boolean z6, boolean z7, boolean z8, @Nullable String str) {
        this.b = z;
        this.c = z2;
        this.d = i2;
        this.e = i3;
        this.f = z3;
        this.g = z4;
        this.h = z5;
        this.i = i4;
        this.j = i5;
        this.k = z6;
        this.l = z7;
        this.m = z8;
        this.a = str;
    }

    CacheControl(Builder builder) {
        this.b = builder.a;
        this.c = builder.b;
        this.d = builder.c;
        this.e = -1;
        this.f = false;
        this.g = false;
        this.h = false;
        this.i = builder.d;
        this.j = builder.e;
        this.k = builder.f;
        this.l = builder.g;
        this.m = builder.h;
    }

    public boolean noCache() {
        return this.b;
    }

    public boolean noStore() {
        return this.c;
    }

    public int maxAgeSeconds() {
        return this.d;
    }

    public int sMaxAgeSeconds() {
        return this.e;
    }

    public boolean isPrivate() {
        return this.f;
    }

    public boolean isPublic() {
        return this.g;
    }

    public boolean mustRevalidate() {
        return this.h;
    }

    public int maxStaleSeconds() {
        return this.i;
    }

    public int minFreshSeconds() {
        return this.j;
    }

    public boolean onlyIfCached() {
        return this.k;
    }

    public boolean noTransform() {
        return this.l;
    }

    public boolean immutable() {
        return this.m;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0043  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static okhttp3.CacheControl parse(okhttp3.Headers r24) {
        /*
            r0 = r24
            int r1 = r24.size()
            r6 = 0
            r7 = 1
            r8 = 0
            r10 = 0
            r11 = 0
            r12 = -1
            r13 = -1
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = -1
            r18 = -1
            r19 = 0
            r20 = 0
            r21 = 0
        L_0x001b:
            if (r6 >= r1) goto L_0x014c
            java.lang.String r9 = r0.name(r6)
            java.lang.String r2 = r0.value(r6)
            java.lang.String r4 = "Cache-Control"
            boolean r4 = r9.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0033
            if (r8 == 0) goto L_0x0031
        L_0x002f:
            r7 = 0
            goto L_0x003c
        L_0x0031:
            r8 = r2
            goto L_0x003c
        L_0x0033:
            java.lang.String r4 = "Pragma"
            boolean r4 = r9.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x0145
            goto L_0x002f
        L_0x003c:
            r4 = 0
        L_0x003d:
            int r9 = r2.length()
            if (r4 >= r9) goto L_0x0145
            java.lang.String r9 = "=,;"
            int r9 = okhttp3.internal.http.HttpHeaders.skipUntil(r2, r4, r9)
            java.lang.String r4 = r2.substring(r4, r9)
            java.lang.String r4 = r4.trim()
            int r3 = r2.length()
            if (r9 == r3) goto L_0x009e
            char r3 = r2.charAt(r9)
            r5 = 44
            if (r3 == r5) goto L_0x009e
            char r3 = r2.charAt(r9)
            r5 = 59
            if (r3 != r5) goto L_0x0068
            goto L_0x009e
        L_0x0068:
            int r9 = r9 + 1
            int r3 = okhttp3.internal.http.HttpHeaders.skipWhitespace(r2, r9)
            int r5 = r2.length()
            if (r3 >= r5) goto L_0x008d
            char r5 = r2.charAt(r3)
            r9 = 34
            if (r5 != r9) goto L_0x008d
            int r3 = r3 + 1
            java.lang.String r5 = "\""
            int r5 = okhttp3.internal.http.HttpHeaders.skipUntil(r2, r3, r5)
            java.lang.String r3 = r2.substring(r3, r5)
            r22 = 1
            int r5 = r5 + 1
            goto L_0x00a4
        L_0x008d:
            r22 = 1
            java.lang.String r5 = ",;"
            int r5 = okhttp3.internal.http.HttpHeaders.skipUntil(r2, r3, r5)
            java.lang.String r3 = r2.substring(r3, r5)
            java.lang.String r3 = r3.trim()
            goto L_0x00a4
        L_0x009e:
            r22 = 1
            int r9 = r9 + 1
            r5 = r9
            r3 = 0
        L_0x00a4:
            java.lang.String r9 = "no-cache"
            boolean r9 = r9.equalsIgnoreCase(r4)
            if (r9 == 0) goto L_0x00b0
            r9 = -1
            r10 = 1
            goto L_0x0142
        L_0x00b0:
            java.lang.String r9 = "no-store"
            boolean r9 = r9.equalsIgnoreCase(r4)
            if (r9 == 0) goto L_0x00bc
            r9 = -1
            r11 = 1
            goto L_0x0142
        L_0x00bc:
            java.lang.String r9 = "max-age"
            boolean r9 = r9.equalsIgnoreCase(r4)
            if (r9 == 0) goto L_0x00cc
            r9 = -1
            int r3 = okhttp3.internal.http.HttpHeaders.parseSeconds(r3, r9)
            r12 = r3
            goto L_0x0142
        L_0x00cc:
            java.lang.String r9 = "s-maxage"
            boolean r9 = r9.equalsIgnoreCase(r4)
            if (r9 == 0) goto L_0x00dc
            r9 = -1
            int r3 = okhttp3.internal.http.HttpHeaders.parseSeconds(r3, r9)
            r13 = r3
            goto L_0x0142
        L_0x00dc:
            java.lang.String r9 = "private"
            boolean r9 = r9.equalsIgnoreCase(r4)
            if (r9 == 0) goto L_0x00e7
            r9 = -1
            r14 = 1
            goto L_0x0142
        L_0x00e7:
            java.lang.String r9 = "public"
            boolean r9 = r9.equalsIgnoreCase(r4)
            if (r9 == 0) goto L_0x00f2
            r9 = -1
            r15 = 1
            goto L_0x0142
        L_0x00f2:
            java.lang.String r9 = "must-revalidate"
            boolean r9 = r9.equalsIgnoreCase(r4)
            if (r9 == 0) goto L_0x00fe
            r9 = -1
            r16 = 1
            goto L_0x0142
        L_0x00fe:
            java.lang.String r9 = "max-stale"
            boolean r9 = r9.equalsIgnoreCase(r4)
            if (r9 == 0) goto L_0x0111
            r4 = 2147483647(0x7fffffff, float:NaN)
            int r3 = okhttp3.internal.http.HttpHeaders.parseSeconds(r3, r4)
            r17 = r3
            r9 = -1
            goto L_0x0142
        L_0x0111:
            java.lang.String r9 = "min-fresh"
            boolean r9 = r9.equalsIgnoreCase(r4)
            if (r9 == 0) goto L_0x0121
            r9 = -1
            int r3 = okhttp3.internal.http.HttpHeaders.parseSeconds(r3, r9)
            r18 = r3
            goto L_0x0142
        L_0x0121:
            r9 = -1
            java.lang.String r3 = "only-if-cached"
            boolean r3 = r3.equalsIgnoreCase(r4)
            if (r3 == 0) goto L_0x012d
            r19 = 1
            goto L_0x0142
        L_0x012d:
            java.lang.String r3 = "no-transform"
            boolean r3 = r3.equalsIgnoreCase(r4)
            if (r3 == 0) goto L_0x0138
            r20 = 1
            goto L_0x0142
        L_0x0138:
            java.lang.String r3 = "immutable"
            boolean r3 = r3.equalsIgnoreCase(r4)
            if (r3 == 0) goto L_0x0142
            r21 = 1
        L_0x0142:
            r4 = r5
            goto L_0x003d
        L_0x0145:
            r9 = -1
            r22 = 1
            int r6 = r6 + 1
            goto L_0x001b
        L_0x014c:
            if (r7 != 0) goto L_0x0151
            r22 = 0
            goto L_0x0153
        L_0x0151:
            r22 = r8
        L_0x0153:
            okhttp3.CacheControl r0 = new okhttp3.CacheControl
            r9 = r0
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.CacheControl.parse(okhttp3.Headers):okhttp3.CacheControl");
    }

    public String toString() {
        String str = this.a;
        if (str != null) {
            return str;
        }
        String a2 = a();
        this.a = a2;
        return a2;
    }

    private String a() {
        StringBuilder sb = new StringBuilder();
        if (this.b) {
            sb.append("no-cache, ");
        }
        if (this.c) {
            sb.append("no-store, ");
        }
        if (this.d != -1) {
            sb.append("max-age=");
            sb.append(this.d);
            sb.append(", ");
        }
        if (this.e != -1) {
            sb.append("s-maxage=");
            sb.append(this.e);
            sb.append(", ");
        }
        if (this.f) {
            sb.append("private, ");
        }
        if (this.g) {
            sb.append("public, ");
        }
        if (this.h) {
            sb.append("must-revalidate, ");
        }
        if (this.i != -1) {
            sb.append("max-stale=");
            sb.append(this.i);
            sb.append(", ");
        }
        if (this.j != -1) {
            sb.append("min-fresh=");
            sb.append(this.j);
            sb.append(", ");
        }
        if (this.k) {
            sb.append("only-if-cached, ");
        }
        if (this.l) {
            sb.append("no-transform, ");
        }
        if (this.m) {
            sb.append("immutable, ");
        }
        if (sb.length() == 0) {
            return "";
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}
