package android.support.v13.view.inputmethod;

import android.content.ClipDescription;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;

public final class InputConnectionCompat {
    public static int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;

    public interface OnCommitContentListener {
        boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0051  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean a(@android.support.annotation.Nullable java.lang.String r7, @android.support.annotation.NonNull android.os.Bundle r8, @android.support.annotation.NonNull android.support.v13.view.inputmethod.InputConnectionCompat.OnCommitContentListener r9) {
        /*
            java.lang.String r0 = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT"
            boolean r7 = android.text.TextUtils.equals(r0, r7)
            r0 = 0
            if (r7 != 0) goto L_0x000a
            return r0
        L_0x000a:
            if (r8 != 0) goto L_0x000d
            return r0
        L_0x000d:
            r7 = 0
            java.lang.String r1 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER"
            android.os.Parcelable r1 = r8.getParcelable(r1)     // Catch:{ all -> 0x004d }
            android.os.ResultReceiver r1 = (android.os.ResultReceiver) r1     // Catch:{ all -> 0x004d }
            java.lang.String r2 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI"
            android.os.Parcelable r2 = r8.getParcelable(r2)     // Catch:{ all -> 0x004b }
            android.net.Uri r2 = (android.net.Uri) r2     // Catch:{ all -> 0x004b }
            java.lang.String r3 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION"
            android.os.Parcelable r3 = r8.getParcelable(r3)     // Catch:{ all -> 0x004b }
            android.content.ClipDescription r3 = (android.content.ClipDescription) r3     // Catch:{ all -> 0x004b }
            java.lang.String r4 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI"
            android.os.Parcelable r4 = r8.getParcelable(r4)     // Catch:{ all -> 0x004b }
            android.net.Uri r4 = (android.net.Uri) r4     // Catch:{ all -> 0x004b }
            java.lang.String r5 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS"
            int r5 = r8.getInt(r5)     // Catch:{ all -> 0x004b }
            java.lang.String r6 = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS"
            android.os.Parcelable r8 = r8.getParcelable(r6)     // Catch:{ all -> 0x004b }
            android.os.Bundle r8 = (android.os.Bundle) r8     // Catch:{ all -> 0x004b }
            android.support.v13.view.inputmethod.InputContentInfoCompat r6 = new android.support.v13.view.inputmethod.InputContentInfoCompat     // Catch:{ all -> 0x004b }
            r6.<init>(r2, r3, r4)     // Catch:{ all -> 0x004b }
            boolean r8 = r9.onCommitContent(r6, r5, r8)     // Catch:{ all -> 0x004b }
            if (r1 == 0) goto L_0x004a
            r1.send(r8, r7)
        L_0x004a:
            return r8
        L_0x004b:
            r8 = move-exception
            goto L_0x004f
        L_0x004d:
            r8 = move-exception
            r1 = r7
        L_0x004f:
            if (r1 == 0) goto L_0x0054
            r1.send(r0, r7)
        L_0x0054:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v13.view.inputmethod.InputConnectionCompat.a(java.lang.String, android.os.Bundle, android.support.v13.view.inputmethod.InputConnectionCompat$OnCommitContentListener):boolean");
    }

    public static boolean commitContent(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull InputContentInfoCompat inputContentInfoCompat, int i, @Nullable Bundle bundle) {
        boolean z;
        ClipDescription description = inputContentInfoCompat.getDescription();
        String[] contentMimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo);
        int length = contentMimeTypes.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                z = false;
                break;
            } else if (description.hasMimeType(contentMimeTypes[i2])) {
                z = true;
                break;
            } else {
                i2++;
            }
        }
        if (!z) {
            return false;
        }
        if (VERSION.SDK_INT >= 25) {
            return inputConnection.commitContent((InputContentInfo) inputContentInfoCompat.unwrap(), i, bundle);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI", inputContentInfoCompat.getContentUri());
        bundle2.putParcelable("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION", inputContentInfoCompat.getDescription());
        bundle2.putParcelable("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI", inputContentInfoCompat.getLinkUri());
        bundle2.putInt("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS", i);
        bundle2.putParcelable("android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS", bundle);
        return inputConnection.performPrivateCommand("android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT", bundle2);
    }

    @NonNull
    public static InputConnection createWrapper(@NonNull InputConnection inputConnection, @NonNull EditorInfo editorInfo, @NonNull final OnCommitContentListener onCommitContentListener) {
        if (inputConnection == null) {
            throw new IllegalArgumentException("inputConnection must be non-null");
        } else if (editorInfo == null) {
            throw new IllegalArgumentException("editorInfo must be non-null");
        } else if (onCommitContentListener == null) {
            throw new IllegalArgumentException("onCommitContentListener must be non-null");
        } else if (VERSION.SDK_INT >= 25) {
            return new InputConnectionWrapper(inputConnection, false) {
                public boolean commitContent(InputContentInfo inputContentInfo, int i, Bundle bundle) {
                    if (onCommitContentListener.onCommitContent(InputContentInfoCompat.wrap(inputContentInfo), i, bundle)) {
                        return true;
                    }
                    return super.commitContent(inputContentInfo, i, bundle);
                }
            };
        } else {
            if (EditorInfoCompat.getContentMimeTypes(editorInfo).length == 0) {
                return inputConnection;
            }
            return new InputConnectionWrapper(inputConnection, false) {
                public boolean performPrivateCommand(String str, Bundle bundle) {
                    if (InputConnectionCompat.a(str, bundle, onCommitContentListener)) {
                        return true;
                    }
                    return super.performPrivateCommand(str, bundle);
                }
            };
        }
    }
}
