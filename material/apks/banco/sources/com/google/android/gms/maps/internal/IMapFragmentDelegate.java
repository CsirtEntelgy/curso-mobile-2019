package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.GoogleMapOptions;

public interface IMapFragmentDelegate extends IInterface {

    public static abstract class zza extends Binder implements IMapFragmentDelegate {

        /* renamed from: com.google.android.gms.maps.internal.IMapFragmentDelegate$zza$zza reason: collision with other inner class name */
        static class C0043zza implements IMapFragmentDelegate {
            private IBinder a;

            C0043zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public IGoogleMapDelegate getMap() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.maps.internal.IGoogleMapDelegate.zza.zzho(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void getMapAsync(zzt zzt) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    obtain.writeStrongBinder(zzt != null ? zzt.asBinder() : null);
                    this.a.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isReady() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    boolean z = false;
                    this.a.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    return z;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onCreate(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd onCreateView(zzd zzd, zzd zzd2, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    IBinder iBinder = null;
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    if (zzd2 != null) {
                        iBinder = zzd2.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onDestroy() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.a.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onDestroyView() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.a.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onEnterAmbient(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onExitAmbient() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.a.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onInflate(zzd zzd, GoogleMapOptions googleMapOptions, Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    if (googleMapOptions != null) {
                        obtain.writeInt(1);
                        googleMapOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onLowMemory() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.a.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onPause() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.a.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onResume() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.a.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onSaveInstanceState(Bundle bundle) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onStart() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.a.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onStop() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IMapFragmentDelegate");
                    this.a.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IMapFragmentDelegate zzhr(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IMapFragmentDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IMapFragmentDelegate)) ? new C0043zza(iBinder) : (IMapFragmentDelegate) queryLocalInterface;
        }

        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v2, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v3, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v4, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v7, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v8, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v10, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v11, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v12, types: [android.os.IBinder] */
        /* JADX WARNING: type inference failed for: r0v13, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v15, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v16, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v18, types: [android.os.Bundle] */
        /* JADX WARNING: type inference failed for: r0v19 */
        /* JADX WARNING: type inference failed for: r0v20 */
        /* JADX WARNING: type inference failed for: r0v21 */
        /* JADX WARNING: type inference failed for: r0v22 */
        /* JADX WARNING: type inference failed for: r0v23 */
        /* JADX WARNING: type inference failed for: r0v24 */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v1
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], android.os.Bundle, android.os.IBinder]
          uses: [android.os.IBinder, android.os.Bundle, ?[int, boolean, OBJECT, ARRAY, byte, short, char]]
          mth insns count: 150
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
        	at java.util.ArrayList.forEach(ArrayList.java:1249)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
        	at jadx.core.ProcessClass.process(ProcessClass.java:35)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 7 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) {
            /*
                r3 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                if (r4 == r0) goto L_0x0170
                r0 = 0
                switch(r4) {
                    case 1: goto L_0x015a;
                    case 2: goto L_0x0127;
                    case 3: goto L_0x010c;
                    case 4: goto L_0x00d6;
                    case 5: goto L_0x00ca;
                    case 6: goto L_0x00be;
                    case 7: goto L_0x00b2;
                    case 8: goto L_0x00a6;
                    case 9: goto L_0x009a;
                    case 10: goto L_0x0072;
                    case 11: goto L_0x0062;
                    case 12: goto L_0x004e;
                    case 13: goto L_0x0033;
                    case 14: goto L_0x0027;
                    case 15: goto L_0x001b;
                    case 16: goto L_0x000f;
                    default: goto L_0x000a;
                }
            L_0x000a:
                boolean r4 = super.onTransact(r4, r5, r6, r7)
                return r4
            L_0x000f:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                r3.onStop()
                r6.writeNoException()
                return r1
            L_0x001b:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                r3.onStart()
                r6.writeNoException()
                return r1
            L_0x0027:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                r3.onExitAmbient()
                r6.writeNoException()
                return r1
            L_0x0033:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0047
                android.os.Parcelable$Creator r4 = android.os.Bundle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0047:
                r3.onEnterAmbient(r0)
                r6.writeNoException()
                return r1
            L_0x004e:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.maps.internal.zzt r4 = com.google.android.gms.maps.internal.zzt.zza.zzii(r4)
                r3.getMapAsync(r4)
                r6.writeNoException()
                return r1
            L_0x0062:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                boolean r4 = r3.isReady()
                r6.writeNoException()
                r6.writeInt(r4)
                return r1
            L_0x0072:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0086
                android.os.Parcelable$Creator r4 = android.os.Bundle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0086:
                r3.onSaveInstanceState(r0)
                r6.writeNoException()
                if (r0 == 0) goto L_0x0095
                r6.writeInt(r1)
                r0.writeToParcel(r6, r1)
                return r1
            L_0x0095:
                r4 = 0
                r6.writeInt(r4)
                return r1
            L_0x009a:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                r3.onLowMemory()
                r6.writeNoException()
                return r1
            L_0x00a6:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                r3.onDestroy()
                r6.writeNoException()
                return r1
            L_0x00b2:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                r3.onDestroyView()
                r6.writeNoException()
                return r1
            L_0x00be:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                r3.onPause()
                r6.writeNoException()
                return r1
            L_0x00ca:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                r3.onResume()
                r6.writeNoException()
                return r1
            L_0x00d6:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                android.os.IBinder r7 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r7 = com.google.android.gms.dynamic.zzd.zza.zzfe(r7)
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x00fa
                android.os.Parcelable$Creator r2 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r2.createFromParcel(r5)
                android.os.Bundle r5 = (android.os.Bundle) r5
                goto L_0x00fb
            L_0x00fa:
                r5 = r0
            L_0x00fb:
                com.google.android.gms.dynamic.zzd r4 = r3.onCreateView(r4, r7, r5)
                r6.writeNoException()
                if (r4 == 0) goto L_0x0108
                android.os.IBinder r0 = r4.asBinder()
            L_0x0108:
                r6.writeStrongBinder(r0)
                return r1
            L_0x010c:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0120
                android.os.Parcelable$Creator r4 = android.os.Bundle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0120:
                r3.onCreate(r0)
                r6.writeNoException()
                return r1
            L_0x0127:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                android.os.IBinder r4 = r5.readStrongBinder()
                com.google.android.gms.dynamic.zzd r4 = com.google.android.gms.dynamic.zzd.zza.zzfe(r4)
                int r7 = r5.readInt()
                if (r7 == 0) goto L_0x0143
                com.google.android.gms.maps.zza r7 = com.google.android.gms.maps.GoogleMapOptions.CREATOR
                java.lang.Object r7 = r7.createFromParcel(r5)
                com.google.android.gms.maps.GoogleMapOptions r7 = (com.google.android.gms.maps.GoogleMapOptions) r7
                goto L_0x0144
            L_0x0143:
                r7 = r0
            L_0x0144:
                int r2 = r5.readInt()
                if (r2 == 0) goto L_0x0153
                android.os.Parcelable$Creator r0 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r0.createFromParcel(r5)
                r0 = r5
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x0153:
                r3.onInflate(r4, r7, r0)
                r6.writeNoException()
                return r1
            L_0x015a:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r5.enforceInterface(r4)
                com.google.android.gms.maps.internal.IGoogleMapDelegate r4 = r3.getMap()
                r6.writeNoException()
                if (r4 == 0) goto L_0x016c
                android.os.IBinder r0 = r4.asBinder()
            L_0x016c:
                r6.writeStrongBinder(r0)
                return r1
            L_0x0170:
                java.lang.String r4 = "com.google.android.gms.maps.internal.IMapFragmentDelegate"
                r6.writeString(r4)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.maps.internal.IMapFragmentDelegate.zza.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    IGoogleMapDelegate getMap();

    void getMapAsync(zzt zzt);

    boolean isReady();

    void onCreate(Bundle bundle);

    zzd onCreateView(zzd zzd, zzd zzd2, Bundle bundle);

    void onDestroy();

    void onDestroyView();

    void onEnterAmbient(Bundle bundle);

    void onExitAmbient();

    void onInflate(zzd zzd, GoogleMapOptions googleMapOptions, Bundle bundle);

    void onLowMemory();

    void onPause();

    void onResume();

    void onSaveInstanceState(Bundle bundle);

    void onStart();

    void onStop();
}
