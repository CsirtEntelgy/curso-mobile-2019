package com.google.android.gms.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class zzapj {
    public static zzapj bl() {
        try {
            Class cls = Class.forName("sun.misc.Unsafe");
            Field declaredField = cls.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            final Object obj = declaredField.get(null);
            final Method method = cls.getMethod("allocateInstance", new Class[]{Class.class});
            return new zzapj() {
                public <T> T zzf(Class<T> cls) {
                    return method.invoke(obj, new Object[]{cls});
                }
            };
        } catch (Exception unused) {
            try {
                Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                declaredMethod.setAccessible(true);
                final int intValue = ((Integer) declaredMethod.invoke(null, new Object[]{Object.class})).intValue();
                final Method declaredMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                declaredMethod2.setAccessible(true);
                return new zzapj() {
                    public <T> T zzf(Class<T> cls) {
                        return declaredMethod2.invoke(null, new Object[]{cls, Integer.valueOf(intValue)});
                    }
                };
            } catch (Exception unused2) {
                try {
                    final Method declaredMethod3 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                    declaredMethod3.setAccessible(true);
                    return new zzapj() {
                        public <T> T zzf(Class<T> cls) {
                            return declaredMethod3.invoke(null, new Object[]{cls, Object.class});
                        }
                    };
                } catch (Exception unused3) {
                    return new zzapj() {
                        public <T> T zzf(Class<T> cls) {
                            String valueOf = String.valueOf(cls);
                            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 16);
                            sb.append("Cannot allocate ");
                            sb.append(valueOf);
                            throw new UnsupportedOperationException(sb.toString());
                        }
                    };
                }
            }
        }
    }

    public abstract <T> T zzf(Class<T> cls);
}
