package cz.msebera.android.httpclient.cookie;

import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.config.Lookup;
import cz.msebera.android.httpclient.params.HttpParams;
import cz.msebera.android.httpclient.protocol.HttpContext;
import cz.msebera.android.httpclient.util.Args;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
@Deprecated
public final class CookieSpecRegistry implements Lookup<CookieSpecProvider> {
    private final ConcurrentHashMap<String, CookieSpecFactory> a = new ConcurrentHashMap<>();

    public void register(String str, CookieSpecFactory cookieSpecFactory) {
        Args.notNull(str, "Name");
        Args.notNull(cookieSpecFactory, "Cookie spec factory");
        this.a.put(str.toLowerCase(Locale.ENGLISH), cookieSpecFactory);
    }

    public void unregister(String str) {
        Args.notNull(str, "Id");
        this.a.remove(str.toLowerCase(Locale.ENGLISH));
    }

    public CookieSpec getCookieSpec(String str, HttpParams httpParams) {
        Args.notNull(str, "Name");
        CookieSpecFactory cookieSpecFactory = (CookieSpecFactory) this.a.get(str.toLowerCase(Locale.ENGLISH));
        if (cookieSpecFactory != null) {
            return cookieSpecFactory.newInstance(httpParams);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unsupported cookie spec: ");
        sb.append(str);
        throw new IllegalStateException(sb.toString());
    }

    public CookieSpec getCookieSpec(String str) {
        return getCookieSpec(str, null);
    }

    public List<String> getSpecNames() {
        return new ArrayList(this.a.keySet());
    }

    public void setItems(Map<String, CookieSpecFactory> map) {
        if (map != null) {
            this.a.clear();
            this.a.putAll(map);
        }
    }

    public CookieSpecProvider lookup(final String str) {
        return new CookieSpecProvider() {
            public CookieSpec create(HttpContext httpContext) {
                return CookieSpecRegistry.this.getCookieSpec(str, ((HttpRequest) httpContext.getAttribute("http.request")).getParams());
            }
        };
    }
}
