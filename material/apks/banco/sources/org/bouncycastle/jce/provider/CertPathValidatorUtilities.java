package org.bouncycastle.jce.provider;

import android.support.v4.os.EnvironmentCompat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyQualifierInfo;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.isismtt.ISISMTTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.jce.X509LDAPCertStoreParameters;
import org.bouncycastle.jce.X509LDAPCertStoreParameters.Builder;
import org.bouncycastle.jce.exception.ExtCertPathValidatorException;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.StoreException;
import org.bouncycastle.x509.ExtendedPKIXBuilderParameters;
import org.bouncycastle.x509.ExtendedPKIXParameters;
import org.bouncycastle.x509.X509AttributeCertStoreSelector;
import org.bouncycastle.x509.X509AttributeCertificate;
import org.bouncycastle.x509.X509CRLStoreSelector;
import org.bouncycastle.x509.X509CertStoreSelector;
import org.bouncycastle.x509.X509Store;
import org.bouncycastle.x509.X509StoreParameters;

public class CertPathValidatorUtilities {
    protected static final String ANY_POLICY = "2.5.29.32.0";
    protected static final String AUTHORITY_KEY_IDENTIFIER = Extension.authorityKeyIdentifier.getId();
    protected static final String BASIC_CONSTRAINTS = Extension.basicConstraints.getId();
    protected static final String CERTIFICATE_POLICIES = Extension.certificatePolicies.getId();
    protected static final String CRL_DISTRIBUTION_POINTS = Extension.cRLDistributionPoints.getId();
    protected static final String CRL_NUMBER = Extension.cRLNumber.getId();
    protected static final int CRL_SIGN = 6;
    protected static final PKIXCRLUtil CRL_UTIL = new PKIXCRLUtil();
    protected static final String DELTA_CRL_INDICATOR = Extension.deltaCRLIndicator.getId();
    protected static final String FRESHEST_CRL = Extension.freshestCRL.getId();
    protected static final String INHIBIT_ANY_POLICY = Extension.inhibitAnyPolicy.getId();
    protected static final String ISSUING_DISTRIBUTION_POINT = Extension.issuingDistributionPoint.getId();
    protected static final int KEY_CERT_SIGN = 5;
    protected static final String KEY_USAGE = Extension.keyUsage.getId();
    protected static final String NAME_CONSTRAINTS = Extension.nameConstraints.getId();
    protected static final String POLICY_CONSTRAINTS = Extension.policyConstraints.getId();
    protected static final String POLICY_MAPPINGS = Extension.policyMappings.getId();
    protected static final String SUBJECT_ALTERNATIVE_NAME = Extension.subjectAlternativeName.getId();
    protected static final String[] crlReasons = {"unspecified", "keyCompromise", "cACompromise", "affiliationChanged", "superseded", "cessationOfOperation", "certificateHold", EnvironmentCompat.MEDIA_UNKNOWN, "removeFromCRL", "privilegeWithdrawn", "aACompromise"};

    private static BigInteger a(Object obj) {
        return obj instanceof X509Certificate ? ((X509Certificate) obj).getSerialNumber() : ((X509AttributeCertificate) obj).getSerialNumber();
    }

    private static ASN1Primitive a(String str, byte[] bArr) {
        try {
            return new ASN1InputStream(((ASN1OctetString) new ASN1InputStream(bArr).readObject()).getOctets()).readObject();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append("exception processing extension ");
            sb.append(str);
            throw new AnnotatedException(sb.toString(), e);
        }
    }

    private static void a(List[] listArr, PKIXPolicyNode pKIXPolicyNode) {
        listArr[pKIXPolicyNode.getDepth()].remove(pKIXPolicyNode);
        if (pKIXPolicyNode.hasChildren()) {
            Iterator children = pKIXPolicyNode.getChildren();
            while (children.hasNext()) {
                a(listArr, (PKIXPolicyNode) children.next());
            }
        }
    }

    private static boolean a(X509CRL x509crl) {
        Set criticalExtensionOIDs = x509crl.getCriticalExtensionOIDs();
        if (criticalExtensionOIDs == null) {
            return false;
        }
        return criticalExtensionOIDs.contains(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR);
    }

