package android.support.v4.util;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class LruCache<K, V> {
    private final LinkedHashMap<K, V> a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;

    /* access modifiers changed from: protected */
    public V create(K k) {
        return null;
    }

    public void entryRemoved(boolean z, K k, V v, V v2) {
    }

    public int sizeOf(K k, V v) {
        return 1;
    }

    public LruCache(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.c = i;
        this.a = new LinkedHashMap<>(0, 0.75f, true);
    }

    public void resize(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        synchronized (this) {
            this.c = i;
        }
        trimToSize(i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0022, code lost:
        r0 = create(r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0026, code lost:
        if (r0 != null) goto L_0x002a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0029, code lost:
        return null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002a, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r4.e++;
        r1 = r4.a.put(r5, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0037, code lost:
        if (r1 == null) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0039, code lost:
        r4.a.put(r5, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003f, code lost:
        r4.b += a(r5, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0048, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0049, code lost:
        if (r1 == null) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004b, code lost:
        entryRemoved(false, r5, r0, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004f, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0050, code lost:
        trimToSize(r4.c);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0055, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V get(K r5) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x000a
            java.lang.NullPointerException r5 = new java.lang.NullPointerException
            java.lang.String r0 = "key == null"
            r5.<init>(r0)
            throw r5
        L_0x000a:
            monitor-enter(r4)
            java.util.LinkedHashMap<K, V> r0 = r4.a     // Catch:{ all -> 0x0059 }
            java.lang.Object r0 = r0.get(r5)     // Catch:{ all -> 0x0059 }
            if (r0 == 0) goto L_0x001b
            int r5 = r4.g     // Catch:{ all -> 0x0059 }
            int r5 = r5 + 1
            r4.g = r5     // Catch:{ all -> 0x0059 }
            monitor-exit(r4)     // Catch:{ all -> 0x0059 }
            return r0
        L_0x001b:
            int r0 = r4.h     // Catch:{ all -> 0x0059 }
            int r0 = r0 + 1
            r4.h = r0     // Catch:{ all -> 0x0059 }
            monitor-exit(r4)     // Catch:{ all -> 0x0059 }
            java.lang.Object r0 = r4.create(r5)
            if (r0 != 0) goto L_0x002a
            r5 = 0
            return r5
        L_0x002a:
            monitor-enter(r4)
            int r1 = r4.e     // Catch:{ all -> 0x0056 }
            int r1 = r1 + 1
            r4.e = r1     // Catch:{ all -> 0x0056 }
            java.util.LinkedHashMap<K, V> r1 = r4.a     // Catch:{ all -> 0x0056 }
            java.lang.Object r1 = r1.put(r5, r0)     // Catch:{ all -> 0x0056 }
            if (r1 == 0) goto L_0x003f
            java.util.LinkedHashMap<K, V> r2 = r4.a     // Catch:{ all -> 0x0056 }
            r2.put(r5, r1)     // Catch:{ all -> 0x0056 }
            goto L_0x0048
        L_0x003f:
            int r2 = r4.b     // Catch:{ all -> 0x0056 }
            int r3 = r4.a(r5, r0)     // Catch:{ all -> 0x0056 }
            int r2 = r2 + r3
            r4.b = r2     // Catch:{ all -> 0x0056 }
        L_0x0048:
            monitor-exit(r4)     // Catch:{ all -> 0x0056 }
            if (r1 == 0) goto L_0x0050
            r2 = 0
            r4.entryRemoved(r2, r5, r0, r1)
            return r1
        L_0x0050:
            int r5 = r4.c
            r4.trimToSize(r5)
            return r0
        L_0x0056:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0056 }
            throw r5
        L_0x0059:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0059 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.LruCache.get(java.lang.Object):java.lang.Object");
    }

    public final V put(K k, V v) {
        V put;
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        synchronized (this) {
            this.d++;
            this.b += a(k, v);
            put = this.a.put(k, v);
            if (put != null) {
                this.b -= a(k, put);
            }
        }
        if (put != null) {
            entryRemoved(false, k, put, v);
        }
        trimToSize(this.c);
        return put;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        r0 = new java.lang.StringBuilder();
        r0.append(getClass().getName());
        r0.append(".sizeOf() is reporting inconsistent results!");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0071, code lost:
        throw new java.lang.IllegalStateException(r0.toString());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void trimToSize(int r5) {
        /*
            r4 = this;
        L_0x0000:
            monitor-enter(r4)
            int r0 = r4.b     // Catch:{ all -> 0x0072 }
            if (r0 < 0) goto L_0x0053
            java.util.LinkedHashMap<K, V> r0 = r4.a     // Catch:{ all -> 0x0072 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x0012
            int r0 = r4.b     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x0012
            goto L_0x0053
        L_0x0012:
            int r0 = r4.b     // Catch:{ all -> 0x0072 }
            if (r0 <= r5) goto L_0x0051
            java.util.LinkedHashMap<K, V> r0 = r4.a     // Catch:{ all -> 0x0072 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x001f
            goto L_0x0051
        L_0x001f:
            java.util.LinkedHashMap<K, V> r0 = r4.a     // Catch:{ all -> 0x0072 }
            java.util.Set r0 = r0.entrySet()     // Catch:{ all -> 0x0072 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0072 }
            java.lang.Object r0 = r0.next()     // Catch:{ all -> 0x0072 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ all -> 0x0072 }
            java.lang.Object r1 = r0.getKey()     // Catch:{ all -> 0x0072 }
            java.lang.Object r0 = r0.getValue()     // Catch:{ all -> 0x0072 }
            java.util.LinkedHashMap<K, V> r2 = r4.a     // Catch:{ all -> 0x0072 }
            r2.remove(r1)     // Catch:{ all -> 0x0072 }
            int r2 = r4.b     // Catch:{ all -> 0x0072 }
            int r3 = r4.a(r1, r0)     // Catch:{ all -> 0x0072 }
            int r2 = r2 - r3
            r4.b = r2     // Catch:{ all -> 0x0072 }
            int r2 = r4.f     // Catch:{ all -> 0x0072 }
            r3 = 1
            int r2 = r2 + r3
            r4.f = r2     // Catch:{ all -> 0x0072 }
            monitor-exit(r4)     // Catch:{ all -> 0x0072 }
            r2 = 0
            r4.entryRemoved(r3, r1, r0, r2)
            goto L_0x0000
        L_0x0051:
            monitor-exit(r4)     // Catch:{ all -> 0x0072 }
            return
        L_0x0053:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0072 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0072 }
            r0.<init>()     // Catch:{ all -> 0x0072 }
            java.lang.Class r1 = r4.getClass()     // Catch:{ all -> 0x0072 }
            java.lang.String r1 = r1.getName()     // Catch:{ all -> 0x0072 }
            r0.append(r1)     // Catch:{ all -> 0x0072 }
            java.lang.String r1 = ".sizeOf() is reporting inconsistent results!"
            r0.append(r1)     // Catch:{ all -> 0x0072 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0072 }
            r5.<init>(r0)     // Catch:{ all -> 0x0072 }
            throw r5     // Catch:{ all -> 0x0072 }
        L_0x0072:
            r5 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0072 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.util.LruCache.trimToSize(int):void");
    }

    public final V remove(K k) {
        V remove;
        if (k == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            remove = this.a.remove(k);
            if (remove != null) {
                this.b -= a(k, remove);
            }
        }
        if (remove != null) {
            entryRemoved(false, k, remove, null);
        }
        return remove;
    }

    private int a(K k, V v) {
        int sizeOf = sizeOf(k, v);
        if (sizeOf >= 0) {
            return sizeOf;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Negative size: ");
        sb.append(k);
        sb.append("=");
        sb.append(v);
        throw new IllegalStateException(sb.toString());
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.b;
    }

    public final synchronized int maxSize() {
        return this.c;
    }

    public final synchronized int hitCount() {
        return this.g;
    }

    public final synchronized int missCount() {
        return this.h;
    }

    public final synchronized int createCount() {
        return this.e;
    }

    public final synchronized int putCount() {
        return this.d;
    }

    public final synchronized int evictionCount() {
        return this.f;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.a);
    }

    public final synchronized String toString() {
        int i;
        i = this.g + this.h;
        return String.format(Locale.US, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Integer.valueOf(this.c), Integer.valueOf(this.g), Integer.valueOf(this.h), Integer.valueOf(i != 0 ? (this.g * 100) / i : 0)});
    }
}
