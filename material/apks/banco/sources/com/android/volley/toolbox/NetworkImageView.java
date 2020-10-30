package com.android.volley.toolbox;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.volley.toolbox.ImageLoader.ImageContainer;

public class NetworkImageView extends ImageView {
    private String a;
    /* access modifiers changed from: private */
    public int b;
    /* access modifiers changed from: private */
    public int c;
    private ImageLoader d;
    private ImageContainer e;

    public NetworkImageView(Context context) {
        this(context, null);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NetworkImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setImageUrl(String str, ImageLoader imageLoader) {
        this.a = str;
        this.d = imageLoader;
        a(false);
    }

    public String getImageURL() {
        return this.a;
    }

    public void setDefaultImageResId(int i) {
        this.b = i;
    }

    public void setErrorImageResId(int i) {
        this.c = i;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(final boolean r9) {
        /*
            r8 = this;
            int r0 = r8.getWidth()
            int r1 = r8.getHeight()
            android.widget.ImageView$ScaleType r7 = r8.getScaleType()
            android.view.ViewGroup$LayoutParams r2 = r8.getLayoutParams()
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x002a
            android.view.ViewGroup$LayoutParams r2 = r8.getLayoutParams()
            int r2 = r2.width
            r5 = -2
            if (r2 != r5) goto L_0x001f
            r2 = 1
            goto L_0x0020
        L_0x001f:
            r2 = 0
        L_0x0020:
            android.view.ViewGroup$LayoutParams r6 = r8.getLayoutParams()
            int r6 = r6.height
            if (r6 != r5) goto L_0x002b
            r5 = 1
            goto L_0x002c
        L_0x002a:
            r2 = 0
        L_0x002b:
            r5 = 0
        L_0x002c:
            if (r2 == 0) goto L_0x0031
            if (r5 == 0) goto L_0x0031
            goto L_0x0032
        L_0x0031:
            r3 = 0
        L_0x0032:
            if (r0 != 0) goto L_0x0039
            if (r1 != 0) goto L_0x0039
            if (r3 != 0) goto L_0x0039
            return
        L_0x0039:
            java.lang.String r3 = r8.a
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 == 0) goto L_0x0051
            com.android.volley.toolbox.ImageLoader$ImageContainer r9 = r8.e
            if (r9 == 0) goto L_0x004d
            com.android.volley.toolbox.ImageLoader$ImageContainer r9 = r8.e
            r9.cancelRequest()
            r9 = 0
            r8.e = r9
        L_0x004d:
            r8.a()
            return
        L_0x0051:
            com.android.volley.toolbox.ImageLoader$ImageContainer r3 = r8.e
            if (r3 == 0) goto L_0x0074
            com.android.volley.toolbox.ImageLoader$ImageContainer r3 = r8.e
            java.lang.String r3 = r3.getRequestUrl()
            if (r3 == 0) goto L_0x0074
            com.android.volley.toolbox.ImageLoader$ImageContainer r3 = r8.e
            java.lang.String r3 = r3.getRequestUrl()
            java.lang.String r6 = r8.a
            boolean r3 = r3.equals(r6)
            if (r3 == 0) goto L_0x006c
            return
        L_0x006c:
            com.android.volley.toolbox.ImageLoader$ImageContainer r3 = r8.e
            r3.cancelRequest()
            r8.a()
        L_0x0074:
            if (r2 == 0) goto L_0x0077
            r0 = 0
        L_0x0077:
            if (r5 == 0) goto L_0x007b
            r6 = 0
            goto L_0x007c
        L_0x007b:
            r6 = r1
        L_0x007c:
            com.android.volley.toolbox.ImageLoader r2 = r8.d
            java.lang.String r3 = r8.a
            com.android.volley.toolbox.NetworkImageView$1 r4 = new com.android.volley.toolbox.NetworkImageView$1
            r4.<init>(r9)
            r5 = r0
            com.android.volley.toolbox.ImageLoader$ImageContainer r9 = r2.get(r3, r4, r5, r6, r7)
            r8.e = r9
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.toolbox.NetworkImageView.a(boolean):void");
    }

    private void a() {
        if (this.b != 0) {
            setImageResource(this.b);
        } else {
            setImageBitmap(null);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        a(true);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.e != null) {
            this.e.cancelRequest();
            setImageBitmap(null);
            this.e = null;
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }
}
