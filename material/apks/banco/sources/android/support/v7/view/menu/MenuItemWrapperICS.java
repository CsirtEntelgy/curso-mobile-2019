package android.support.v7.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.support.v7.view.CollapsibleActionView;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.FrameLayout;
import java.lang.reflect.Method;

@RequiresApi(14)
@RestrictTo({Scope.LIBRARY_GROUP})
public class MenuItemWrapperICS extends BaseMenuWrapper<SupportMenuItem> implements MenuItem {
    private Method c;

    class ActionProviderWrapper extends ActionProvider {
        final android.view.ActionProvider a;

        public ActionProviderWrapper(Context context, android.view.ActionProvider actionProvider) {
            super(context);
            this.a = actionProvider;
        }

        public View onCreateActionView() {
            return this.a.onCreateActionView();
        }

        public boolean onPerformDefaultAction() {
            return this.a.onPerformDefaultAction();
        }

        public boolean hasSubMenu() {
            return this.a.hasSubMenu();
        }

        public void onPrepareSubMenu(SubMenu subMenu) {
            this.a.onPrepareSubMenu(MenuItemWrapperICS.this.a(subMenu));
        }
    }

    static class CollapsibleActionViewWrapper extends FrameLayout implements CollapsibleActionView {
        final android.view.CollapsibleActionView a;

        CollapsibleActionViewWrapper(View view) {
            super(view.getContext());
            this.a = (android.view.CollapsibleActionView) view;
            addView(view);
        }

        public void onActionViewExpanded() {
            this.a.onActionViewExpanded();
        }

        public void onActionViewCollapsed() {
            this.a.onActionViewCollapsed();
        }

        /* access modifiers changed from: 0000 */
        public View a() {
            return (View) this.a;
        }
    }

