package android.support.v13.view;

import android.app.Activity;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;

public final class DragAndDropPermissionsCompat {
    private Object a;

    private DragAndDropPermissionsCompat(Object obj) {
        this.a = obj;
    }

    @Nullable
    @RestrictTo({Scope.LIBRARY_GROUP})
    public static DragAndDropPermissionsCompat request(Activity activity, DragEvent dragEvent) {
        if (VERSION.SDK_INT >= 24) {
            DragAndDropPermissions requestDragAndDropPermissions = activity.requestDragAndDropPermissions(dragEvent);
            if (requestDragAndDropPermissions != null) {
                return new DragAndDropPermissionsCompat(requestDragAndDropPermissions);
            }
        }
        return null;
    }

    public void release() {
        if (VERSION.SDK_INT >= 24) {
            ((DragAndDropPermissions) this.a).release();
        }
    }
}
