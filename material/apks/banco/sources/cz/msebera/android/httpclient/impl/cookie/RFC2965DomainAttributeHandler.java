package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.cookie.ClientCookie;
import cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.cookie.CookieOrigin;
import cz.msebera.android.httpclient.cookie.CookieRestrictionViolationException;
import cz.msebera.android.httpclient.cookie.MalformedCookieException;
import cz.msebera.android.httpclient.cookie.SetCookie;
import cz.msebera.android.httpclient.util.Args;
import java.util.Locale;

@Immutable
public class RFC2965DomainAttributeHandler implements CommonCookieAttributeHandler {
    public String getAttributeName() {
        return ClientCookie.DOMAIN_ATTR;
    }

    public void parse(SetCookie setCookie, String str) {
        Args.notNull(setCookie, "Cookie");
        if (str == null) {
            throw new MalformedCookieException("Missing value for domain attribute");
        } else if (str.trim().isEmpty()) {
            throw new MalformedCookieException("Blank value for domain attribute");
        } else {
            String lowerCase = str.toLowerCase(Locale.ROOT);
            if (!str.startsWith(".")) {
                StringBuilder sb = new StringBuilder();
                sb.append('.');
                sb.append(lowerCase);
                lowerCase = sb.toString();
            }
            setCookie.setDomain(lowerCase);
        }
    }

    public boolean domainMatch(String str, String str2) {
        return str.equals(str2) || (str2.startsWith(".") && str.endsWith(str2));
    }

    public void validate(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String lowerCase = cookieOrigin.getHost().toLowerCase(Locale.ROOT);
        if (cookie.getDomain() == null) {
            throw new CookieRestrictionViolationException("Invalid cookie state: domain not specified");
        }
        String lowerCase2 = cookie.getDomain().toLowerCase(Locale.ROOT);
        if (!(cookie instanceof ClientCookie) || !((ClientCookie) cookie).containsAttribute(ClientCookie.DOMAIN_ATTR)) {
            if (!cookie.getDomain().equals(lowerCase)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Illegal domain attribute: \"");
                sb.append(cookie.getDomain());
                sb.append("\".");
                sb.append("Domain of origin: \"");
                sb.append(lowerCase);
                sb.append("\"");
                throw new CookieRestrictionViolationException(sb.toString());
            }
        } else if (!lowerCase2.startsWith(".")) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Domain attribute \"");
            sb2.append(cookie.getDomain());
            sb2.append("\" violates RFC 2109: domain must start with a dot");
            throw new CookieRestrictionViolationException(sb2.toString());
        } else {
            int indexOf = lowerCase2.indexOf(46, 1);
            if ((indexOf < 0 || indexOf == lowerCase2.length() - 1) && !lowerCase2.equals(".local")) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Domain attribute \"");
                sb3.append(cookie.getDomain());
                sb3.append("\" violates RFC 2965: the value contains no embedded dots ");
                sb3.append("and the value is not .local");
                throw new CookieRestrictionViolationException(sb3.toString());
            } else if (!domainMatch(lowerCase, lowerCase2)) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Domain attribute \"");
                sb4.append(cookie.getDomain());
                sb4.append("\" violates RFC 2965: effective host name does not ");
                sb4.append("domain-match domain attribute.");
                throw new CookieRestrictionViolationException(sb4.toString());
            } else if (lowerCase.substring(0, lowerCase.length() - lowerCase2.length()).indexOf(46) != -1) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("Domain attribute \"");
                sb5.append(cookie.getDomain());
                sb5.append("\" violates RFC 2965: ");
                sb5.append("effective host minus domain may not contain any dots");
                throw new CookieRestrictionViolationException(sb5.toString());
            }
        }
    }

    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        Args.notNull(cookie, "Cookie");
        Args.notNull(cookieOrigin, "Cookie origin");
        String lowerCase = cookieOrigin.getHost().toLowerCase(Locale.ROOT);
        String domain = cookie.getDomain();
        boolean z = false;
        if (!domainMatch(lowerCase, domain)) {
            return false;
        }
        if (lowerCase.substring(0, lowerCase.length() - domain.length()).indexOf(46) == -1) {
            z = true;
        }
        return z;
    }
}
