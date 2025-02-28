package cz.msebera.android.httpclient.impl.client.cache;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HeaderElement;
import cz.msebera.android.httpclient.HttpRequest;
import cz.msebera.android.httpclient.HttpVersion;
import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.client.cache.HeaderConstants;
import cz.msebera.android.httpclient.extras.HttpClientAndroidLog;

@Immutable
class CacheableRequestPolicy {
    public HttpClientAndroidLog a = new HttpClientAndroidLog(getClass());

    CacheableRequestPolicy() {
    }

    public boolean a(HttpRequest httpRequest) {
        String method = httpRequest.getRequestLine().getMethod();
        if (HttpVersion.HTTP_1_1.compareToVersion(httpRequest.getRequestLine().getProtocolVersion()) != 0) {
            this.a.trace("non-HTTP/1.1 request was not serveable from cache");
            return false;
        } else if (!method.equals("GET") && !method.equals("HEAD")) {
            this.a.trace("non-GET or non-HEAD request was not serveable from cache");
            return false;
        } else if (httpRequest.getHeaders("Pragma").length > 0) {
            this.a.trace("request with Pragma header was not serveable from cache");
            return false;
        } else {
            for (Header elements : httpRequest.getHeaders("Cache-Control")) {
                HeaderElement[] elements2 = elements.getElements();
                int length = elements2.length;
                int i = 0;
                while (i < length) {
                    HeaderElement headerElement = elements2[i];
                    if (HeaderConstants.CACHE_CONTROL_NO_STORE.equalsIgnoreCase(headerElement.getName())) {
                        this.a.trace("Request with no-store was not serveable from cache");
                        return false;
                    } else if (HeaderConstants.CACHE_CONTROL_NO_CACHE.equalsIgnoreCase(headerElement.getName())) {
                        this.a.trace("Request with no-cache was not serveable from cache");
                        return false;
                    } else {
                        i++;
                    }
                }
            }
            this.a.trace("Request was serveable from cache");
            return true;
        }
    }
}
