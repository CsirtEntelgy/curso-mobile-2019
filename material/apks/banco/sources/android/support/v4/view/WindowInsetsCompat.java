package android.support.v4.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.WindowInsets;

public class WindowInsetsCompat {
    private final Object a;

    private WindowInsetsCompat(Object obj) {
        this.a = obj;
    }

    public WindowInsetsCompat(WindowInsetsCompat windowInsetsCompat) {
        WindowInsets windowInsets = null;
        if (VERSION.SDK_INT >= 20) {
            if (windowInsetsCompat != null) {
                windowInsets = new WindowInsets((WindowInsets) windowInsetsCompat.a);
            }
            this.a = windowInsets;
            return;
        }
        this.a = null;
    }

    public int getSystemWindowInsetLeft() {
        if (VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).getSystemWindowInsetLeft();
        }
        return 0;
    }

    public int getSystemWindowInsetTop() {
        if (VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).getSystemWindowInsetTop();
        }
        return 0;
    }

    public int getSystemWindowInsetRight() {
        if (VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).getSystemWindowInsetRight();
        }
        return 0;
    }

    public int getSystemWindowInsetBottom() {
        if (VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).getSystemWindowInsetBottom();
        }
        return 0;
    }

    public boolean hasSystemWindowInsets() {
        if (VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).hasSystemWindowInsets();
        }
        return false;
    }

    public boolean hasInsets() {
        if (VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).hasInsets();
        }
        return false;
    }

    public boolean isConsumed() {
        if (VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.a).isConsumed();
        }
        return false;
    }

    public boolean isRound() {
        if (VERSION.SDK_INT >= 20) {
            return ((WindowInsets) this.a).isRound();
        }
        return false;
    }

    public WindowInsetsCompat consumeSystemWindowInsets() {
        if (VERSION.SDK_INT >= 20) {
            return new WindowInsetsCompat((Object) ((WindowInsets) this.a).consumeSystemWindowInsets());
        }
        return null;
    }

    public WindowInsetsCompat replaceSystemWindowInsets(int i, int i2, int i3, int i4) {
        if (VERSION.SDK_INT >= 20) {
            return new WindowInsetsCompat((Object) ((WindowInsets) this.a).replaceSystemWindowInsets(i, i2, i3, i4));
        }
        return null;
    }

    public WindowInsetsCompat replaceSystemWindowInsets(Rect rect) {
        if (VERSION.SDK_INT >= 21) {
            return new WindowInsetsCompat((Object) ((WindowInsets) this.a).replaceSystemWindowInsets(rect));
        }
        return null;
    }

    public int getStableInsetTop() {
        if (VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.a).getStableInsetTop();
        }
        return 0;
    }

    public int getStableInsetLeft() {
        if (VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.a).getStableInsetLeft();
        }
        return 0;
    }

    public int getStableInsetRight() {
        if (VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.a).getStableInsetRight();
        }
        return 0;
    }

    public int getStableInsetBottom() {
        if (VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.a).getStableInsetBottom();
        }
        return 0;
    }

    public boolean hasStableInsets() {
        if (VERSION.SDK_INT >= 21) {
            return ((WindowInsets) this.a).hasStableInsets();
        }
        return false;
    }

    public WindowInsetsCompat consumeStableInsets() {
        if (VERSION.SDK_INT >= 21) {
            return new WindowInsetsCompat((Object) ((WindowInsets) this.a).consumeStableInsets());
        }
        return null;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WindowInsetsCompat windowInsetsCompat = (WindowInsetsCompat) obj;
        if (this.a != null) {
            z = this.a.equals(windowInsetsCompat.a);
        } else if (windowInsetsCompat.a != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        if (this.a == null) {
            return 0;
        }
        return this.a.hashCode();
    }

    static WindowInsetsCompat a(Object obj) {
        if (obj == null) {
            return null;
        }
        return new WindowInsetsCompat(obj);
    }

    static Object a(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat == null) {
            return null;
        }
        return windowInsetsCompat.a;
    }
}
