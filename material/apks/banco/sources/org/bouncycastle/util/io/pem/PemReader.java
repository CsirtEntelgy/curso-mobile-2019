package org.bouncycastle.util.io.pem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import org.bouncycastle.util.encoders.Base64;

public class PemReader extends BufferedReader {
    public PemReader(Reader reader) {
        super(reader);
    }

    private PemObject a(String str) {
        String readLine;
        StringBuilder sb = new StringBuilder();
        sb.append("-----END ");
        sb.append(str);
        String sb2 = sb.toString();
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList arrayList = new ArrayList();
        while (true) {
            readLine = readLine();
            if (readLine == null) {
                break;
            } else if (readLine.indexOf(":") >= 0) {
                int indexOf = readLine.indexOf(58);
                arrayList.add(new PemHeader(readLine.substring(0, indexOf), readLine.substring(indexOf + 1).trim()));
            } else if (readLine.indexOf(sb2) != -1) {
                break;
            } else {
                stringBuffer.append(readLine.trim());
            }
        }
        if (readLine != null) {
            return new PemObject(str, arrayList, Base64.decode(stringBuffer.toString()));
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(" not found");
        throw new IOException(sb3.toString());
    }

    public PemObject readPemObject() {
        String readLine;
        do {
            readLine = readLine();
            if (readLine == null) {
                break;
            }
        } while (!readLine.startsWith("-----BEGIN "));
        if (readLine != null) {
            String substring = readLine.substring("-----BEGIN ".length());
            int indexOf = substring.indexOf(45);
            String substring2 = substring.substring(0, indexOf);
            if (indexOf > 0) {
                return a(substring2);
            }
        }
        return null;
    }
}
