package android.support.v4.os;

import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;

public final class EnvironmentCompat {
    public static final String MEDIA_UNKNOWN = "unknown";

    public static String getStorageState(File file) {
        if (VERSION.SDK_INT >= 19) {
            return Environment.getStorageState(file);
        }
        try {
            if (file.getCanonicalPath().startsWith(Environment.getExternalStorageDirectory().getCanonicalPath())) {
                return Environment.getExternalStorageState();
            }
        } catch (IOException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to resolve canonical path: ");
            sb.append(e);
            Log.w("EnvironmentCompat", sb.toString());
        }
        return MEDIA_UNKNOWN;
    }

    private EnvironmentCompat() {
    }
}
