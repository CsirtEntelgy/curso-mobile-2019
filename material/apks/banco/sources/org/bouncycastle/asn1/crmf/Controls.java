package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class Controls extends ASN1Object {
    private ASN1Sequence a;

    private Controls(ASN1Sequence aSN1Sequence) {
        this.a = aSN1Sequence;
    }

    public Controls(AttributeTypeAndValue attributeTypeAndValue) {
        this.a = new DERSequence((ASN1Encodable) attributeTypeAndValue);
    }

    public Controls(AttributeTypeAndValue[] attributeTypeAndValueArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (AttributeTypeAndValue add : attributeTypeAndValueArr) {
            aSN1EncodableVector.add(add);
        }
        this.a = new DERSequence(aSN1EncodableVector);
    }

    public static Controls getInstance(Object obj) {
        if (obj instanceof Controls) {
            return (Controls) obj;
        }
        if (obj != null) {
            return new Controls(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }

    public AttributeTypeAndValue[] toAttributeTypeAndValueArray() {
        AttributeTypeAndValue[] attributeTypeAndValueArr = new AttributeTypeAndValue[this.a.size()];
        for (int i = 0; i != attributeTypeAndValueArr.length; i++) {
            attributeTypeAndValueArr[i] = AttributeTypeAndValue.getInstance(this.a.getObjectAt(i));
        }
        return attributeTypeAndValueArr;
    }
}
