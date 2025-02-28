package cz.msebera.android.httpclient.impl.client;

import cz.msebera.android.httpclient.HttpHost;
import cz.msebera.android.httpclient.annotation.ThreadSafe;
import cz.msebera.android.httpclient.auth.AuthScheme;
import cz.msebera.android.httpclient.client.AuthCache;
import cz.msebera.android.httpclient.conn.SchemePortResolver;
import cz.msebera.android.httpclient.conn.UnsupportedSchemeException;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;
import cz.msebera.android.httpclient.impl.conn.DefaultSchemePortResolver;
import cz.msebera.android.httpclient.util.Args;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class BasicAuthCache implements AuthCache {
    private final Map<HttpHost, byte[]> a;
    private final SchemePortResolver b;
    public HttpClientAndroidLog log;

    public BasicAuthCache(SchemePortResolver schemePortResolver) {
        this.log = new HttpClientAndroidLog(getClass());
        this.a = new ConcurrentHashMap();
        if (schemePortResolver == null) {
            schemePortResolver = DefaultSchemePortResolver.INSTANCE;
        }
        this.b = schemePortResolver;
    }

    public BasicAuthCache() {
        this(null);
    }

    /* access modifiers changed from: protected */
    public HttpHost getKey(HttpHost httpHost) {
        if (httpHost.getPort() > 0) {
            return httpHost;
        }
        try {
            return new HttpHost(httpHost.getHostName(), this.b.resolve(httpHost), httpHost.getSchemeName());
        } catch (UnsupportedSchemeException unused) {
            return httpHost;
        }
    }

    public void put(HttpHost httpHost, AuthScheme authScheme) {
        Args.notNull(httpHost, "HTTP host");
        if (authScheme != null) {
            if (authScheme instanceof Serializable) {
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(authScheme);
                    objectOutputStream.close();
                    this.a.put(getKey(httpHost), byteArrayOutputStream.toByteArray());
                } catch (IOException e) {
                    if (this.log.isWarnEnabled()) {
                        this.log.warn("Unexpected I/O error while serializing auth scheme", e);
                    }
                }
            } else if (this.log.isDebugEnabled()) {
                HttpClientAndroidLog httpClientAndroidLog = this.log;
                StringBuilder sb = new StringBuilder();
                sb.append("Auth scheme ");
                sb.append(authScheme.getClass());
                sb.append(" is not serializable");
                httpClientAndroidLog.debug(sb.toString());
            }
        }
    }

    public AuthScheme get(HttpHost httpHost) {
        Args.notNull(httpHost, "HTTP host");
        byte[] bArr = (byte[]) this.a.get(getKey(httpHost));
        if (bArr == null) {
            return null;
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bArr));
            AuthScheme authScheme = (AuthScheme) objectInputStream.readObject();
            objectInputStream.close();
            return authScheme;
        } catch (IOException e) {
            if (this.log.isWarnEnabled()) {
                this.log.warn("Unexpected I/O error while de-serializing auth scheme", e);
            }
            return null;
        } catch (ClassNotFoundException e2) {
            if (this.log.isWarnEnabled()) {
                this.log.warn("Unexpected error while de-serializing auth scheme", e2);
            }
            return null;
        }
    }

    public void remove(HttpHost httpHost) {
        Args.notNull(httpHost, "HTTP host");
        this.a.remove(getKey(httpHost));
    }

    public void clear() {
        this.a.clear();
    }

    public String toString() {
        return this.a.toString();
    }
}
