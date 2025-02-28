package android.support.v7.app;

import android.app.UiModeManager;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Window;

@RequiresApi(23)
class AppCompatDelegateImplV23 extends AppCompatDelegateImplV14 {
    private final UiModeManager t;

    class AppCompatWindowCallbackV23 extends AppCompatWindowCallbackV14 {
        public ActionMode onWindowStartingActionMode(Callback callback) {
            return null;
        }

        AppCompatWindowCallbackV23(Window.Callback callback) {
            super(callback);
        }

        public ActionMode onWindowStartingActionMode(Callback callback, int i) {
            if (!AppCompatDelegateImplV23.this.isHandleNativeActionModesEnabled() || i != 0) {
                return super.onWindowStartingActionMode(callback, i);
            }
            return a(callback);
        }
    }

    AppCompatDelegateImplV23(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
        this.t = (UiModeManager) context.getSystemService("uimode");
    }

    /* access modifiers changed from: 0000 */
    public Window.Callback a(Window.Callback callback) {
        return new AppCompatWindowCallbackV23(callback);
    }

    /* access modifiers changed from: 0000 */
    public int a(int i) {
        if (i == 0 && this.t.getNightMode() == 0) {
            return -1;
        }
        return super.a(i);
    }
}
