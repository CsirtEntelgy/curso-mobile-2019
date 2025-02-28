package org.bouncycastle.asn1.x9;

import cz.msebera.android.httpclient.HttpStatus;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.eac.EACTags;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.math.ec.ECCurve.F2m;
import org.bouncycastle.math.ec.ECCurve.Fp;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class X962NamedCurves {
    static X9ECParametersHolder a = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("ffffffffffffffffffffffff99def836146bc9b1b4d22831", 16);
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(new BigInteger("6277101735386680763835789423207666416083908700390324961279"), new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(fp, fp.decodePoint(Hex.decode("03188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012")), bigInteger, valueOf, Hex.decode("3045AE6FC8422f64ED579528D38120EAE12196D5"));
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder b = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("fffffffffffffffffffffffe5fb1a724dc80418648d8dd31", 16);
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(new BigInteger("6277101735386680763835789423207666416083908700390324961279"), new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger("cc22d6dfb95c6b25e49c0d6364a4e5980c393aa21668d953", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(fp, fp.decodePoint(Hex.decode("03eea2bae7e1497842f2de7769cfe9c989c072ad696f48034a")), bigInteger, valueOf, Hex.decode("31a92ee2029fd10d901b113e990710f0d21ac6b6"));
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder c = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("ffffffffffffffffffffffff7a62d031c83f4294f640ec13", 16);
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(new BigInteger("6277101735386680763835789423207666416083908700390324961279"), new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16), new BigInteger("22123dc2395a05caa7423daeccc94760a7d462256bd56916", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(fp, fp.decodePoint(Hex.decode("027d29778100c65a1da1783716588dce2b8b4aee8e228f1896")), bigInteger, valueOf, Hex.decode("c469684435deb378c4b65ca9591e2a5763059a2e"));
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder d = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("7fffffffffffffffffffffff7fffff9e5e9a9f5d9071fbd1522688909d0b", 16);
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839"), new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16), new BigInteger("6b016c3bdcf18941d0d654921475ca71a9db2fb27d1d37796185c2942c0a", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(fp, fp.decodePoint(Hex.decode("020ffa963cdca8816ccc33b8642bedf905c3d358573d3f27fbbd3b3cb9aaaf")), bigInteger, valueOf, Hex.decode("e43bb460f0b80cc0c0b075798e948060f8321b7d"));
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder e = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("7fffffffffffffffffffffff800000cfa7e8594377d414c03821bc582063", 16);
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839"), new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16), new BigInteger("617fab6832576cbbfed50d99f0249c3fee58b94ba0038c7ae84c8c832f2c", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(fp, fp.decodePoint(Hex.decode("0238af09d98727705120c921bb5e9e26296a3cdcf2f35757a0eafd87b830e7")), bigInteger, valueOf, Hex.decode("e8b4011604095303ca3b8099982be09fcb9ae616"));
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder f = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("7fffffffffffffffffffffff7fffff975deb41b3a6057c3c432146526551", 16);
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(new BigInteger("883423532389192164791648750360308885314476597252960362792450860609699839"), new BigInteger("7fffffffffffffffffffffff7fffffffffff8000000000007ffffffffffc", 16), new BigInteger("255705fa2a306654b1f4cb03d6a750a30c250102d4988717d9ba15ab6d3e", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(fp, fp.decodePoint(Hex.decode("036768ae8e18bb92cfcf005c949aa2c6d94853d0e660bbf854b1c9505fe95a")), bigInteger, valueOf, Hex.decode("7d7374168ffe3471b60a857686a19475d3bfa2ff"));
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder g = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551", 16);
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951"), new BigInteger("ffffffff00000001000000000000000000000000fffffffffffffffffffffffc", 16), new BigInteger("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(fp, fp.decodePoint(Hex.decode("036b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296")), bigInteger, valueOf, Hex.decode("c49d360886e704936a6678e1139d26b7819f7e90"));
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder h = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("0400000000000000000001E60FC8821CC74DAEAFC1", 16);
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m((int) CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 1, 2, 8, new BigInteger("072546B5435234A422E0789675F432C89435DE5242", 16), new BigInteger("00C9517D06D5240D3CFF38C74B20B6CD4D6F9DD4D9", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("0307AF69989546103D79329FCC3D74880F33BBE803CB")), bigInteger, valueOf, Hex.decode("D2C0FB15760860DEF1EEF4D696E6768756151754"));
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder i = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("03FFFFFFFFFFFFFFFFFFFDF64DE1151ADBB78F10A7", 16);
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m((int) CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 1, 2, 8, new BigInteger("0108B39E77C4B108BED981ED0E890E117C511CF072", 16), new BigInteger("0667ACEB38AF4E488C407433FFAE4F1C811638DF20", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("030024266E4EB5106D0A964D92C4860E2671DB9B6CC5")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder j = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("03FFFFFFFFFFFFFFFFFFFE1AEE140F110AFF961309", 16);
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m((int) CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 1, 2, 8, new BigInteger("07A526C63D3E25A256A007699F5447E32AE456B50E", 16), new BigInteger("03F7061798EB99E238FD6F1BF95B48FEEB4854252B", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("0202F9F87B7C574D0BDECF8A22E6524775F98CDEBDCB")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder k = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("010092537397ECA4F6145799D62B0A19CE06FE26AD", 16);
            BigInteger valueOf = BigInteger.valueOf(65390);
            F2m f2m = new F2m((int) CipherSuite.TLS_PSK_WITH_NULL_SHA256, 1, 2, 43, new BigInteger("00E4E6DB2995065C407D9D39B8D0967B96704BA8E9C90B", 16), new BigInteger("005DDA470ABE6414DE8EC133AE28E9BBD7FCEC0AE0FFF2", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("038D16C2866798B600F9F08BB4A8E860F3298CE04A5798")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder l = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("40000000000000000000000004A20E90C39067C893BBB9A5", 16);
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m((int) CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, 9, new BigInteger("2866537B676752636A68F56554E12640276B649EF7526267", 16), new BigInteger("2E45EF571F00786F67B0081B9495A3D95462F5DE0AA185EC", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("0236B3DAF8A23206F9C4F299D7B21A9C369137F2C84AE1AA0D")), bigInteger, valueOf, Hex.decode("4E13CA542744D696E67687561517552F279A8C84"));
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder m = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("20000000000000000000000050508CB89F652824E06B8173", 16);
            BigInteger valueOf = BigInteger.valueOf(4);
            F2m f2m = new F2m((int) CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, 9, new BigInteger("401028774D7777C7B7666D1366EA432071274F89FF01E718", 16), new BigInteger("0620048D28BCBD03B6249C99182B7C8CD19700C362C46A01", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("023809B2B7CC1B28CC5A87926AAD83FD28789E81E2C9E3BF10")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder n = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("155555555555555555555555610C0B196812BFB6288A3EA3", 16);
            BigInteger valueOf = BigInteger.valueOf(6);
            F2m f2m = new F2m((int) CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, 9, new BigInteger("6C01074756099122221056911C77D77E77A777E7E7E77FCB", 16), new BigInteger("71FE1AF926CF847989EFEF8DB459F66394D90F32AD3F15E8", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("03375D4CE24FDE434489DE8746E71786015009E66E38A926DD")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder o = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("0101BAF95C9723C57B6C21DA2EFF2D5ED588BDD5717E212F9D", 16);
            BigInteger valueOf = BigInteger.valueOf(65096);
            F2m f2m = new F2m(208, 1, 2, 83, new BigInteger("0", 16), new BigInteger("00C8619ED45A62E6212E1160349E2BFA844439FAFC2A3FD1638F9E", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("0289FDFBE4ABE193DF9559ECF07AC0CE78554E2784EB8C1ED1A57A")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder p = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("2000000000000000000000000000000F4D42FFE1492A4993F1CAD666E447", 16);
            BigInteger valueOf = BigInteger.valueOf(4);
            F2m f2m = new F2m(239, 36, new BigInteger("32010857077C5431123A46B808906756F543423E8D27877578125778AC76", 16), new BigInteger("790408F2EEDAF392B012EDEFB3392F30F4327C0CA3F31FC383C422AA8C16", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("0257927098FA932E7C0A96D3FD5B706EF7E5F5C156E16B7E7C86038552E91D")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder q = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("1555555555555555555555555555553C6F2885259C31E3FCDF154624522D", 16);
            BigInteger valueOf = BigInteger.valueOf(6);
            F2m f2m = new F2m(239, 36, new BigInteger("4230017757A767FAE42398569B746325D45313AF0766266479B75654E65F", 16), new BigInteger("5037EA654196CFF0CD82B2C14A2FCF2E3FF8775285B545722F03EACDB74B", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("0228F9D04E900069C8DC47A08534FE76D2B900B7D7EF31F5709F200C4CA205")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder r = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("0CCCCCCCCCCCCCCCCCCCCCCCCCCCCCAC4912D2D9DF903EF9888B8A0E4CFF", 16);
            BigInteger valueOf = BigInteger.valueOf(10);
            F2m f2m = new F2m(239, 36, new BigInteger("01238774666A67766D6676F778E676B66999176666E687666D8766C66A9F", 16), new BigInteger("6A941977BA9F6A435199ACFC51067ED587F519C5ECB541B8E44111DE1D40", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("0370F6E9D04D289C4E89913CE3530BFDE903977D42B146D539BF1BDE4E9C92")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder s = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("0100FAF51354E0E39E4892DF6E319C72C8161603FA45AA7B998A167B8F1E629521", 16);
            BigInteger valueOf = BigInteger.valueOf(65286);
            F2m f2m = new F2m(272, 1, 3, 56, new BigInteger("0091A091F03B5FBA4AB2CCF49C4EDD220FB028712D42BE752B2C40094DBACDB586FB20", 16), new BigInteger("7167EFC92BB2E3CE7C8AAAFF34E12A9C557003D7C73A6FAF003F99F6CC8482E540F7", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("026108BABB2CEEBCF787058A056CBE0CFE622D7723A289E08A07AE13EF0D10D171DD8D")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder t = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("0101D556572AABAC800101D556572AABAC8001022D5C91DD173F8FB561DA6899164443051D", 16);
            BigInteger valueOf = BigInteger.valueOf(65070);
            F2m f2m = new F2m((int) HttpStatus.SC_NOT_MODIFIED, 1, 2, 11, new BigInteger("00FD0D693149A118F651E6DCE6802085377E5F882D1B510B44160074C1288078365A0396C8E681", 16), new BigInteger("00BDDB97E555A50A908E43B01C798EA5DAA6788F1EA2794EFCF57166B8C14039601E55827340BE", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("02197B07845E9BE2D96ADB0F5F3C7F2CFFBD7A3EB8B6FEC35C7FD67F26DDF6285A644F740A2614")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder u = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("01AF286BCA1AF286BCA1AF286BCA1AF286BCA1AF286BC9FB8F6B85C556892C20A7EB964FE7719E74F490758D3B", 16);
            BigInteger valueOf = BigInteger.valueOf(76);
            F2m f2m = new F2m(359, 68, new BigInteger("5667676A654B20754F356EA92017D946567C46675556F19556A04616B567D223A5E05656FB549016A96656A557", 16), new BigInteger("2472E2D0197C49363F1FE7F5B6DB075D52B6947D135D8CA445805D39BC345626089687742B6329E70680231988", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("033C258EF3047767E7EDE0F1FDAA79DAEE3841366A132E163ACED4ED2401DF9C6BDCDE98E8E707C07A2239B1B097")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder v = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("010090512DA9AF72B08349D98A5DD4C7B0532ECA51CE03E2D10F3B7AC579BD87E909AE40A6F131E9CFCE5BD967", 16);
            BigInteger valueOf = BigInteger.valueOf(65392);
            F2m f2m = new F2m(368, 1, 2, 85, new BigInteger("00E0D2EE25095206F5E2A4F9ED229F1F256E79A0E2B455970D8D0D865BD94778C576D62F0AB7519CCD2A1A906AE30D", 16), new BigInteger("00FC1217D4320A90452C760A58EDCD30C8DD069B3C34453837A34ED50CB54917E1C2112D84D164F444F8F74786046A", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("021085E2755381DCCCE3C1557AFA10C2F0C0C2825646C5B34A394CBCFA8BC16B22E7E789E927BE216F02E1FB136A5F")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder w = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = new BigInteger("0340340340340340340340340340340340340340340340340340340323C313FAB50589703B5EC68D3587FEC60D161CC149C1AD4A91", 16);
            BigInteger valueOf = BigInteger.valueOf(10080);
            F2m f2m = new F2m(431, (int) EACTags.COMPATIBLE_TAG_ALLOCATION_AUTHORITY, new BigInteger("1A827EF00DD6FC0E234CAF046C6A5D8A85395B236CC4AD2CF32A0CADBDC9DDF620B0EB9906D0957F6C6FEACD615468DF104DE296CD8F", 16), new BigInteger("10D9B4A3D9047D8B154359ABFB1B7F5485B04CEB868237DDC9DEDA982A679A5A919B626D4E50A8DD731B107A9962381FB5D807BF2618", 16), bigInteger, valueOf);
            X9ECParameters x9ECParameters = new X9ECParameters(f2m, f2m.decodePoint(Hex.decode("02120FC05D3C67A99DE161D2F4092622FECA701BE4F50F4758714E8A87BBF2A658EF8C21E7C5EFE965361F6C2999C0C247B0DBD70CE6B7")), bigInteger, valueOf, null);
            return x9ECParameters;
        }
    };
    static final Hashtable x = new Hashtable();
    static final Hashtable y = new Hashtable();
    static final Hashtable z = new Hashtable();

    static {
        a("prime192v1", X9ObjectIdentifiers.prime192v1, a);
        a("prime192v2", X9ObjectIdentifiers.prime192v2, b);
        a("prime192v3", X9ObjectIdentifiers.prime192v3, c);
        a("prime239v1", X9ObjectIdentifiers.prime239v1, d);
        a("prime239v2", X9ObjectIdentifiers.prime239v2, e);
        a("prime239v3", X9ObjectIdentifiers.prime239v3, f);
        a("prime256v1", X9ObjectIdentifiers.prime256v1, g);
        a("c2pnb163v1", X9ObjectIdentifiers.c2pnb163v1, h);
        a("c2pnb163v2", X9ObjectIdentifiers.c2pnb163v2, i);
        a("c2pnb163v3", X9ObjectIdentifiers.c2pnb163v3, j);
        a("c2pnb176w1", X9ObjectIdentifiers.c2pnb176w1, k);
        a("c2tnb191v1", X9ObjectIdentifiers.c2tnb191v1, l);
        a("c2tnb191v2", X9ObjectIdentifiers.c2tnb191v2, m);
        a("c2tnb191v3", X9ObjectIdentifiers.c2tnb191v3, n);
        a("c2pnb208w1", X9ObjectIdentifiers.c2pnb208w1, o);
        a("c2tnb239v1", X9ObjectIdentifiers.c2tnb239v1, p);
        a("c2tnb239v2", X9ObjectIdentifiers.c2tnb239v2, q);
        a("c2tnb239v3", X9ObjectIdentifiers.c2tnb239v3, r);
        a("c2pnb272w1", X9ObjectIdentifiers.c2pnb272w1, s);
        a("c2pnb304w1", X9ObjectIdentifiers.c2pnb304w1, t);
        a("c2tnb359v1", X9ObjectIdentifiers.c2tnb359v1, u);
        a("c2pnb368w1", X9ObjectIdentifiers.c2pnb368w1, v);
        a("c2tnb431r1", X9ObjectIdentifiers.c2tnb431r1, w);
    }

    static void a(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, X9ECParametersHolder x9ECParametersHolder) {
        x.put(str, aSN1ObjectIdentifier);
        z.put(aSN1ObjectIdentifier, str);
        y.put(aSN1ObjectIdentifier, x9ECParametersHolder);
    }

    public static X9ECParameters getByName(String str) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) x.get(Strings.toLowerCase(str));
        if (aSN1ObjectIdentifier != null) {
            return getByOID(aSN1ObjectIdentifier);
        }
        return null;
    }

    public static X9ECParameters getByOID(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        X9ECParametersHolder x9ECParametersHolder = (X9ECParametersHolder) y.get(aSN1ObjectIdentifier);
        if (x9ECParametersHolder != null) {
            return x9ECParametersHolder.getParameters();
        }
        return null;
    }

    public static String getName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (String) z.get(aSN1ObjectIdentifier);
    }

    public static Enumeration getNames() {
        return x.keys();
    }

    public static ASN1ObjectIdentifier getOID(String str) {
        return (ASN1ObjectIdentifier) x.get(Strings.toLowerCase(str));
    }
}
