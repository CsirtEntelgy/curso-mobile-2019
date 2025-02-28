package org.bouncycastle.asn1.crmf;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.GeneralName;

public class CertId extends ASN1Object {
    private GeneralName a;
    private ASN1Integer b;

    private CertId(ASN1Sequence aSN1Sequence) {
        this.a = GeneralName.getInstance(aSN1Sequence.getObjectAt(0));
        this.b = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(1));
    }

    public CertId(GeneralName generalName, BigInteger bigInteger) {
        this(generalName, new ASN1Integer(bigInteger));
    }

    public CertId(GeneralName generalName, ASN1Integer aSN1Integer) {
        this.a = generalName;
        this.b = aSN1Integer;
    }

    public static CertId getInstance(Object obj) {
        if (obj instanceof CertId) {
            return (CertId) obj;
        }
        if (obj != null) {
            return new CertId(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static CertId getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public GeneralName getIssuer() {
        return this.a;
    }

    public ASN1Integer getSerialNumber() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        return new DERSequence(aSN1EncodableVector);
    }
}
