package android.support.v4.text.util;

import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.util.PatternsCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.text.util.Linkify.MatchFilter;
import android.text.util.Linkify.TransformFilter;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LinkifyCompat {
    private static final String[] a = new String[0];
    private static final Comparator<LinkSpec> b = new Comparator<LinkSpec>() {
        /* renamed from: a */
        public int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
            if (linkSpec.c < linkSpec2.c) {
                return -1;
            }
            if (linkSpec.c > linkSpec2.c || linkSpec.d < linkSpec2.d) {
                return 1;
            }
            if (linkSpec.d > linkSpec2.d) {
                return -1;
            }
            return 0;
        }
    };

    static class LinkSpec {
        URLSpan a;
        String b;
        int c;
        int d;

        LinkSpec() {
        }
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LinkifyMask {
    }

    public static boolean addLinks(@NonNull Spannable spannable, int i) {
        if (VERSION.SDK_INT >= 27) {
            return Linkify.addLinks(spannable, i);
        }
        if (i == 0) {
            return false;
        }
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int length = uRLSpanArr.length - 1; length >= 0; length--) {
            spannable.removeSpan(uRLSpanArr[length]);
        }
        if ((i & 4) != 0) {
            Linkify.addLinks(spannable, 4);
        }
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            ArrayList arrayList2 = arrayList;
            Spannable spannable2 = spannable;
            a(arrayList2, spannable2, PatternsCompat.AUTOLINK_WEB_URL, new String[]{"http://", "https://", "rtsp://"}, Linkify.sUrlMatchFilter, null);
        }
        if ((i & 2) != 0) {
            a(arrayList, spannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        }
        if ((i & 8) != 0) {
            a(arrayList, spannable);
        }
        b(arrayList, spannable);
        if (arrayList.size() == 0) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            LinkSpec linkSpec = (LinkSpec) it.next();
            if (linkSpec.a == null) {
                a(linkSpec.b, linkSpec.c, linkSpec.d, spannable);
            }
        }
        return true;
    }

    public static boolean addLinks(@NonNull TextView textView, int i) {
        if (VERSION.SDK_INT >= 26) {
            return Linkify.addLinks(textView, i);
        }
        if (i == 0) {
            return false;
        }
        CharSequence text = textView.getText();
        if (!(text instanceof Spannable)) {
            SpannableString valueOf = SpannableString.valueOf(text);
            if (!addLinks((Spannable) valueOf, i)) {
                return false;
            }
            a(textView);
            textView.setText(valueOf);
            return true;
        } else if (!addLinks((Spannable) text, i)) {
            return false;
        } else {
            a(textView);
            return true;
        }
    }

    public static void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str) {
        if (VERSION.SDK_INT >= 26) {
            Linkify.addLinks(textView, pattern, str);
        } else {
            addLinks(textView, pattern, str, (String[]) null, (MatchFilter) null, (TransformFilter) null);
        }
    }

    public static void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str, @Nullable MatchFilter matchFilter, @Nullable TransformFilter transformFilter) {
        if (VERSION.SDK_INT >= 26) {
            Linkify.addLinks(textView, pattern, str, matchFilter, transformFilter);
        } else {
            addLinks(textView, pattern, str, (String[]) null, matchFilter, transformFilter);
        }
    }

    public static void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str, @Nullable String[] strArr, @Nullable MatchFilter matchFilter, @Nullable TransformFilter transformFilter) {
        if (VERSION.SDK_INT >= 26) {
            Linkify.addLinks(textView, pattern, str, strArr, matchFilter, transformFilter);
            return;
        }
        SpannableString valueOf = SpannableString.valueOf(textView.getText());
        if (addLinks((Spannable) valueOf, pattern, str, strArr, matchFilter, transformFilter)) {
            textView.setText(valueOf);
            a(textView);
        }
    }

    public static boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str) {
        if (VERSION.SDK_INT >= 26) {
            return Linkify.addLinks(spannable, pattern, str);
        }
        return addLinks(spannable, pattern, str, (String[]) null, (MatchFilter) null, (TransformFilter) null);
    }

    public static boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str, @Nullable MatchFilter matchFilter, @Nullable TransformFilter transformFilter) {
        if (VERSION.SDK_INT >= 26) {
            return Linkify.addLinks(spannable, pattern, str, matchFilter, transformFilter);
        }
        return addLinks(spannable, pattern, str, (String[]) null, matchFilter, transformFilter);
    }

    public static boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str, @Nullable String[] strArr, @Nullable MatchFilter matchFilter, @Nullable TransformFilter transformFilter) {
        String str2;
        if (VERSION.SDK_INT >= 26) {
            return Linkify.addLinks(spannable, pattern, str, strArr, matchFilter, transformFilter);
        }
        if (str == null) {
            str = "";
        }
        if (strArr == null || strArr.length < 1) {
            strArr = a;
        }
        String[] strArr2 = new String[(strArr.length + 1)];
        strArr2[0] = str.toLowerCase(Locale.ROOT);
        int i = 0;
        while (i < strArr.length) {
            String str3 = strArr[i];
            i++;
            if (str3 == null) {
                str2 = "";
            } else {
                str2 = str3.toLowerCase(Locale.ROOT);
            }
            strArr2[i] = str2;
        }
        Matcher matcher = pattern.matcher(spannable);
        boolean z = false;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter != null ? matchFilter.acceptMatch(spannable, start, end) : true) {
                a(a(matcher.group(0), strArr2, matcher, transformFilter), start, end, spannable);
                z = true;
            }
        }
        return z;
    }

    private static void a(@NonNull TextView textView) {
        MovementMethod movementMethod = textView.getMovementMethod();
        if ((movementMethod == null || !(movementMethod instanceof LinkMovementMethod)) && textView.getLinksClickable()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private static String a(@NonNull String str, @NonNull String[] strArr, Matcher matcher, @Nullable TransformFilter transformFilter) {
        boolean z;
        if (transformFilter != null) {
            str = transformFilter.transformUrl(matcher, str);
        }
        int i = 0;
        while (true) {
            z = true;
            if (i >= strArr.length) {
                z = false;
                break;
            }
            if (str.regionMatches(true, 0, strArr[i], 0, strArr[i].length())) {
                if (!str.regionMatches(false, 0, strArr[i], 0, strArr[i].length())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(strArr[i]);
                    sb.append(str.substring(strArr[i].length()));
                    str = sb.toString();
                }
            } else {
                i++;
            }
        }
        if (z || strArr.length <= 0) {
            return str;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(strArr[0]);
        sb2.append(str);
        return sb2.toString();
    }

    private static void a(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern pattern, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter == null || matchFilter.acceptMatch(spannable, start, end)) {
                LinkSpec linkSpec = new LinkSpec();
                linkSpec.b = a(matcher.group(0), strArr, matcher, transformFilter);
                linkSpec.c = start;
                linkSpec.d = end;
                arrayList.add(linkSpec);
            }
        }
    }

    private static void a(String str, int i, int i2, Spannable spannable) {
        spannable.setSpan(new URLSpan(str), i, i2, 33);
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:1:0x0005 */
    /* JADX WARNING: Removed duplicated region for block: B:1:0x0005 A[LOOP:0: B:1:0x0005->B:16:0x0005, LOOP_START, PHI: r0 r6 
      PHI: (r0v1 int) = (r0v0 int), (r0v2 int) binds: [B:0:0x0000, B:16:0x0005] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r6v2 java.lang.String) = (r6v1 java.lang.String), (r6v3 java.lang.String) binds: [B:0:0x0000, B:16:0x0005] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC, Splitter:B:1:0x0005] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(java.util.ArrayList<android.support.v4.text.util.LinkifyCompat.LinkSpec> r5, android.text.Spannable r6) {
        /*
            java.lang.String r6 = r6.toString()
            r0 = 0
        L_0x0005:
            java.lang.String r1 = android.webkit.WebView.findAddress(r6)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            if (r1 == 0) goto L_0x0043
            int r2 = r6.indexOf(r1)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            if (r2 >= 0) goto L_0x0012
            goto L_0x0043
        L_0x0012:
            android.support.v4.text.util.LinkifyCompat$LinkSpec r3 = new android.support.v4.text.util.LinkifyCompat$LinkSpec     // Catch:{ UnsupportedOperationException -> 0x0044 }
            r3.<init>()     // Catch:{ UnsupportedOperationException -> 0x0044 }
            int r4 = r1.length()     // Catch:{ UnsupportedOperationException -> 0x0044 }
            int r4 = r4 + r2
            int r2 = r2 + r0
            r3.c = r2     // Catch:{ UnsupportedOperationException -> 0x0044 }
            int r0 = r0 + r4
            r3.d = r0     // Catch:{ UnsupportedOperationException -> 0x0044 }
            java.lang.String r6 = r6.substring(r4)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            java.lang.String r2 = "UTF-8"
            java.lang.String r1 = java.net.URLEncoder.encode(r1, r2)     // Catch:{ UnsupportedEncodingException -> 0x0005 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ UnsupportedOperationException -> 0x0044 }
            r2.<init>()     // Catch:{ UnsupportedOperationException -> 0x0044 }
            java.lang.String r4 = "geo:0,0?q="
            r2.append(r4)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            r2.append(r1)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            java.lang.String r1 = r2.toString()     // Catch:{ UnsupportedOperationException -> 0x0044 }
            r3.b = r1     // Catch:{ UnsupportedOperationException -> 0x0044 }
            r5.add(r3)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            goto L_0x0005
        L_0x0043:
            return
        L_0x0044:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.text.util.LinkifyCompat.a(java.util.ArrayList, android.text.Spannable):void");
    }

    private static void b(ArrayList<LinkSpec> arrayList, Spannable spannable) {
        int i = 0;
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int i2 = 0; i2 < uRLSpanArr.length; i2++) {
            LinkSpec linkSpec = new LinkSpec();
            linkSpec.a = uRLSpanArr[i2];
            linkSpec.c = spannable.getSpanStart(uRLSpanArr[i2]);
            linkSpec.d = spannable.getSpanEnd(uRLSpanArr[i2]);
            arrayList.add(linkSpec);
        }
        Collections.sort(arrayList, b);
        int size = arrayList.size();
        while (i < size - 1) {
            LinkSpec linkSpec2 = (LinkSpec) arrayList.get(i);
            int i3 = i + 1;
            LinkSpec linkSpec3 = (LinkSpec) arrayList.get(i3);
            if (linkSpec2.c <= linkSpec3.c && linkSpec2.d > linkSpec3.c) {
                int i4 = (linkSpec3.d > linkSpec2.d && linkSpec2.d - linkSpec2.c <= linkSpec3.d - linkSpec3.c) ? linkSpec2.d - linkSpec2.c < linkSpec3.d - linkSpec3.c ? i : -1 : i3;
                if (i4 != -1) {
                    URLSpan uRLSpan = ((LinkSpec) arrayList.get(i4)).a;
                    if (uRLSpan != null) {
                        spannable.removeSpan(uRLSpan);
                    }
                    arrayList.remove(i4);
                    size--;
                }
            }
            i = i3;
        }
    }

    private LinkifyCompat() {
    }
}
