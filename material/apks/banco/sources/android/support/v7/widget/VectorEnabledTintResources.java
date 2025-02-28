package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v7.app.AppCompatDelegate;
import java.lang.ref.WeakReference;

@RestrictTo({Scope.LIBRARY_GROUP})
public class VectorEnabledTintResources extends Resources {
    public static final int MAX_SDK_WHERE_REQUIRED = 20;
    private final WeakReference<Context> a;

    public static boolean shouldBeUsed() {
        return AppCompatDelegate.isCompatVectorFromResourcesEnabled() && VERSION.SDK_INT <= 20;
    }

    public VectorEnabledTintResources(@NonNull Context context, @NonNull Resources resources) {
        super(resources.getAssets(), resources.getDisplayMetrics(), resources.getConfiguration());
        this.a = new WeakReference<>(context);
    }

    public Drawable getDrawable(int i) {
        Context context = (Context) this.a.get();
        if (context != null) {
            return AppCompatDrawableManager.get().a(context, this, i);
        }
        return super.getDrawable(i);
    }

    /* access modifiers changed from: 0000 */
    public final Drawable a(int i) {
        return super.getDrawable(i);
    }
}
