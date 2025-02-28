package org.bouncycastle.asn1.nist;

import ar.com.santander.rio.mbanking.app.ui.constants.SegurosConstants.CodRamo;
import ar.com.santander.rio.mbanking.app.ui.constants.TarjetasConstants;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public interface NISTObjectIdentifiers {
    public static final ASN1ObjectIdentifier aes = nistAlgorithm.branch("1");
    public static final ASN1ObjectIdentifier dsa_with_sha224 = id_dsa_with_sha2.branch("1");
    public static final ASN1ObjectIdentifier dsa_with_sha256 = id_dsa_with_sha2.branch("2");
    public static final ASN1ObjectIdentifier dsa_with_sha384 = id_dsa_with_sha2.branch("3");
    public static final ASN1ObjectIdentifier dsa_with_sha512 = id_dsa_with_sha2.branch("4");
    public static final ASN1ObjectIdentifier hashAlgs = nistAlgorithm.branch("2");
    public static final ASN1ObjectIdentifier id_aes128_CBC = aes.branch("2");
    public static final ASN1ObjectIdentifier id_aes128_CCM = aes.branch("7");
    public static final ASN1ObjectIdentifier id_aes128_CFB = aes.branch("4");
    public static final ASN1ObjectIdentifier id_aes128_ECB = aes.branch("1");
    public static final ASN1ObjectIdentifier id_aes128_GCM = aes.branch("6");
    public static final ASN1ObjectIdentifier id_aes128_OFB = aes.branch("3");
    public static final ASN1ObjectIdentifier id_aes128_wrap = aes.branch("5");
    public static final ASN1ObjectIdentifier id_aes192_CBC = aes.branch("22");
    public static final ASN1ObjectIdentifier id_aes192_CCM = aes.branch("27");
    public static final ASN1ObjectIdentifier id_aes192_CFB = aes.branch("24");
    public static final ASN1ObjectIdentifier id_aes192_ECB = aes.branch("21");
    public static final ASN1ObjectIdentifier id_aes192_GCM = aes.branch(CodRamo.TENENCIA_COMPRA_PROTEGIDA_2);
    public static final ASN1ObjectIdentifier id_aes192_OFB = aes.branch("23");
    public static final ASN1ObjectIdentifier id_aes192_wrap = aes.branch(CodRamo.SEGURO_MOVIL);
    public static final ASN1ObjectIdentifier id_aes256_CBC = aes.branch(TarjetasConstants.CODIGO_TARJETA_AMEX);
    public static final ASN1ObjectIdentifier id_aes256_CCM = aes.branch("47");
    public static final ASN1ObjectIdentifier id_aes256_CFB = aes.branch("44");
    public static final ASN1ObjectIdentifier id_aes256_ECB = aes.branch("41");
    public static final ASN1ObjectIdentifier id_aes256_GCM = aes.branch("46");
    public static final ASN1ObjectIdentifier id_aes256_OFB = aes.branch("43");
    public static final ASN1ObjectIdentifier id_aes256_wrap = aes.branch("45");
    public static final ASN1ObjectIdentifier id_dsa_with_sha2 = nistAlgorithm.branch("3");
    public static final ASN1ObjectIdentifier id_sha224 = hashAlgs.branch("4");
    public static final ASN1ObjectIdentifier id_sha256 = hashAlgs.branch("1");
    public static final ASN1ObjectIdentifier id_sha384 = hashAlgs.branch("2");
    public static final ASN1ObjectIdentifier id_sha512 = hashAlgs.branch("3");
    public static final ASN1ObjectIdentifier id_sha512_224 = hashAlgs.branch("5");
    public static final ASN1ObjectIdentifier id_sha512_256 = hashAlgs.branch("6");
    public static final ASN1ObjectIdentifier nistAlgorithm = new ASN1ObjectIdentifier("2.16.840.1.101.3.4");
}