    protected static void addAdditionalStoreFromLocation(String str, ExtendedPKIXParameters extendedPKIXParameters) {
        StringBuilder sb;
        if (extendedPKIXParameters.isAdditionalLocationsEnabled()) {
            try {
                if (str.startsWith("ldap://")) {
                    String substring = str.substring(7);
                    String str2 = null;
                    if (substring.indexOf("/") != -1) {
                        str2 = substring.substring(substring.indexOf("/"));
                        sb = new StringBuilder();
                        sb.append("ldap://");
                        substring = substring.substring(0, substring.indexOf("/"));
                    } else {
                        sb = new StringBuilder();
                        sb.append("ldap://");
                    }
                    sb.append(substring);
                    X509LDAPCertStoreParameters build = new Builder(sb.toString(), str2).build();
                    extendedPKIXParameters.addAdditionalStore(X509Store.getInstance("CERTIFICATE/LDAP", (X509StoreParameters) build, BouncyCastleProvider.PROVIDER_NAME));
                    extendedPKIXParameters.addAdditionalStore(X509Store.getInstance("CRL/LDAP", (X509StoreParameters) build, BouncyCastleProvider.PROVIDER_NAME));
                    extendedPKIXParameters.addAdditionalStore(X509Store.getInstance("ATTRIBUTECERTIFICATE/LDAP", (X509StoreParameters) build, BouncyCastleProvider.PROVIDER_NAME));
                    extendedPKIXParameters.addAdditionalStore(X509Store.getInstance("CERTIFICATEPAIR/LDAP", (X509StoreParameters) build, BouncyCastleProvider.PROVIDER_NAME));
                }
            } catch (Exception unused) {
                throw new RuntimeException("Exception adding X.509 stores.");
            }
        }
    }

    protected static void addAdditionalStoresFromAltNames(X509Certificate x509Certificate, ExtendedPKIXParameters extendedPKIXParameters) {
        if (x509Certificate.getIssuerAlternativeNames() != null) {
            for (List list : x509Certificate.getIssuerAlternativeNames()) {
                if (list.get(0).equals(Integers.valueOf(6))) {
                    addAdditionalStoreFromLocation((String) list.get(1), extendedPKIXParameters);
                }
            }
        }
    }

