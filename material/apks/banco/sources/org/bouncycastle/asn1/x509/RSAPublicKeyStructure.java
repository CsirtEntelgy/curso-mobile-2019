package org.bouncycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class RSAPublicKeyStructure extends ASN1Object {
    private BigInteger a;
    private BigInteger b;

    public RSAPublicKeyStructure(BigInteger bigInteger, BigInteger bigInteger2) {
        this.a = bigInteger;
        this.b = bigInteger2;
    }

    public RSAPublicKeyStructure(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad sequence size: ");
            sb.append(aSN1Sequence.size());
            throw new IllegalArgumentException(sb.toString());
        }
        Enumeration objects = aSN1Sequence.getObjects();
        this.a = ASN1Integer.getInstance(objects.nextElement()).getPositiveValue();
        this.b = ASN1Integer.getInstance(objects.nextElement()).getPositiveValue();
    }

    public static RSAPublicKeyStructure getInstance(Object obj) {
        if (obj == null || (obj instanceof RSAPublicKeyStructure)) {
            return (RSAPublicKeyStructure) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new RSAPublicKeyStructure((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid RSAPublicKeyStructure: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static RSAPublicKeyStructure getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public BigInteger getModulus() {
        return this.a;
    }

    public BigInteger getPublicExponent() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new ASN1Integer(getModulus()));
        aSN1EncodableVector.add(new ASN1Integer(getPublicExponent()));
        return new DERSequence(aSN1EncodableVector);
    }
}
