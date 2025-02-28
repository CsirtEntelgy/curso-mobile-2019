package org.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Hashtable;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyGenerationParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECCurve.F2m;
import org.bouncycastle.math.ec.ECCurve.Fp;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.field.PolynomialExtensionField;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Integers;

public class TlsECCUtils {
    public static final Integer EXT_ec_point_formats = Integers.valueOf(11);
    public static final Integer EXT_elliptic_curves = Integers.valueOf(10);
    private static final String[] a = {"sect163k1", "sect163r1", "sect163r2", "sect193r1", "sect193r2", "sect233k1", "sect233r1", "sect239k1", "sect283k1", "sect283r1", "sect409k1", "sect409r1", "sect571k1", "sect571r1", "secp160k1", "secp160r1", "secp160r2", "secp192k1", "secp192r1", "secp224k1", "secp224r1", "secp256k1", "secp256r1", "secp384r1", "secp521r1", "brainpoolP256r1", "brainpoolP384r1", "brainpoolP512r1"};

    private static void a(int[] iArr, int i) {
        if (iArr != null && !Arrays.contains(iArr, i)) {
            throw new TlsFatalAlert(47);
        }
    }

    public static void addSupportedEllipticCurvesExtension(Hashtable hashtable, int[] iArr) {
        hashtable.put(EXT_elliptic_curves, createSupportedEllipticCurvesExtension(iArr));
    }

    public static void addSupportedPointFormatsExtension(Hashtable hashtable, short[] sArr) {
        hashtable.put(EXT_ec_point_formats, createSupportedPointFormatsExtension(sArr));
    }

    public static boolean areOnSameCurve(ECDomainParameters eCDomainParameters, ECDomainParameters eCDomainParameters2) {
        return eCDomainParameters.getCurve().equals(eCDomainParameters2.getCurve()) && eCDomainParameters.getG().equals(eCDomainParameters2.getG()) && eCDomainParameters.getN().equals(eCDomainParameters2.getN()) && eCDomainParameters.getH().equals(eCDomainParameters2.getH());
    }

    public static byte[] calculateECDHBasicAgreement(ECPublicKeyParameters eCPublicKeyParameters, ECPrivateKeyParameters eCPrivateKeyParameters) {
        ECDHBasicAgreement eCDHBasicAgreement = new ECDHBasicAgreement();
        eCDHBasicAgreement.init(eCPrivateKeyParameters);
        return BigIntegers.asUnsignedByteArray(eCDHBasicAgreement.getFieldSize(), eCDHBasicAgreement.calculateAgreement(eCPublicKeyParameters));
    }

    public static boolean containsECCCipherSuites(int[] iArr) {
        for (int isECCCipherSuite : iArr) {
            if (isECCCipherSuite(isECCCipherSuite)) {
                return true;
            }
        }
        return false;
    }

    public static byte[] createSupportedEllipticCurvesExtension(int[] iArr) {
        if (iArr != null && iArr.length >= 1) {
            return TlsUtils.encodeUint16ArrayWithUint16Length(iArr);
        }
        throw new TlsFatalAlert(80);
    }

    public static byte[] createSupportedPointFormatsExtension(short[] sArr) {
        if (sArr == null || !Arrays.contains(sArr, 0)) {
            sArr = Arrays.append(sArr, 0);
        }
        return TlsUtils.encodeUint8ArrayWithUint8Length(sArr);
    }

    public static BigInteger deserializeECFieldElement(int i, byte[] bArr) {
        if (bArr.length == (i + 7) / 8) {
            return new BigInteger(1, bArr);
        }
        throw new TlsFatalAlert(50);
    }

    public static ECPoint deserializeECPoint(short[] sArr, ECCurve eCCurve, byte[] bArr) {
        if (bArr != null) {
            short s = 1;
            if (bArr.length >= 1) {
                switch (bArr[0]) {
                    case 2:
                    case 3:
                        if (ECAlgorithms.isF2mCurve(eCCurve)) {
                            s = 2;
                            break;
                        } else if (!ECAlgorithms.isFpCurve(eCCurve)) {
                            throw new TlsFatalAlert(47);
                        }
                        break;
                    case 4:
                        s = 0;
                        break;
                    default:
                        throw new TlsFatalAlert(47);
                }
                if (Arrays.contains(sArr, s)) {
                    return eCCurve.decodePoint(bArr);
                }
                throw new TlsFatalAlert(47);
            }
        }
        throw new TlsFatalAlert(47);
    }

