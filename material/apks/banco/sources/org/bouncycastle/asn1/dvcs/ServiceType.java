package org.bouncycastle.asn1.dvcs;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;

public class ServiceType extends ASN1Object {
    public static final ServiceType CCPD = new ServiceType(4);
    public static final ServiceType CPD = new ServiceType(1);
    public static final ServiceType VPKC = new ServiceType(3);
    public static final ServiceType VSD = new ServiceType(2);
    private ASN1Enumerated a;

    public ServiceType(int i) {
        this.a = new ASN1Enumerated(i);
    }

    private ServiceType(ASN1Enumerated aSN1Enumerated) {
        this.a = aSN1Enumerated;
    }

    public static ServiceType getInstance(Object obj) {
        if (obj instanceof ServiceType) {
            return (ServiceType) obj;
        }
        if (obj != null) {
            return new ServiceType(ASN1Enumerated.getInstance(obj));
        }
        return null;
    }

    public static ServiceType getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Enumerated.getInstance(aSN1TaggedObject, z));
    }

    public BigInteger getValue() {
        return this.a.getValue();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.a;
    }

    public String toString() {
        int intValue = this.a.getValue().intValue();
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(intValue);
        String str = intValue == CPD.getValue().intValue() ? "(CPD)" : intValue == VSD.getValue().intValue() ? "(VSD)" : intValue == VPKC.getValue().intValue() ? "(VPKC)" : intValue == CCPD.getValue().intValue() ? "(CCPD)" : "?";
        sb.append(str);
        return sb.toString();
    }
}
