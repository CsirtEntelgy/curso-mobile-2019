package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.annotation.GuardedBy;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.client.CookieStore;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieIdentityComparator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

@ThreadSafe
public class BasicCookieStore implements CookieStore, Serializable {
    private static final long serialVersionUID = -7581093305228232025L;
    @GuardedBy("this")
    private final TreeSet<Cookie> a = new TreeSet<>(new CookieIdentityComparator());

    public synchronized void addCookie(Cookie cookie) {
        if (cookie != null) {
            this.a.remove(cookie);
            if (!cookie.isExpired(new Date())) {
                this.a.add(cookie);
            }
        }
    }

    public synchronized void addCookies(Cookie[] cookieArr) {
        if (cookieArr != null) {
            for (Cookie addCookie : cookieArr) {
                addCookie(addCookie);
            }
        }
    }

    public synchronized List<Cookie> getCookies() {
        return new ArrayList(this.a);
    }

    public synchronized boolean clearExpired(Date date) {
        boolean z = false;
        if (date == null) {
            return false;
        }
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            if (((Cookie) it.next()).isExpired(date)) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    public synchronized void clear() {
        this.a.clear();
    }

    public synchronized String toString() {
        return this.a.toString();
    }
}
