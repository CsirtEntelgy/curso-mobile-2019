package org.bouncycastle.jcajce.provider.asymmetric.ies;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.jce.spec.IESParameterSpec;

public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    IESParameterSpec a;

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded() {
        try {
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            aSN1EncodableVector.add(new DEROctetString(this.a.getDerivationV()));
            aSN1EncodableVector.add(new DEROctetString(this.a.getEncodingV()));
            aSN1EncodableVector.add(new ASN1Integer((long) this.a.getMacKeySize()));
            return new DERSequence(aSN1EncodableVector).getEncoded(ASN1Encoding.DER);
        } catch (IOException unused) {
            throw new RuntimeException("Error encoding IESParameters");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetEncoded(String str) {
        if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
            return engineGetEncoded();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec engineGetParameterSpec(Class cls) {
        if (cls != null) {
            return localEngineGetParameterSpec(cls);
        }
        throw new NullPointerException("argument to getParameterSpec must not be null");
    }

    /* access modifiers changed from: protected */
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        if (!(algorithmParameterSpec instanceof IESParameterSpec)) {
            throw new InvalidParameterSpecException("IESParameterSpec required to initialise a IES algorithm parameters object");
        }
        this.a = (IESParameterSpec) algorithmParameterSpec;
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bArr) {
        try {
            ASN1Sequence aSN1Sequence = (ASN1Sequence) ASN1Primitive.fromByteArray(bArr);
            this.a = new IESParameterSpec(((ASN1OctetString) aSN1Sequence.getObjectAt(0)).getOctets(), ((ASN1OctetString) aSN1Sequence.getObjectAt(0)).getOctets(), ((ASN1Integer) aSN1Sequence.getObjectAt(0)).getValue().intValue());
        } catch (ClassCastException unused) {
            throw new IOException("Not a valid IES Parameter encoding.");
        } catch (ArrayIndexOutOfBoundsException unused2) {
            throw new IOException("Not a valid IES Parameter encoding.");
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(byte[] bArr, String str) {
        if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
            engineInit(bArr);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown parameter format ");
        sb.append(str);
        throw new IOException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public String engineToString() {
        return "IES Parameters";
    }

    /* access modifiers changed from: protected */
    public boolean isASN1FormatString(String str) {
        return str == null || str.equals("ASN.1");
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
        if (cls == IESParameterSpec.class) {
            return this.a;
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
    }
}
