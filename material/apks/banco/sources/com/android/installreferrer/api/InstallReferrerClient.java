package com.android.installreferrer.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class InstallReferrerClient {

    @Retention(RetentionPolicy.SOURCE)
    public @interface InstallReferrerResponse {
        public static final int DEVELOPER_ERROR = 3;
        public static final int FEATURE_NOT_SUPPORTED = 2;
        public static final int OK = 0;
        public static final int SERVICE_DISCONNECTED = -1;
        public static final int SERVICE_UNAVAILABLE = 1;
    }

    public static final class Builder {
        private final Context a;

        private Builder(Context context) {
            this.a = context;
        }

        @UiThread
        public InstallReferrerClient build() {
            if (this.a != null) {
                return new InstallReferrerClientImpl(this.a);
            }
            throw new IllegalArgumentException("Please provide a valid Context.");
        }
    }

    @UiThread
    public abstract void endConnection();

    @UiThread
    public abstract ReferrerDetails getInstallReferrer();

    @UiThread
    public abstract boolean isReady();

    @UiThread
    public abstract void startConnection(@NonNull InstallReferrerStateListener installReferrerStateListener);

    @UiThread
    public static Builder newBuilder(@NonNull Context context) {
        return new Builder(context);
    }
}
