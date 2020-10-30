package org.bouncycastle.i18n;

import ar.com.santander.rio.mbanking.utils.UtilsCuentas;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import org.bouncycastle.i18n.filter.Filter;
import org.bouncycastle.i18n.filter.TrustedInput;
import org.bouncycastle.i18n.filter.UntrustedInput;
import org.bouncycastle.i18n.filter.UntrustedUrlInput;

public class LocalizedMessage {
    public static final String DEFAULT_ENCODING = "ISO-8859-1";
    protected FilteredArguments arguments;
    protected String encoding = "ISO-8859-1";
    protected FilteredArguments extraArgs = null;
    protected Filter filter = null;

    /* renamed from: id reason: collision with root package name */
    protected final String f275id;
    protected ClassLoader loader = null;
    protected final String resource;

    public class FilteredArguments {
        protected static final int FILTER = 1;
        protected static final int FILTER_URL = 2;
        protected static final int NO_FILTER = 0;
        protected int[] argFilterType;
        protected Object[] arguments;
        protected Filter filter;
        protected Object[] filteredArgs;
        protected boolean[] isLocaleSpecific;
        protected Object[] unpackedArgs;

        FilteredArguments(LocalizedMessage localizedMessage) {
            this(new Object[0]);
        }

        FilteredArguments(Object[] objArr) {
            this.filter = null;
            this.arguments = objArr;
            this.unpackedArgs = new Object[objArr.length];
            this.filteredArgs = new Object[objArr.length];
            this.isLocaleSpecific = new boolean[objArr.length];
            this.argFilterType = new int[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                if (objArr[i] instanceof TrustedInput) {
                    this.unpackedArgs[i] = objArr[i].getInput();
                    this.argFilterType[i] = 0;
                } else if (objArr[i] instanceof UntrustedInput) {
                    this.unpackedArgs[i] = objArr[i].getInput();
                    if (objArr[i] instanceof UntrustedUrlInput) {
                        this.argFilterType[i] = 2;
                    } else {
                        this.argFilterType[i] = 1;
                    }
                } else {
                    this.unpackedArgs[i] = objArr[i];
                    this.argFilterType[i] = 1;
                }
                this.isLocaleSpecific[i] = this.unpackedArgs[i] instanceof LocaleString;
            }
        }

        private Object a(int i, Object obj) {
            if (this.filter != null) {
                if (obj == null) {
                    obj = "null";
                }
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        return this.filter.doFilter(obj.toString());
                    case 2:
                        return this.filter.doFilterUrl(obj.toString());
                    default:
                        return null;
                }
            }
            return obj;
        }

        public Object[] getArguments() {
            return this.arguments;
        }

        public Filter getFilter() {
            return this.filter;
        }

        public Object[] getFilteredArgs(Locale locale) {
            Object obj;
            Object[] objArr = new Object[this.unpackedArgs.length];
            for (int i = 0; i < this.unpackedArgs.length; i++) {
                if (this.filteredArgs[i] != null) {
                    obj = this.filteredArgs[i];
                } else {
                    Object obj2 = this.unpackedArgs[i];
                    if (this.isLocaleSpecific[i]) {
                        obj = a(this.argFilterType[i], ((LocaleString) obj2).getLocaleString(locale));
                    } else {
                        obj = a(this.argFilterType[i], obj2);
                        this.filteredArgs[i] = obj;
                    }
                }
                objArr[i] = obj;
            }
            return objArr;
        }

        public boolean isEmpty() {
            return this.unpackedArgs.length == 0;
        }

