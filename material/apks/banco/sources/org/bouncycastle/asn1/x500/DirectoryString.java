package org.bouncycastle.asn1.x500;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.DERPrintableString;
import org.bouncycastle.asn1.DERT61String;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.DERUniversalString;

public class DirectoryString extends ASN1Object implements ASN1Choice, ASN1String {
    private ASN1String a;

    public DirectoryString(String str) {
        this.a = new DERUTF8String(str);
    }

    private DirectoryString(DERBMPString dERBMPString) {
        this.a = dERBMPString;
    }

    private DirectoryString(DERPrintableString dERPrintableString) {
        this.a = dERPrintableString;
    }

    private DirectoryString(DERT61String dERT61String) {
        this.a = dERT61String;
    }

    private DirectoryString(DERUTF8String dERUTF8String) {
        this.a = dERUTF8String;
    }

    private DirectoryString(DERUniversalString dERUniversalString) {
        this.a = dERUniversalString;
    }

    public static DirectoryString getInstance(Object obj) {
        if (obj == null || (obj instanceof DirectoryString)) {
            return (DirectoryString) obj;
        }
        if (obj instanceof DERT61String) {
            return new DirectoryString((DERT61String) obj);
        }
        if (obj instanceof DERPrintableString) {
            return new DirectoryString((DERPrintableString) obj);
        }
        if (obj instanceof DERUniversalString) {
            return new DirectoryString((DERUniversalString) obj);
        }
        if (obj instanceof DERUTF8String) {
            return new DirectoryString((DERUTF8String) obj);
        }
        if (obj instanceof DERBMPString) {
            return new DirectoryString((DERBMPString) obj);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("illegal object in getInstance: ");
        sb.append(obj.getClass().getName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static DirectoryString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        if (z) {
            return getInstance(aSN1TaggedObject.getObject());
        }
        throw new IllegalArgumentException("choice item must be explicitly tagged");
    }

    public String getString() {
        return this.a.getString();
    }

    public ASN1Primitive toASN1Primitive() {
        return ((ASN1Encodable) this.a).toASN1Primitive();
    }

    public String toString() {
        return this.a.getString();
    }
}
