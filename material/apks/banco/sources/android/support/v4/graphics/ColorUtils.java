package android.support.v4.graphics;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public final class ColorUtils {
    private static final ThreadLocal<double[]> a = new ThreadLocal<>();

    private static int a(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    private static float b(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    private ColorUtils() {
    }

    public static int compositeColors(@ColorInt int i, @ColorInt int i2) {
        int alpha = Color.alpha(i2);
        int alpha2 = Color.alpha(i);
        int a2 = a(alpha2, alpha);
        return Color.argb(a2, a(Color.red(i), alpha2, Color.red(i2), alpha, a2), a(Color.green(i), alpha2, Color.green(i2), alpha, a2), a(Color.blue(i), alpha2, Color.blue(i2), alpha, a2));
    }

    private static int a(int i, int i2) {
        return 255 - (((255 - i2) * (255 - i)) / 255);
    }

    private static int a(int i, int i2, int i3, int i4, int i5) {
        if (i5 == 0) {
            return 0;
        }
        return (((i * 255) * i2) + ((i3 * i4) * (255 - i2))) / (i5 * 255);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public static double calculateLuminance(@ColorInt int i) {
        double[] a2 = a();
        colorToXYZ(i, a2);
        return a2[1] / 100.0d;
    }

    public static double calculateContrast(@ColorInt int i, @ColorInt int i2) {
        if (Color.alpha(i2) != 255) {
            StringBuilder sb = new StringBuilder();
            sb.append("background can not be translucent: #");
            sb.append(Integer.toHexString(i2));
            throw new IllegalArgumentException(sb.toString());
        }
        if (Color.alpha(i) < 255) {
            i = compositeColors(i, i2);
        }
        double calculateLuminance = calculateLuminance(i) + 0.05d;
        double calculateLuminance2 = calculateLuminance(i2) + 0.05d;
        return Math.max(calculateLuminance, calculateLuminance2) / Math.min(calculateLuminance, calculateLuminance2);
    }

    public static int calculateMinimumAlpha(@ColorInt int i, @ColorInt int i2, float f) {
        int i3 = 255;
        if (Color.alpha(i2) != 255) {
            StringBuilder sb = new StringBuilder();
            sb.append("background can not be translucent: #");
            sb.append(Integer.toHexString(i2));
            throw new IllegalArgumentException(sb.toString());
        }
        double d = (double) f;
        if (calculateContrast(setAlphaComponent(i, 255), i2) < d) {
            return -1;
        }
        int i4 = 0;
        for (int i5 = 0; i5 <= 10 && i3 - i4 > 1; i5++) {
            int i6 = (i4 + i3) / 2;
            if (calculateContrast(setAlphaComponent(i, i6), i2) < d) {
                i4 = i6;
            } else {
                i3 = i6;
            }
        }
        return i3;
    }

    public static void RGBToHSL(@IntRange(from = 0, to = 255) int i, @IntRange(from = 0, to = 255) int i2, @IntRange(from = 0, to = 255) int i3, @NonNull float[] fArr) {
        float f;
        float f2;
        float f3 = ((float) i) / 255.0f;
        float f4 = ((float) i2) / 255.0f;
        float f5 = ((float) i3) / 255.0f;
        float max = Math.max(f3, Math.max(f4, f5));
        float min = Math.min(f3, Math.min(f4, f5));
        float f6 = max - min;
        float f7 = (max + min) / 2.0f;
        if (max == min) {
            f2 = BitmapDescriptorFactory.HUE_RED;
            f = BitmapDescriptorFactory.HUE_RED;
        } else {
            f2 = max == f3 ? ((f4 - f5) / f6) % 6.0f : max == f4 ? ((f5 - f3) / f6) + 2.0f : ((f3 - f4) / f6) + 4.0f;
            f = f6 / (1.0f - Math.abs((2.0f * f7) - 1.0f));
        }
        float f8 = (f2 * 60.0f) % 360.0f;
        if (f8 < BitmapDescriptorFactory.HUE_RED) {
            f8 += 360.0f;
        }
        fArr[0] = b(f8, BitmapDescriptorFactory.HUE_RED, 360.0f);
        fArr[1] = b(f, BitmapDescriptorFactory.HUE_RED, 1.0f);
        fArr[2] = b(f7, BitmapDescriptorFactory.HUE_RED, 1.0f);
    }

    public static void colorToHSL(@ColorInt int i, @NonNull float[] fArr) {
        RGBToHSL(Color.red(i), Color.green(i), Color.blue(i), fArr);
    }

    @ColorInt
    public static int HSLToColor(@NonNull float[] fArr) {
        int i;
        int i2;
        int i3;
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[2];
        float abs = (1.0f - Math.abs((f3 * 2.0f) - 1.0f)) * f2;
        float f4 = f3 - (0.5f * abs);
        float abs2 = (1.0f - Math.abs(((f / 60.0f) % 2.0f) - 1.0f)) * abs;
        switch (((int) f) / 60) {
            case 0:
                i3 = Math.round((abs + f4) * 255.0f);
                i2 = Math.round((abs2 + f4) * 255.0f);
                i = Math.round(f4 * 255.0f);
                break;
            case 1:
                i3 = Math.round((abs2 + f4) * 255.0f);
                i2 = Math.round((abs + f4) * 255.0f);
                i = Math.round(f4 * 255.0f);
                break;
            case 2:
                i3 = Math.round(f4 * 255.0f);
                i2 = Math.round((abs + f4) * 255.0f);
                i = Math.round((abs2 + f4) * 255.0f);
                break;
            case 3:
                i3 = Math.round(f4 * 255.0f);
                i2 = Math.round((abs2 + f4) * 255.0f);
                i = Math.round((abs + f4) * 255.0f);
                break;
            case 4:
                i3 = Math.round((abs2 + f4) * 255.0f);
                i2 = Math.round(f4 * 255.0f);
                i = Math.round((abs + f4) * 255.0f);
                break;
            case 5:
            case 6:
                i3 = Math.round((abs + f4) * 255.0f);
                i2 = Math.round(f4 * 255.0f);
                i = Math.round((abs2 + f4) * 255.0f);
                break;
            default:
                i = 0;
                i3 = 0;
                i2 = 0;
                break;
        }
        return Color.rgb(a(i3, 0, 255), a(i2, 0, 255), a(i, 0, 255));
    }

    @ColorInt
    public static int setAlphaComponent(@ColorInt int i, @IntRange(from = 0, to = 255) int i2) {
        if (i2 >= 0 && i2 <= 255) {
            return (i & ViewCompat.MEASURED_SIZE_MASK) | (i2 << 24);
        }
        throw new IllegalArgumentException("alpha must be between 0 and 255.");
    }

    public static void colorToLAB(@ColorInt int i, @NonNull double[] dArr) {
        RGBToLAB(Color.red(i), Color.green(i), Color.blue(i), dArr);
    }

    public static void RGBToLAB(@IntRange(from = 0, to = 255) int i, @IntRange(from = 0, to = 255) int i2, @IntRange(from = 0, to = 255) int i3, @NonNull double[] dArr) {
        RGBToXYZ(i, i2, i3, dArr);
        XYZToLAB(dArr[0], dArr[1], dArr[2], dArr);
    }

    public static void colorToXYZ(@ColorInt int i, @NonNull double[] dArr) {
        RGBToXYZ(Color.red(i), Color.green(i), Color.blue(i), dArr);
    }

    public static void RGBToXYZ(@IntRange(from = 0, to = 255) int i, @IntRange(from = 0, to = 255) int i2, @IntRange(from = 0, to = 255) int i3, @NonNull double[] dArr) {
        double pow;
        double pow2;
        double d;
        double[] dArr2 = dArr;
        if (dArr2.length != 3) {
            throw new IllegalArgumentException("outXyz must have a length of 3.");
        }
        double d2 = ((double) i) / 255.0d;
        if (d2 < 0.04045d) {
            pow = d2 / 12.92d;
        } else {
            pow = Math.pow((d2 + 0.055d) / 1.055d, 2.4d);
        }
        double d3 = pow;
        double d4 = ((double) i2) / 255.0d;
        if (d4 < 0.04045d) {
            pow2 = d4 / 12.92d;
        } else {
            pow2 = Math.pow((d4 + 0.055d) / 1.055d, 2.4d);
        }
        double d5 = pow2;
        double d6 = ((double) i3) / 255.0d;
        if (d6 < 0.04045d) {
            d = d6 / 12.92d;
        } else {
            d = Math.pow((d6 + 0.055d) / 1.055d, 2.4d);
        }
        dArr2[0] = ((0.4124d * d3) + (0.3576d * d5) + (0.1805d * d)) * 100.0d;
        dArr2[1] = ((0.2126d * d3) + (0.7152d * d5) + (0.0722d * d)) * 100.0d;
        dArr2[2] = ((d3 * 0.0193d) + (d5 * 0.1192d) + (d * 0.9505d)) * 100.0d;
    }

    public static void XYZToLAB(@FloatRange(from = 0.0d, to = 95.047d) double d, @FloatRange(from = 0.0d, to = 100.0d) double d2, @FloatRange(from = 0.0d, to = 108.883d) double d3, @NonNull double[] dArr) {
        if (dArr.length != 3) {
            throw new IllegalArgumentException("outLab must have a length of 3.");
        }
        double a2 = a(d / 95.047d);
        double a3 = a(d2 / 100.0d);
        double a4 = a(d3 / 108.883d);
        dArr[0] = Math.max(0.0d, (116.0d * a3) - 16.0d);
        dArr[1] = (a2 - a3) * 500.0d;
        dArr[2] = (a3 - a4) * 200.0d;
    }

    public static void LABToXYZ(@FloatRange(from = 0.0d, to = 100.0d) double d, @FloatRange(from = -128.0d, to = 127.0d) double d2, @FloatRange(from = -128.0d, to = 127.0d) double d3, @NonNull double[] dArr) {
        double d4 = (d + 16.0d) / 116.0d;
        double d5 = (d2 / 500.0d) + d4;
        double d6 = d4 - (d3 / 200.0d);
        double pow = Math.pow(d5, 3.0d);
        if (pow <= 0.008856d) {
            pow = ((d5 * 116.0d) - 16.0d) / 903.3d;
        }
        double pow2 = d > 7.9996247999999985d ? Math.pow(d4, 3.0d) : d / 903.3d;
        double pow3 = Math.pow(d6, 3.0d);
        if (pow3 <= 0.008856d) {
            pow3 = ((d6 * 116.0d) - 16.0d) / 903.3d;
        }
        dArr[0] = pow * 95.047d;
        dArr[1] = pow2 * 100.0d;
        dArr[2] = pow3 * 108.883d;
    }

    @ColorInt
    public static int XYZToColor(@FloatRange(from = 0.0d, to = 95.047d) double d, @FloatRange(from = 0.0d, to = 100.0d) double d2, @FloatRange(from = 0.0d, to = 108.883d) double d3) {
        double d4 = (((3.2406d * d) + (-1.5372d * d2)) + (-0.4986d * d3)) / 100.0d;
        double d5 = (((-0.9689d * d) + (1.8758d * d2)) + (0.0415d * d3)) / 100.0d;
        double d6 = (((d * 0.0557d) + (d2 * -0.204d)) + (1.057d * d3)) / 100.0d;
        return Color.rgb(a((int) Math.round((d4 > 0.0031308d ? (Math.pow(d4, 0.4166666666666667d) * 1.055d) - 0.055d : d4 * 12.92d) * 255.0d), 0, 255), a((int) Math.round((d5 > 0.0031308d ? (Math.pow(d5, 0.4166666666666667d) * 1.055d) - 0.055d : d5 * 12.92d) * 255.0d), 0, 255), a((int) Math.round((d6 > 0.0031308d ? (Math.pow(d6, 0.4166666666666667d) * 1.055d) - 0.055d : d6 * 12.92d) * 255.0d), 0, 255));
    }

    @ColorInt
    public static int LABToColor(@FloatRange(from = 0.0d, to = 100.0d) double d, @FloatRange(from = -128.0d, to = 127.0d) double d2, @FloatRange(from = -128.0d, to = 127.0d) double d3) {
        double[] a2 = a();
        LABToXYZ(d, d2, d3, a2);
        return XYZToColor(a2[0], a2[1], a2[2]);
    }

    public static double distanceEuclidean(@NonNull double[] dArr, @NonNull double[] dArr2) {
        return Math.sqrt(Math.pow(dArr[0] - dArr2[0], 2.0d) + Math.pow(dArr[1] - dArr2[1], 2.0d) + Math.pow(dArr[2] - dArr2[2], 2.0d));
    }

    private static double a(double d) {
        return d > 0.008856d ? Math.pow(d, 0.3333333333333333d) : ((d * 903.3d) + 16.0d) / 116.0d;
    }

    @ColorInt
    public static int blendARGB(@ColorInt int i, @ColorInt int i2, @FloatRange(from = 0.0d, to = 1.0d) float f) {
        float f2 = 1.0f - f;
        return Color.argb((int) ((((float) Color.alpha(i)) * f2) + (((float) Color.alpha(i2)) * f)), (int) ((((float) Color.red(i)) * f2) + (((float) Color.red(i2)) * f)), (int) ((((float) Color.green(i)) * f2) + (((float) Color.green(i2)) * f)), (int) ((((float) Color.blue(i)) * f2) + (((float) Color.blue(i2)) * f)));
    }

    public static void blendHSL(@NonNull float[] fArr, @NonNull float[] fArr2, @FloatRange(from = 0.0d, to = 1.0d) float f, @NonNull float[] fArr3) {
        if (fArr3.length != 3) {
            throw new IllegalArgumentException("result must have a length of 3.");
        }
        float f2 = 1.0f - f;
        fArr3[0] = a(fArr[0], fArr2[0], f);
        fArr3[1] = (fArr[1] * f2) + (fArr2[1] * f);
        fArr3[2] = (fArr[2] * f2) + (fArr2[2] * f);
    }

    public static void blendLAB(@NonNull double[] dArr, @NonNull double[] dArr2, @FloatRange(from = 0.0d, to = 1.0d) double d, @NonNull double[] dArr3) {
        if (dArr3.length != 3) {
            throw new IllegalArgumentException("outResult must have a length of 3.");
        }
        double d2 = 1.0d - d;
        dArr3[0] = (dArr[0] * d2) + (dArr2[0] * d);
        dArr3[1] = (dArr[1] * d2) + (dArr2[1] * d);
        dArr3[2] = (dArr[2] * d2) + (dArr2[2] * d);
    }

    @VisibleForTesting
    static float a(float f, float f2, float f3) {
        if (Math.abs(f2 - f) > 180.0f) {
            if (f2 > f) {
                f += 360.0f;
            } else {
                f2 += 360.0f;
            }
        }
        return (f + ((f2 - f) * f3)) % 360.0f;
    }

    private static double[] a() {
        double[] dArr = (double[]) a.get();
        if (dArr != null) {
            return dArr;
        }
        double[] dArr2 = new double[3];
        a.set(dArr2);
        return dArr2;
    }
}
