package android.support.v4.hardware.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.v4.os.CancellationSignal;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

public final class FingerprintManagerCompat {
    private final Context a;

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int i, CharSequence charSequence) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationHelp(int i, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }
    }

    public static final class AuthenticationResult {
        private final CryptoObject a;

        public AuthenticationResult(CryptoObject cryptoObject) {
            this.a = cryptoObject;
        }

        public CryptoObject getCryptoObject() {
            return this.a;
        }
    }

    public static class CryptoObject {
        private final Signature a;
        private final Cipher b;
        private final Mac c;

        public CryptoObject(@NonNull Signature signature) {
            this.a = signature;
            this.b = null;
            this.c = null;
        }

        public CryptoObject(@NonNull Cipher cipher) {
            this.b = cipher;
            this.a = null;
            this.c = null;
        }

        public CryptoObject(@NonNull Mac mac) {
            this.c = mac;
            this.b = null;
            this.a = null;
        }

        @Nullable
        public Signature getSignature() {
            return this.a;
        }

        @Nullable
        public Cipher getCipher() {
            return this.b;
        }

        @Nullable
        public Mac getMac() {
            return this.c;
        }
    }

    @NonNull
    public static FingerprintManagerCompat from(@NonNull Context context) {
        return new FingerprintManagerCompat(context);
    }

    private FingerprintManagerCompat(Context context) {
        this.a = context;
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public boolean hasEnrolledFingerprints() {
        boolean z = false;
        if (VERSION.SDK_INT < 23) {
            return false;
        }
        FingerprintManager a2 = a(this.a);
        if (a2 != null && a2.hasEnrolledFingerprints()) {
            z = true;
        }
        return z;
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public boolean isHardwareDetected() {
        boolean z = false;
        if (VERSION.SDK_INT < 23) {
            return false;
        }
        FingerprintManager a2 = a(this.a);
        if (a2 != null && a2.isHardwareDetected()) {
            z = true;
        }
        return z;
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public void authenticate(@Nullable CryptoObject cryptoObject, int i, @Nullable CancellationSignal cancellationSignal, @NonNull AuthenticationCallback authenticationCallback, @Nullable Handler handler) {
        if (VERSION.SDK_INT >= 23) {
            FingerprintManager a2 = a(this.a);
            if (a2 != null) {
                a2.authenticate(a(cryptoObject), cancellationSignal != null ? (android.os.CancellationSignal) cancellationSignal.getCancellationSignalObject() : null, i, a(authenticationCallback), handler);
            }
        }
    }

    @Nullable
    @RequiresApi(23)
    private static FingerprintManager a(@NonNull Context context) {
        if (context.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return (FingerprintManager) context.getSystemService(FingerprintManager.class);
        }
        return null;
    }

    @RequiresApi(23)
    private static android.hardware.fingerprint.FingerprintManager.CryptoObject a(CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new android.hardware.fingerprint.FingerprintManager.CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new android.hardware.fingerprint.FingerprintManager.CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() != null) {
            return new android.hardware.fingerprint.FingerprintManager.CryptoObject(cryptoObject.getMac());
        }
        return null;
    }

    /* access modifiers changed from: private */
    @RequiresApi(23)
    public static CryptoObject b(android.hardware.fingerprint.FingerprintManager.CryptoObject cryptoObject) {
        if (cryptoObject == null) {
            return null;
        }
        if (cryptoObject.getCipher() != null) {
            return new CryptoObject(cryptoObject.getCipher());
        }
        if (cryptoObject.getSignature() != null) {
            return new CryptoObject(cryptoObject.getSignature());
        }
        if (cryptoObject.getMac() != null) {
            return new CryptoObject(cryptoObject.getMac());
        }
        return null;
    }

    @RequiresApi(23)
    private static android.hardware.fingerprint.FingerprintManager.AuthenticationCallback a(final AuthenticationCallback authenticationCallback) {
        return new android.hardware.fingerprint.FingerprintManager.AuthenticationCallback() {
            public void onAuthenticationError(int i, CharSequence charSequence) {
                authenticationCallback.onAuthenticationError(i, charSequence);
            }

            public void onAuthenticationHelp(int i, CharSequence charSequence) {
                authenticationCallback.onAuthenticationHelp(i, charSequence);
            }

            public void onAuthenticationSucceeded(android.hardware.fingerprint.FingerprintManager.AuthenticationResult authenticationResult) {
                authenticationCallback.onAuthenticationSucceeded(new AuthenticationResult(FingerprintManagerCompat.b(authenticationResult.getCryptoObject())));
            }

            public void onAuthenticationFailed() {
                authenticationCallback.onAuthenticationFailed();
            }
        };
    }
}
