package org.bouncycastle.jce.spec;

import java.security.spec.KeySpec;

public class ECKeySpec implements KeySpec {
    private ECParameterSpec a;

    protected ECKeySpec(ECParameterSpec eCParameterSpec) {
        this.a = eCParameterSpec;
    }

    public ECParameterSpec getParams() {
        return this.a;
    }
}
