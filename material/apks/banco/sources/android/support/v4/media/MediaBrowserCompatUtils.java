package android.support.v4.media;

import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

@RestrictTo({Scope.LIBRARY_GROUP})
public class MediaBrowserCompatUtils {
    public static boolean areSameOptions(Bundle bundle, Bundle bundle2) {
        boolean z = true;
        if (bundle == bundle2) {
            return true;
        }
        if (bundle == null) {
            if (!(bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == -1 && bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == -1)) {
                z = false;
            }
            return z;
        } else if (bundle2 == null) {
            if (!(bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == -1 && bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == -1)) {
                z = false;
            }
            return z;
        } else {
            if (!(bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) && bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1))) {
                z = false;
            }
            return z;
        }
    }

    public static boolean hasDuplicatedItems(Bundle bundle, Bundle bundle2) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7 = bundle == null ? -1 : bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        if (bundle2 == null) {
            i = -1;
        } else {
            i = bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        }
        if (bundle == null) {
            i2 = -1;
        } else {
            i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        }
        if (bundle2 == null) {
            i3 = -1;
        } else {
            i3 = bundle2.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        }
        int i8 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
        if (i7 == -1 || i2 == -1) {
            i4 = SubsamplingScaleImageView.TILE_SIZE_AUTO;
            i5 = 0;
        } else {
            i5 = i7 * i2;
            i4 = (i2 + i5) - 1;
        }
        if (i == -1 || i3 == -1) {
            i6 = 0;
        } else {
            i6 = i3 * i;
            i8 = (i3 + i6) - 1;
        }
        if (i5 > i6 || i6 > i4) {
            return i5 <= i8 && i8 <= i4;
        }
        return true;
    }
}
