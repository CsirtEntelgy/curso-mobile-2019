package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.auth.AuthScope;
import cz.msebera.android.httpclient.auth.Credentials;
import cz.msebera.android.httpclient.client.CredentialsProvider;
import cz.msebera.android.httpclient.util.Args;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class BasicCredentialsProvider implements CredentialsProvider {
    private final ConcurrentHashMap<AuthScope, Credentials> a = new ConcurrentHashMap<>();

    public void setCredentials(AuthScope authScope, Credentials credentials) {
        Args.notNull(authScope, "Authentication scope");
        this.a.put(authScope, credentials);
    }

    private static Credentials a(Map<AuthScope, Credentials> map, AuthScope authScope) {
        Credentials credentials = (Credentials) map.get(authScope);
        if (credentials != null) {
            return credentials;
        }
        int i = -1;
        AuthScope authScope2 = null;
        for (AuthScope authScope3 : map.keySet()) {
            int match = authScope.match(authScope3);
            if (match > i) {
                authScope2 = authScope3;
                i = match;
            }
        }
        return authScope2 != null ? (Credentials) map.get(authScope2) : credentials;
    }

    public Credentials getCredentials(AuthScope authScope) {
        Args.notNull(authScope, "Authentication scope");
        return a(this.a, authScope);
    }

    public void clear() {
        this.a.clear();
    }

    public String toString() {
        return this.a.toString();
    }
}
