package org.bouncycastle.jce.interfaces;

import org.bouncycastle.jce.spec.ElGamalParameterSpec;

public interface ElGamalKey {
    ElGamalParameterSpec getParameters();
}
