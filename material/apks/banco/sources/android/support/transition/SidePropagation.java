package android.support.transition;

import android.graphics.Rect;
import android.view.ViewGroup;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class SidePropagation extends VisibilityPropagation {
    private float a = 3.0f;
    private int b = 80;

    public void setSide(int i) {
        this.b = i;
    }

    public void setPropagationSpeed(float f) {
        if (f == BitmapDescriptorFactory.HUE_RED) {
            throw new IllegalArgumentException("propagationSpeed may not be 0");
        }
        this.a = f;
    }

    public long getStartDelay(ViewGroup viewGroup, Transition transition, TransitionValues transitionValues, TransitionValues transitionValues2) {
        int i;
        int i2;
        int i3;
        TransitionValues transitionValues3 = transitionValues;
        if (transitionValues3 == null && transitionValues2 == null) {
            return 0;
        }
        Rect epicenter = transition.getEpicenter();
        if (transitionValues2 == null || getViewVisibility(transitionValues3) == 0) {
            i = -1;
        } else {
            transitionValues3 = transitionValues2;
            i = 1;
        }
        int viewX = getViewX(transitionValues3);
        int viewY = getViewY(transitionValues3);
        int[] iArr = new int[2];
        ViewGroup viewGroup2 = viewGroup;
        viewGroup2.getLocationOnScreen(iArr);
        int round = iArr[0] + Math.round(viewGroup.getTranslationX());
        int round2 = iArr[1] + Math.round(viewGroup.getTranslationY());
        int width = round + viewGroup.getWidth();
        int height = round2 + viewGroup.getHeight();
        if (epicenter != null) {
            i3 = epicenter.centerX();
            i2 = epicenter.centerY();
        } else {
            i3 = (round + width) / 2;
            i2 = (round2 + height) / 2;
        }
        float a2 = ((float) a(viewGroup2, viewX, viewY, i3, i2, round, round2, width, height)) / ((float) a(viewGroup));
        long duration = transition.getDuration();
        if (duration < 0) {
            duration = 300;
        }
        return (long) Math.round((((float) (duration * ((long) i))) / this.a) * a2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0028, code lost:
        if (r4 != false) goto L_0x0017;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0013, code lost:
        if (r4 != false) goto L_0x0015;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0017, code lost:
        r7 = 3;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0055  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int a(android.view.View r7, int r8, int r9, int r10, int r11, int r12, int r13, int r14, int r15) {
        /*
            r6 = this;
            int r0 = r6.b
            r1 = 3
            r2 = 5
            r3 = 0
            r4 = 1
            r5 = 8388611(0x800003, float:1.1754948E-38)
            if (r0 != r5) goto L_0x0019
            int r7 = android.support.v4.view.ViewCompat.getLayoutDirection(r7)
            if (r7 != r4) goto L_0x0012
            goto L_0x0013
        L_0x0012:
            r4 = 0
        L_0x0013:
            if (r4 == 0) goto L_0x0017
        L_0x0015:
            r7 = 5
            goto L_0x002d
        L_0x0017:
            r7 = 3
            goto L_0x002d
        L_0x0019:
            int r0 = r6.b
            r5 = 8388613(0x800005, float:1.175495E-38)
            if (r0 != r5) goto L_0x002b
            int r7 = android.support.v4.view.ViewCompat.getLayoutDirection(r7)
            if (r7 != r4) goto L_0x0027
            goto L_0x0028
        L_0x0027:
            r4 = 0
        L_0x0028:
            if (r4 == 0) goto L_0x0015
            goto L_0x0017
        L_0x002b:
            int r7 = r6.b
        L_0x002d:
            if (r7 == r1) goto L_0x0055
            if (r7 == r2) goto L_0x004c
            r11 = 48
            if (r7 == r11) goto L_0x0043
            r11 = 80
            if (r7 == r11) goto L_0x003a
            goto L_0x005d
        L_0x003a:
            int r9 = r9 - r13
            int r10 = r10 - r8
            int r7 = java.lang.Math.abs(r10)
            int r3 = r9 + r7
            goto L_0x005d
        L_0x0043:
            int r15 = r15 - r9
            int r10 = r10 - r8
            int r7 = java.lang.Math.abs(r10)
            int r3 = r15 + r7
            goto L_0x005d
        L_0x004c:
            int r8 = r8 - r12
            int r11 = r11 - r9
            int r7 = java.lang.Math.abs(r11)
            int r3 = r8 + r7
            goto L_0x005d
        L_0x0055:
            int r14 = r14 - r8
            int r11 = r11 - r9
            int r7 = java.lang.Math.abs(r11)
            int r3 = r14 + r7
        L_0x005d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.SidePropagation.a(android.view.View, int, int, int, int, int, int, int, int):int");
    }

    private int a(ViewGroup viewGroup) {
        int i = this.b;
        if (i == 3 || i == 5 || i == 8388611 || i == 8388613) {
            return viewGroup.getWidth();
        }
        return viewGroup.getHeight();
    }
}
