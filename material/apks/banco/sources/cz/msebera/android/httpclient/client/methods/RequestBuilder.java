package cz.msebera.android.httpclient.client.methods;

import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderIterator;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.ProtocolVersion;
import cz.msebera.android.httpclient.annotation.NotThreadSafe;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.utils.URIBuilder;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.message.HeaderGroup;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.Args;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@NotThreadSafe
public class RequestBuilder {
    private String a;
    private Charset b;
    private ProtocolVersion c;
    private URI d;
    private HeaderGroup e;
    private HttpEntity f;
    private List<NameValuePair> g;
    private RequestConfig h;

    static class InternalEntityEclosingRequest extends HttpEntityEnclosingRequestBase {
        private final String a;

        InternalEntityEclosingRequest(String str) {
            this.a = str;
        }

        public String getMethod() {
            return this.a;
        }
    }

    static class InternalRequest extends HttpRequestBase {
        private final String a;

        InternalRequest(String str) {
            this.a = str;
        }

        public String getMethod() {
            return this.a;
        }
    }

    RequestBuilder(String str) {
        this.b = Consts.UTF_8;
        this.a = str;
    }

    RequestBuilder(String str, URI uri) {
        this.a = str;
        this.d = uri;
    }

    RequestBuilder(String str, String str2) {
        this.a = str;
        this.d = str2 != null ? URI.create(str2) : null;
    }

    RequestBuilder() {
        this(null);
    }

    public static RequestBuilder create(String str) {
        Args.notBlank(str, "HTTP method");
        return new RequestBuilder(str);
    }

    public static RequestBuilder get() {
        return new RequestBuilder("GET");
    }

    public static RequestBuilder get(URI uri) {
        return new RequestBuilder("GET", uri);
    }

    public static RequestBuilder get(String str) {
        return new RequestBuilder("GET", str);
    }

    public static RequestBuilder head() {
        return new RequestBuilder("HEAD");
    }

    public static RequestBuilder head(URI uri) {
        return new RequestBuilder("HEAD", uri);
    }

    public static RequestBuilder head(String str) {
        return new RequestBuilder("HEAD", str);
    }

    public static RequestBuilder patch() {
        return new RequestBuilder("PATCH");
    }

    public static RequestBuilder patch(URI uri) {
        return new RequestBuilder("PATCH", uri);
    }

    public static RequestBuilder patch(String str) {
        return new RequestBuilder("PATCH", str);
    }

    public static RequestBuilder post() {
        return new RequestBuilder("POST");
    }

    public static RequestBuilder post(URI uri) {
        return new RequestBuilder("POST", uri);
    }

    public static RequestBuilder post(String str) {
        return new RequestBuilder("POST", str);
    }

    public static RequestBuilder put() {
        return new RequestBuilder("PUT");
    }

    public static RequestBuilder put(URI uri) {
        return new RequestBuilder("PUT", uri);
    }

    public static RequestBuilder put(String str) {
        return new RequestBuilder("PUT", str);
    }

    public static RequestBuilder delete() {
        return new RequestBuilder("DELETE");
    }

    public static RequestBuilder delete(URI uri) {
        return new RequestBuilder("DELETE", uri);
    }

    public static RequestBuilder delete(String str) {
        return new RequestBuilder("DELETE", str);
    }

    public static RequestBuilder trace() {
        return new RequestBuilder("TRACE");
    }

    public static RequestBuilder trace(URI uri) {
        return new RequestBuilder("TRACE", uri);
    }

    public static RequestBuilder trace(String str) {
        return new RequestBuilder("TRACE", str);
    }

    public static RequestBuilder options() {
        return new RequestBuilder("OPTIONS");
    }

    public static RequestBuilder options(URI uri) {
        return new RequestBuilder("OPTIONS", uri);
    }

    public static RequestBuilder options(String str) {
        return new RequestBuilder("OPTIONS", str);
    }

    public static RequestBuilder copy(HttpRequest httpRequest) {
        Args.notNull(httpRequest, "HTTP request");
        return new RequestBuilder().a(httpRequest);
    }

    private RequestBuilder a(HttpRequest httpRequest) {
        URI uri;
        if (httpRequest == null) {
            return this;
        }
        this.a = httpRequest.getRequestLine().getMethod();
        this.c = httpRequest.getRequestLine().getProtocolVersion();
        if (this.e == null) {
            this.e = new HeaderGroup();
        }
        this.e.clear();
        this.e.setHeaders(httpRequest.getAllHeaders());
        this.g = null;
        this.f = null;
        if (httpRequest instanceof HttpEntityEnclosingRequest) {
            HttpEntity entity = ((HttpEntityEnclosingRequest) httpRequest).getEntity();
            ContentType contentType = ContentType.get(entity);
            if (contentType == null || !contentType.getMimeType().equals(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())) {
                this.f = entity;
            } else {
                try {
                    List<NameValuePair> parse = URLEncodedUtils.parse(entity);
                    if (!parse.isEmpty()) {
                        this.g = parse;
                    }
                } catch (IOException unused) {
                }
            }
        }
        if (httpRequest instanceof HttpUriRequest) {
            uri = ((HttpUriRequest) httpRequest).getURI();
        } else {
            uri = URI.create(httpRequest.getRequestLine().getUri());
        }
        URIBuilder uRIBuilder = new URIBuilder(uri);
        if (this.g == null) {
            List<NameValuePair> queryParams = uRIBuilder.getQueryParams();
            if (!queryParams.isEmpty()) {
                this.g = queryParams;
                uRIBuilder.clearParameters();
            } else {
                this.g = null;
            }
        }
        try {
            this.d = uRIBuilder.build();
        } catch (URISyntaxException unused2) {
            this.d = uri;
        }
        if (httpRequest instanceof Configurable) {
            this.h = ((Configurable) httpRequest).getConfig();
        } else {
            this.h = null;
        }
        return this;
    }

