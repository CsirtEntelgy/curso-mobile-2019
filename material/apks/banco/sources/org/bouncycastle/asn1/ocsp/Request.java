package org.bouncycastle.asn1.ocsp;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.Extensions;

public class Request extends ASN1Object {
    CertID a;
    Extensions b;

    private Request(ASN1Sequence aSN1Sequence) {
        this.a = CertID.getInstance(aSN1Sequence.getObjectAt(0));
        if (aSN1Sequence.size() == 2) {
            this.b = Extensions.getInstance((ASN1TaggedObject) aSN1Sequence.getObjectAt(1), true);
        }
    }

    public Request(CertID certID, Extensions extensions) {
        this.a = certID;
        this.b = extensions;
    }

    public static Request getInstance(Object obj) {
        if (obj instanceof Request) {
            return (Request) obj;
        }
        if (obj != null) {
            return new Request(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static Request getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public CertID getReqCert() {
        return this.a;
    }

    public Extensions getSingleRequestExtensions() {
        return this.b;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        if (this.b != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, this.b));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}
