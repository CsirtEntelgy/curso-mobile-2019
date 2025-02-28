package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.android.gms.analytics.zzg;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public abstract class zzg<T extends zzg> {
    private static String a(Object obj, int i) {
        if (i > 10) {
            return "ERROR: Recursive toString calls";
        }
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return TextUtils.isEmpty((String) obj) ? "" : obj.toString();
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue() == 0 ? "" : obj.toString();
        }
        if (obj instanceof Long) {
            return ((Long) obj).longValue() == 0 ? "" : obj.toString();
        }
        if (obj instanceof Double) {
            return ((Double) obj).doubleValue() == 0.0d ? "" : obj.toString();
        }
        if (obj instanceof Boolean) {
            return !((Boolean) obj).booleanValue() ? "" : obj.toString();
        }
        if (obj instanceof List) {
            StringBuffer stringBuffer = new StringBuffer();
            if (i > 0) {
                stringBuffer.append("[");
            }
            List list = (List) obj;
            int length = stringBuffer.length();
            for (Object next : list) {
                if (stringBuffer.length() > length) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(a(next, i + 1));
            }
            if (i > 0) {
                stringBuffer.append("]");
            }
            return stringBuffer.toString();
        } else if (!(obj instanceof Map)) {
            return obj.toString();
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            boolean z = false;
            int i2 = 0;
            for (Entry entry : new TreeMap((Map) obj).entrySet()) {
                String a = a(entry.getValue(), i + 1);
                if (!TextUtils.isEmpty(a)) {
                    if (i > 0 && !z) {
                        stringBuffer2.append("{");
                        i2 = stringBuffer2.length();
                        z = true;
                    }
                    if (stringBuffer2.length() > i2) {
                        stringBuffer2.append(", ");
                    }
                    stringBuffer2.append((String) entry.getKey());
                    stringBuffer2.append('=');
                    stringBuffer2.append(a);
                }
            }
            if (z) {
                stringBuffer2.append("}");
            }
            return stringBuffer2.toString();
        }
    }

    public static String zzap(Map map) {
        return a(map, 1);
    }

    public static String zzj(Object obj) {
        return a(obj, 0);
    }

    public abstract void zzb(T t);
}
