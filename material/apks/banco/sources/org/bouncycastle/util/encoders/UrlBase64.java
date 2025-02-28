package org.bouncycastle.util.encoders;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class UrlBase64 {
    private static final Encoder a = new UrlBase64Encoder();

    public static int decode(String str, OutputStream outputStream) {
        return a.decode(str, outputStream);
    }

    public static int decode(byte[] bArr, OutputStream outputStream) {
        return a.decode(bArr, 0, bArr.length, outputStream);
    }

    public static byte[] decode(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a.decode(str, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception decoding URL safe base64 string: ");
            sb.append(e.getMessage());
            throw new DecoderException(sb.toString(), e);
        }
    }

    public static byte[] decode(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a.decode(bArr, 0, bArr.length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception decoding URL safe base64 string: ");
            sb.append(e.getMessage());
            throw new DecoderException(sb.toString(), e);
        }
    }

    public static int encode(byte[] bArr, OutputStream outputStream) {
        return a.encode(bArr, 0, bArr.length, outputStream);
    }

    public static byte[] encode(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a.encode(bArr, 0, bArr.length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception encoding URL safe base64 data: ");
            sb.append(e.getMessage());
            throw new EncoderException(sb.toString(), e);
        }
    }
}
