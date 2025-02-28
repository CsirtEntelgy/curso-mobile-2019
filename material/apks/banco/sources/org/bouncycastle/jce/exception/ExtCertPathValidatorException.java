package org.bouncycastle.jce.exception;

import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;

public class ExtCertPathValidatorException extends CertPathValidatorException implements ExtException {
    private Throwable a;

    public ExtCertPathValidatorException(String str, Throwable th) {
        super(str);
        this.a = th;
    }

    public ExtCertPathValidatorException(String str, Throwable th, CertPath certPath, int i) {
        super(str, th, certPath, i);
        this.a = th;
    }

    public Throwable getCause() {
        return this.a;
    }
}
