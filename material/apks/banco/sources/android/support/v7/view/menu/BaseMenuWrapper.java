package android.support.v7.view.menu;

import android.content.Context;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.internal.view.SupportSubMenu;
import android.support.v4.util.ArrayMap;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Iterator;
import java.util.Map;

abstract class BaseMenuWrapper<T> extends BaseWrapper<T> {
    final Context a;
    private Map<SupportMenuItem, MenuItem> c;
    private Map<SupportSubMenu, SubMenu> d;

    BaseMenuWrapper(Context context, T t) {
        super(t);
        this.a = context;
    }

    /* access modifiers changed from: 0000 */
    public final MenuItem a(MenuItem menuItem) {
        if (!(menuItem instanceof SupportMenuItem)) {
            return menuItem;
        }
        SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
        if (this.c == null) {
            this.c = new ArrayMap();
        }
        MenuItem menuItem2 = (MenuItem) this.c.get(menuItem);
        if (menuItem2 == null) {
            menuItem2 = MenuWrapperFactory.wrapSupportMenuItem(this.a, supportMenuItem);
            this.c.put(supportMenuItem, menuItem2);
        }
        return menuItem2;
    }

    /* access modifiers changed from: 0000 */
    public final SubMenu a(SubMenu subMenu) {
        if (!(subMenu instanceof SupportSubMenu)) {
            return subMenu;
        }
        SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu;
        if (this.d == null) {
            this.d = new ArrayMap();
        }
        SubMenu subMenu2 = (SubMenu) this.d.get(supportSubMenu);
        if (subMenu2 == null) {
            subMenu2 = MenuWrapperFactory.wrapSupportSubMenu(this.a, supportSubMenu);
            this.d.put(supportSubMenu, subMenu2);
        }
        return subMenu2;
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        if (this.c != null) {
            this.c.clear();
        }
        if (this.d != null) {
            this.d.clear();
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i) {
        if (this.c != null) {
            Iterator it = this.c.keySet().iterator();
            while (it.hasNext()) {
                if (i == ((MenuItem) it.next()).getGroupId()) {
                    it.remove();
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(int i) {
        if (this.c != null) {
            Iterator it = this.c.keySet().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (i == ((MenuItem) it.next()).getItemId()) {
                        it.remove();
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }
}
