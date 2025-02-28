package org.bouncycastle.jcajce.provider.asymmetric.x509;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PSSParameterSpec;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2ParameterSpec;

class X509SignatureUtil {
    private static final ASN1Null a = DERNull.INSTANCE;

    X509SignatureUtil() {
    }

    private static String a(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return PKCSObjectIdentifiers.md5.equals(aSN1ObjectIdentifier) ? CommonUtils.MD5_INSTANCE : OIWObjectIdentifiers.idSHA1.equals(aSN1ObjectIdentifier) ? "SHA1" : NISTObjectIdentifiers.id_sha224.equals(aSN1ObjectIdentifier) ? "SHA224" : NISTObjectIdentifiers.id_sha256.equals(aSN1ObjectIdentifier) ? McElieceCCA2ParameterSpec.DEFAULT_MD : NISTObjectIdentifiers.id_sha384.equals(aSN1ObjectIdentifier) ? "SHA384" : NISTObjectIdentifiers.id_sha512.equals(aSN1ObjectIdentifier) ? "SHA512" : TeleTrusTObjectIdentifiers.ripemd128.equals(aSN1ObjectIdentifier) ? "RIPEMD128" : TeleTrusTObjectIdentifiers.ripemd160.equals(aSN1ObjectIdentifier) ? "RIPEMD160" : TeleTrusTObjectIdentifiers.ripemd256.equals(aSN1ObjectIdentifier) ? "RIPEMD256" : CryptoProObjectIdentifiers.gostR3411.equals(aSN1ObjectIdentifier) ? "GOST3411" : aSN1ObjectIdentifier.getId();
    }

    static String a(AlgorithmIdentifier algorithmIdentifier) {
        ASN1Encodable parameters = algorithmIdentifier.getParameters();
        if (parameters != null && !a.equals(parameters)) {
            if (algorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.id_RSASSA_PSS)) {
                RSASSAPSSparams instance = RSASSAPSSparams.getInstance(parameters);
                StringBuilder sb = new StringBuilder();
                sb.append(a(instance.getHashAlgorithm().getAlgorithm()));
                sb.append("withRSAandMGF1");
                return sb.toString();
            } else if (algorithmIdentifier.getAlgorithm().equals(X9ObjectIdentifiers.ecdsa_with_SHA2)) {
                ASN1Sequence instance2 = ASN1Sequence.getInstance(parameters);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(a((ASN1ObjectIdentifier) instance2.getObjectAt(0)));
                sb2.append("withECDSA");
                return sb2.toString();
            }
        }
        return algorithmIdentifier.getAlgorithm().getId();
    }

    static void a(Signature signature, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null && !a.equals(aSN1Encodable)) {
            AlgorithmParameters instance = AlgorithmParameters.getInstance(signature.getAlgorithm(), signature.getProvider());
            try {
                instance.init(aSN1Encodable.toASN1Primitive().getEncoded());
                if (signature.getAlgorithm().endsWith("MGF1")) {
                    try {
                        signature.setParameter(instance.getParameterSpec(PSSParameterSpec.class));
                    } catch (GeneralSecurityException e) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Exception extracting parameters: ");
                        sb.append(e.getMessage());
                        throw new SignatureException(sb.toString());
                    }
                }
            } catch (IOException e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("IOException decoding parameters: ");
                sb2.append(e2.getMessage());
                throw new SignatureException(sb2.toString());
            }
        }
    }
}
