package android.support.v4.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public final class TaskStackBuilder implements Iterable<Intent> {
    private static final TaskStackBuilderBaseImpl a;
    private final ArrayList<Intent> b = new ArrayList<>();
    private final Context c;

    public interface SupportParentable {
        @Nullable
        Intent getSupportParentActivityIntent();
    }

    @RequiresApi(16)
    static class TaskStackBuilderApi16Impl extends TaskStackBuilderBaseImpl {
        TaskStackBuilderApi16Impl() {
        }

        public PendingIntent a(Context context, Intent[] intentArr, int i, int i2, Bundle bundle) {
            intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
            return PendingIntent.getActivities(context, i, intentArr, i2, bundle);
        }
    }

    static class TaskStackBuilderBaseImpl {
        TaskStackBuilderBaseImpl() {
        }

        public PendingIntent a(Context context, Intent[] intentArr, int i, int i2, Bundle bundle) {
            intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
            return PendingIntent.getActivities(context, i, intentArr, i2);
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            a = new TaskStackBuilderApi16Impl();
        } else {
            a = new TaskStackBuilderBaseImpl();
        }
    }

    private TaskStackBuilder(Context context) {
        this.c = context;
    }

    @NonNull
    public static TaskStackBuilder create(@NonNull Context context) {
        return new TaskStackBuilder(context);
    }

    @Deprecated
    public static TaskStackBuilder from(Context context) {
        return create(context);
    }

    @NonNull
    public TaskStackBuilder addNextIntent(@NonNull Intent intent) {
        this.b.add(intent);
        return this;
    }

    @NonNull
    public TaskStackBuilder addNextIntentWithParentStack(@NonNull Intent intent) {
        ComponentName component = intent.getComponent();
        if (component == null) {
            component = intent.resolveActivity(this.c.getPackageManager());
        }
        if (component != null) {
            addParentStack(component);
        }
        addNextIntent(intent);
        return this;
    }

    @NonNull
    public TaskStackBuilder addParentStack(@NonNull Activity activity) {
        Intent supportParentActivityIntent = activity instanceof SupportParentable ? ((SupportParentable) activity).getSupportParentActivityIntent() : null;
        if (supportParentActivityIntent == null) {
            supportParentActivityIntent = NavUtils.getParentActivityIntent(activity);
        }
        if (supportParentActivityIntent != null) {
            ComponentName component = supportParentActivityIntent.getComponent();
            if (component == null) {
                component = supportParentActivityIntent.resolveActivity(this.c.getPackageManager());
            }
            addParentStack(component);
            addNextIntent(supportParentActivityIntent);
        }
        return this;
    }

    @NonNull
    public TaskStackBuilder addParentStack(@NonNull Class<?> cls) {
        return addParentStack(new ComponentName(this.c, cls));
    }

    public TaskStackBuilder addParentStack(ComponentName componentName) {
        int size = this.b.size();
        try {
            Intent parentActivityIntent = NavUtils.getParentActivityIntent(this.c, componentName);
            while (parentActivityIntent != null) {
                this.b.add(size, parentActivityIntent);
                parentActivityIntent = NavUtils.getParentActivityIntent(this.c, parentActivityIntent.getComponent());
            }
            return this;
        } catch (NameNotFoundException e) {
            Log.e("TaskStackBuilder", "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException(e);
        }
    }

    public int getIntentCount() {
        return this.b.size();
    }

    @Deprecated
    public Intent getIntent(int i) {
        return editIntentAt(i);
    }

    @Nullable
    public Intent editIntentAt(int i) {
        return (Intent) this.b.get(i);
    }

    @Deprecated
    public Iterator<Intent> iterator() {
        return this.b.iterator();
    }

    public void startActivities() {
        startActivities(null);
    }

    public void startActivities(@Nullable Bundle bundle) {
        if (this.b.isEmpty()) {
            throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
        }
        Intent[] intentArr = (Intent[]) this.b.toArray(new Intent[this.b.size()]);
        intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
        if (!ContextCompat.startActivities(this.c, intentArr, bundle)) {
            Intent intent = new Intent(intentArr[intentArr.length - 1]);
            intent.addFlags(268435456);
            this.c.startActivity(intent);
        }
    }

    @Nullable
    public PendingIntent getPendingIntent(int i, int i2) {
        return getPendingIntent(i, i2, null);
    }

    @Nullable
    public PendingIntent getPendingIntent(int i, int i2, @Nullable Bundle bundle) {
        if (this.b.isEmpty()) {
            throw new IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
        }
        Intent[] intentArr = (Intent[]) this.b.toArray(new Intent[this.b.size()]);
        intentArr[0] = new Intent(intentArr[0]).addFlags(268484608);
        return a.a(this.c, intentArr, i, i2, bundle);
    }

    @NonNull
    public Intent[] getIntents() {
        Intent[] intentArr = new Intent[this.b.size()];
        if (intentArr.length == 0) {
            return intentArr;
        }
        intentArr[0] = new Intent((Intent) this.b.get(0)).addFlags(268484608);
        for (int i = 1; i < intentArr.length; i++) {
            intentArr[i] = new Intent((Intent) this.b.get(i));
        }
        return intentArr;
    }
}
