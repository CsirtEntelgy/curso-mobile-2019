package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class KeyTransRecipientInfo extends ASN1Object {
    private ASN1Integer a;
    private RecipientIdentifier b;
    private AlgorithmIdentifier c;
    private ASN1OctetString d;

    public KeyTransRecipientInfo(ASN1Sequence aSN1Sequence) {
        this.a = (ASN1Integer) aSN1Sequence.getObjectAt(0);
        this.b = RecipientIdentifier.getInstance(aSN1Sequence.getObjectAt(1));
        this.c = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(2));
        this.d = (ASN1OctetString) aSN1Sequence.getObjectAt(3);
    }

    public KeyTransRecipientInfo(RecipientIdentifier recipientIdentifier, AlgorithmIdentifier algorithmIdentifier, ASN1OctetString aSN1OctetString) {
        this.a = recipientIdentifier.toASN1Primitive() instanceof ASN1TaggedObject ? new ASN1Integer(2) : new ASN1Integer(0);
        this.b = recipientIdentifier;
        this.c = algorithmIdentifier;
        this.d = aSN1OctetString;
    }

    public static KeyTransRecipientInfo getInstance(Object obj) {
        if (obj instanceof KeyTransRecipientInfo) {
            return (KeyTransRecipientInfo) obj;
        }
        if (obj != null) {
            return new KeyTransRecipientInfo(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1OctetString getEncryptedKey() {
        return this.d;
    }

    public AlgorithmIdentifier getKeyEncryptionAlgorithm() {
        return this.c;
    }

    public RecipientIdentifier getRecipientIdentifier() {
        return this.b;
    }

    public ASN1Integer getVersion() {
        return this.a;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.a);
        aSN1EncodableVector.add(this.b);
        aSN1EncodableVector.add(this.c);
        aSN1EncodableVector.add(this.d);
        return new DERSequence(aSN1EncodableVector);
    }
}
