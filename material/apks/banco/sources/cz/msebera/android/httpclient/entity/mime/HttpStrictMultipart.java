package cz.msebera.android.httpclient.entity.mime;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

class HttpStrictMultipart extends AbstractMultipartForm {
    private final List<FormBodyPart> c;

    public HttpStrictMultipart(Charset charset, String str, List<FormBodyPart> list) {
        super(charset, str);
        this.c = list;
    }

    public List<FormBodyPart> a() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void a(FormBodyPart formBodyPart, OutputStream outputStream) {
        Iterator it = formBodyPart.getHeader().iterator();
        while (it.hasNext()) {
            a((MinimalField) it.next(), outputStream);
        }
    }
}