    protected static void addAdditionalStoresFromCRLDistributionPoint(CRLDistPoint cRLDistPoint, ExtendedPKIXParameters extendedPKIXParameters) {
        if (cRLDistPoint != null) {
            try {
                DistributionPoint[] distributionPoints = cRLDistPoint.getDistributionPoints();
                for (DistributionPoint distributionPoint : distributionPoints) {
                    DistributionPointName distributionPoint2 = distributionPoint.getDistributionPoint();
                    if (distributionPoint2 != null && distributionPoint2.getType() == 0) {
                        GeneralName[] names = GeneralNames.getInstance(distributionPoint2.getName()).getNames();
                        for (int i = 0; i < names.length; i++) {
                            if (names[i].getTagNo() == 6) {
                                addAdditionalStoreFromLocation(DERIA5String.getInstance(names[i].getName()).getString(), extendedPKIXParameters);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new AnnotatedException("Distribution points could not be read.", e);
            }
        }
    }

    protected static Collection findCertificates(X509AttributeCertStoreSelector x509AttributeCertStoreSelector, List list) {
        HashSet hashSet = new HashSet();
        for (Object next : list) {
            if (next instanceof X509Store) {
                try {
                    hashSet.addAll(((X509Store) next).getMatches(x509AttributeCertStoreSelector));
                } catch (StoreException e) {
                    throw new AnnotatedException("Problem while picking certificates from X.509 store.", e);
                }
            }
        }
        return hashSet;
    }

    protected static Collection findCertificates(X509CertStoreSelector x509CertStoreSelector, List list) {
        HashSet hashSet = new HashSet();
        for (Object next : list) {
            if (next instanceof X509Store) {
                try {
                    hashSet.addAll(((X509Store) next).getMatches(x509CertStoreSelector));
                } catch (StoreException e) {
                    throw new AnnotatedException("Problem while picking certificates from X.509 store.", e);
                }
            } else {
                try {
                    hashSet.addAll(((CertStore) next).getCertificates(x509CertStoreSelector));
                } catch (CertStoreException e2) {
                    throw new AnnotatedException("Problem while picking certificates from certificate store.", e2);
                }
            }
        }
        return hashSet;
    }

    protected static Collection findIssuerCerts(X509Certificate x509Certificate, ExtendedPKIXBuilderParameters extendedPKIXBuilderParameters) {
        X509CertStoreSelector x509CertStoreSelector = new X509CertStoreSelector();
        HashSet hashSet = new HashSet();
        try {
            x509CertStoreSelector.setSubject(x509Certificate.getIssuerX500Principal().getEncoded());
            try {
                ArrayList<X509Certificate> arrayList = new ArrayList<>();
                arrayList.addAll(findCertificates(x509CertStoreSelector, extendedPKIXBuilderParameters.getCertStores()));
                arrayList.addAll(findCertificates(x509CertStoreSelector, extendedPKIXBuilderParameters.getStores()));
                arrayList.addAll(findCertificates(x509CertStoreSelector, extendedPKIXBuilderParameters.getAdditionalStores()));
                for (X509Certificate add : arrayList) {
                    hashSet.add(add);
                }
                return hashSet;
            } catch (AnnotatedException e) {
                throw new AnnotatedException("Issuer certificate cannot be searched.", e);
            }
        } catch (IOException e2) {
            throw new AnnotatedException("Subject criteria for certificate selector to find issuer certificate could not be set.", e2);
        }
    }

    protected static TrustAnchor findTrustAnchor(X509Certificate x509Certificate, Set set) {
        return findTrustAnchor(x509Certificate, set, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0063 A[SYNTHETIC, Splitter:B:24:0x0063] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0018 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static java.security.cert.TrustAnchor findTrustAnchor(java.security.cert.X509Certificate r8, java.util.Set r9, java.lang.String r10) {
        /*
            java.security.cert.X509CertSelector r0 = new java.security.cert.X509CertSelector
            r0.<init>()
            javax.security.auth.x500.X500Principal r1 = getEncodedIssuerPrincipal(r8)
            byte[] r2 = r1.getEncoded()     // Catch:{ IOException -> 0x0078 }
            r0.setSubject(r2)     // Catch:{ IOException -> 0x0078 }
            java.util.Iterator r9 = r9.iterator()
            r2 = 0
            r3 = r2
            r4 = r3
            r5 = r4
        L_0x0018:
            boolean r6 = r9.hasNext()
            if (r6 == 0) goto L_0x006b
            if (r3 != 0) goto L_0x006b
            java.lang.Object r3 = r9.next()
            java.security.cert.TrustAnchor r3 = (java.security.cert.TrustAnchor) r3
            java.security.cert.X509Certificate r6 = r3.getTrustedCert()
            if (r6 == 0) goto L_0x0041
            java.security.cert.X509Certificate r6 = r3.getTrustedCert()
            boolean r6 = r0.match(r6)
            if (r6 == 0) goto L_0x003f
            java.security.cert.X509Certificate r5 = r3.getTrustedCert()
            java.security.PublicKey r5 = r5.getPublicKey()
            goto L_0x0061
        L_0x003f:
            r3 = r2
            goto L_0x0061
        L_0x0041:
            java.lang.String r6 = r3.getCAName()
            if (r6 == 0) goto L_0x003f
            java.security.PublicKey r6 = r3.getCAPublicKey()
            if (r6 == 0) goto L_0x003f
            javax.security.auth.x500.X500Principal r6 = new javax.security.auth.x500.X500Principal     // Catch:{ IllegalArgumentException -> 0x003f }
            java.lang.String r7 = r3.getCAName()     // Catch:{ IllegalArgumentException -> 0x003f }
            r6.<init>(r7)     // Catch:{ IllegalArgumentException -> 0x003f }
            boolean r6 = r1.equals(r6)     // Catch:{ IllegalArgumentException -> 0x003f }
            if (r6 == 0) goto L_0x003f
            java.security.PublicKey r6 = r3.getCAPublicKey()     // Catch:{ IllegalArgumentException -> 0x003f }
            r5 = r6
        L_0x0061:
            if (r5 == 0) goto L_0x0018
            verifyX509Certificate(r8, r5, r10)     // Catch:{ Exception -> 0x0067 }
            goto L_0x0018
        L_0x0067:
            r4 = move-exception
            r3 = r2
            r5 = r3
            goto L_0x0018
        L_0x006b:
            if (r3 != 0) goto L_0x0077
            if (r4 == 0) goto L_0x0077
            org.bouncycastle.jce.provider.AnnotatedException r8 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r9 = "TrustAnchor found but certificate validation failed."
            r8.<init>(r9, r4)
            throw r8
        L_0x0077:
            return r3
        L_0x0078:
            r8 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r9 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r10 = "Cannot set subject search criteria for trust anchor."
            r9.<init>(r10, r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.CertPathValidatorUtilities.findTrustAnchor(java.security.cert.X509Certificate, java.util.Set, java.lang.String):java.security.cert.TrustAnchor");
    }

    protected static AlgorithmIdentifier getAlgorithmIdentifier(PublicKey publicKey) {
        try {
            return SubjectPublicKeyInfo.getInstance(new ASN1InputStream(publicKey.getEncoded()).readObject()).getAlgorithmId();
        } catch (Exception e) {
            throw new ExtCertPathValidatorException("Subject public key cannot be decoded.", e);
        }
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Collection, code=java.util.Collection<javax.security.auth.x500.X500Principal>, for r3v0, types: [java.util.Collection, java.util.Collection<javax.security.auth.x500.X500Principal>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static void getCRLIssuersFromDistributionPoint(org.bouncycastle.asn1.x509.DistributionPoint r2, java.util.Collection<javax.security.auth.x500.X500Principal> r3, java.security.cert.X509CRLSelector r4, org.bouncycastle.x509.ExtendedPKIXParameters r5) {
        /*
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            org.bouncycastle.asn1.x509.GeneralNames r0 = r2.getCRLIssuer()
            if (r0 == 0) goto L_0x0043
            org.bouncycastle.asn1.x509.GeneralNames r2 = r2.getCRLIssuer()
            org.bouncycastle.asn1.x509.GeneralName[] r2 = r2.getNames()
            r3 = 0
        L_0x0014:
            int r0 = r2.length
            if (r3 >= r0) goto L_0x0065
            r0 = r2[r3]
            int r0 = r0.getTagNo()
            r1 = 4
            if (r0 != r1) goto L_0x0040
            javax.security.auth.x500.X500Principal r0 = new javax.security.auth.x500.X500Principal     // Catch:{ IOException -> 0x0037 }
            r1 = r2[r3]     // Catch:{ IOException -> 0x0037 }
            org.bouncycastle.asn1.ASN1Encodable r1 = r1.getName()     // Catch:{ IOException -> 0x0037 }
            org.bouncycastle.asn1.ASN1Primitive r1 = r1.toASN1Primitive()     // Catch:{ IOException -> 0x0037 }
            byte[] r1 = r1.getEncoded()     // Catch:{ IOException -> 0x0037 }
            r0.<init>(r1)     // Catch:{ IOException -> 0x0037 }
            r5.add(r0)     // Catch:{ IOException -> 0x0037 }
            goto L_0x0040
        L_0x0037:
            r2 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r3 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r4 = "CRL issuer information from distribution point cannot be decoded."
            r3.<init>(r4, r2)
            throw r3
        L_0x0040:
            int r3 = r3 + 1
            goto L_0x0014
        L_0x0043:
            org.bouncycastle.asn1.x509.DistributionPointName r2 = r2.getDistributionPoint()
            if (r2 != 0) goto L_0x0051
            org.bouncycastle.jce.provider.AnnotatedException r2 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r3 = "CRL issuer is omitted from distribution point but no distributionPoint field present."
            r2.<init>(r3)
            throw r2
        L_0x0051:
            java.util.Iterator r2 = r3.iterator()
        L_0x0055:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0065
            java.lang.Object r3 = r2.next()
            javax.security.auth.x500.X500Principal r3 = (javax.security.auth.x500.X500Principal) r3
            r5.add(r3)
            goto L_0x0055
        L_0x0065:
            java.util.Iterator r2 = r5.iterator()
        L_0x0069:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0086
            java.lang.Object r3 = r2.next()     // Catch:{ IOException -> 0x007d }
            javax.security.auth.x500.X500Principal r3 = (javax.security.auth.x500.X500Principal) r3     // Catch:{ IOException -> 0x007d }
            byte[] r3 = r3.getEncoded()     // Catch:{ IOException -> 0x007d }
            r4.addIssuerName(r3)     // Catch:{ IOException -> 0x007d }
            goto L_0x0069
        L_0x007d:
            r2 = move-exception
            org.bouncycastle.jce.provider.AnnotatedException r3 = new org.bouncycastle.jce.provider.AnnotatedException
            java.lang.String r4 = "Cannot decode CRL issuer information."
            r3.<init>(r4, r2)
            throw r3
        L_0x0086:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.CertPathValidatorUtilities.getCRLIssuersFromDistributionPoint(org.bouncycastle.asn1.x509.DistributionPoint, java.util.Collection, java.security.cert.X509CRLSelector, org.bouncycastle.x509.ExtendedPKIXParameters):void");
    }

    protected static void getCertStatus(Date date, X509CRL x509crl, Object obj, CertStatus certStatus) {
        X509CRLEntry x509CRLEntry;
        try {
            if (X509CRLObject.a(x509crl)) {
                x509CRLEntry = x509crl.getRevokedCertificate(a(obj));
                if (x509CRLEntry != null) {
                    X500Principal certificateIssuer = x509CRLEntry.getCertificateIssuer();
                    if (certificateIssuer == null) {
                        certificateIssuer = getIssuerPrincipal(x509crl);
                    }
                    if (!getEncodedIssuerPrincipal(obj).equals(certificateIssuer)) {
                        return;
                    }
                } else {
                    return;
                }
            } else if (getEncodedIssuerPrincipal(obj).equals(getIssuerPrincipal(x509crl))) {
                x509CRLEntry = x509crl.getRevokedCertificate(a(obj));
                if (x509CRLEntry == null) {
                    return;
                }
            } else {
                return;
            }
            ASN1Enumerated aSN1Enumerated = null;
            if (x509CRLEntry.hasExtensions()) {
                try {
                    aSN1Enumerated = ASN1Enumerated.getInstance(getExtensionValue(x509CRLEntry, X509Extension.reasonCode.getId()));
                } catch (Exception e) {
                    throw new AnnotatedException("Reason code CRL entry extension could not be decoded.", e);
                }
            }
            if (date.getTime() >= x509CRLEntry.getRevocationDate().getTime() || aSN1Enumerated == null || aSN1Enumerated.getValue().intValue() == 0 || aSN1Enumerated.getValue().intValue() == 1 || aSN1Enumerated.getValue().intValue() == 2 || aSN1Enumerated.getValue().intValue() == 8) {
                certStatus.a(aSN1Enumerated != null ? aSN1Enumerated.getValue().intValue() : 0);
                certStatus.a(x509CRLEntry.getRevocationDate());
            }
        } catch (CRLException e2) {
            throw new AnnotatedException("Failed check for indirect CRL.", e2);
        }
    }

    protected static Set getCompleteCRLs(DistributionPoint distributionPoint, Object obj, Date date, ExtendedPKIXParameters extendedPKIXParameters) {
        X509CRLStoreSelector x509CRLStoreSelector = new X509CRLStoreSelector();
        try {
            HashSet hashSet = new HashSet();
            hashSet.add(obj instanceof X509AttributeCertificate ? ((X509AttributeCertificate) obj).getIssuer().getPrincipals()[0] : getEncodedIssuerPrincipal(obj));
            getCRLIssuersFromDistributionPoint(distributionPoint, hashSet, x509CRLStoreSelector, extendedPKIXParameters);
            if (obj instanceof X509Certificate) {
                x509CRLStoreSelector.setCertificateChecking((X509Certificate) obj);
            } else if (obj instanceof X509AttributeCertificate) {
                x509CRLStoreSelector.setAttrCertificateChecking((X509AttributeCertificate) obj);
            }
            x509CRLStoreSelector.setCompleteCRLEnabled(true);
            Set findCRLs = CRL_UTIL.findCRLs(x509CRLStoreSelector, extendedPKIXParameters, date);
            if (!findCRLs.isEmpty()) {
                return findCRLs;
            }
            if (obj instanceof X509AttributeCertificate) {
                X509AttributeCertificate x509AttributeCertificate = (X509AttributeCertificate) obj;
                StringBuilder sb = new StringBuilder();
                sb.append("No CRLs found for issuer \"");
                sb.append(x509AttributeCertificate.getIssuer().getPrincipals()[0]);
                sb.append("\"");
                throw new AnnotatedException(sb.toString());
            }
            X509Certificate x509Certificate = (X509Certificate) obj;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("No CRLs found for issuer \"");
            sb2.append(x509Certificate.getIssuerX500Principal());
            sb2.append("\"");
            throw new AnnotatedException(sb2.toString());
        } catch (AnnotatedException e) {
            throw new AnnotatedException("Could not get issuer information from distribution point.", e);
        }
    }

    protected static Set getDeltaCRLs(Date date, ExtendedPKIXParameters extendedPKIXParameters, X509CRL x509crl) {
        X509CRLStoreSelector x509CRLStoreSelector = new X509CRLStoreSelector();
        try {
            x509CRLStoreSelector.addIssuerName(getIssuerPrincipal(x509crl).getEncoded());
            try {
                ASN1Primitive extensionValue = getExtensionValue(x509crl, CRL_NUMBER);
                BigInteger bigInteger = null;
                BigInteger positiveValue = extensionValue != null ? ASN1Integer.getInstance(extensionValue).getPositiveValue() : null;
                try {
                    byte[] extensionValue2 = x509crl.getExtensionValue(ISSUING_DISTRIBUTION_POINT);
                    if (positiveValue != null) {
                        bigInteger = positiveValue.add(BigInteger.valueOf(1));
                    }
                    x509CRLStoreSelector.setMinCRLNumber(bigInteger);
                    x509CRLStoreSelector.setIssuingDistributionPoint(extensionValue2);
                    x509CRLStoreSelector.setIssuingDistributionPointEnabled(true);
                    x509CRLStoreSelector.setMaxBaseCRLNumber(positiveValue);
                    Set<X509CRL> findCRLs = CRL_UTIL.findCRLs(x509CRLStoreSelector, extendedPKIXParameters, date);
                    HashSet hashSet = new HashSet();
                    for (X509CRL x509crl2 : findCRLs) {
                        if (a(x509crl2)) {
                            hashSet.add(x509crl2);
                        }
                    }
                    return hashSet;
                } catch (Exception e) {
                    throw new AnnotatedException("Issuing distribution point extension value could not be read.", e);
                }
            } catch (Exception e2) {
                throw new AnnotatedException("CRL number extension could not be extracted from CRL.", e2);
            }
        } catch (IOException e3) {
            throw new AnnotatedException("Cannot extract issuer from CRL.", e3);
        }
    }

    protected static X500Principal getEncodedIssuerPrincipal(Object obj) {
        return obj instanceof X509Certificate ? ((X509Certificate) obj).getIssuerX500Principal() : (X500Principal) ((X509AttributeCertificate) obj).getIssuer().getPrincipals()[0];
    }

    protected static ASN1Primitive getExtensionValue(java.security.cert.X509Extension x509Extension, String str) {
        byte[] extensionValue = x509Extension.getExtensionValue(str);
        if (extensionValue == null) {
            return null;
        }
        return a(str, extensionValue);
    }

    protected static X500Principal getIssuerPrincipal(X509CRL x509crl) {
        return x509crl.getIssuerX500Principal();
    }

    protected static PublicKey getNextWorkingKey(List list, int i) {
        DSAPublicKey dSAPublicKey;
        PublicKey publicKey = ((Certificate) list.get(i)).getPublicKey();
        if (!(publicKey instanceof DSAPublicKey)) {
            return publicKey;
        }
        DSAPublicKey dSAPublicKey2 = (DSAPublicKey) publicKey;
        if (dSAPublicKey2.getParams() != null) {
            return dSAPublicKey2;
        }
        do {
            i++;
            if (i < list.size()) {
                PublicKey publicKey2 = ((X509Certificate) list.get(i)).getPublicKey();
                if (!(publicKey2 instanceof DSAPublicKey)) {
                    throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
                }
                dSAPublicKey = (DSAPublicKey) publicKey2;
            } else {
                throw new CertPathValidatorException("DSA parameters cannot be inherited from previous certificate.");
            }
        } while (dSAPublicKey.getParams() == null);
        DSAParams params = dSAPublicKey.getParams();
        try {
            return KeyFactory.getInstance("DSA", BouncyCastleProvider.PROVIDER_NAME).generatePublic(new DSAPublicKeySpec(dSAPublicKey2.getY(), params.getP(), params.getQ(), params.getG()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected static final Set getQualifierSet(ASN1Sequence aSN1Sequence) {
        HashSet hashSet = new HashSet();
        if (aSN1Sequence == null) {
            return hashSet;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ASN1OutputStream aSN1OutputStream = new ASN1OutputStream(byteArrayOutputStream);
        Enumeration objects = aSN1Sequence.getObjects();
        while (objects.hasMoreElements()) {
            try {
                aSN1OutputStream.writeObject((ASN1Encodable) objects.nextElement());
                hashSet.add(new PolicyQualifierInfo(byteArrayOutputStream.toByteArray()));
                byteArrayOutputStream.reset();
            } catch (IOException e) {
                throw new ExtCertPathValidatorException("Policy qualifier info cannot be decoded.", e);
            }
        }
        return hashSet;
    }

    protected static X500Principal getSubjectPrincipal(X509Certificate x509Certificate) {
        return x509Certificate.getSubjectX500Principal();
    }

    protected static Date getValidCertDateFromValidityModel(ExtendedPKIXParameters extendedPKIXParameters, CertPath certPath, int i) {
        if (extendedPKIXParameters.getValidityModel() != 1) {
            return getValidDate(extendedPKIXParameters);
        }
        if (i <= 0) {
            return getValidDate(extendedPKIXParameters);
        }
        int i2 = i - 1;
        if (i2 == 0) {
            ASN1GeneralizedTime aSN1GeneralizedTime = null;
            try {
                byte[] extensionValue = ((X509Certificate) certPath.getCertificates().get(i2)).getExtensionValue(ISISMTTObjectIdentifiers.id_isismtt_at_dateOfCertGen.getId());
                if (extensionValue != null) {
                    aSN1GeneralizedTime = ASN1GeneralizedTime.getInstance(ASN1Primitive.fromByteArray(extensionValue));
                }
                if (aSN1GeneralizedTime != null) {
                    try {
                        return aSN1GeneralizedTime.getDate();
                    } catch (ParseException e) {
                        throw new AnnotatedException("Date from date of cert gen extension could not be parsed.", e);
                    }
                }
            } catch (IOException unused) {
                throw new AnnotatedException("Date of cert gen extension could not be read.");
            } catch (IllegalArgumentException unused2) {
                throw new AnnotatedException("Date of cert gen extension could not be read.");
            }
        }
        return ((X509Certificate) certPath.getCertificates().get(i2)).getNotBefore();
    }

    protected static Date getValidDate(PKIXParameters pKIXParameters) {
        Date date = pKIXParameters.getDate();
        return date == null ? new Date() : date;
    }

    protected static boolean isAnyPolicy(Set set) {
        return set == null || set.contains("2.5.29.32.0") || set.isEmpty();
    }

    protected static boolean isSelfIssued(X509Certificate x509Certificate) {
        return x509Certificate.getSubjectDN().equals(x509Certificate.getIssuerDN());
    }

    protected static void prepareNextCertB1(int i, List[] listArr, String str, Map map, X509Certificate x509Certificate) {
        boolean z;
        Iterator it = listArr[i].iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode) it.next();
            if (pKIXPolicyNode.getValidPolicy().equals(str)) {
                z = true;
                pKIXPolicyNode.expectedPolicies = (Set) map.get(str);
                break;
            }
        }
        if (!z) {
            Iterator it2 = listArr[i].iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                PKIXPolicyNode pKIXPolicyNode2 = (PKIXPolicyNode) it2.next();
                if ("2.5.29.32.0".equals(pKIXPolicyNode2.getValidPolicy())) {
                    Set set = null;
                    try {
                        Enumeration objects = DERSequence.getInstance(getExtensionValue(x509Certificate, CERTIFICATE_POLICIES)).getObjects();
                        while (true) {
                            if (!objects.hasMoreElements()) {
                                break;
                            }
                            try {
                                PolicyInformation instance = PolicyInformation.getInstance(objects.nextElement());
                                if ("2.5.29.32.0".equals(instance.getPolicyIdentifier().getId())) {
                                    try {
                                        set = getQualifierSet(instance.getPolicyQualifiers());
                                        break;
                                    } catch (CertPathValidatorException e) {
                                        throw new ExtCertPathValidatorException("Policy qualifier info set could not be built.", e);
                                    }
                                }
                            } catch (Exception e2) {
                                throw new AnnotatedException("Policy information cannot be decoded.", e2);
                            }
                        }
                        Set set2 = set;
                        boolean contains = x509Certificate.getCriticalExtensionOIDs() != null ? x509Certificate.getCriticalExtensionOIDs().contains(CERTIFICATE_POLICIES) : false;
                        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) pKIXPolicyNode2.getParent();
                        if ("2.5.29.32.0".equals(pKIXPolicyNode3.getValidPolicy())) {
                            PKIXPolicyNode pKIXPolicyNode4 = new PKIXPolicyNode(new ArrayList(), i, (Set) map.get(str), pKIXPolicyNode3, set2, str, contains);
                            pKIXPolicyNode3.addChild(pKIXPolicyNode4);
                            listArr[i].add(pKIXPolicyNode4);
                            return;
                        }
                    } catch (Exception e3) {
                        throw new AnnotatedException("Certificate policies cannot be decoded.", e3);
                    }
                }
            }
        }
    }

    protected static PKIXPolicyNode prepareNextCertB2(int i, List[] listArr, String str, PKIXPolicyNode pKIXPolicyNode) {
        Iterator it = listArr[i].iterator();
        while (it.hasNext()) {
            PKIXPolicyNode pKIXPolicyNode2 = (PKIXPolicyNode) it.next();
            if (pKIXPolicyNode2.getValidPolicy().equals(str)) {
                ((PKIXPolicyNode) pKIXPolicyNode2.getParent()).removeChild(pKIXPolicyNode2);
                it.remove();
                for (int i2 = i - 1; i2 >= 0; i2--) {
                    List list = listArr[i2];
                    for (int i3 = 0; i3 < list.size(); i3++) {
                        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) list.get(i3);
                        if (!pKIXPolicyNode3.hasChildren()) {
                            pKIXPolicyNode = removePolicyNode(pKIXPolicyNode, listArr, pKIXPolicyNode3);
                            if (pKIXPolicyNode == null) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return pKIXPolicyNode;
    }

    protected static boolean processCertD1i(int i, List[] listArr, ASN1ObjectIdentifier aSN1ObjectIdentifier, Set set) {
        List list = listArr[i - 1];
        for (int i2 = 0; i2 < list.size(); i2++) {
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode) list.get(i2);
            if (pKIXPolicyNode.getExpectedPolicies().contains(aSN1ObjectIdentifier.getId())) {
                HashSet hashSet = new HashSet();
                hashSet.add(aSN1ObjectIdentifier.getId());
                PKIXPolicyNode pKIXPolicyNode2 = new PKIXPolicyNode(new ArrayList(), i, hashSet, pKIXPolicyNode, set, aSN1ObjectIdentifier.getId(), false);
                pKIXPolicyNode.addChild(pKIXPolicyNode2);
                listArr[i].add(pKIXPolicyNode2);
                return true;
            }
        }
        return false;
    }

    protected static void processCertD1ii(int i, List[] listArr, ASN1ObjectIdentifier aSN1ObjectIdentifier, Set set) {
        List list = listArr[i - 1];
        for (int i2 = 0; i2 < list.size(); i2++) {
            PKIXPolicyNode pKIXPolicyNode = (PKIXPolicyNode) list.get(i2);
            if ("2.5.29.32.0".equals(pKIXPolicyNode.getValidPolicy())) {
                HashSet hashSet = new HashSet();
                hashSet.add(aSN1ObjectIdentifier.getId());
                PKIXPolicyNode pKIXPolicyNode2 = new PKIXPolicyNode(new ArrayList(), i, hashSet, pKIXPolicyNode, set, aSN1ObjectIdentifier.getId(), false);
                pKIXPolicyNode.addChild(pKIXPolicyNode2);
                listArr[i].add(pKIXPolicyNode2);
                return;
            }
        }
    }

    protected static PKIXPolicyNode removePolicyNode(PKIXPolicyNode pKIXPolicyNode, List[] listArr, PKIXPolicyNode pKIXPolicyNode2) {
        PKIXPolicyNode pKIXPolicyNode3 = (PKIXPolicyNode) pKIXPolicyNode2.getParent();
        if (pKIXPolicyNode == null) {
            return null;
        }
        if (pKIXPolicyNode3 == null) {
            for (int i = 0; i < listArr.length; i++) {
                listArr[i] = new ArrayList();
            }
            return null;
        }
        pKIXPolicyNode3.removeChild(pKIXPolicyNode2);
        a(listArr, pKIXPolicyNode2);
        return pKIXPolicyNode;
    }

    protected static void verifyX509Certificate(X509Certificate x509Certificate, PublicKey publicKey, String str) {
        if (str == null) {
            x509Certificate.verify(publicKey);
        } else {
            x509Certificate.verify(publicKey, str);
        }
    }
}
