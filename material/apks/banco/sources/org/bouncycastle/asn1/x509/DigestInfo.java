package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;

public class DigestInfo extends ASN1Object {
    private byte[] a;
    private AlgorithmIdentifier b;

    public DigestInfo(ASN1Sequence aSN1Sequence) {
        Enumeration objects = aSN1Sequence.getObjects();
        this.b = AlgorithmIdentifier.getInstance(objects.nextElement());
        this.a = ASN1OctetString.getInstance(objects.nextElement()).getOctets();
    }

    public DigestInfo(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        this.a = bArr;
        this.b = algorithmIdentifier;
    }

    public static DigestInfo getInstance(Object obj) {
        if (obj instanceof DigestInfo) {
            return (DigestInfo) obj;
        }
        if (obj != null) {
            return new DigestInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static DigestInfo getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public AlgorithmIdentifier getAlgorithmId() {
        return this.b;
    }

    public byte[] getDigest() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(new DEROctetString(this.a));
        return new DERSequence(aSN1EncodableVector);
    }
}
