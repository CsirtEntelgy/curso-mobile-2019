package org.bouncycastle.asn1.x509.qualified;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;

public class TypeOfBiometricData extends ASN1Object implements ASN1Choice {
    public static final int HANDWRITTEN_SIGNATURE = 1;
    public static final int PICTURE = 0;
    ASN1Encodable a;

    public TypeOfBiometricData(int i) {
        if (i == 0 || i == 1) {
            this.a = new ASN1Integer((long) i);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("unknow PredefinedBiometricType : ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public TypeOfBiometricData(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.a = aSN1ObjectIdentifier;
    }

    public static TypeOfBiometricData getInstance(Object obj) {
        if (obj == null || (obj instanceof TypeOfBiometricData)) {
            return (TypeOfBiometricData) obj;
        }
        if (obj instanceof ASN1Integer) {
            return new TypeOfBiometricData(ASN1Integer.getInstance(obj).getValue().intValue());
        }
        if (obj instanceof ASN1ObjectIdentifier) {
            return new TypeOfBiometricData(ASN1ObjectIdentifier.getInstance(obj));
        }
        throw new IllegalArgumentException("unknown object in getInstance");
    }

    public ASN1ObjectIdentifier getBiometricDataOid() {
        return (ASN1ObjectIdentifier) this.a;
    }

    public int getPredefinedBiometricType() {
        return ((ASN1Integer) this.a).getValue().intValue();
    }

    public boolean isPredefined() {
        return this.a instanceof ASN1Integer;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a.toASN1Primitive();
    }
}
