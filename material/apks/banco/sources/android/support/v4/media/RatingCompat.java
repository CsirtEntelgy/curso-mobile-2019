package android.support.v4.media;

import android.media.Rating;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.util.Log;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RatingCompat implements Parcelable {
    public static final Creator<RatingCompat> CREATOR = new Creator<RatingCompat>() {
        /* renamed from: a */
        public RatingCompat createFromParcel(Parcel parcel) {
            return new RatingCompat(parcel.readInt(), parcel.readFloat());
        }

        /* renamed from: a */
        public RatingCompat[] newArray(int i) {
            return new RatingCompat[i];
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private final int a;
    private final float b;
    private Object c;

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StarStyle {
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
    }

    RatingCompat(int i, float f) {
        this.a = i;
        this.b = f;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("Rating:style=");
        sb.append(this.a);
        sb.append(" rating=");
        if (this.b < BitmapDescriptorFactory.HUE_RED) {
            str = "unrated";
        } else {
            str = String.valueOf(this.b);
        }
        sb.append(str);
        return sb.toString();
    }

    public int describeContents() {
        return this.a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.a);
        parcel.writeFloat(this.b);
    }

    public static RatingCompat newUnratedRating(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return new RatingCompat(i, -1.0f);
            default:
                return null;
        }
    }

    public static RatingCompat newHeartRating(boolean z) {
        return new RatingCompat(1, z ? 1.0f : BitmapDescriptorFactory.HUE_RED);
    }

    public static RatingCompat newThumbRating(boolean z) {
        return new RatingCompat(2, z ? 1.0f : BitmapDescriptorFactory.HUE_RED);
    }

    public static RatingCompat newStarRating(int i, float f) {
        float f2;
        switch (i) {
            case 3:
                f2 = 3.0f;
                break;
            case 4:
                f2 = 4.0f;
                break;
            case 5:
                f2 = 5.0f;
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid rating style (");
                sb.append(i);
                sb.append(") for a star rating");
                Log.e("Rating", sb.toString());
                return null;
        }
        if (f >= BitmapDescriptorFactory.HUE_RED && f <= f2) {
            return new RatingCompat(i, f);
        }
        Log.e("Rating", "Trying to set out of range star-based rating");
        return null;
    }

    public static RatingCompat newPercentageRating(float f) {
        if (f >= BitmapDescriptorFactory.HUE_RED && f <= 100.0f) {
            return new RatingCompat(6, f);
        }
        Log.e("Rating", "Invalid percentage-based rating value");
        return null;
    }

    public boolean isRated() {
        return this.b >= BitmapDescriptorFactory.HUE_RED;
    }

    public int getRatingStyle() {
        return this.a;
    }

    public boolean hasHeart() {
        boolean z = false;
        if (this.a != 1) {
            return false;
        }
        if (this.b == 1.0f) {
            z = true;
        }
        return z;
    }

    public boolean isThumbUp() {
        boolean z = false;
        if (this.a != 2) {
            return false;
        }
        if (this.b == 1.0f) {
            z = true;
        }
        return z;
    }

    public float getStarRating() {
        switch (this.a) {
            case 3:
            case 4:
            case 5:
                if (isRated()) {
                    return this.b;
                }
                break;
        }
        return -1.0f;
    }

    public float getPercentRating() {
        if (this.a != 6 || !isRated()) {
            return -1.0f;
        }
        return this.b;
    }

    public static RatingCompat fromRating(Object obj) {
        RatingCompat ratingCompat;
        if (obj == null || VERSION.SDK_INT < 19) {
            return null;
        }
        Rating rating = (Rating) obj;
        int ratingStyle = rating.getRatingStyle();
        if (rating.isRated()) {
            switch (ratingStyle) {
                case 1:
                    ratingCompat = newHeartRating(rating.hasHeart());
                    break;
                case 2:
                    ratingCompat = newThumbRating(rating.isThumbUp());
                    break;
                case 3:
                case 4:
                case 5:
                    ratingCompat = newStarRating(ratingStyle, rating.getStarRating());
                    break;
                case 6:
                    ratingCompat = newPercentageRating(rating.getPercentRating());
                    break;
                default:
                    return null;
            }
        } else {
            ratingCompat = newUnratedRating(ratingStyle);
        }
        ratingCompat.c = obj;
        return ratingCompat;
    }

    public Object getRating() {
        if (this.c == null && VERSION.SDK_INT >= 19) {
            if (isRated()) {
                switch (this.a) {
                    case 1:
                        this.c = Rating.newHeartRating(hasHeart());
                        break;
                    case 2:
                        this.c = Rating.newThumbRating(isThumbUp());
                        break;
                    case 3:
                    case 4:
                    case 5:
                        this.c = Rating.newStarRating(this.a, getStarRating());
                        break;
                    case 6:
                        this.c = Rating.newPercentageRating(getPercentRating());
                        break;
                    default:
                        return null;
                }
            } else {
                this.c = Rating.newUnratedRating(this.a);
            }
        }
        return this.c;
    }
}
