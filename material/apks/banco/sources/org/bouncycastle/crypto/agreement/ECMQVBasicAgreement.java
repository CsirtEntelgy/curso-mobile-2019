package org.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import org.bouncycastle.crypto.BasicAgreement;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.MQVPrivateParameters;
import org.bouncycastle.crypto.params.MQVPublicParameters;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

public class ECMQVBasicAgreement implements BasicAgreement {
    MQVPrivateParameters a;

    private ECPoint a(ECDomainParameters eCDomainParameters, ECPrivateKeyParameters eCPrivateKeyParameters, ECPrivateKeyParameters eCPrivateKeyParameters2, ECPublicKeyParameters eCPublicKeyParameters, ECPublicKeyParameters eCPublicKeyParameters2, ECPublicKeyParameters eCPublicKeyParameters3) {
        BigInteger n = eCDomainParameters.getN();
        int bitLength = (n.bitLength() + 1) / 2;
        BigInteger shiftLeft = ECConstants.ONE.shiftLeft(bitLength);
        ECCurve curve = eCDomainParameters.getCurve();
        ECPoint[] eCPointArr = new ECPoint[3];
        eCPointArr[0] = ECAlgorithms.importPoint(curve, eCPublicKeyParameters == null ? eCDomainParameters.getG().multiply(eCPrivateKeyParameters2.getD()) : eCPublicKeyParameters.getQ());
        eCPointArr[1] = ECAlgorithms.importPoint(curve, eCPublicKeyParameters2.getQ());
        eCPointArr[2] = ECAlgorithms.importPoint(curve, eCPublicKeyParameters3.getQ());
        curve.normalizeAll(eCPointArr);
        ECPoint eCPoint = eCPointArr[0];
        ECPoint eCPoint2 = eCPointArr[1];
        ECPoint eCPoint3 = eCPointArr[2];
        BigInteger mod = eCPrivateKeyParameters.getD().multiply(eCPoint.getAffineXCoord().toBigInteger().mod(shiftLeft).setBit(bitLength)).add(eCPrivateKeyParameters2.getD()).mod(n);
        BigInteger bit = eCPoint3.getAffineXCoord().toBigInteger().mod(shiftLeft).setBit(bitLength);
        BigInteger mod2 = eCDomainParameters.getH().multiply(mod).mod(n);
        return ECAlgorithms.sumOfTwoMultiplies(eCPoint2, bit.multiply(mod2).mod(n), eCPoint3, mod2);
    }

    public BigInteger calculateAgreement(CipherParameters cipherParameters) {
        MQVPublicParameters mQVPublicParameters = (MQVPublicParameters) cipherParameters;
        ECPrivateKeyParameters staticPrivateKey = this.a.getStaticPrivateKey();
        ECPoint normalize = a(staticPrivateKey.getParameters(), staticPrivateKey, this.a.getEphemeralPrivateKey(), this.a.getEphemeralPublicKey(), mQVPublicParameters.getStaticPublicKey(), mQVPublicParameters.getEphemeralPublicKey()).normalize();
        if (!normalize.isInfinity()) {
            return normalize.getAffineXCoord().toBigInteger();
        }
        throw new IllegalStateException("Infinity is not a valid agreement value for MQV");
    }

    public int getFieldSize() {
        return (this.a.getStaticPrivateKey().getParameters().getCurve().getFieldSize() + 7) / 8;
    }

    public void init(CipherParameters cipherParameters) {
        this.a = (MQVPrivateParameters) cipherParameters;
    }
}
