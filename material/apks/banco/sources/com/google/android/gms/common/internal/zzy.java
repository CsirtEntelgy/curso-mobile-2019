package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.google.android.gms.dynamic.zzd;

public interface zzy extends IInterface {

    public static abstract class zza extends Binder implements zzy {

        /* renamed from: com.google.android.gms.common.internal.zzy$zza$zza reason: collision with other inner class name */
        static class C0013zza implements zzy {
            private IBinder a;

            C0013zza(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public zzd zza(zzd zzd, int i, int i2) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.a.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zza(zzd zzd, SignInButtonConfig signInButtonConfig) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.common.internal.ISignInButtonCreator");
                    obtain.writeStrongBinder(zzd != null ? zzd.asBinder() : null);
                    if (signInButtonConfig != null) {
                        obtain.writeInt(1);
                        signInButtonConfig.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return com.google.android.gms.dynamic.zzd.zza.zzfe(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzy zzdy(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzy)) ? new C0013zza(iBinder) : (zzy) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i != 1598968902) {
                IBinder iBinder = null;
                switch (i) {
                    case 1:
                        parcel.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
                        zzd zza = zza(com.google.android.gms.dynamic.zzd.zza.zzfe(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt());
                        parcel2.writeNoException();
                        if (zza != null) {
                            iBinder = zza.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    case 2:
                        parcel.enforceInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
                        zzd zza2 = zza(com.google.android.gms.dynamic.zzd.zza.zzfe(parcel.readStrongBinder()), parcel.readInt() != 0 ? (SignInButtonConfig) SignInButtonConfig.CREATOR.createFromParcel(parcel) : null);
                        parcel2.writeNoException();
                        if (zza2 != null) {
                            iBinder = zza2.asBinder();
                        }
                        parcel2.writeStrongBinder(iBinder);
                        return true;
                    default:
                        return super.onTransact(i, parcel, parcel2, i2);
                }
            } else {
                parcel2.writeString("com.google.android.gms.common.internal.ISignInButtonCreator");
                return true;
            }
        }
    }

    zzd zza(zzd zzd, int i, int i2);

    zzd zza(zzd zzd, SignInButtonConfig signInButtonConfig);
}
