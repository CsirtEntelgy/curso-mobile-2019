package com.scottyab.rootbeer;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.scottyab.rootbeer.util.QLog;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RootBeer {
    private final Context a;
    private boolean b = true;

    public RootBeer(Context context) {
        this.a = context;
    }

    public boolean isRooted() {
        return detectRootManagementApps() || detectPotentiallyDangerousApps() || checkForBinary("su") || checkForBinary("busybox") || checkForDangerousProps() || checkForRWPaths() || detectTestKeys() || checkSuExists() || checkForRootNative() || checkForMagiskBinary();
    }

    public boolean isRootedWithoutBusyBoxCheck() {
        return detectRootManagementApps() || detectPotentiallyDangerousApps() || checkForBinary("su") || checkForDangerousProps() || checkForRWPaths() || detectTestKeys() || checkSuExists() || checkForRootNative() || checkForMagiskBinary();
    }

    public boolean detectTestKeys() {
        String str = Build.TAGS;
        return str != null && str.contains("test-keys");
    }

    public boolean detectRootManagementApps() {
        return detectRootManagementApps(null);
    }

    public boolean detectRootManagementApps(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(Const.knownRootAppsPackages));
        if (strArr != null && strArr.length > 0) {
            arrayList.addAll(Arrays.asList(strArr));
        }
        return a(arrayList);
    }

    public boolean detectPotentiallyDangerousApps() {
        return detectPotentiallyDangerousApps(null);
    }

    public boolean detectPotentiallyDangerousApps(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(Const.knownDangerousAppsPackages));
        if (strArr != null && strArr.length > 0) {
            arrayList.addAll(Arrays.asList(strArr));
        }
        return a(arrayList);
    }

    public boolean detectRootCloakingApps() {
        return detectRootCloakingApps(null) || (canLoadNativeLibrary() && !checkForNativeLibraryReadAccess());
    }

    public boolean detectRootCloakingApps(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(Const.knownRootCloakingPackages));
        if (strArr != null && strArr.length > 0) {
            arrayList.addAll(Arrays.asList(strArr));
        }
        return a(arrayList);
    }

    public boolean checkForSuBinary() {
        return checkForBinary("su");
    }

    public boolean checkForMagiskBinary() {
        return checkForBinary("magisk");
    }

    public boolean checkForBusyBoxBinary() {
        return checkForBinary("busybox");
    }

    public boolean checkForBinary(String str) {
        String[] strArr;
        boolean z = false;
        for (String str2 : Const.suPaths) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            String sb2 = sb.toString();
            if (new File(str2, str).exists()) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(" binary detected!");
                QLog.v(sb3.toString());
                z = true;
            }
        }
        return z;
    }

    public void setLogging(boolean z) {
        this.b = z;
        QLog.LOGGING_LEVEL = z ? 5 : 0;
    }

    private String[] a() {
        try {
            InputStream inputStream = Runtime.getRuntime().exec("getprop").getInputStream();
            if (inputStream == null) {
                return null;
            }
            return new Scanner(inputStream).useDelimiter("\\A").next().split("\n");
        } catch (IOException | NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String[] b() {
        try {
            InputStream inputStream = Runtime.getRuntime().exec("mount").getInputStream();
            if (inputStream == null) {
                return null;
            }
            return new Scanner(inputStream).useDelimiter("\\A").next().split("\n");
        } catch (IOException | NoSuchElementException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean a(List<String> list) {
        PackageManager packageManager = this.a.getPackageManager();
        boolean z = false;
        for (String str : list) {
            try {
                packageManager.getPackageInfo(str, 0);
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(" ROOT management app detected!");
                QLog.e(sb.toString());
                z = true;
            } catch (NameNotFoundException unused) {
            }
        }
        return z;
    }

    public boolean checkForDangerousProps() {
        HashMap hashMap = new HashMap();
        hashMap.put("ro.debuggable", "1");
        hashMap.put("ro.secure", "0");
        String[] a2 = a();
        if (a2 == null) {
            return false;
        }
        boolean z = false;
        for (String str : a2) {
            for (String str2 : hashMap.keySet()) {
                if (str.contains(str2)) {
                    String str3 = (String) hashMap.get(str2);
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    sb.append(str3);
                    sb.append("]");
                    String sb2 = sb.toString();
                    if (str.contains(sb2)) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str2);
                        sb3.append(" = ");
                        sb3.append(sb2);
                        sb3.append(" detected!");
                        QLog.v(sb3.toString());
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    public boolean checkForRWPaths() {
        String[] strArr;
        String[] b2 = b();
        if (b2 == null) {
            return false;
        }
        boolean z = false;
        for (String str : b2) {
            String[] split = str.split(UtilsCuentas.SEPARAOR2);
            if (split.length < 4) {
                StringBuilder sb = new StringBuilder();
                sb.append("Error formatting mount line: ");
                sb.append(str);
                QLog.e(sb.toString());
            } else {
                String str2 = split[1];
                String str3 = split[3];
                boolean z2 = z;
                for (String str4 : Const.pathsThatShouldNotBeWrtiable) {
                    if (str2.equalsIgnoreCase(str4)) {
                        String[] split2 = str3.split(",");
                        int length = split2.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            } else if (split2[i].equalsIgnoreCase("rw")) {
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append(str4);
                                sb2.append(" path is mounted with rw permissions! ");
                                sb2.append(str);
                                QLog.v(sb2.toString());
                                z2 = true;
                                break;
                            } else {
                                i++;
                            }
                        }
                    }
                }
                z = z2;
            }
        }
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x003f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean checkSuExists() {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x003d, all -> 0x0036 }
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ Throwable -> 0x003d, all -> 0x0036 }
            java.lang.String r4 = "which"
            r3[r0] = r4     // Catch:{ Throwable -> 0x003d, all -> 0x0036 }
            java.lang.String r4 = "su"
            r5 = 1
            r3[r5] = r4     // Catch:{ Throwable -> 0x003d, all -> 0x0036 }
            java.lang.Process r2 = r2.exec(r3)     // Catch:{ Throwable -> 0x003d, all -> 0x0036 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
            java.io.InputStream r4 = r2.getInputStream()     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
            r3.<init>(r4)     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
            r1.<init>(r3)     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
            java.lang.String r1 = r1.readLine()     // Catch:{ Throwable -> 0x0034, all -> 0x0031 }
            if (r1 == 0) goto L_0x002b
            r0 = 1
        L_0x002b:
            if (r2 == 0) goto L_0x0030
            r2.destroy()
        L_0x0030:
            return r0
        L_0x0031:
            r0 = move-exception
            r1 = r2
            goto L_0x0037
        L_0x0034:
            r1 = r2
            goto L_0x003d
        L_0x0036:
            r0 = move-exception
        L_0x0037:
            if (r1 == 0) goto L_0x003c
            r1.destroy()
        L_0x003c:
            throw r0
        L_0x003d:
            if (r1 == 0) goto L_0x0042
            r1.destroy()
        L_0x0042:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.scottyab.rootbeer.RootBeer.checkSuExists():boolean");
    }

    public boolean checkForNativeLibraryReadAccess() {
        try {
            new RootBeerNative().setLogDebugMessages(this.b);
            return true;
        } catch (UnsatisfiedLinkError unused) {
            return false;
        }
    }

    public boolean canLoadNativeLibrary() {
        return new RootBeerNative().wasNativeLibraryLoaded();
    }

    public boolean checkForRootNative() {
        boolean z = false;
        if (!canLoadNativeLibrary()) {
            QLog.e("We could not load the native library to test for root");
            return false;
        }
        String str = "su";
        String[] strArr = new String[Const.suPaths.length];
        for (int i = 0; i < strArr.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(Const.suPaths[i]);
            sb.append(str);
            strArr[i] = sb.toString();
        }
        RootBeerNative rootBeerNative = new RootBeerNative();
        try {
            rootBeerNative.setLogDebugMessages(this.b);
            if (rootBeerNative.checkForRoot(strArr) > 0) {
                z = true;
            }
            return z;
        } catch (UnsatisfiedLinkError unused) {
            return false;
        }
    }
}
