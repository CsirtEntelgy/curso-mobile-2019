package org.bouncycastle.asn1.util;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;

public class DERDump extends ASN1Dump {
    public static String dumpAsString(ASN1Encodable aSN1Encodable) {
        StringBuffer stringBuffer = new StringBuffer();
        a("", false, aSN1Encodable.toASN1Primitive(), stringBuffer);
        return stringBuffer.toString();
    }

    public static String dumpAsString(ASN1Primitive aSN1Primitive) {
        StringBuffer stringBuffer = new StringBuffer();
        a("", false, aSN1Primitive, stringBuffer);
        return stringBuffer.toString();
    }
}