    public RequestBuilder setCharset(Charset charset) {
        this.b = charset;
        return this;
    }

    public Charset getCharset() {
        return this.b;
    }

    public String getMethod() {
        return this.a;
    }

    public ProtocolVersion getVersion() {
        return this.c;
    }

    public RequestBuilder setVersion(ProtocolVersion protocolVersion) {
        this.c = protocolVersion;
        return this;
    }

    public URI getUri() {
        return this.d;
    }

    public RequestBuilder setUri(URI uri) {
        this.d = uri;
        return this;
    }

    public RequestBuilder setUri(String str) {
        this.d = str != null ? URI.create(str) : null;
        return this;
    }

    public Header getFirstHeader(String str) {
        if (this.e != null) {
            return this.e.getFirstHeader(str);
        }
        return null;
    }

    public Header getLastHeader(String str) {
        if (this.e != null) {
            return this.e.getLastHeader(str);
        }
        return null;
    }

    public Header[] getHeaders(String str) {
        if (this.e != null) {
            return this.e.getHeaders(str);
        }
        return null;
    }

    public RequestBuilder addHeader(Header header) {
        if (this.e == null) {
            this.e = new HeaderGroup();
        }
        this.e.addHeader(header);
        return this;
    }

    public RequestBuilder addHeader(String str, String str2) {
        if (this.e == null) {
            this.e = new HeaderGroup();
        }
        this.e.addHeader(new BasicHeader(str, str2));
        return this;
    }

    public RequestBuilder removeHeader(Header header) {
        if (this.e == null) {
            this.e = new HeaderGroup();
        }
        this.e.removeHeader(header);
        return this;
    }

    public RequestBuilder removeHeaders(String str) {
        if (str == null || this.e == null) {
            return this;
        }
        HeaderIterator it = this.e.iterator();
        while (it.hasNext()) {
            if (str.equalsIgnoreCase(it.nextHeader().getName())) {
                it.remove();
            }
        }
        return this;
    }

    public RequestBuilder setHeader(Header header) {
        if (this.e == null) {
            this.e = new HeaderGroup();
        }
        this.e.updateHeader(header);
        return this;
    }

    public RequestBuilder setHeader(String str, String str2) {
        if (this.e == null) {
            this.e = new HeaderGroup();
        }
        this.e.updateHeader(new BasicHeader(str, str2));
        return this;
    }

    public HttpEntity getEntity() {
        return this.f;
    }

    public RequestBuilder setEntity(HttpEntity httpEntity) {
        this.f = httpEntity;
        return this;
    }

    public List<NameValuePair> getParameters() {
        return this.g != null ? new ArrayList(this.g) : new ArrayList();
    }

    public RequestBuilder addParameter(NameValuePair nameValuePair) {
        Args.notNull(nameValuePair, "Name value pair");
        if (this.g == null) {
            this.g = new LinkedList();
        }
        this.g.add(nameValuePair);
        return this;
    }

    public RequestBuilder addParameter(String str, String str2) {
        return addParameter(new BasicNameValuePair(str, str2));
    }

    public RequestBuilder addParameters(NameValuePair... nameValuePairArr) {
        for (NameValuePair addParameter : nameValuePairArr) {
            addParameter(addParameter);
        }
        return this;
    }

    public RequestConfig getConfig() {
        return this.h;
    }

    public RequestBuilder setConfig(RequestConfig requestConfig) {
        this.h = requestConfig;
        return this;
    }

    public HttpUriRequest build() {
        HttpRequestBase httpRequestBase;
        URI create = this.d != null ? this.d : URI.create("/");
        HttpEntity httpEntity = this.f;
        if (this.g != null && !this.g.isEmpty()) {
            if (httpEntity != null || (!"POST".equalsIgnoreCase(this.a) && !"PUT".equalsIgnoreCase(this.a))) {
                try {
                    create = new URIBuilder(create).setCharset(this.b).addParameters(this.g).build();
                } catch (URISyntaxException unused) {
                }
            } else {
                httpEntity = new UrlEncodedFormEntity((Iterable<? extends NameValuePair>) this.g, HTTP.DEF_CONTENT_CHARSET);
            }
        }
        if (httpEntity == null) {
            httpRequestBase = new InternalRequest(this.a);
        } else {
            InternalEntityEclosingRequest internalEntityEclosingRequest = new InternalEntityEclosingRequest(this.a);
            internalEntityEclosingRequest.setEntity(httpEntity);
            httpRequestBase = internalEntityEclosingRequest;
        }
        httpRequestBase.setProtocolVersion(this.c);
        httpRequestBase.setURI(create);
        if (this.e != null) {
            httpRequestBase.setHeaders(this.e.getAllHeaders());
        }
        httpRequestBase.setConfig(this.h);
        return httpRequestBase;
    }
}
