package net.danlew.android.joda;

import android.content.Context;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import net.danlew.android.joda.R.raw;
import org.joda.time.DateTimeZone;
import org.joda.time.tz.Provider;

public class ResourceZoneInfoProvider implements Provider {
    private Context a;
    private final Map<String, Object> b;

    public ResourceZoneInfoProvider(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null");
        }
        this.a = context.getApplicationContext();
        this.b = a(a("ZoneInfoMap"));
    }

    public DateTimeZone getZone(String str) {
        if (str == null) {
            return null;
        }
        Object obj = this.b.get(str);
        if (obj == null) {
            return null;
        }
        if (str.equals(obj)) {
            return b(str);
        }
        if (!(obj instanceof SoftReference)) {
            return getZone((String) obj);
        }
        DateTimeZone dateTimeZone = (DateTimeZone) ((SoftReference) obj).get();
        if (dateTimeZone != null) {
            return dateTimeZone;
        }
        return b(str);
    }

    public Set<String> getAvailableIDs() {
        return new TreeSet(this.b.keySet());
    }

    /* access modifiers changed from: protected */
    public void uncaughtException(Exception exc) {
        exc.printStackTrace();
    }

    private InputStream a(String str) {
        if (this.a == null) {
            throw new RuntimeException("Need to call JodaTimeAndroid.init() before using joda-time-android");
        }
        String tzResource = ResUtils.getTzResource(str);
        int identifier = ResUtils.getIdentifier(raw.class, tzResource);
        if (identifier != 0) {
            return this.a.getResources().openRawResource(identifier);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Resource not found: \"");
        sb.append(str);
        sb.append("\" (resName: \"");
        sb.append(tzResource);
        sb.append("\")");
        throw new IOException(sb.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x002c A[SYNTHETIC, Splitter:B:19:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0032 A[SYNTHETIC, Splitter:B:24:0x0032] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.joda.time.DateTimeZone b(java.lang.String r6) {
        /*
            r5 = this;
            r0 = 0
            java.io.InputStream r1 = r5.a(r6)     // Catch:{ IOException -> 0x0020, all -> 0x001d }
            org.joda.time.DateTimeZone r2 = org.joda.time.tz.DateTimeZoneBuilder.readFrom(r1, r6)     // Catch:{ IOException -> 0x001b }
            java.util.Map<java.lang.String, java.lang.Object> r3 = r5.b     // Catch:{ IOException -> 0x001b }
            java.lang.ref.SoftReference r4 = new java.lang.ref.SoftReference     // Catch:{ IOException -> 0x001b }
            r4.<init>(r2)     // Catch:{ IOException -> 0x001b }
            r3.put(r6, r4)     // Catch:{ IOException -> 0x001b }
            if (r1 == 0) goto L_0x0018
            r1.close()     // Catch:{ IOException -> 0x0018 }
        L_0x0018:
            return r2
        L_0x0019:
            r6 = move-exception
            goto L_0x0030
        L_0x001b:
            r2 = move-exception
            goto L_0x0022
        L_0x001d:
            r6 = move-exception
            r1 = r0
            goto L_0x0030
        L_0x0020:
            r2 = move-exception
            r1 = r0
        L_0x0022:
            r5.uncaughtException(r2)     // Catch:{ all -> 0x0019 }
            java.util.Map<java.lang.String, java.lang.Object> r2 = r5.b     // Catch:{ all -> 0x0019 }
            r2.remove(r6)     // Catch:{ all -> 0x0019 }
            if (r1 == 0) goto L_0x002f
            r1.close()     // Catch:{ IOException -> 0x002f }
        L_0x002f:
            return r0
        L_0x0030:
            if (r1 == 0) goto L_0x0035
            r1.close()     // Catch:{ IOException -> 0x0035 }
        L_0x0035:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: net.danlew.android.joda.ResourceZoneInfoProvider.b(java.lang.String):org.joda.time.DateTimeZone");
    }

    private static Map<String, Object> a(InputStream inputStream) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        try {
            a(dataInputStream, concurrentHashMap);
            concurrentHashMap.put("UTC", new SoftReference(DateTimeZone.UTC));
            return concurrentHashMap;
        } finally {
            try {
                dataInputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    private static void a(DataInputStream dataInputStream, Map<String, Object> map) {
        int readUnsignedShort = dataInputStream.readUnsignedShort();
        String[] strArr = new String[readUnsignedShort];
        int i = 0;
        for (int i2 = 0; i2 < readUnsignedShort; i2++) {
            strArr[i2] = dataInputStream.readUTF().intern();
        }
        int readUnsignedShort2 = dataInputStream.readUnsignedShort();
        while (i < readUnsignedShort2) {
            try {
                map.put(strArr[dataInputStream.readUnsignedShort()], strArr[dataInputStream.readUnsignedShort()]);
                i++;
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IOException("Corrupt zone info map");
            }
        }
    }
}
