package cz.msebera.android.httpclient.impl.cookie;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.cookie.CommonCookieAttributeHandler;
import cz.msebera.android.httpclient.cookie.SetCookie;
import cz.msebera.android.httpclient.util.Args;
import cz.msebera.android.httpclient.util.TextUtils;
import java.util.Date;
import java.util.regex.Pattern;

@Immutable
public class LaxMaxAgeHandler extends AbstractCookieAttributeHandler implements CommonCookieAttributeHandler {
    private static final Pattern a = Pattern.compile("^\\-?[0-9]+$");

    public String getAttributeName() {
        return "max-age";
    }

    public void parse(SetCookie setCookie, String str) {
        Args.notNull(setCookie, "Cookie");
        if (!TextUtils.isBlank(str) && a.matcher(str).matches()) {
            try {
                int parseInt = Integer.parseInt(str);
                setCookie.setExpiryDate(parseInt >= 0 ? new Date(System.currentTimeMillis() + (((long) parseInt) * 1000)) : new Date(Long.MIN_VALUE));
            } catch (NumberFormatException unused) {
            }
        }
    }
}
