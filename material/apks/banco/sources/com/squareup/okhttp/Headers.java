package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HttpDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public final class Headers {
    private final String[] a;

    public static final class Builder {
        /* access modifiers changed from: private */
        public final List<String> a = new ArrayList(20);

        /* access modifiers changed from: 0000 */
        public Builder a(String str) {
            int indexOf = str.indexOf(":", 1);
            if (indexOf != -1) {
                return a(str.substring(0, indexOf), str.substring(indexOf + 1));
            }
            if (str.startsWith(":")) {
                return a("", str.substring(1));
            }
            return a("", str);
        }

        public Builder add(String str) {
            int indexOf = str.indexOf(":");
            if (indexOf != -1) {
                return add(str.substring(0, indexOf).trim(), str.substring(indexOf + 1));
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected header: ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString());
        }

        public Builder add(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("name == null");
            } else if (str2 == null) {
                throw new IllegalArgumentException("value == null");
            } else if (str.length() != 0 && str.indexOf(0) == -1 && str2.indexOf(0) == -1) {
                return a(str, str2);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected header: ");
                sb.append(str);
                sb.append(": ");
                sb.append(str2);
                throw new IllegalArgumentException(sb.toString());
            }
        }

        /* access modifiers changed from: 0000 */
        public Builder a(String str, String str2) {
            this.a.add(str);
            this.a.add(str2.trim());
            return this;
        }

        public Builder removeAll(String str) {
            int i = 0;
            while (i < this.a.size()) {
                if (str.equalsIgnoreCase((String) this.a.get(i))) {
                    this.a.remove(i);
                    this.a.remove(i);
                    i -= 2;
                }
                i += 2;
            }
            return this;
        }

        public Builder set(String str, String str2) {
            removeAll(str);
            add(str, str2);
            return this;
        }

        public String get(String str) {
            for (int size = this.a.size() - 2; size >= 0; size -= 2) {
                if (str.equalsIgnoreCase((String) this.a.get(size))) {
                    return (String) this.a.get(size + 1);
                }
            }
            return null;
        }

        public Headers build() {
            return new Headers(this);
        }
    }

    private Headers(Builder builder) {
        this.a = (String[]) builder.a.toArray(new String[builder.a.size()]);
    }

    private Headers(String[] strArr) {
        this.a = strArr;
    }

    public String get(String str) {
        return a(this.a, str);
    }

    public Date getDate(String str) {
        String str2 = get(str);
        if (str2 != null) {
            return HttpDate.parse(str2);
        }
        return null;
    }

    public int size() {
        return this.a.length / 2;
    }

    public String name(int i) {
        int i2 = i * 2;
        if (i2 < 0 || i2 >= this.a.length) {
            return null;
        }
        return this.a[i2];
    }

    public String value(int i) {
        int i2 = (i * 2) + 1;
        if (i2 < 0 || i2 >= this.a.length) {
            return null;
        }
        return this.a[i2];
    }

    public Set<String> names() {
        TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
        int size = size();
        for (int i = 0; i < size; i++) {
            treeSet.add(name(i));
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public List<String> values(String str) {
        int size = size();
        ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            if (str.equalsIgnoreCase(name(i))) {
                if (arrayList == null) {
                    arrayList = new ArrayList(2);
                }
                arrayList.add(value(i));
            }
        }
        if (arrayList != null) {
            return Collections.unmodifiableList(arrayList);
        }
        return Collections.emptyList();
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        Collections.addAll(builder.a, this.a);
        return builder;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = size();
        for (int i = 0; i < size; i++) {
            sb.append(name(i));
            sb.append(": ");
            sb.append(value(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    public Map<String, List<String>> toMultimap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int size = size();
        for (int i = 0; i < size; i++) {
            String name = name(i);
            List list = (List) linkedHashMap.get(name);
            if (list == null) {
                list = new ArrayList(2);
                linkedHashMap.put(name, list);
            }
            list.add(value(i));
        }
        return linkedHashMap;
    }

    private static String a(String[] strArr, String str) {
        for (int length = strArr.length - 2; length >= 0; length -= 2) {
            if (str.equalsIgnoreCase(strArr[length])) {
                return strArr[length + 1];
            }
        }
        return null;
    }

    public static Headers of(String... strArr) {
        if (strArr == null || strArr.length % 2 != 0) {
            throw new IllegalArgumentException("Expected alternating header names and values");
        }
        String[] strArr2 = (String[]) strArr.clone();
        for (int i = 0; i < strArr2.length; i++) {
            if (strArr2[i] == null) {
                throw new IllegalArgumentException("Headers cannot be null");
            }
            strArr2[i] = strArr2[i].trim();
        }
        int i2 = 0;
        while (i2 < strArr2.length) {
            String str = strArr2[i2];
            String str2 = strArr2[i2 + 1];
            if (str.length() != 0 && str.indexOf(0) == -1 && str2.indexOf(0) == -1) {
                i2 += 2;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected header: ");
                sb.append(str);
                sb.append(": ");
                sb.append(str2);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        return new Headers(strArr2);
    }

    public static Headers of(Map<String, String> map) {
        if (map == null) {
            throw new IllegalArgumentException("Expected map with header names and values");
        }
        String[] strArr = new String[(map.size() * 2)];
        int i = 0;
        for (Entry entry : map.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                throw new IllegalArgumentException("Headers cannot be null");
            }
            String trim = ((String) entry.getKey()).trim();
            String trim2 = ((String) entry.getValue()).trim();
            if (trim.length() != 0 && trim.indexOf(0) == -1 && trim2.indexOf(0) == -1) {
                strArr[i] = trim;
                strArr[i + 1] = trim2;
                i += 2;
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected header: ");
                sb.append(trim);
                sb.append(": ");
                sb.append(trim2);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        return new Headers(strArr);
    }
}