        public void setFilter(Filter filter2) {
            if (filter2 != this.filter) {
                for (int i = 0; i < this.unpackedArgs.length; i++) {
                    this.filteredArgs[i] = null;
                }
            }
            this.filter = filter2;
        }
    }

    public LocalizedMessage(String str, String str2) {
        if (str == null || str2 == null) {
            throw new NullPointerException();
        }
        this.f275id = str2;
        this.resource = str;
        this.arguments = new FilteredArguments(this);
    }

    public LocalizedMessage(String str, String str2, String str3) {
        if (str == null || str2 == null) {
            throw new NullPointerException();
        }
        this.f275id = str2;
        this.resource = str;
        this.arguments = new FilteredArguments(this);
        if (!Charset.isSupported(str3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("The encoding \"");
            sb.append(str3);
            sb.append("\" is not supported.");
            throw new UnsupportedEncodingException(sb.toString());
        }
        this.encoding = str3;
    }

    public LocalizedMessage(String str, String str2, String str3, Object[] objArr) {
        if (str == null || str2 == null || objArr == null) {
            throw new NullPointerException();
        }
        this.f275id = str2;
        this.resource = str;
        this.arguments = new FilteredArguments(objArr);
        if (!Charset.isSupported(str3)) {
            StringBuilder sb = new StringBuilder();
            sb.append("The encoding \"");
            sb.append(str3);
            sb.append("\" is not supported.");
            throw new UnsupportedEncodingException(sb.toString());
        }
        this.encoding = str3;
    }

    public LocalizedMessage(String str, String str2, Object[] objArr) {
        if (str == null || str2 == null || objArr == null) {
            throw new NullPointerException();
        }
        this.f275id = str2;
        this.resource = str;
        this.arguments = new FilteredArguments(objArr);
    }

    /* access modifiers changed from: protected */
    public String addExtraArgs(String str, Locale locale) {
        if (this.extraArgs == null) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(str);
        Object[] filteredArgs = this.extraArgs.getFilteredArgs(locale);
        for (Object append : filteredArgs) {
            stringBuffer.append(append);
        }
        return stringBuffer.toString();
    }

    /* access modifiers changed from: protected */
    public String formatWithTimeZone(String str, Object[] objArr, Locale locale, TimeZone timeZone) {
        MessageFormat messageFormat = new MessageFormat(UtilsCuentas.SEPARAOR2);
        messageFormat.setLocale(locale);
        messageFormat.applyPattern(str);
        if (!timeZone.equals(TimeZone.getDefault())) {
            Format[] formats = messageFormat.getFormats();
            for (int i = 0; i < formats.length; i++) {
                if (formats[i] instanceof DateFormat) {
                    DateFormat dateFormat = (DateFormat) formats[i];
                    dateFormat.setTimeZone(timeZone);
                    messageFormat.setFormat(i, dateFormat);
                }
            }
        }
        return messageFormat.format(objArr);
    }

    public Object[] getArguments() {
        return this.arguments.getArguments();
    }

    public ClassLoader getClassLoader() {
        return this.loader;
    }

    public String getEntry(String str, Locale locale, TimeZone timeZone) {
        String str2 = this.f275id;
        if (str != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(".");
            sb.append(str);
            str2 = sb.toString();
        }
        String str3 = str2;
        try {
            String string = (this.loader == null ? ResourceBundle.getBundle(this.resource, locale) : ResourceBundle.getBundle(this.resource, locale, this.loader)).getString(str3);
            if (!this.encoding.equals("ISO-8859-1")) {
                string = new String(string.getBytes("ISO-8859-1"), this.encoding);
            }
            if (!this.arguments.isEmpty()) {
                string = formatWithTimeZone(string, this.arguments.getFilteredArgs(locale), locale, timeZone);
            }
            return addExtraArgs(string, locale);
        } catch (MissingResourceException unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Can't find entry ");
            sb2.append(str3);
            sb2.append(" in resource file ");
            sb2.append(this.resource);
            sb2.append(".");
            MissingEntryException missingEntryException = new MissingEntryException(sb2.toString(), this.resource, str3, locale, this.loader != null ? this.loader : getClassLoader());
            throw missingEntryException;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public Object[] getExtraArgs() {
        if (this.extraArgs == null) {
            return null;
        }
        return this.extraArgs.getArguments();
    }

    public Filter getFilter() {
        return this.filter;
    }

    public String getId() {
        return this.f275id;
    }

    public String getResource() {
        return this.resource;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }

    public void setExtraArgument(Object obj) {
        setExtraArguments(new Object[]{obj});
    }

    public void setExtraArguments(Object[] objArr) {
        if (objArr != null) {
            this.extraArgs = new FilteredArguments(objArr);
            this.extraArgs.setFilter(this.filter);
            return;
        }
        this.extraArgs = null;
    }

    public void setFilter(Filter filter2) {
        this.arguments.setFilter(filter2);
        if (this.extraArgs != null) {
            this.extraArgs.setFilter(filter2);
        }
        this.filter = filter2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Resource: \"");
        stringBuffer.append(this.resource);
        stringBuffer.append("\" Id: \"");
        stringBuffer.append(this.f275id);
        stringBuffer.append("\"");
        stringBuffer.append(" Arguments: ");
        stringBuffer.append(this.arguments.getArguments().length);
        stringBuffer.append(" normal");
        if (this.extraArgs != null && this.extraArgs.getArguments().length > 0) {
            stringBuffer.append(", ");
            stringBuffer.append(this.extraArgs.getArguments().length);
            stringBuffer.append(" extra");
        }
        stringBuffer.append(" Encoding: ");
        stringBuffer.append(this.encoding);
        stringBuffer.append(" ClassLoader: ");
        stringBuffer.append(this.loader);
        return stringBuffer.toString();
    }
}
