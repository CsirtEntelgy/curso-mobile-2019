package ar.com.santander.rio.mbanking.components.popup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class PopupWindows {
    protected Drawable mBackground = null;
    protected Bitmap mBitMap = null;
    protected Context mContext;
    protected View mRootView;
    protected PopupWindow mWindow;
    protected WindowManager mWindowManager;

    /* access modifiers changed from: protected */
    public void onDismiss() {
    }

    /* access modifiers changed from: protected */
    public void onShow() {
    }

    public PopupWindows(Context context) {
        this.mContext = context;
        this.mWindow = new PopupWindow(context);
        this.mWindow.setTouchInterceptor(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() != 4) {
                    return false;
                }
                PopupWindows.this.mWindow.dismiss();
                return true;
            }
        });
        this.mWindowManager = (WindowManager) context.getSystemService("window");
    }

    /* access modifiers changed from: protected */
    public void preShow() {
        if (this.mRootView == null) {
            throw new IllegalStateException("setContentView was not called with a view to display.");
        }
        onShow();
        if (this.mBackground == null) {
            this.mWindow.setBackgroundDrawable(new BitmapDrawable());
        } else {
            this.mWindow.setBackgroundDrawable(this.mBackground);
        }
        this.mWindow.setWidth(-2);
        this.mWindow.setHeight(-2);
        this.mWindow.setTouchable(true);
        this.mWindow.setFocusable(true);
        this.mWindow.setOutsideTouchable(true);
        this.mWindow.setContentView(this.mRootView);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.mBackground = drawable;
    }

    public void setContentView(View view) {
        this.mRootView = view;
        this.mWindow.setContentView(view);
    }

    public void setContentView(int i) {
        setContentView(((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(i, null));
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mWindow.setOnDismissListener(onDismissListener);
    }

    public void dismiss() {
        this.mWindow.dismiss();
    }
}
