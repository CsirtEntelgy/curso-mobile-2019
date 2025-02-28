package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompatExtras;
import android.util.Log;
import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.zzac;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.common.util.zzs;
import com.twincoders.twinpush.sdk.services.NotificationIntentService;

public final class GooglePlayServicesUtil extends zze {
    public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    static class zza extends Handler {
        private final Context a;

        zza(Context context) {
            super(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
            this.a = context.getApplicationContext();
        }

        public void handleMessage(Message message) {
            if (message.what != 1) {
                int i = message.what;
                StringBuilder sb = new StringBuilder(50);
                sb.append("Don't know how to handle this message: ");
                sb.append(i);
                Log.w("GooglePlayServicesUtil", sb.toString());
                return;
            }
            int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.a);
            if (GooglePlayServicesUtil.isUserRecoverableError(isGooglePlayServicesAvailable)) {
                GooglePlayServicesUtil.zza(isGooglePlayServicesAvailable, this.a);
            }
        }
    }

    private GooglePlayServicesUtil() {
    }

    @Deprecated
    public static Dialog getErrorDialog(int i, Activity activity, int i2) {
        return getErrorDialog(i, activity, i2, null);
    }

    @Deprecated
    public static Dialog getErrorDialog(int i, Activity activity, int i2, OnCancelListener onCancelListener) {
        if (zzd(activity, i)) {
            i = 18;
        }
        return GoogleApiAvailability.getInstance().getErrorDialog(activity, i, i2, onCancelListener);
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return zze.getErrorPendingIntent(i, context, i2);
    }

    @Deprecated
    public static String getErrorString(int i) {
        return zze.getErrorString(i);
    }

    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(Context context) {
        return zze.getOpenSourceSoftwareLicenseInfo(context);
    }

    public static Context getRemoteContext(Context context) {
        return zze.getRemoteContext(context);
    }

    public static Resources getRemoteResource(Context context) {
        return zze.getRemoteResource(context);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return zze.isGooglePlayServicesAvailable(context);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        return zze.isUserRecoverableError(i);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int i, Activity activity, int i2) {
        return showErrorDialogFragment(i, activity, i2, null);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int i, Activity activity, int i2, OnCancelListener onCancelListener) {
        return showErrorDialogFragment(i, activity, null, i2, onCancelListener);
    }

    public static boolean showErrorDialogFragment(int i, Activity activity, Fragment fragment, int i2, OnCancelListener onCancelListener) {
        if (zzd(activity, i)) {
            i = 18;
        }
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        if (fragment == null) {
            return instance.showErrorDialogFragment(activity, i, i2, onCancelListener);
        }
        Dialog a = instance.a((Context) activity, i, zzj.zza(fragment, GoogleApiAvailability.getInstance().zza((Context) activity, i, "d"), i2), onCancelListener);
        if (a == null) {
            return false;
        }
        instance.a(activity, a, GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    @Deprecated
    public static void showErrorNotification(int i, Context context) {
        if (zzi.zzcl(context) && i == 2) {
            i = 42;
        }
        if (zzd(context, i) || zze(context, i)) {
            zzbs(context);
        } else {
            zza(i, context);
        }
    }

    /* access modifiers changed from: private */
    public static void zza(int i, Context context) {
        zza(i, context, (String) null);
    }

    static void zza(int i, Context context, PendingIntent pendingIntent) {
        zza(i, context, null, pendingIntent);
    }

    private static void zza(int i, Context context, String str) {
        zza(i, context, str, GoogleApiAvailability.getInstance().zza(context, i, 0, "n"));
    }

    @TargetApi(20)
    private static void zza(int i, Context context, String str, PendingIntent pendingIntent) {
        Notification notification;
        int i2;
        Resources resources = context.getResources();
        String zzh = com.google.android.gms.common.internal.zzi.zzh(context, i);
        String zzj = com.google.android.gms.common.internal.zzi.zzj(context, i);
        if (zzi.zzcl(context)) {
            zzac.zzbr(zzs.zzaxo());
            Builder autoCancel = new Builder(context).setSmallIcon(R.drawable.common_ic_googleplayservices).setPriority(2).setAutoCancel(true);
            BigTextStyle bigTextStyle = new BigTextStyle();
            StringBuilder sb = new StringBuilder(String.valueOf(zzh).length() + 1 + String.valueOf(zzj).length());
            sb.append(zzh);
            sb.append(UtilsCuentas.SEPARAOR2);
            sb.append(zzj);
            notification = autoCancel.setStyle(bigTextStyle.bigText(sb.toString())).addAction(R.drawable.common_full_open_on_phone, resources.getString(R.string.common_open_on_phone), pendingIntent).build();
        } else {
            String string = resources.getString(R.string.common_google_play_services_notification_ticker);
            if (zzs.zzaxk()) {
                Builder autoCancel2 = new Builder(context).setSmallIcon(17301642).setContentTitle(zzh).setContentText(zzj).setContentIntent(pendingIntent).setTicker(string).setAutoCancel(true);
                if (zzs.zzaxs()) {
                    autoCancel2.setLocalOnly(true);
                }
                if (zzs.zzaxo()) {
                    autoCancel2.setStyle(new BigTextStyle().bigText(zzj));
                    notification = autoCancel2.build();
                } else {
                    notification = autoCancel2.getNotification();
                }
                if (VERSION.SDK_INT == 19) {
                    notification.extras.putBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY, true);
                }
            } else {
                notification = new NotificationCompat.Builder(context).setSmallIcon(17301642).setTicker(string).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent(pendingIntent).setContentTitle(zzh).setContentText(zzj).build();
            }
        }
        if (zzfn(i)) {
            i2 = 10436;
            vd.set(false);
        } else {
            i2 = 39789;
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NotificationIntentService.EXTRA_NOTIFICATION);
        if (str != null) {
            notificationManager.notify(str, i2, notification);
        } else {
            notificationManager.notify(i2, notification);
        }
    }

    private static void zzbs(Context context) {
        zza zza2 = new zza(context);
        zza2.sendMessageDelayed(zza2.obtainMessage(1), 120000);
    }

    @Deprecated
    public static boolean zzd(Context context, int i) {
        return zze.zzd(context, i);
    }

    @Deprecated
    public static boolean zze(Context context, int i) {
        return zze.zze(context, i);
    }

    @Deprecated
    public static Intent zzfm(int i) {
        return zze.zzfm(i);
    }
}
