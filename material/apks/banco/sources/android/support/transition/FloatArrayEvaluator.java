package android.support.transition;

import android.animation.TypeEvaluator;

class FloatArrayEvaluator implements TypeEvaluator<float[]> {
    private float[] a;

    FloatArrayEvaluator(float[] fArr) {
        this.a = fArr;
    }

    /* renamed from: a */
    public float[] evaluate(float f, float[] fArr, float[] fArr2) {
        float[] fArr3 = this.a;
        if (fArr3 == null) {
            fArr3 = new float[fArr.length];
        }
        for (int i = 0; i < fArr3.length; i++) {
            float f2 = fArr[i];
            fArr3[i] = f2 + ((fArr2[i] - f2) * f);
        }
        return fArr3;
    }
}