    public static ECPublicKeyParameters deserializeECPublicKey(short[] sArr, ECDomainParameters eCDomainParameters, byte[] bArr) {
        try {
            return new ECPublicKeyParameters(deserializeECPoint(sArr, eCDomainParameters.getCurve(), bArr), eCDomainParameters);
        } catch (RuntimeException unused) {
            throw new TlsFatalAlert(47);
        }
    }

    public static AsymmetricCipherKeyPair generateECKeyPair(SecureRandom secureRandom, ECDomainParameters eCDomainParameters) {
        ECKeyPairGenerator eCKeyPairGenerator = new ECKeyPairGenerator();
        eCKeyPairGenerator.init(new ECKeyGenerationParameters(eCDomainParameters, secureRandom));
        return eCKeyPairGenerator.generateKeyPair();
    }

    public static ECPrivateKeyParameters generateEphemeralClientKeyExchange(SecureRandom secureRandom, short[] sArr, ECDomainParameters eCDomainParameters, OutputStream outputStream) {
        AsymmetricCipherKeyPair generateECKeyPair = generateECKeyPair(secureRandom, eCDomainParameters);
        writeECPoint(sArr, ((ECPublicKeyParameters) generateECKeyPair.getPublic()).getQ(), outputStream);
        return (ECPrivateKeyParameters) generateECKeyPair.getPrivate();
    }

    public static String getNameOfNamedCurve(int i) {
        if (isSupportedNamedCurve(i)) {
            return a[i - 1];
        }
        return null;
    }

    public static ECDomainParameters getParametersForNamedCurve(int i) {
        String nameOfNamedCurve = getNameOfNamedCurve(i);
        if (nameOfNamedCurve == null) {
            return null;
        }
        X9ECParameters byName = CustomNamedCurves.getByName(nameOfNamedCurve);
        if (byName == null) {
            byName = ECNamedCurveTable.getByName(nameOfNamedCurve);
            if (byName == null) {
                return null;
            }
        }
        ECDomainParameters eCDomainParameters = new ECDomainParameters(byName.getCurve(), byName.getG(), byName.getN(), byName.getH(), byName.getSeed());
        return eCDomainParameters;
    }

    public static int[] getSupportedEllipticCurvesExtension(Hashtable hashtable) {
        byte[] extensionData = TlsUtils.getExtensionData(hashtable, EXT_elliptic_curves);
        if (extensionData == null) {
            return null;
        }
        return readSupportedEllipticCurvesExtension(extensionData);
    }

    public static short[] getSupportedPointFormatsExtension(Hashtable hashtable) {
        byte[] extensionData = TlsUtils.getExtensionData(hashtable, EXT_ec_point_formats);
        if (extensionData == null) {
            return null;
        }
        return readSupportedPointFormatsExtension(extensionData);
    }

    public static boolean hasAnySupportedNamedCurves() {
        return a.length > 0;
    }

    public static boolean isCompressionPreferred(short[] sArr, short s) {
        if (sArr == null) {
            return false;
        }
        for (short s2 : sArr) {
            if (s2 == 0) {
                return false;
            }
            if (s2 == s) {
                return true;
            }
        }
        return false;
    }

