package cz.msebera.android.httpclient.client.utils;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.conn.util.InetAddressUtils;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@NotThreadSafe
public class URIBuilder {
    private String a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private int g;
    private String h;
    private String i;
    private String j;
    private List<NameValuePair> k;
    private String l;
    private Charset m;
    private String n;
    private String o;

    public URIBuilder() {
        this.g = -1;
    }

    public URIBuilder(String str) {
        a(new URI(str));
    }

    public URIBuilder(URI uri) {
        a(uri);
    }

    public URIBuilder setCharset(Charset charset) {
        this.m = charset;
        return this;
    }

    public Charset getCharset() {
        return this.m;
    }

    private List<NameValuePair> a(String str, Charset charset) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        return URLEncodedUtils.parse(str, charset);
    }

    public URI build() {
        return new URI(a());
    }

    private String a() {
        StringBuilder sb = new StringBuilder();
        if (this.a != null) {
            sb.append(this.a);
            sb.append(':');
        }
        if (this.b != null) {
            sb.append(this.b);
        } else {
            if (this.c != null) {
                sb.append("//");
                sb.append(this.c);
            } else if (this.f != null) {
                sb.append("//");
                if (this.e != null) {
                    sb.append(this.e);
                    sb.append("@");
                } else if (this.d != null) {
                    sb.append(a(this.d));
                    sb.append("@");
                }
                if (InetAddressUtils.isIPv6Address(this.f)) {
                    sb.append("[");
                    sb.append(this.f);
                    sb.append("]");
                } else {
                    sb.append(this.f);
                }
                if (this.g >= 0) {
                    sb.append(":");
                    sb.append(this.g);
                }
            }
            if (this.i != null) {
                sb.append(d(this.i));
            } else if (this.h != null) {
                sb.append(b(d(this.h)));
            }
            if (this.j != null) {
                sb.append("?");
                sb.append(this.j);
            } else if (this.k != null) {
                sb.append("?");
                sb.append(a(this.k));
            } else if (this.l != null) {
                sb.append("?");
                sb.append(c(this.l));
            }
        }
        if (this.o != null) {
            sb.append("#");
            sb.append(this.o);
        } else if (this.n != null) {
            sb.append("#");
            sb.append(c(this.n));
        }
        return sb.toString();
    }

    private void a(URI uri) {
        this.a = uri.getScheme();
        this.b = uri.getRawSchemeSpecificPart();
        this.c = uri.getRawAuthority();
        this.f = uri.getHost();
        this.g = uri.getPort();
        this.e = uri.getRawUserInfo();
        this.d = uri.getUserInfo();
        this.i = uri.getRawPath();
        this.h = uri.getPath();
        this.j = uri.getRawQuery();
        this.k = a(uri.getRawQuery(), this.m != null ? this.m : Consts.UTF_8);
        this.o = uri.getRawFragment();
        this.n = uri.getFragment();
    }

    private String a(String str) {
        return URLEncodedUtils.a(str, this.m != null ? this.m : Consts.UTF_8);
    }

    private String b(String str) {
        return URLEncodedUtils.c(str, this.m != null ? this.m : Consts.UTF_8);
    }

    private String a(List<NameValuePair> list) {
        return URLEncodedUtils.format((Iterable<? extends NameValuePair>) list, this.m != null ? this.m : Consts.UTF_8);
    }

    private String c(String str) {
        return URLEncodedUtils.b(str, this.m != null ? this.m : Consts.UTF_8);
    }

    public URIBuilder setScheme(String str) {
        this.a = str;
        return this;
    }

    public URIBuilder setUserInfo(String str) {
        this.d = str;
        this.b = null;
        this.c = null;
        this.e = null;
        return this;
    }

    public URIBuilder setUserInfo(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(':');
        sb.append(str2);
        return setUserInfo(sb.toString());
    }

    public URIBuilder setHost(String str) {
        this.f = str;
        this.b = null;
        this.c = null;
        return this;
    }

    public URIBuilder setPort(int i2) {
        if (i2 < 0) {
            i2 = -1;
        }
        this.g = i2;
        this.b = null;
        this.c = null;
        return this;
    }

    public URIBuilder setPath(String str) {
        this.h = str;
        this.b = null;
        this.i = null;
        return this;
    }

    public URIBuilder removeQuery() {
        this.k = null;
        this.l = null;
        this.j = null;
        this.b = null;
        return this;
    }

    @Deprecated
    public URIBuilder setQuery(String str) {
        this.k = a(str, this.m != null ? this.m : Consts.UTF_8);
        this.l = null;
        this.j = null;
        this.b = null;
        return this;
    }

    public URIBuilder setParameters(List<NameValuePair> list) {
        if (this.k == null) {
            this.k = new ArrayList();
        } else {
            this.k.clear();
        }
        this.k.addAll(list);
        this.j = null;
        this.b = null;
        this.l = null;
        return this;
    }

    public URIBuilder addParameters(List<NameValuePair> list) {
        if (this.k == null) {
            this.k = new ArrayList();
        }
        this.k.addAll(list);
        this.j = null;
        this.b = null;
        this.l = null;
        return this;
    }

    public URIBuilder setParameters(NameValuePair... nameValuePairArr) {
        if (this.k == null) {
            this.k = new ArrayList();
        } else {
            this.k.clear();
        }
        for (NameValuePair add : nameValuePairArr) {
            this.k.add(add);
        }
        this.j = null;
        this.b = null;
        this.l = null;
        return this;
    }

    public URIBuilder addParameter(String str, String str2) {
        if (this.k == null) {
            this.k = new ArrayList();
        }
        this.k.add(new BasicNameValuePair(str, str2));
        this.j = null;
        this.b = null;
        this.l = null;
        return this;
    }

    public URIBuilder setParameter(String str, String str2) {
        if (this.k == null) {
            this.k = new ArrayList();
        }
        if (!this.k.isEmpty()) {
            Iterator it = this.k.iterator();
            while (it.hasNext()) {
                if (((NameValuePair) it.next()).getName().equals(str)) {
                    it.remove();
                }
            }
        }
        this.k.add(new BasicNameValuePair(str, str2));
        this.j = null;
        this.b = null;
        this.l = null;
        return this;
    }

    public URIBuilder clearParameters() {
        this.k = null;
        this.j = null;
        this.b = null;
        return this;
    }

    public URIBuilder setCustomQuery(String str) {
        this.l = str;
        this.j = null;
        this.b = null;
        this.k = null;
        return this;
    }

    public URIBuilder setFragment(String str) {
        this.n = str;
        this.o = null;
        return this;
    }

    public boolean isAbsolute() {
        return this.a != null;
    }

    public boolean isOpaque() {
        return this.h == null;
    }

    public String getScheme() {
        return this.a;
    }

    public String getUserInfo() {
        return this.d;
    }

    public String getHost() {
        return this.f;
    }

    public int getPort() {
        return this.g;
    }

    public String getPath() {
        return this.h;
    }

    public List<NameValuePair> getQueryParams() {
        if (this.k != null) {
            return new ArrayList(this.k);
        }
        return new ArrayList();
    }

    public String getFragment() {
        return this.n;
    }

    public String toString() {
        return a();
    }

    private static String d(String str) {
        if (str == null) {
            return null;
        }
        int i2 = 0;
        while (i2 < str.length() && str.charAt(i2) == '/') {
            i2++;
        }
        if (i2 > 1) {
            str = str.substring(i2 - 1);
        }
        return str;
    }
}
