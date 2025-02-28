package org.bouncycastle.crypto.ec;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECMultiplier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;

public class ECFixedTransform implements ECPairFactorTransform {
    private ECPublicKeyParameters a;
    private BigInteger b;

    public ECFixedTransform(BigInteger bigInteger) {
        this.b = bigInteger;
    }

    /* access modifiers changed from: protected */
    public ECMultiplier createBasePointMultiplier() {
        return new FixedPointCombMultiplier();
    }

    public BigInteger getTransformValue() {
        return this.b;
    }

    public void init(CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ECPublicKeyParameters)) {
            throw new IllegalArgumentException("ECPublicKeyParameters are required for fixed transform.");
        }
        this.a = (ECPublicKeyParameters) cipherParameters;
    }

    public ECPair transform(ECPair eCPair) {
        if (this.a == null) {
            throw new IllegalStateException("ECFixedTransform not initialised");
        }
        ECDomainParameters parameters = this.a.getParameters();
        BigInteger n = parameters.getN();
        ECMultiplier createBasePointMultiplier = createBasePointMultiplier();
        BigInteger mod = this.b.mod(n);
        ECPoint[] eCPointArr = {createBasePointMultiplier.multiply(parameters.getG(), mod).add(eCPair.getX()), this.a.getQ().multiply(mod).add(eCPair.getY())};
        parameters.getCurve().normalizeAll(eCPointArr);
        return new ECPair(eCPointArr[0], eCPointArr[1]);
    }
}
