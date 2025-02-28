package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

public class CertificatePair extends ASN1Object {
    private Certificate a;
    private Certificate b;

    private CertificatePair(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() == 1 || aSN1Sequence.size() == 2) {
            Enumeration objects = aSN1Sequence.getObjects();
            while (objects.hasMoreElements()) {
                ASN1TaggedObject instance = ASN1TaggedObject.getInstance(objects.nextElement());
                if (instance.getTagNo() == 0) {
                    this.a = Certificate.getInstance(instance, true);
                } else if (instance.getTagNo() == 1) {
                    this.b = Certificate.getInstance(instance, true);
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Bad tag number: ");
                    sb.append(instance.getTagNo());
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            return;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Bad sequence size: ");
        sb2.append(aSN1Sequence.size());
        throw new IllegalArgumentException(sb2.toString());
    }

    public CertificatePair(Certificate certificate, Certificate certificate2) {
        this.a = certificate;
        this.b = certificate2;
    }

    public static CertificatePair getInstance(Object obj) {
        if (obj == null || (obj instanceof CertificatePair)) {
            return (CertificatePair) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new CertificatePair((ASN1Sequence) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public Certificate getForward() {
        return this.a;
    }

    public Certificate getReverse() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (this.a != null) {
            aSN1EncodableVector.add(new DERTaggedObject(0, this.a));
        }
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(1, this.b));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
