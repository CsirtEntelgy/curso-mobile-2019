package android.support.v4.os;

import android.os.Parcel;
import android.os.Parcelable.ClassLoaderCreator;
import android.os.Parcelable.Creator;

@Deprecated
public final class ParcelableCompat {

    static class ParcelableCompatCreatorHoneycombMR2<T> implements ClassLoaderCreator<T> {
        private final ParcelableCompatCreatorCallbacks<T> a;

        ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks<T> parcelableCompatCreatorCallbacks) {
            this.a = parcelableCompatCreatorCallbacks;
        }

        public T createFromParcel(Parcel parcel) {
            return this.a.createFromParcel(parcel, null);
        }

        public T createFromParcel(Parcel parcel, ClassLoader classLoader) {
            return this.a.createFromParcel(parcel, classLoader);
        }

        public T[] newArray(int i) {
            return this.a.newArray(i);
        }
    }

    @Deprecated
    public static <T> Creator<T> newCreator(ParcelableCompatCreatorCallbacks<T> parcelableCompatCreatorCallbacks) {
        return new ParcelableCompatCreatorHoneycombMR2(parcelableCompatCreatorCallbacks);
    }

    private ParcelableCompat() {
    }
}
