package android.support.v4.util;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import cz.msebera.android.httpclient.message.TokenParser;
import java.io.PrintWriter;
import org.joda.time.DateTimeConstants;

@RestrictTo({Scope.LIBRARY_GROUP})
public final class TimeUtils {
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static final int HUNDRED_DAY_FIELD_LEN = 19;
    private static final Object a = new Object();
    private static char[] b = new char[24];

    private static int a(int i, int i2, boolean z, int i3) {
        if (i > 99 || (z && i3 >= 3)) {
            return i2 + 3;
        }
        if (i > 9 || (z && i3 >= 2)) {
            return i2 + 2;
        }
        if (z || i > 0) {
            return i2 + 1;
        }
        return 0;
    }

    private static int a(char[] cArr, int i, char c, int i2, boolean z, int i3) {
        int i4;
        if (!z && i <= 0) {
            return i2;
        }
        if ((!z || i3 < 3) && i <= 99) {
            i4 = i2;
        } else {
            int i5 = i / 100;
            cArr[i2] = (char) (i5 + 48);
            i4 = i2 + 1;
            i -= i5 * 100;
        }
        if ((z && i3 >= 2) || i > 9 || i2 != i4) {
            int i6 = i / 10;
            cArr[i4] = (char) (i6 + 48);
            i4++;
            i -= i6 * 10;
        }
        cArr[i4] = (char) (i + 48);
        int i7 = i4 + 1;
        cArr[i7] = c;
        return i7 + 1;
    }

    private static int a(long j, int i) {
        char c;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        long j2 = j;
        int i7 = i;
        if (b.length < i7) {
            b = new char[i7];
        }
        char[] cArr = b;
        if (j2 == 0) {
            int i8 = i7 - 1;
            while (i8 > 0) {
                cArr[0] = TokenParser.SP;
            }
            cArr[0] = TarjetasConstants.ULT_NUM_AMEX;
            return 1;
        }
        if (j2 > 0) {
            c = '+';
        } else {
            c = '-';
            j2 = -j2;
        }
        int i9 = (int) (j2 % 1000);
        int floor = (int) Math.floor((double) (j2 / 1000));
        if (floor > 86400) {
            i2 = floor / DateTimeConstants.SECONDS_PER_DAY;
            floor -= DateTimeConstants.SECONDS_PER_DAY * i2;
        } else {
            i2 = 0;
        }
        if (floor > 3600) {
            i3 = floor / 3600;
            floor -= i3 * 3600;
        } else {
            i3 = 0;
        }
        if (floor > 60) {
            int i10 = floor / 60;
            i4 = floor - (i10 * 60);
            i5 = i10;
        } else {
            i4 = floor;
            i5 = 0;
        }
        if (i7 != 0) {
            int a2 = a(i2, 1, false, 0);
            int a3 = a2 + a(i3, 1, a2 > 0, 2);
            int a4 = a3 + a(i5, 1, a3 > 0, 2);
            int a5 = a4 + a(i4, 1, a4 > 0, 2);
            i6 = 0;
            for (int a6 = a5 + a(i9, 2, true, a5 > 0 ? 3 : 0) + 1; a6 < i7; a6++) {
                cArr[i6] = TokenParser.SP;
                i6++;
            }
        } else {
            i6 = 0;
        }
        cArr[i6] = c;
        int i11 = i6 + 1;
        boolean z = i7 != 0;
        int i12 = i11;
        int a7 = a(cArr, i2, 'd', i11, false, 0);
        int a8 = a(cArr, i3, 'h', a7, a7 != i12, z ? 2 : 0);
        int a9 = a(cArr, i5, 'm', a8, a8 != i12, z ? 2 : 0);
        int a10 = a(cArr, i4, 's', a9, a9 != i12, z ? 2 : 0);
        int a11 = a(cArr, i9, 'm', a10, true, (!z || a10 == i12) ? 0 : 3);
        cArr[a11] = 's';
        return a11 + 1;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static void formatDuration(long j, StringBuilder sb) {
        synchronized (a) {
            sb.append(b, 0, a(j, 0));
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static void formatDuration(long j, PrintWriter printWriter, int i) {
        synchronized (a) {
            printWriter.print(new String(b, 0, a(j, i)));
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static void formatDuration(long j, PrintWriter printWriter) {
        formatDuration(j, printWriter, 0);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static void formatDuration(long j, long j2, PrintWriter printWriter) {
        if (j == 0) {
            printWriter.print("--");
        } else {
            formatDuration(j - j2, printWriter, 0);
        }
    }

    private TimeUtils() {
    }
}
