package org.bouncycastle.asn1.x9;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECFieldElement.F2m;
import org.bouncycastle.math.ec.ECFieldElement.Fp;

public class X9FieldElement extends ASN1Object {
    private static X9IntegerConverter a = new X9IntegerConverter();
    protected ECFieldElement f;

    public X9FieldElement(int i, int i2, int i3, int i4, ASN1OctetString aSN1OctetString) {
        F2m f2m = new F2m(i, i2, i3, i4, new BigInteger(1, aSN1OctetString.getOctets()));
        this(f2m);
    }

    public X9FieldElement(BigInteger bigInteger, ASN1OctetString aSN1OctetString) {
        this(new Fp(bigInteger, new BigInteger(1, aSN1OctetString.getOctets())));
    }

    public X9FieldElement(ECFieldElement eCFieldElement) {
        this.f = eCFieldElement;
    }

    public ECFieldElement getValue() {
        return this.f;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DEROctetString(a.integerToBytes(this.f.toBigInteger(), a.getByteLength(this.f)));
    }
}
