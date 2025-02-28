package io.fabric.sdk.android.services.common;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;

public class ApiKey {
    /* access modifiers changed from: protected */
    public String buildApiKeyInstructions() {
        return "Fabric could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"io.fabric.ApiKey\" android:value=\"YOUR_API_KEY\"/>";
    }

    @Deprecated
    public static String getApiKey(Context context) {
        Fabric.getLogger().w(Fabric.TAG, "getApiKey(context) is deprecated, please upgrade kit(s) to the latest version.");
        return new ApiKey().getValue(context);
    }

    @Deprecated
    public static String getApiKey(Context context, boolean z) {
        Fabric.getLogger().w(Fabric.TAG, "getApiKey(context, debug) is deprecated, please upgrade kit(s) to the latest version.");
        return new ApiKey().getValue(context);
    }

    public String getValue(Context context) {
        String apiKeyFromManifest = getApiKeyFromManifest(context);
        if (TextUtils.isEmpty(apiKeyFromManifest)) {
            apiKeyFromManifest = getApiKeyFromStrings(context);
        }
        if (TextUtils.isEmpty(apiKeyFromManifest)) {
            logErrorOrThrowException(context);
        }
        return apiKeyFromManifest;
    }

    /* access modifiers changed from: protected */
    public String getApiKeyFromManifest(Context context) {
        String str = null;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle == null) {
                return null;
            }
            String string = bundle.getString("io.fabric.ApiKey");
            if (string != null) {
                return string;
            }
            try {
                Fabric.getLogger().d(Fabric.TAG, "Falling back to Crashlytics key lookup from Manifest");
                return bundle.getString("com.crashlytics.ApiKey");
            } catch (Exception e) {
                e = e;
                str = string;
                Logger logger = Fabric.getLogger();
                String str2 = Fabric.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Caught non-fatal exception while retrieving apiKey: ");
                sb.append(e);
                logger.d(str2, sb.toString());
                return str;
            }
        } catch (Exception e2) {
            e = e2;
            Logger logger2 = Fabric.getLogger();
            String str22 = Fabric.TAG;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Caught non-fatal exception while retrieving apiKey: ");
            sb2.append(e);
            logger2.d(str22, sb2.toString());
            return str;
        }
    }

    /* access modifiers changed from: protected */
    public String getApiKeyFromStrings(Context context) {
        int resourcesIdentifier = CommonUtils.getResourcesIdentifier(context, "io.fabric.ApiKey", "string");
        if (resourcesIdentifier == 0) {
            Fabric.getLogger().d(Fabric.TAG, "Falling back to Crashlytics key lookup from Strings");
            resourcesIdentifier = CommonUtils.getResourcesIdentifier(context, "com.crashlytics.ApiKey", "string");
        }
        if (resourcesIdentifier != 0) {
            return context.getResources().getString(resourcesIdentifier);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void logErrorOrThrowException(Context context) {
        if (Fabric.isDebuggable() || CommonUtils.isAppDebuggable(context)) {
            throw new IllegalArgumentException(buildApiKeyInstructions());
        }
        Fabric.getLogger().e(Fabric.TAG, buildApiKeyInstructions());
    }
}