    class OnActionExpandListenerWrapper extends BaseWrapper<OnActionExpandListener> implements OnActionExpandListener {
        OnActionExpandListenerWrapper(OnActionExpandListener onActionExpandListener) {
            super(onActionExpandListener);
        }

        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            return ((OnActionExpandListener) this.b).onMenuItemActionExpand(MenuItemWrapperICS.this.a(menuItem));
        }

        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            return ((OnActionExpandListener) this.b).onMenuItemActionCollapse(MenuItemWrapperICS.this.a(menuItem));
        }
    }

    class OnMenuItemClickListenerWrapper extends BaseWrapper<OnMenuItemClickListener> implements OnMenuItemClickListener {
        OnMenuItemClickListenerWrapper(OnMenuItemClickListener onMenuItemClickListener) {
            super(onMenuItemClickListener);
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            return ((OnMenuItemClickListener) this.b).onMenuItemClick(MenuItemWrapperICS.this.a(menuItem));
        }
    }

    MenuItemWrapperICS(Context context, SupportMenuItem supportMenuItem) {
        super(context, supportMenuItem);
    }

    public int getItemId() {
        return ((SupportMenuItem) this.b).getItemId();
    }

    public int getGroupId() {
        return ((SupportMenuItem) this.b).getGroupId();
    }

    public int getOrder() {
        return ((SupportMenuItem) this.b).getOrder();
    }

    public MenuItem setTitle(CharSequence charSequence) {
        ((SupportMenuItem) this.b).setTitle(charSequence);
        return this;
    }

    public MenuItem setTitle(int i) {
        ((SupportMenuItem) this.b).setTitle(i);
        return this;
    }

    public CharSequence getTitle() {
        return ((SupportMenuItem) this.b).getTitle();
    }

    public MenuItem setTitleCondensed(CharSequence charSequence) {
        ((SupportMenuItem) this.b).setTitleCondensed(charSequence);
        return this;
    }

    public CharSequence getTitleCondensed() {
        return ((SupportMenuItem) this.b).getTitleCondensed();
    }

    public MenuItem setIcon(Drawable drawable) {
        ((SupportMenuItem) this.b).setIcon(drawable);
        return this;
    }

    public MenuItem setIcon(int i) {
        ((SupportMenuItem) this.b).setIcon(i);
        return this;
    }

    public Drawable getIcon() {
        return ((SupportMenuItem) this.b).getIcon();
    }

    public MenuItem setIntent(Intent intent) {
        ((SupportMenuItem) this.b).setIntent(intent);
        return this;
    }

    public Intent getIntent() {
        return ((SupportMenuItem) this.b).getIntent();
    }

    public MenuItem setShortcut(char c2, char c3) {
        ((SupportMenuItem) this.b).setShortcut(c2, c3);
        return this;
    }

    public MenuItem setShortcut(char c2, char c3, int i, int i2) {
        ((SupportMenuItem) this.b).setShortcut(c2, c3, i, i2);
        return this;
    }

    public MenuItem setNumericShortcut(char c2) {
        ((SupportMenuItem) this.b).setNumericShortcut(c2);
        return this;
    }

    public MenuItem setNumericShortcut(char c2, int i) {
        ((SupportMenuItem) this.b).setNumericShortcut(c2, i);
        return this;
    }

    public char getNumericShortcut() {
        return ((SupportMenuItem) this.b).getNumericShortcut();
    }

    public int getNumericModifiers() {
        return ((SupportMenuItem) this.b).getNumericModifiers();
    }

    public MenuItem setAlphabeticShortcut(char c2) {
        ((SupportMenuItem) this.b).setAlphabeticShortcut(c2);
        return this;
    }

    public MenuItem setAlphabeticShortcut(char c2, int i) {
        ((SupportMenuItem) this.b).setAlphabeticShortcut(c2, i);
        return this;
    }

    public char getAlphabeticShortcut() {
        return ((SupportMenuItem) this.b).getAlphabeticShortcut();
    }

    public int getAlphabeticModifiers() {
        return ((SupportMenuItem) this.b).getAlphabeticModifiers();
    }

    public MenuItem setCheckable(boolean z) {
        ((SupportMenuItem) this.b).setCheckable(z);
        return this;
    }

    public boolean isCheckable() {
        return ((SupportMenuItem) this.b).isCheckable();
    }

    public MenuItem setChecked(boolean z) {
        ((SupportMenuItem) this.b).setChecked(z);
        return this;
    }

    public boolean isChecked() {
        return ((SupportMenuItem) this.b).isChecked();
    }

    public MenuItem setVisible(boolean z) {
        return ((SupportMenuItem) this.b).setVisible(z);
    }

    public boolean isVisible() {
        return ((SupportMenuItem) this.b).isVisible();
    }

    public MenuItem setEnabled(boolean z) {
        ((SupportMenuItem) this.b).setEnabled(z);
        return this;
    }

    public boolean isEnabled() {
        return ((SupportMenuItem) this.b).isEnabled();
    }

    public boolean hasSubMenu() {
        return ((SupportMenuItem) this.b).hasSubMenu();
    }

    public SubMenu getSubMenu() {
        return a(((SupportMenuItem) this.b).getSubMenu());
    }

    public MenuItem setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        ((SupportMenuItem) this.b).setOnMenuItemClickListener(onMenuItemClickListener != null ? new OnMenuItemClickListenerWrapper(onMenuItemClickListener) : null);
        return this;
    }

    public ContextMenuInfo getMenuInfo() {
        return ((SupportMenuItem) this.b).getMenuInfo();
    }

    public void setShowAsAction(int i) {
        ((SupportMenuItem) this.b).setShowAsAction(i);
    }

    public MenuItem setShowAsActionFlags(int i) {
        ((SupportMenuItem) this.b).setShowAsActionFlags(i);
        return this;
    }

    public MenuItem setActionView(View view) {
        if (view instanceof android.view.CollapsibleActionView) {
            view = new CollapsibleActionViewWrapper(view);
        }
        ((SupportMenuItem) this.b).setActionView(view);
        return this;
    }

    public MenuItem setActionView(int i) {
        ((SupportMenuItem) this.b).setActionView(i);
        View actionView = ((SupportMenuItem) this.b).getActionView();
        if (actionView instanceof android.view.CollapsibleActionView) {
            ((SupportMenuItem) this.b).setActionView((View) new CollapsibleActionViewWrapper(actionView));
        }
        return this;
    }

    public View getActionView() {
        View actionView = ((SupportMenuItem) this.b).getActionView();
        return actionView instanceof CollapsibleActionViewWrapper ? ((CollapsibleActionViewWrapper) actionView).a() : actionView;
    }

    public MenuItem setActionProvider(android.view.ActionProvider actionProvider) {
        ((SupportMenuItem) this.b).setSupportActionProvider(actionProvider != null ? a(actionProvider) : null);
        return this;
    }

    public android.view.ActionProvider getActionProvider() {
        ActionProvider supportActionProvider = ((SupportMenuItem) this.b).getSupportActionProvider();
        if (supportActionProvider instanceof ActionProviderWrapper) {
            return ((ActionProviderWrapper) supportActionProvider).a;
        }
        return null;
    }

    public boolean expandActionView() {
        return ((SupportMenuItem) this.b).expandActionView();
    }

    public boolean collapseActionView() {
        return ((SupportMenuItem) this.b).collapseActionView();
    }

    public boolean isActionViewExpanded() {
        return ((SupportMenuItem) this.b).isActionViewExpanded();
    }

    public MenuItem setOnActionExpandListener(OnActionExpandListener onActionExpandListener) {
        ((SupportMenuItem) this.b).setOnActionExpandListener(onActionExpandListener != null ? new OnActionExpandListenerWrapper(onActionExpandListener) : null);
        return this;
    }

    public MenuItem setContentDescription(CharSequence charSequence) {
        ((SupportMenuItem) this.b).setContentDescription(charSequence);
        return this;
    }

    public CharSequence getContentDescription() {
        return ((SupportMenuItem) this.b).getContentDescription();
    }

    public MenuItem setTooltipText(CharSequence charSequence) {
        ((SupportMenuItem) this.b).setTooltipText(charSequence);
        return this;
    }

    public CharSequence getTooltipText() {
        return ((SupportMenuItem) this.b).getTooltipText();
    }

    public MenuItem setIconTintList(ColorStateList colorStateList) {
        ((SupportMenuItem) this.b).setIconTintList(colorStateList);
        return this;
    }

    public ColorStateList getIconTintList() {
        return ((SupportMenuItem) this.b).getIconTintList();
    }

    public MenuItem setIconTintMode(Mode mode) {
        ((SupportMenuItem) this.b).setIconTintMode(mode);
        return this;
    }

    public Mode getIconTintMode() {
        return ((SupportMenuItem) this.b).getIconTintMode();
    }

    public void setExclusiveCheckable(boolean z) {
        try {
            if (this.c == null) {
                this.c = ((SupportMenuItem) this.b).getClass().getDeclaredMethod("setExclusiveCheckable", new Class[]{Boolean.TYPE});
            }
            this.c.invoke(this.b, new Object[]{Boolean.valueOf(z)});
        } catch (Exception e) {
            Log.w("MenuItemWrapper", "Error while calling setExclusiveCheckable", e);
        }
    }

    /* access modifiers changed from: 0000 */
    public ActionProviderWrapper a(android.view.ActionProvider actionProvider) {
        return new ActionProviderWrapper(this.a, actionProvider);
    }
}
