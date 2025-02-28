package android.support.v7.view;

import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public abstract class ActionMode {
    private Object a;
    private boolean b;

    public interface Callback {
        boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem);

        boolean onCreateActionMode(ActionMode actionMode, Menu menu);

        void onDestroyActionMode(ActionMode actionMode);

        boolean onPrepareActionMode(ActionMode actionMode, Menu menu);
    }

    public abstract void finish();

    public abstract View getCustomView();

    public abstract Menu getMenu();

    public abstract MenuInflater getMenuInflater();

    public abstract CharSequence getSubtitle();

    public abstract CharSequence getTitle();

    public abstract void invalidate();

    public boolean isTitleOptional() {
        return false;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public boolean isUiFocusable() {
        return true;
    }

    public abstract void setCustomView(View view);

    public abstract void setSubtitle(int i);

    public abstract void setSubtitle(CharSequence charSequence);

    public abstract void setTitle(int i);

    public abstract void setTitle(CharSequence charSequence);

    public void setTag(Object obj) {
        this.a = obj;
    }

    public Object getTag() {
        return this.a;
    }

    public void setTitleOptionalHint(boolean z) {
        this.b = z;
    }

    public boolean getTitleOptionalHint() {
        return this.b;
    }
}
