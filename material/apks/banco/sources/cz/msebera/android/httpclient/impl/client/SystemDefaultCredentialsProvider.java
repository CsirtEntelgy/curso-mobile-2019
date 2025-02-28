package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.auth.AuthScope;
import cz.msebera.android.httpclient.auth.Credentials;
import cz.msebera.android.httpclient.auth.NTCredentials;
import cz.msebera.android.httpclient.auth.UsernamePasswordCredentials;
import cz.msebera.android.httpclient.client.CredentialsProvider;
import cz.msebera.android.httpclient.util.Args;
import java.net.Authenticator;
import java.net.Authenticator.RequestorType;
import java.net.PasswordAuthentication;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class SystemDefaultCredentialsProvider implements CredentialsProvider {
    private static final Map<String, String> a = new ConcurrentHashMap();
    private final BasicCredentialsProvider b = new BasicCredentialsProvider();

    static {
        a.put("Basic".toUpperCase(Locale.ROOT), "Basic");
        a.put("Digest".toUpperCase(Locale.ROOT), "Digest");
        a.put("NTLM".toUpperCase(Locale.ROOT), "NTLM");
        a.put("Negotiate".toUpperCase(Locale.ROOT), "SPNEGO");
        a.put("Kerberos".toUpperCase(Locale.ROOT), "Kerberos");
    }

    private static String a(String str) {
        if (str == null) {
            return null;
        }
        String str2 = (String) a.get(str);
        if (str2 != null) {
            str = str2;
        }
        return str;
    }

    public void setCredentials(AuthScope authScope, Credentials credentials) {
        this.b.setCredentials(authScope, credentials);
    }

    private static PasswordAuthentication a(AuthScope authScope, RequestorType requestorType) {
        String host = authScope.getHost();
        int port = authScope.getPort();
        HttpHost origin = authScope.getOrigin();
        String str = origin != null ? origin.getSchemeName() : port == 443 ? "https" : HttpHost.DEFAULT_SCHEME_NAME;
        return Authenticator.requestPasswordAuthentication(host, null, port, str, null, a(authScope.getScheme()), null, requestorType);
    }

    public Credentials getCredentials(AuthScope authScope) {
        Args.notNull(authScope, "Auth scope");
        Credentials credentials = this.b.getCredentials(authScope);
        if (credentials != null) {
            return credentials;
        }
        if (authScope.getHost() != null) {
            PasswordAuthentication a2 = a(authScope, RequestorType.SERVER);
            if (a2 == null) {
                a2 = a(authScope, RequestorType.PROXY);
            }
            if (a2 != null) {
                String property = System.getProperty("http.auth.ntlm.domain");
                if (property != null) {
                    return new NTCredentials(a2.getUserName(), new String(a2.getPassword()), null, property);
                }
                if ("NTLM".equalsIgnoreCase(authScope.getScheme())) {
                    return new NTCredentials(a2.getUserName(), new String(a2.getPassword()), null, null);
                }
                return new UsernamePasswordCredentials(a2.getUserName(), new String(a2.getPassword()));
            }
        }
        return null;
    }

    public void clear() {
        this.b.clear();
    }
}
