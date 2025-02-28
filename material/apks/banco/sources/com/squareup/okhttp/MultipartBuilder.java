package com.squareup.okhttp;

import com.google.common.base.Ascii;
import com.squareup.okhttp.internal.Util;
import cz.msebera.android.httpclient.message.TokenParser;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import okio.BufferedSink;
import okio.ByteString;

public final class MultipartBuilder {
    public static final MediaType ALTERNATIVE = MediaType.parse("multipart/alternative");
    public static final MediaType DIGEST = MediaType.parse("multipart/digest");
    public static final MediaType FORM = MediaType.parse("multipart/form-data");
    public static final MediaType MIXED = MediaType.parse("multipart/mixed");
    public static final MediaType PARALLEL = MediaType.parse("multipart/parallel");
    /* access modifiers changed from: private */
    public static final byte[] a = {58, 32};
    /* access modifiers changed from: private */
    public static final byte[] b = {Ascii.CR, 10};
    /* access modifiers changed from: private */
    public static final byte[] c = {45, 45};
    private final ByteString d;
    private MediaType e;
    private final List<Headers> f;
    private final List<RequestBody> g;

    static final class MultipartRequestBody extends RequestBody {
        private final ByteString a;
        private final MediaType b;
        private final List<Headers> c;
        private final List<RequestBody> d;
        private long e = -1;

        public MultipartRequestBody(MediaType mediaType, ByteString byteString, List<Headers> list, List<RequestBody> list2) {
            if (mediaType == null) {
                throw new NullPointerException("type == null");
            }
            this.a = byteString;
            StringBuilder sb = new StringBuilder();
            sb.append(mediaType);
            sb.append("; boundary=");
            sb.append(byteString.utf8());
            this.b = MediaType.parse(sb.toString());
            this.c = Util.immutableList(list);
            this.d = Util.immutableList(list2);
        }

        public MediaType contentType() {
            return this.b;
        }

        public long contentLength() {
            long j = this.e;
            if (j != -1) {
                return j;
            }
            long a2 = a(null, true);
            this.e = a2;
            return a2;
        }

