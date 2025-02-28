package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateTypeAdapter extends TypeAdapter<Date> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Date.class) {
                return new DateTypeAdapter();
            }
            return null;
        }
    };
    private final DateFormat a = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat b = DateFormat.getDateTimeInstance(2, 2);

    public Date read(JsonReader jsonReader) {
        if (jsonReader.peek() != JsonToken.NULL) {
            return a(jsonReader.nextString());
        }
        jsonReader.nextNull();
        return null;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:6|7|8|9) */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        return com.google.gson.internal.bind.util.ISO8601Utils.parse(r3, new java.text.ParsePosition(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0025, code lost:
        throw new com.google.gson.JsonSyntaxException(r3, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        return r2.a.parse(r3);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0013 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x000b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.util.Date a(java.lang.String r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.text.DateFormat r0 = r2.b     // Catch:{ ParseException -> 0x000b }
            java.util.Date r0 = r0.parse(r3)     // Catch:{ ParseException -> 0x000b }
            monitor-exit(r2)
            return r0
        L_0x0009:
            r3 = move-exception
            goto L_0x0026
        L_0x000b:
            java.text.DateFormat r0 = r2.a     // Catch:{ ParseException -> 0x0013 }
            java.util.Date r0 = r0.parse(r3)     // Catch:{ ParseException -> 0x0013 }
            monitor-exit(r2)
            return r0
        L_0x0013:
            java.text.ParsePosition r0 = new java.text.ParsePosition     // Catch:{ ParseException -> 0x001f }
            r1 = 0
            r0.<init>(r1)     // Catch:{ ParseException -> 0x001f }
            java.util.Date r0 = com.google.gson.internal.bind.util.ISO8601Utils.parse(r3, r0)     // Catch:{ ParseException -> 0x001f }
            monitor-exit(r2)
            return r0
        L_0x001f:
            r0 = move-exception
            com.google.gson.JsonSyntaxException r1 = new com.google.gson.JsonSyntaxException     // Catch:{ all -> 0x0009 }
            r1.<init>(r3, r0)     // Catch:{ all -> 0x0009 }
            throw r1     // Catch:{ all -> 0x0009 }
        L_0x0026:
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.DateTypeAdapter.a(java.lang.String):java.util.Date");
    }

    public synchronized void write(JsonWriter jsonWriter, Date date) {
        if (date == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(this.a.format(date));
        }
    }
}
