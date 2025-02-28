package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Converter;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

@GwtCompatible
public final class Longs {
    public static final int BYTES = 8;
    public static final long MAX_POWER_OF_TWO = 4611686018427387904L;
    private static final byte[] a = a();

    @GwtCompatible
    static class LongArrayAsList extends AbstractList<Long> implements Serializable, RandomAccess {
        private static final long serialVersionUID = 0;
        final long[] a;
        final int b;
        final int c;

        public boolean isEmpty() {
            return false;
        }

        LongArrayAsList(long[] jArr) {
            this(jArr, 0, jArr.length);
        }

        LongArrayAsList(long[] jArr, int i, int i2) {
            this.a = jArr;
            this.b = i;
            this.c = i2;
        }

        public int size() {
            return this.c - this.b;
        }

        /* renamed from: a */
        public Long get(int i) {
            Preconditions.checkElementIndex(i, size());
            return Long.valueOf(this.a[this.b + i]);
        }

        public boolean contains(Object obj) {
            return (obj instanceof Long) && Longs.c(this.a, ((Long) obj).longValue(), this.b, this.c) != -1;
        }

        public int indexOf(Object obj) {
            if (obj instanceof Long) {
                int a2 = Longs.c(this.a, ((Long) obj).longValue(), this.b, this.c);
                if (a2 >= 0) {
                    return a2 - this.b;
                }
            }
            return -1;
        }

        public int lastIndexOf(Object obj) {
            if (obj instanceof Long) {
                int b2 = Longs.d(this.a, ((Long) obj).longValue(), this.b, this.c);
                if (b2 >= 0) {
                    return b2 - this.b;
                }
            }
            return -1;
        }

        /* renamed from: a */
        public Long set(int i, Long l) {
            Preconditions.checkElementIndex(i, size());
            long j = this.a[this.b + i];
            this.a[this.b + i] = ((Long) Preconditions.checkNotNull(l)).longValue();
            return Long.valueOf(j);
        }

