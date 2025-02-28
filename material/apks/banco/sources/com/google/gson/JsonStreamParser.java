package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

public final class JsonStreamParser implements Iterator<JsonElement> {
    private final JsonReader a;
    private final Object b;

    public JsonStreamParser(String str) {
        this((Reader) new StringReader(str));
    }

    public JsonStreamParser(Reader reader) {
        this.a = new JsonReader(reader);
        this.a.setLenient(true);
        this.b = new Object();
    }

    /* JADX WARNING: type inference failed for: r0v4, types: [java.lang.Throwable] */
    /* JADX WARNING: type inference failed for: r0v5, types: [java.util.NoSuchElementException] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.gson.JsonElement next() {
        /*
            r3 = this;
            boolean r0 = r3.hasNext()
            if (r0 != 0) goto L_0x000c
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            r0.<init>()
            throw r0
        L_0x000c:
            com.google.gson.stream.JsonReader r0 = r3.a     // Catch:{ StackOverflowError -> 0x002b, OutOfMemoryError -> 0x0022, JsonParseException -> 0x0013 }
            com.google.gson.JsonElement r0 = com.google.gson.internal.Streams.parse(r0)     // Catch:{ StackOverflowError -> 0x002b, OutOfMemoryError -> 0x0022, JsonParseException -> 0x0013 }
            return r0
        L_0x0013:
            r0 = move-exception
            java.lang.Throwable r1 = r0.getCause()
            boolean r1 = r1 instanceof java.io.EOFException
            if (r1 == 0) goto L_0x0021
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException
            r0.<init>()
        L_0x0021:
            throw r0
        L_0x0022:
            r0 = move-exception
            com.google.gson.JsonParseException r1 = new com.google.gson.JsonParseException
            java.lang.String r2 = "Failed parsing JSON source to Json"
            r1.<init>(r2, r0)
            throw r1
        L_0x002b:
            r0 = move-exception
            com.google.gson.JsonParseException r1 = new com.google.gson.JsonParseException
            java.lang.String r2 = "Failed parsing JSON source to Json"
            r1.<init>(r2, r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.JsonStreamParser.next():com.google.gson.JsonElement");
    }

    public boolean hasNext() {
        boolean z;
        synchronized (this.b) {
            try {
                z = this.a.peek() != JsonToken.END_DOCUMENT;
            } catch (MalformedJsonException e) {
                throw new JsonSyntaxException((Throwable) e);
            } catch (IOException e2) {
                throw new JsonIOException((Throwable) e2);
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