    public static boolean isECCCipherSuite(int i) {
        switch (i) {
            case CipherSuite.TLS_ECDH_ECDSA_WITH_NULL_SHA /*49153*/:
            case CipherSuite.TLS_ECDH_ECDSA_WITH_RC4_128_SHA /*49154*/:
            case CipherSuite.TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA /*49155*/:
            case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA /*49156*/:
            case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA /*49157*/:
            case CipherSuite.TLS_ECDHE_ECDSA_WITH_NULL_SHA /*49158*/:
            case CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA /*49159*/:
            case CipherSuite.TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA /*49160*/:
            case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA /*49161*/:
            case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA /*49162*/:
            case CipherSuite.TLS_ECDH_RSA_WITH_NULL_SHA /*49163*/:
            case CipherSuite.TLS_ECDH_RSA_WITH_RC4_128_SHA /*49164*/:
            case CipherSuite.TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA /*49165*/:
            case CipherSuite.TLS_ECDH_RSA_WITH_AES_128_CBC_SHA /*49166*/:
            case CipherSuite.TLS_ECDH_RSA_WITH_AES_256_CBC_SHA /*49167*/:
            case CipherSuite.TLS_ECDHE_RSA_WITH_NULL_SHA /*49168*/:
            case CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA /*49169*/:
            case CipherSuite.TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA /*49170*/:
            case CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA /*49171*/:
            case CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA /*49172*/:
            case CipherSuite.TLS_ECDH_anon_WITH_NULL_SHA /*49173*/:
            case CipherSuite.TLS_ECDH_anon_WITH_RC4_128_SHA /*49174*/:
            case CipherSuite.TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA /*49175*/:
            case CipherSuite.TLS_ECDH_anon_WITH_AES_128_CBC_SHA /*49176*/:
            case CipherSuite.TLS_ECDH_anon_WITH_AES_256_CBC_SHA /*49177*/:
                break;
            default:
                switch (i) {
                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 /*49187*/:
                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384 /*49188*/:
                    case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256 /*49189*/:
                    case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384 /*49190*/:
                    case CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256 /*49191*/:
                    case CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384 /*49192*/:
                    case CipherSuite.TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256 /*49193*/:
                    case CipherSuite.TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384 /*49194*/:
                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 /*49195*/:
                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384 /*49196*/:
                    case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256 /*49197*/:
                    case CipherSuite.TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384 /*49198*/:
                    case CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 /*49199*/:
                    case CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384 /*49200*/:
                    case CipherSuite.TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256 /*49201*/:
                    case CipherSuite.TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384 /*49202*/:
                    case CipherSuite.TLS_ECDHE_PSK_WITH_RC4_128_SHA /*49203*/:
                    case CipherSuite.TLS_ECDHE_PSK_WITH_3DES_EDE_CBC_SHA /*49204*/:
                    case CipherSuite.TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA /*49205*/:
                    case CipherSuite.TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA /*49206*/:
                    case CipherSuite.TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA256 /*49207*/:
                    case CipherSuite.TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA384 /*49208*/:
                    case CipherSuite.TLS_ECDHE_PSK_WITH_NULL_SHA /*49209*/:
                    case CipherSuite.TLS_ECDHE_PSK_WITH_NULL_SHA256 /*49210*/:
                    case CipherSuite.TLS_ECDHE_PSK_WITH_NULL_SHA384 /*49211*/:
                        break;
                    default:
                        switch (i) {
                            case CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_CBC_SHA256 /*49266*/:
                            case CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_CBC_SHA384 /*49267*/:
                            case CipherSuite.TLS_ECDH_ECDSA_WITH_CAMELLIA_128_CBC_SHA256 /*49268*/:
                            case CipherSuite.TLS_ECDH_ECDSA_WITH_CAMELLIA_256_CBC_SHA384 /*49269*/:
                            case CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_128_CBC_SHA256 /*49270*/:
                            case CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_256_CBC_SHA384 /*49271*/:
                            case CipherSuite.TLS_ECDH_RSA_WITH_CAMELLIA_128_CBC_SHA256 /*49272*/:
                            case CipherSuite.TLS_ECDH_RSA_WITH_CAMELLIA_256_CBC_SHA384 /*49273*/:
                                break;
                            default:
                                switch (i) {
                                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_GCM_SHA256 /*49286*/:
                                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_GCM_SHA384 /*49287*/:
                                    case CipherSuite.TLS_ECDH_ECDSA_WITH_CAMELLIA_128_GCM_SHA256 /*49288*/:
                                    case CipherSuite.TLS_ECDH_ECDSA_WITH_CAMELLIA_256_GCM_SHA384 /*49289*/:
                                    case CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_128_GCM_SHA256 /*49290*/:
                                    case CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_256_GCM_SHA384 /*49291*/:
                                    case CipherSuite.TLS_ECDH_RSA_WITH_CAMELLIA_128_GCM_SHA256 /*49292*/:
                                    case CipherSuite.TLS_ECDH_RSA_WITH_CAMELLIA_256_GCM_SHA384 /*49293*/:
                                        break;
                                    default:
                                        switch (i) {
                                            case CipherSuite.TLS_ECDHE_PSK_WITH_CAMELLIA_128_CBC_SHA256 /*49306*/:
                                            case CipherSuite.TLS_ECDHE_PSK_WITH_CAMELLIA_256_CBC_SHA384 /*49307*/:
                                                break;
                                            default:
                                                switch (i) {
                                                    case CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256 /*52243*/:
                                                    case CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256 /*52244*/:
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case CipherSuite.TLS_ECDHE_RSA_WITH_ESTREAM_SALSA20_SHA1 /*58386*/:
                                                            case CipherSuite.TLS_ECDHE_RSA_WITH_SALSA20_SHA1 /*58387*/:
                                                            case CipherSuite.TLS_ECDHE_ECDSA_WITH_ESTREAM_SALSA20_SHA1 /*58388*/:
                                                            case CipherSuite.TLS_ECDHE_ECDSA_WITH_SALSA20_SHA1 /*58389*/:
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case CipherSuite.TLS_ECDHE_PSK_WITH_ESTREAM_SALSA20_SHA1 /*58392*/:
                                                                    case CipherSuite.TLS_ECDHE_PSK_WITH_SALSA20_SHA1 /*58393*/:
                                                                        break;
                                                                    default:
                                                                        return false;
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
        return true;
    }

    public static boolean isSupportedNamedCurve(int i) {
        return i > 0 && i <= a.length;
    }

    public static int readECExponent(int i, InputStream inputStream) {
        BigInteger readECParameter = readECParameter(inputStream);
        if (readECParameter.bitLength() < 32) {
            int intValue = readECParameter.intValue();
            if (intValue > 0 && intValue < i) {
                return intValue;
            }
        }
        throw new TlsFatalAlert(47);
    }

    public static BigInteger readECFieldElement(int i, InputStream inputStream) {
        return deserializeECFieldElement(i, TlsUtils.readOpaque8(inputStream));
    }

    public static BigInteger readECParameter(InputStream inputStream) {
        return new BigInteger(1, TlsUtils.readOpaque8(inputStream));
    }

    public static ECDomainParameters readECParameters(int[] iArr, short[] sArr, InputStream inputStream) {
        int i;
        int i2;
        try {
            switch (TlsUtils.readUint8(inputStream)) {
                case 1:
                    a(iArr, 65281);
                    BigInteger readECParameter = readECParameter(inputStream);
                    BigInteger readECFieldElement = readECFieldElement(readECParameter.bitLength(), inputStream);
                    BigInteger readECFieldElement2 = readECFieldElement(readECParameter.bitLength(), inputStream);
                    byte[] readOpaque8 = TlsUtils.readOpaque8(inputStream);
                    BigInteger readECParameter2 = readECParameter(inputStream);
                    BigInteger readECParameter3 = readECParameter(inputStream);
                    Fp fp = new Fp(readECParameter, readECFieldElement, readECFieldElement2, readECParameter2, readECParameter3);
                    return new ECDomainParameters(fp, deserializeECPoint(sArr, fp, readOpaque8), readECParameter2, readECParameter3);
                case 2:
                    a(iArr, NamedCurve.arbitrary_explicit_char2_curves);
                    int readUint16 = TlsUtils.readUint16(inputStream);
                    short readUint8 = TlsUtils.readUint8(inputStream);
                    if (!ECBasisType.isValid(readUint8)) {
                        throw new TlsFatalAlert(47);
                    }
                    int readECExponent = readECExponent(readUint16, inputStream);
                    if (readUint8 == 2) {
                        int readECExponent2 = readECExponent(readUint16, inputStream);
                        i = readECExponent(readUint16, inputStream);
                        i2 = readECExponent2;
                    } else {
                        i2 = -1;
                        i = -1;
                    }
                    BigInteger readECFieldElement3 = readECFieldElement(readUint16, inputStream);
                    BigInteger readECFieldElement4 = readECFieldElement(readUint16, inputStream);
                    byte[] readOpaque82 = TlsUtils.readOpaque8(inputStream);
                    BigInteger readECParameter4 = readECParameter(inputStream);
                    BigInteger readECParameter5 = readECParameter(inputStream);
                    F2m f2m = readUint8 == 2 ? new F2m(readUint16, readECExponent, i2, i, readECFieldElement3, readECFieldElement4, readECParameter4, readECParameter5) : new F2m(readUint16, readECExponent, readECFieldElement3, readECFieldElement4, readECParameter4, readECParameter5);
                    return new ECDomainParameters(f2m, deserializeECPoint(sArr, f2m, readOpaque82), readECParameter4, readECParameter5);
                case 3:
                    int readUint162 = TlsUtils.readUint16(inputStream);
                    if (!NamedCurve.refersToASpecificNamedCurve(readUint162)) {
                        throw new TlsFatalAlert(47);
                    }
                    a(iArr, readUint162);
                    return getParametersForNamedCurve(readUint162);
                default:
                    throw new TlsFatalAlert(47);
            }
        } catch (RuntimeException unused) {
            throw new TlsFatalAlert(47);
        }
    }

    public static int[] readSupportedEllipticCurvesExtension(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("'extensionData' cannot be null");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        int readUint16 = TlsUtils.readUint16(byteArrayInputStream);
        if (readUint16 < 2 || (readUint16 & 1) != 0) {
            throw new TlsFatalAlert(50);
        }
        int[] readUint16Array = TlsUtils.readUint16Array(readUint16 / 2, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        return readUint16Array;
    }

    public static short[] readSupportedPointFormatsExtension(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("'extensionData' cannot be null");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        short readUint8 = TlsUtils.readUint8(byteArrayInputStream);
        if (readUint8 < 1) {
            throw new TlsFatalAlert(50);
        }
        short[] readUint8Array = TlsUtils.readUint8Array(readUint8, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        if (Arrays.contains(readUint8Array, 0)) {
            return readUint8Array;
        }
        throw new TlsFatalAlert(47);
    }

    public static byte[] serializeECFieldElement(int i, BigInteger bigInteger) {
        return BigIntegers.asUnsignedByteArray((i + 7) / 8, bigInteger);
    }

    public static byte[] serializeECPoint(short[] sArr, ECPoint eCPoint) {
        boolean z;
        short s;
        ECCurve curve = eCPoint.getCurve();
        if (ECAlgorithms.isFpCurve(curve)) {
            s = 1;
        } else if (ECAlgorithms.isF2mCurve(curve)) {
            s = 2;
        } else {
            z = false;
            return eCPoint.getEncoded(z);
        }
        z = isCompressionPreferred(sArr, s);
        return eCPoint.getEncoded(z);
    }

    public static byte[] serializeECPublicKey(short[] sArr, ECPublicKeyParameters eCPublicKeyParameters) {
        return serializeECPoint(sArr, eCPublicKeyParameters.getQ());
    }

    public static ECPublicKeyParameters validateECPublicKey(ECPublicKeyParameters eCPublicKeyParameters) {
        return eCPublicKeyParameters;
    }

    public static void writeECExponent(int i, OutputStream outputStream) {
        writeECParameter(BigInteger.valueOf((long) i), outputStream);
    }

    public static void writeECFieldElement(int i, BigInteger bigInteger, OutputStream outputStream) {
        TlsUtils.writeOpaque8(serializeECFieldElement(i, bigInteger), outputStream);
    }

    public static void writeECFieldElement(ECFieldElement eCFieldElement, OutputStream outputStream) {
        TlsUtils.writeOpaque8(eCFieldElement.getEncoded(), outputStream);
    }

    public static void writeECParameter(BigInteger bigInteger, OutputStream outputStream) {
        TlsUtils.writeOpaque8(BigIntegers.asUnsignedByteArray(bigInteger), outputStream);
    }

    public static void writeECPoint(short[] sArr, ECPoint eCPoint, OutputStream outputStream) {
        TlsUtils.writeOpaque8(serializeECPoint(sArr, eCPoint), outputStream);
    }

    public static void writeExplicitECParameters(short[] sArr, ECDomainParameters eCDomainParameters, OutputStream outputStream) {
        int i;
        ECCurve curve = eCDomainParameters.getCurve();
        if (ECAlgorithms.isFpCurve(curve)) {
            TlsUtils.writeUint8(1, outputStream);
            writeECParameter(curve.getField().getCharacteristic(), outputStream);
        } else if (ECAlgorithms.isF2mCurve(curve)) {
            int[] exponentsPresent = ((PolynomialExtensionField) curve.getField()).getMinimalPolynomial().getExponentsPresent();
            TlsUtils.writeUint8(2, outputStream);
            int i2 = exponentsPresent[exponentsPresent.length - 1];
            TlsUtils.checkUint16(i2);
            TlsUtils.writeUint16(i2, outputStream);
            if (exponentsPresent.length == 3) {
                TlsUtils.writeUint8(1, outputStream);
                i = exponentsPresent[1];
            } else if (exponentsPresent.length == 5) {
                TlsUtils.writeUint8(2, outputStream);
                writeECExponent(exponentsPresent[1], outputStream);
                writeECExponent(exponentsPresent[2], outputStream);
                i = exponentsPresent[3];
            } else {
                throw new IllegalArgumentException("Only trinomial and pentomial curves are supported");
            }
            writeECExponent(i, outputStream);
        } else {
            throw new IllegalArgumentException("'ecParameters' not a known curve type");
        }
        writeECFieldElement(curve.getA(), outputStream);
        writeECFieldElement(curve.getB(), outputStream);
        TlsUtils.writeOpaque8(serializeECPoint(sArr, eCDomainParameters.getG()), outputStream);
        writeECParameter(eCDomainParameters.getN(), outputStream);
        writeECParameter(eCDomainParameters.getH(), outputStream);
    }

    public static void writeNamedECParameters(int i, OutputStream outputStream) {
        if (!NamedCurve.refersToASpecificNamedCurve(i)) {
            throw new TlsFatalAlert(80);
        }
        TlsUtils.writeUint8(3, outputStream);
        TlsUtils.checkUint16(i);
        TlsUtils.writeUint16(i, outputStream);
    }
}