        public List<Long> subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, size());
            if (i == i2) {
                return Collections.emptyList();
            }
            return new LongArrayAsList(this.a, this.b + i, this.b + i2);
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof LongArrayAsList)) {
                return super.equals(obj);
            }
            LongArrayAsList longArrayAsList = (LongArrayAsList) obj;
            int size = size();
            if (longArrayAsList.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (this.a[this.b + i] != longArrayAsList.a[longArrayAsList.b + i]) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int i = 1;
            for (int i2 = this.b; i2 < this.c; i2++) {
                i = (i * 31) + Longs.hashCode(this.a[i2]);
            }
            return i;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(size() * 10);
            sb.append('[');
            sb.append(this.a[this.b]);
            int i = this.b;
            while (true) {
                i++;
                if (i < this.c) {
                    sb.append(", ");
                    sb.append(this.a[i]);
                } else {
                    sb.append(']');
                    return sb.toString();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public long[] a() {
            int size = size();
            long[] jArr = new long[size];
            System.arraycopy(this.a, this.b, jArr, 0, size);
            return jArr;
        }
    }

    enum LexicographicalComparator implements Comparator<long[]> {
        INSTANCE;

        public String toString() {
            return "Longs.lexicographicalComparator()";
        }

        /* renamed from: a */
        public int compare(long[] jArr, long[] jArr2) {
            int min = Math.min(jArr.length, jArr2.length);
            for (int i = 0; i < min; i++) {
                int compare = Longs.compare(jArr[i], jArr2[i]);
                if (compare != 0) {
                    return compare;
                }
            }
            return jArr.length - jArr2.length;
        }
    }

    static final class LongConverter extends Converter<String, Long> implements Serializable {
        static final LongConverter a = new LongConverter();
        private static final long serialVersionUID = 1;

        public String toString() {
            return "Longs.stringConverter()";
        }

        private LongConverter() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public Long doForward(String str) {
            return Long.decode(str);
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public String doBackward(Long l) {
            return l.toString();
        }

        private Object readResolve() {
            return a;
        }
    }

    public static int compare(long j, long j2) {
        if (j < j2) {
            return -1;
        }
        return j > j2 ? 1 : 0;
    }

    public static long fromBytes(byte b, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        return ((((long) b) & 255) << 56) | ((((long) b2) & 255) << 48) | ((((long) b3) & 255) << 40) | ((((long) b4) & 255) << 32) | ((((long) b5) & 255) << 24) | ((((long) b6) & 255) << 16) | ((((long) b7) & 255) << 8) | (((long) b8) & 255);
    }

    public static int hashCode(long j) {
        return (int) (j ^ (j >>> 32));
    }

    private Longs() {
    }

    public static boolean contains(long[] jArr, long j) {
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            if (jArr[i] == j) {
                return true;
            }
        }
        return false;
    }

    public static int indexOf(long[] jArr, long j) {
        return c(jArr, j, 0, jArr.length);
    }

    /* access modifiers changed from: private */
    public static int c(long[] jArr, long j, int i, int i2) {
        while (i < i2) {
            if (jArr[i] == j) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int indexOf(long[] jArr, long[] jArr2) {
        Preconditions.checkNotNull(jArr, "array");
        Preconditions.checkNotNull(jArr2, "target");
        if (jArr2.length == 0) {
            return 0;
        }
        int i = 0;
        while (i < (jArr.length - jArr2.length) + 1) {
            int i2 = 0;
            while (i2 < jArr2.length) {
                if (jArr[i + i2] != jArr2[i2]) {
                    i++;
                } else {
                    i2++;
                }
            }
            return i;
        }
        return -1;
    }

    public static int lastIndexOf(long[] jArr, long j) {
        return d(jArr, j, 0, jArr.length);
    }

    /* access modifiers changed from: private */
    public static int d(long[] jArr, long j, int i, int i2) {
        for (int i3 = i2 - 1; i3 >= i; i3--) {
            if (jArr[i3] == j) {
                return i3;
            }
        }
        return -1;
    }

    public static long min(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        long j = jArr[0];
        for (int i = 1; i < jArr.length; i++) {
            if (jArr[i] < j) {
                j = jArr[i];
            }
        }
        return j;
    }

    public static long max(long... jArr) {
        Preconditions.checkArgument(jArr.length > 0);
        long j = jArr[0];
        for (int i = 1; i < jArr.length; i++) {
            if (jArr[i] > j) {
                j = jArr[i];
            }
        }
        return j;
    }

    public static long[] concat(long[]... jArr) {
        int i = 0;
        for (long[] length : jArr) {
            i += length.length;
        }
        long[] jArr2 = new long[i];
        int i2 = 0;
        for (long[] jArr3 : jArr) {
            System.arraycopy(jArr3, 0, jArr2, i2, jArr3.length);
            i2 += jArr3.length;
        }
        return jArr2;
    }

    public static byte[] toByteArray(long j) {
        byte[] bArr = new byte[8];
        for (int i = 7; i >= 0; i--) {
            bArr[i] = (byte) ((int) (j & 255));
            j >>= 8;
        }
        return bArr;
    }

    public static long fromByteArray(byte[] bArr) {
        Preconditions.checkArgument(bArr.length >= 8, "array too small: %s < %s", bArr.length, 8);
        return fromBytes(bArr[0], bArr[1], bArr[2], bArr[3], bArr[4], bArr[5], bArr[6], bArr[7]);
    }

    private static byte[] a() {
        byte[] bArr = new byte[128];
        Arrays.fill(bArr, -1);
        for (int i = 0; i <= 9; i++) {
            bArr[i + 48] = (byte) i;
        }
        for (int i2 = 0; i2 <= 26; i2++) {
            byte b = (byte) (i2 + 10);
            bArr[i2 + 65] = b;
            bArr[i2 + 97] = b;
        }
        return bArr;
    }

    private static int a(char c) {
        if (c < 128) {
            return a[c];
        }
        return -1;
    }

    @CheckForNull
    @Nullable
    @Beta
    public static Long tryParse(String str) {
        return tryParse(str, 10);
    }

    @CheckForNull
    @Nullable
    @Beta
    public static Long tryParse(String str, int i) {
        String str2 = str;
        int i2 = i;
        if (((String) Preconditions.checkNotNull(str)).isEmpty()) {
            return null;
        }
        if (i2 < 2 || i2 > 36) {
            StringBuilder sb = new StringBuilder();
            sb.append("radix must be between MIN_RADIX and MAX_RADIX but was ");
            sb.append(i2);
            throw new IllegalArgumentException(sb.toString());
        }
        int i3 = 0;
        if (str2.charAt(0) == '-') {
            i3 = 1;
        }
        if (i3 == str.length()) {
            return null;
        }
        int i4 = i3 + 1;
        int a2 = a(str2.charAt(i3));
        if (a2 < 0 || a2 >= i2) {
            return null;
        }
        long j = (long) (-a2);
        long j2 = (long) i2;
        long j3 = Long.MIN_VALUE / j2;
        while (i4 < str.length()) {
            int i5 = i4 + 1;
            int a3 = a(str2.charAt(i4));
            if (a3 < 0 || a3 >= i2 || j < j3) {
                return null;
            }
            long j4 = j * j2;
            long j5 = (long) a3;
            if (j4 < j5 - Long.MIN_VALUE) {
                return null;
            }
            i4 = i5;
            j = j4 - j5;
        }
        if (i3 != 0) {
            return Long.valueOf(j);
        }
        if (j == Long.MIN_VALUE) {
            return null;
        }
        return Long.valueOf(-j);
    }

    @Beta
    public static Converter<String, Long> stringConverter() {
        return LongConverter.a;
    }

    public static long[] ensureCapacity(long[] jArr, int i, int i2) {
        boolean z = false;
        Preconditions.checkArgument(i >= 0, "Invalid minLength: %s", i);
        if (i2 >= 0) {
            z = true;
        }
        Preconditions.checkArgument(z, "Invalid padding: %s", i2);
        return jArr.length < i ? Arrays.copyOf(jArr, i + i2) : jArr;
    }

    public static String join(String str, long... jArr) {
        Preconditions.checkNotNull(str);
        if (jArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(jArr.length * 10);
        sb.append(jArr[0]);
        for (int i = 1; i < jArr.length; i++) {
            sb.append(str);
            sb.append(jArr[i]);
        }
        return sb.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static long[] toArray(Collection<? extends Number> collection) {
        if (collection instanceof LongArrayAsList) {
            return ((LongArrayAsList) collection).a();
        }
        Object[] array = collection.toArray();
        int length = array.length;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = ((Number) Preconditions.checkNotNull(array[i])).longValue();
        }
        return jArr;
    }

    public static List<Long> asList(long... jArr) {
        if (jArr.length == 0) {
            return Collections.emptyList();
        }
        return new LongArrayAsList(jArr);
    }
}
