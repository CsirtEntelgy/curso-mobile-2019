package com.google.android.gms.common.util;

import android.support.v4.util.ArrayMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzf {
    private static <K, V> void a(K[] kArr, V[] vArr) {
        if (kArr.length != vArr.length) {
            int length = kArr.length;
            int length2 = vArr.length;
            StringBuilder sb = new StringBuilder(66);
            sb.append("Key and values array lengths not equal: ");
            sb.append(length);
            sb.append(" != ");
            sb.append(length2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static <K, V> Map<K, V> zza(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        ArrayMap arrayMap = new ArrayMap(6);
        arrayMap.put(k, v);
        arrayMap.put(k2, v2);
        arrayMap.put(k3, v3);
        arrayMap.put(k4, v4);
        arrayMap.put(k5, v5);
        arrayMap.put(k6, v6);
        return Collections.unmodifiableMap(arrayMap);
    }

    public static <T> Set<T> zza(T t, T t2, T t3) {
        zza zza = new zza(3);
        zza.add(t);
        zza.add(t2);
        zza.add(t3);
        return Collections.unmodifiableSet(zza);
    }

    public static <T> Set<T> zza(T t, T t2, T t3, T t4) {
        zza zza = new zza(4);
        zza.add(t);
        zza.add(t2);
        zza.add(t3);
        zza.add(t4);
        return Collections.unmodifiableSet(zza);
    }

    public static <T> Set<T> zzaa(T t) {
        return Collections.singleton(t);
    }

    public static <T> Set<T> zzaxh() {
        return Collections.emptySet();
    }

    public static <K, V> Map<K, V> zzaxi() {
        return Collections.emptyMap();
    }

    public static <K, V> Map<K, V> zzb(K[] kArr, V[] vArr) {
        a(kArr, vArr);
        int length = kArr.length;
        switch (length) {
            case 0:
                return zzaxi();
            case 1:
                return zze(kArr[0], vArr[0]);
            default:
                Map arrayMap = length <= 32 ? new ArrayMap(length) : new HashMap(length, 1.0f);
                for (int i = 0; i < length; i++) {
                    arrayMap.put(kArr[i], vArr[i]);
                }
                return Collections.unmodifiableMap(arrayMap);
        }
    }

    public static <T> List<T> zzc(T t, T t2) {
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(t);
        arrayList.add(t2);
        return Collections.unmodifiableList(arrayList);
    }

    public static <T> Set<T> zzc(T... tArr) {
        switch (tArr.length) {
            case 0:
                return zzaxh();
            case 1:
                return zzaa(tArr[0]);
            case 2:
                return zzd(tArr[0], tArr[1]);
            case 3:
                return zza(tArr[0], tArr[1], tArr[2]);
            case 4:
                return zza(tArr[0], tArr[1], tArr[2], tArr[3]);
            default:
                return Collections.unmodifiableSet(tArr.length <= 32 ? new zza((Collection<E>) Arrays.asList(tArr)) : new HashSet(Arrays.asList(tArr)));
        }
    }

    public static <T> Set<T> zzd(T t, T t2) {
        zza zza = new zza(2);
        zza.add(t);
        zza.add(t2);
        return Collections.unmodifiableSet(zza);
    }

    public static <K, V> Map<K, V> zze(K k, V v) {
        return Collections.singletonMap(k, v);
    }

    public static <T> List<T> zzz(T t) {
        return Collections.singletonList(t);
    }
}