        /* JADX WARNING: type inference failed for: r13v1, types: [okio.BufferedSink] */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r13v3, types: [okio.Buffer] */
        /* JADX WARNING: type inference failed for: r13v4 */
        /* JADX WARNING: type inference failed for: r13v5 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* JADX WARNING: Unknown variable types count: 3 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private long a(okio.BufferedSink r13, boolean r14) {
            /*
                r12 = this;
                if (r14 == 0) goto L_0x0009
                okio.Buffer r13 = new okio.Buffer
                r13.<init>()
                r0 = r13
                goto L_0x000a
            L_0x0009:
                r0 = 0
            L_0x000a:
                java.util.List<com.squareup.okhttp.Headers> r1 = r12.c
                int r1 = r1.size()
                r2 = 0
                r3 = 0
                r4 = r3
                r3 = 0
            L_0x0015:
                if (r3 >= r1) goto L_0x00c5
                java.util.List<com.squareup.okhttp.Headers> r6 = r12.c
                java.lang.Object r6 = r6.get(r3)
                com.squareup.okhttp.Headers r6 = (com.squareup.okhttp.Headers) r6
                java.util.List<com.squareup.okhttp.RequestBody> r7 = r12.d
                java.lang.Object r7 = r7.get(r3)
                com.squareup.okhttp.RequestBody r7 = (com.squareup.okhttp.RequestBody) r7
                byte[] r8 = com.squareup.okhttp.MultipartBuilder.c
                r13.write(r8)
                okio.ByteString r8 = r12.a
                r13.write(r8)
                byte[] r8 = com.squareup.okhttp.MultipartBuilder.b
                r13.write(r8)
                if (r6 == 0) goto L_0x0065
                int r8 = r6.size()
                r9 = 0
            L_0x0041:
                if (r9 >= r8) goto L_0x0065
                java.lang.String r10 = r6.name(r9)
                okio.BufferedSink r10 = r13.writeUtf8(r10)
                byte[] r11 = com.squareup.okhttp.MultipartBuilder.a
                okio.BufferedSink r10 = r10.write(r11)
                java.lang.String r11 = r6.value(r9)
                okio.BufferedSink r10 = r10.writeUtf8(r11)
                byte[] r11 = com.squareup.okhttp.MultipartBuilder.b
                r10.write(r11)
                int r9 = r9 + 1
                goto L_0x0041
            L_0x0065:
                com.squareup.okhttp.MediaType r6 = r7.contentType()
                if (r6 == 0) goto L_0x0080
                java.lang.String r8 = "Content-Type: "
                okio.BufferedSink r8 = r13.writeUtf8(r8)
                java.lang.String r6 = r6.toString()
                okio.BufferedSink r6 = r8.writeUtf8(r6)
                byte[] r8 = com.squareup.okhttp.MultipartBuilder.b
                r6.write(r8)
            L_0x0080:
                long r6 = r7.contentLength()
                r8 = -1
                int r10 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r10 == 0) goto L_0x009c
                java.lang.String r8 = "Content-Length: "
                okio.BufferedSink r8 = r13.writeUtf8(r8)
                okio.BufferedSink r8 = r8.writeDecimalLong(r6)
                byte[] r9 = com.squareup.okhttp.MultipartBuilder.b
                r8.write(r9)
                goto L_0x00a2
            L_0x009c:
                if (r14 == 0) goto L_0x00a2
                r0.clear()
                return r8
            L_0x00a2:
                byte[] r8 = com.squareup.okhttp.MultipartBuilder.b
                r13.write(r8)
                if (r14 == 0) goto L_0x00af
                long r8 = r4 + r6
                r4 = r8
                goto L_0x00ba
            L_0x00af:
                java.util.List<com.squareup.okhttp.RequestBody> r6 = r12.d
                java.lang.Object r6 = r6.get(r3)
                com.squareup.okhttp.RequestBody r6 = (com.squareup.okhttp.RequestBody) r6
                r6.writeTo(r13)
            L_0x00ba:
                byte[] r6 = com.squareup.okhttp.MultipartBuilder.b
                r13.write(r6)
                int r3 = r3 + 1
                goto L_0x0015
            L_0x00c5:
                byte[] r1 = com.squareup.okhttp.MultipartBuilder.c
                r13.write(r1)
                okio.ByteString r1 = r12.a
                r13.write(r1)
                byte[] r1 = com.squareup.okhttp.MultipartBuilder.c
                r13.write(r1)
                byte[] r1 = com.squareup.okhttp.MultipartBuilder.b
                r13.write(r1)
                if (r14 == 0) goto L_0x00eb
                long r13 = r0.size()
                long r1 = r4 + r13
                r0.clear()
                goto L_0x00ec
            L_0x00eb:
                r1 = r4
            L_0x00ec:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.okhttp.MultipartBuilder.MultipartRequestBody.a(okio.BufferedSink, boolean):long");
        }

        public void writeTo(BufferedSink bufferedSink) {
            a(bufferedSink, false);
        }
    }

    public MultipartBuilder() {
        this(UUID.randomUUID().toString());
    }

    public MultipartBuilder(String str) {
        this.e = MIXED;
        this.f = new ArrayList();
        this.g = new ArrayList();
        this.d = ByteString.encodeUtf8(str);
    }

    public MultipartBuilder type(MediaType mediaType) {
        if (mediaType == null) {
            throw new NullPointerException("type == null");
        } else if (!mediaType.type().equals("multipart")) {
            StringBuilder sb = new StringBuilder();
            sb.append("multipart != ");
            sb.append(mediaType);
            throw new IllegalArgumentException(sb.toString());
        } else {
            this.e = mediaType;
            return this;
        }
    }

    public MultipartBuilder addPart(RequestBody requestBody) {
        return addPart(null, requestBody);
    }

    public MultipartBuilder addPart(Headers headers, RequestBody requestBody) {
        if (requestBody == null) {
            throw new NullPointerException("body == null");
        } else if (headers != null && headers.get("Content-Type") != null) {
            throw new IllegalArgumentException("Unexpected header: Content-Type");
        } else if (headers == null || headers.get("Content-Length") == null) {
            this.f.add(headers);
            this.g.add(requestBody);
            return this;
        } else {
            throw new IllegalArgumentException("Unexpected header: Content-Length");
        }
    }

    private static StringBuilder a(StringBuilder sb, String str) {
        sb.append(TokenParser.DQUOTE);
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == 10) {
                sb.append("%0A");
            } else if (charAt == 13) {
                sb.append("%0D");
            } else if (charAt != '\"') {
                sb.append(charAt);
            } else {
                sb.append("%22");
            }
        }
        sb.append(TokenParser.DQUOTE);
        return sb;
    }

    public MultipartBuilder addFormDataPart(String str, String str2) {
        return addFormDataPart(str, null, RequestBody.create((MediaType) null, str2));
    }

    public MultipartBuilder addFormDataPart(String str, String str2, RequestBody requestBody) {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        StringBuilder sb = new StringBuilder("form-data; name=");
        a(sb, str);
        if (str2 != null) {
            sb.append("; filename=");
            a(sb, str2);
        }
        return addPart(Headers.of("Content-Disposition", sb.toString()), requestBody);
    }

    public RequestBody build() {
        if (!this.f.isEmpty()) {
            return new MultipartRequestBody(this.e, this.d, this.f, this.g);
        }
        throw new IllegalStateException("Multipart body must have at least one part.");
    }
}
