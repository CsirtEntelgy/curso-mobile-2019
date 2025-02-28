package org.bouncycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;

public class ExtendedKeyUsage extends ASN1Object {
    Hashtable a = new Hashtable();
    ASN1Sequence b;

    public ExtendedKeyUsage(Vector vector) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Enumeration elements = vector.elements();
        while (elements.hasMoreElements()) {
            KeyPurposeId instance = KeyPurposeId.getInstance(elements.nextElement());
            aSN1EncodableVector.add(instance);
            this.a.put(instance, instance);
        }
        this.b = new DERSequence(aSN1EncodableVector);
    }

    private ExtendedKeyUsage(ASN1Sequence aSN1Sequence) {
        this.b = aSN1Sequence;
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            ASN1Encodable aSN1Encodable = (ASN1Encodable) objects.nextElement();
            if (!(aSN1Encodable.toASN1Primitive() instanceof ASN1ObjectIdentifier)) {
                throw new IllegalArgumentException("Only ASN1ObjectIdentifiers allowed in ExtendedKeyUsage.");
            }
            this.a.put(aSN1Encodable, aSN1Encodable);
        }
    }

    public ExtendedKeyUsage(KeyPurposeId keyPurposeId) {
        this.b = new DERSequence((ASN1Encodable) keyPurposeId);
        this.a.put(keyPurposeId, keyPurposeId);
    }

    public ExtendedKeyUsage(KeyPurposeId[] keyPurposeIdArr) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i = 0; i != keyPurposeIdArr.length; i++) {
            aSN1EncodableVector.add(keyPurposeIdArr[i]);
            this.a.put(keyPurposeIdArr[i], keyPurposeIdArr[i]);
        }
        this.b = new DERSequence(aSN1EncodableVector);
    }

    public static ExtendedKeyUsage fromExtensions(Extensions extensions) {
        return getInstance(extensions.getExtensionParsedValue(Extension.extendedKeyUsage));
    }

    public static ExtendedKeyUsage getInstance(Object obj) {
        if (obj instanceof ExtendedKeyUsage) {
            return (ExtendedKeyUsage) obj;
        }
        if (obj != null) {
            return new ExtendedKeyUsage(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public static ExtendedKeyUsage getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z));
    }

    public KeyPurposeId[] getUsages() {
        KeyPurposeId[] keyPurposeIdArr = new KeyPurposeId[this.b.size()];
        Enumeration objects = this.b.getObjects();
        int i = 0;
        while (objects.hasMoreElements()) {
            int i2 = i + 1;
            keyPurposeIdArr[i] = KeyPurposeId.getInstance(objects.nextElement());
            i = i2;
        }
        return keyPurposeIdArr;
    }

    public boolean hasKeyPurposeId(KeyPurposeId keyPurposeId) {
        return this.a.get(keyPurposeId) != null;
    }

    public int size() {
        return this.a.size();
    }

    public ASN1Primitive toASN1Primitive() {
        return this.b;
    }
}
