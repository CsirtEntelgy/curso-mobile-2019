package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import java.lang.ref.WeakReference;

class TintResources extends ResourcesWrapper {
    private final WeakReference<Context> a;

    public TintResources(@NonNull Context context, @NonNull Resources resources) {
        super(resources);
        this.a = new WeakReference<>(context);
    }

    public Drawable getDrawable(int i) {
        Drawable drawable = super.getDrawable(i);
        Context context = (Context) this.a.get();
        if (!(drawable == null || context == null)) {
            AppCompatDrawableManager.get();
            AppCompatDrawableManager.a(context, i, drawable);
        }
        return drawable;
    }
}
