package android.support.v4.content.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutManager;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

public class ShortcutManagerCompat {
    private ShortcutManagerCompat() {
    }

    public static boolean isRequestPinShortcutSupported(@NonNull Context context) {
        if (VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).isRequestPinShortcutSupported();
        }
        if (ContextCompat.checkSelfPermission(context, "com.android.launcher.permission.INSTALL_SHORTCUT") != 0) {
            return false;
        }
        for (ResolveInfo resolveInfo : context.getPackageManager().queryBroadcastReceivers(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"), 0)) {
            String str = resolveInfo.activityInfo.permission;
            if (!TextUtils.isEmpty(str)) {
                if ("com.android.launcher.permission.INSTALL_SHORTCUT".equals(str)) {
                }
            }
            return true;
        }
        return false;
    }

    public static boolean requestPinShortcut(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat, @Nullable final IntentSender intentSender) {
        if (VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).requestPinShortcut(shortcutInfoCompat.toShortcutInfo(), intentSender);
        }
        if (!isRequestPinShortcutSupported(context)) {
            return false;
        }
        Intent a = shortcutInfoCompat.a(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"));
        if (intentSender == null) {
            context.sendBroadcast(a);
            return true;
        }
        context.sendOrderedBroadcast(a, null, new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                try {
                    intentSender.sendIntent(context, 0, null, null, null);
                } catch (SendIntentException unused) {
                }
            }
        }, null, -1, null, null);
        return true;
    }

    @NonNull
    public static Intent createShortcutResultIntent(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat) {
        Intent createShortcutResultIntent = VERSION.SDK_INT >= 26 ? ((ShortcutManager) context.getSystemService(ShortcutManager.class)).createShortcutResultIntent(shortcutInfoCompat.toShortcutInfo()) : null;
        if (createShortcutResultIntent == null) {
            createShortcutResultIntent = new Intent();
        }
        return shortcutInfoCompat.a(createShortcutResultIntent);
    }
}
