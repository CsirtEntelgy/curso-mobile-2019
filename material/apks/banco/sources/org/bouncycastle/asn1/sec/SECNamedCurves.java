package org.bouncycastle.asn1.sec;

import cz.msebera.android.httpclient.HttpStatus;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Hashtable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.asn1.x9.X9ECParametersHolder;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECCurve.F2m;
import org.bouncycastle.math.ec.ECCurve.Fp;
import org.bouncycastle.math.ec.endo.GLVTypeBEndomorphism;
import org.bouncycastle.math.ec.endo.GLVTypeBParameters;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

public class SECNamedCurves {
    static X9ECParametersHolder A = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = ECConstants.ZERO;
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("2000000000000000000000000000005A79FEC67CB6E91F1C1DA800E478A5");
            BigInteger valueOf2 = BigInteger.valueOf(4);
            F2m f2m = new F2m(239, (int) CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, bigInteger, valueOf, a, valueOf2);
            ECCurve a2 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a2, a2.decodePoint(Hex.decode("0429A0B6A887A983E9730988A68727A8B2D126C44CC2CC7B2A6555193035DC76310804F12E549BDB011C103089E73510ACB275FC312A5DC6B76553F0CA")), a, valueOf2, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder B = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = ECConstants.ZERO;
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE9AE2ED07577265DFF7F94451E061E163C61");
            BigInteger valueOf2 = BigInteger.valueOf(4);
            F2m f2m = new F2m(283, 5, 7, 12, bigInteger, valueOf, a, valueOf2);
            ECCurve a2 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a2, a2.decodePoint(Hex.decode("040503213F78CA44883F1A3B8162F188E553CD265F23C1567A16876913B0C2AC245849283601CCDA380F1C9E318D90F95D07E5426FE87E45C0E8184698E45962364E34116177DD2259")), a, valueOf2, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder C = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("027B680AC8B8596DA5A4AF8A19A0303FCA97FD7645309FA2A581485AF6263E313B79A2F5");
            byte[] decode = Hex.decode("77E2B07370EB0F832A6DD5B62DFC88CD06BB84BE");
            BigInteger a2 = SECNamedCurves.b("03FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEF90399660FC938A90165B042A7CEFADB307");
            BigInteger valueOf2 = BigInteger.valueOf(2);
            F2m f2m = new F2m(283, 5, 7, 12, valueOf, a, a2, valueOf2);
            ECCurve a3 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a3, a3.decodePoint(Hex.decode("0405F939258DB7DD90E1934F8C70B0DFEC2EED25B8557EAC9C80E2E198F8CDBECD86B1205303676854FE24141CB98FE6D4B20D02B4516FF702350EDDB0826779C813F0DF45BE8112F4")), a2, valueOf2, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder D = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = ECConstants.ZERO;
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("7FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE5F83B2D4EA20400EC4557D5ED3E3E7CA5B4B5C83B8E01E5FCF");
            BigInteger valueOf2 = BigInteger.valueOf(4);
            F2m f2m = new F2m((int) HttpStatus.SC_CONFLICT, 87, bigInteger, valueOf, a, valueOf2);
            ECCurve a2 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a2, a2.decodePoint(Hex.decode("040060F05F658F49C1AD3AB1890F7184210EFD0987E307C84C27ACCFB8F9F67CC2C460189EB5AAAA62EE222EB1B35540CFE902374601E369050B7C4E42ACBA1DACBF04299C3460782F918EA427E6325165E9EA10E3DA5F6C42E9C55215AA9CA27A5863EC48D8E0286B")), a, valueOf2, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder E = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("0021A5C2C8EE9FEB5C4B9A753B7B476B7FD6422EF1F3DD674761FA99D6AC27C8A9A197B272822F6CD57A55AA4F50AE317B13545F");
            byte[] decode = Hex.decode("4099B5A457F9D69F79213D094C4BCD4D4262210B");
            BigInteger a2 = SECNamedCurves.b("010000000000000000000000000000000000000000000000000001E2AAD6A612F33307BE5FA47C3C9E052F838164CD37D9A21173");
            BigInteger valueOf2 = BigInteger.valueOf(2);
            F2m f2m = new F2m((int) HttpStatus.SC_CONFLICT, 87, valueOf, a, a2, valueOf2);
            ECCurve a3 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a3, a3.decodePoint(Hex.decode("04015D4860D088DDB3496B0C6064756260441CDE4AF1771D4DB01FFE5B34E59703DC255A868A1180515603AEAB60794E54BB7996A70061B1CFAB6BE5F32BBFA78324ED106A7636B9C5A7BD198D0158AA4F5488D08F38514F1FDF4B4F40D2181B3681C364BA0273C706")), a2, valueOf2, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder F = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = ECConstants.ZERO;
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("020000000000000000000000000000000000000000000000000000000000000000000000131850E1F19A63E4B391A8DB917F4138B630D84BE5D639381E91DEB45CFE778F637C1001");
            BigInteger valueOf2 = BigInteger.valueOf(4);
            F2m f2m = new F2m(571, 2, 5, 10, bigInteger, valueOf, a, valueOf2);
            ECCurve a2 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a2, a2.decodePoint(Hex.decode("04026EB7A859923FBC82189631F8103FE4AC9CA2970012D5D46024804801841CA44370958493B205E647DA304DB4CEB08CBBD1BA39494776FB988B47174DCA88C7E2945283A01C89720349DC807F4FBF374F4AEADE3BCA95314DD58CEC9F307A54FFC61EFC006D8A2C9D4979C0AC44AEA74FBEBBB9F772AEDCB620B01A7BA7AF1B320430C8591984F601CD4C143EF1C7A3")), a, valueOf2, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder G = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("02F40E7E2221F295DE297117B7F3D62F5C6A97FFCB8CEFF1CD6BA8CE4A9A18AD84FFABBD8EFA59332BE7AD6756A66E294AFD185A78FF12AA520E4DE739BACA0C7FFEFF7F2955727A");
            byte[] decode = Hex.decode("2AA058F73A0E33AB486B0F610410C53A7F132310");
            BigInteger a2 = SECNamedCurves.b("03FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE661CE18FF55987308059B186823851EC7DD9CA1161DE93D5174D66E8382E9BB2FE84E47");
            BigInteger valueOf2 = BigInteger.valueOf(2);
            F2m f2m = new F2m(571, 2, 5, 10, valueOf, a, a2, valueOf2);
            ECCurve a3 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a3, a3.decodePoint(Hex.decode("040303001D34B856296C16C0D40D3CD7750A93D1D2955FA80AA5F40FC8DB7B2ABDBDE53950F4C0D293CDD711A35B67FB1499AE60038614F1394ABFA3B4C850D927E1E7769C8EEC2D19037BF27342DA639B6DCCFFFEB73D69D78C6C27A6009CBBCA1980F8533921E8A684423E43BAB08A576291AF8F461BB2A8B3531D2F0485C19B16E2F1516E23DD3C1A4827AF1B8AC15B")), a2, valueOf2, decode);
            return x9ECParameters;
        }
    };
    static final Hashtable H = new Hashtable();
    static final Hashtable I = new Hashtable();
    static final Hashtable J = new Hashtable();
    static X9ECParametersHolder a = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("DB7C2ABF62E35E668076BEAD208B");
            BigInteger a2 = SECNamedCurves.b("DB7C2ABF62E35E668076BEAD2088");
            BigInteger a3 = SECNamedCurves.b("659EF8BA043916EEDE8911702B22");
            byte[] decode = Hex.decode("00F50B028E4D696E676875615175290472783FB1");
            BigInteger a4 = SECNamedCurves.b("DB7C2ABF62E35E7628DFAC6561C5");
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("0409487239995A5EE76B55F9C2F098A89CE5AF8724C0A23E0E0FF77500")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder b = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("DB7C2ABF62E35E668076BEAD208B");
            BigInteger a2 = SECNamedCurves.b("6127C24C05F38A0AAAF65C0EF02C");
            BigInteger a3 = SECNamedCurves.b("51DEF1815DB5ED74FCC34C85D709");
            byte[] decode = Hex.decode("002757A1114D696E6768756151755316C05E0BD4");
            BigInteger a4 = SECNamedCurves.b("36DF0AAFD8B8D7597CA10520D04B");
            BigInteger valueOf = BigInteger.valueOf(4);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("044BA30AB5E892B4E1649DD0928643ADCD46F5882E3747DEF36E956E97")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder c = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF");
            BigInteger a2 = SECNamedCurves.b("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFC");
            BigInteger a3 = SECNamedCurves.b("E87579C11079F43DD824993C2CEE5ED3");
            byte[] decode = Hex.decode("000E0D4D696E6768756151750CC03A4473D03679");
            BigInteger a4 = SECNamedCurves.b("FFFFFFFE0000000075A30D1B9038A115");
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("04161FF7528B899B2D0C28607CA52C5B86CF5AC8395BAFEB13C02DA292DDED7A83")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder d = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFDFFFFFFFFFFFFFFFFFFFFFFFF");
            BigInteger a2 = SECNamedCurves.b("D6031998D1B3BBFEBF59CC9BBFF9AEE1");
            BigInteger a3 = SECNamedCurves.b("5EEEFCA380D02919DC2C6558BB6D8A5D");
            byte[] decode = Hex.decode("004D696E67687561517512D8F03431FCE63B88F4");
            BigInteger a4 = SECNamedCurves.b("3FFFFFFF7FFFFFFFBE0024720613B5A3");
            BigInteger valueOf = BigInteger.valueOf(4);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("047B6AA5D85E572983E6FB32A7CDEBC14027B6916A894D3AEE7106FE805FC34B44")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder e = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC73");
            BigInteger bigInteger = ECConstants.ZERO;
            BigInteger valueOf = BigInteger.valueOf(7);
            BigInteger a2 = SECNamedCurves.b("0100000000000000000001B8FA16DFAB9ACA16B6B3");
            BigInteger valueOf2 = BigInteger.valueOf(1);
            GLVTypeBParameters gLVTypeBParameters = new GLVTypeBParameters(new BigInteger("9ba48cba5ebcb9b6bd33b92830b2a2e0e192f10a", 16), new BigInteger("c39c6c3b3a36d7701b9c71a1f5804ae5d0003f4", 16), new BigInteger[]{new BigInteger("9162fbe73984472a0a9e", 16), new BigInteger("-96341f1138933bc2f505", 16)}, new BigInteger[]{new BigInteger("127971af8721782ecffa3", 16), new BigInteger("9162fbe73984472a0a9e", 16)}, new BigInteger("9162fbe73984472a0a9d0590", 16), new BigInteger("96341f1138933bc2f503fd44", 16), CipherSuite.TLS_PSK_WITH_NULL_SHA256);
            Fp fp = new Fp(a, bigInteger, valueOf, a2, valueOf2);
            ECCurve a3 = SECNamedCurves.b(fp, gLVTypeBParameters);
            X9ECParameters x9ECParameters = new X9ECParameters(a3, a3.decodePoint(Hex.decode("043B4C382CE37AA192A4019E763036F4F5DD4D7EBB938CF935318FDCED6BC28286531733C3F03C4FEE")), a2, valueOf2, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder f = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFF");
            BigInteger a2 = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF7FFFFFFC");
            BigInteger a3 = SECNamedCurves.b("1C97BEFC54BD7A8B65ACF89F81D4D4ADC565FA45");
            byte[] decode = Hex.decode("1053CDE42C14D696E67687561517533BF3F83345");
            BigInteger a4 = SECNamedCurves.b("0100000000000000000001F4C8F927AED3CA752257");
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("044A96B5688EF573284664698968C38BB913CBFC8223A628553168947D59DCC912042351377AC5FB32")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder g = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC73");
            BigInteger a2 = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC70");
            BigInteger a3 = SECNamedCurves.b("B4E134D3FB59EB8BAB57274904664D5AF50388BA");
            byte[] decode = Hex.decode("B99B99B099B323E02709A4D696E6768756151751");
            BigInteger a4 = SECNamedCurves.b("0100000000000000000000351EE786A818F3A1A16B");
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("0452DCB034293A117E1F4FF11B30F7199D3144CE6DFEAFFEF2E331F296E071FA0DF9982CFEA7D43F2E")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder h = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFEE37");
            BigInteger bigInteger = ECConstants.ZERO;
            BigInteger valueOf = BigInteger.valueOf(3);
            BigInteger a2 = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFE26F2FC170F69466A74DEFD8D");
            BigInteger valueOf2 = BigInteger.valueOf(1);
            GLVTypeBParameters gLVTypeBParameters = new GLVTypeBParameters(new BigInteger("bb85691939b869c1d087f601554b96b80cb4f55b35f433c2", 16), new BigInteger("3d84f26c12238d7b4f3d516613c1759033b1a5800175d0b1", 16), new BigInteger[]{new BigInteger("71169be7330b3038edb025f1", 16), new BigInteger("-b3fb3400dec5c4adceb8655c", 16)}, new BigInteger[]{new BigInteger("12511cfe811d0f4e6bc688b4d", 16), new BigInteger("71169be7330b3038edb025f1", 16)}, new BigInteger("71169be7330b3038edb025f1d0f9", 16), new BigInteger("b3fb3400dec5c4adceb8655d4c94", 16), 208);
            Fp fp = new Fp(a, bigInteger, valueOf, a2, valueOf2);
            ECCurve a3 = SECNamedCurves.b(fp, gLVTypeBParameters);
            X9ECParameters x9ECParameters = new X9ECParameters(a3, a3.decodePoint(Hex.decode("04DB4FF10EC057E9AE26B07D0280B7F4341DA5D1B1EAE06C7D9B2F2F6D9C5628A7844163D015BE86344082AA88D95E2F9D")), a2, valueOf2, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder i = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF");
            BigInteger a2 = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFC");
            BigInteger a3 = SECNamedCurves.b("64210519E59C80E70FA7E9AB72243049FEB8DEECC146B9B1");
            byte[] decode = Hex.decode("3045AE6FC8422F64ED579528D38120EAE12196D5");
            BigInteger a4 = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831");
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("04188DA80EB03090F67CBF20EB43A18800F4FF0AFD82FF101207192B95FFC8DA78631011ED6B24CDD573F977A11E794811")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder j = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFE56D");
            BigInteger bigInteger = ECConstants.ZERO;
            BigInteger valueOf = BigInteger.valueOf(5);
            BigInteger a2 = SECNamedCurves.b("010000000000000000000000000001DCE8D2EC6184CAF0A971769FB1F7");
            BigInteger valueOf2 = BigInteger.valueOf(1);
            GLVTypeBParameters gLVTypeBParameters = new GLVTypeBParameters(new BigInteger("fe0e87005b4e83761908c5131d552a850b3f58b749c37cf5b84d6768", 16), new BigInteger("60dcd2104c4cbc0be6eeefc2bdd610739ec34e317f9b33046c9e4788", 16), new BigInteger[]{new BigInteger("6b8cf07d4ca75c88957d9d670591", 16), new BigInteger("-b8adf1378a6eb73409fa6c9c637d", 16)}, new BigInteger[]{new BigInteger("1243ae1b4d71613bc9f780a03690e", 16), new BigInteger("6b8cf07d4ca75c88957d9d670591", 16)}, new BigInteger("6b8cf07d4ca75c88957d9d67059037a4", 16), new BigInteger("b8adf1378a6eb73409fa6c9c637ba7f5", 16), 240);
            Fp fp = new Fp(a, bigInteger, valueOf, a2, valueOf2);
            ECCurve a3 = SECNamedCurves.b(fp, gLVTypeBParameters);
            X9ECParameters x9ECParameters = new X9ECParameters(a3, a3.decodePoint(Hex.decode("04A1455B334DF099DF30FC28A169A467E9E47075A90F7E650EB6B7A45C7E089FED7FBA344282CAFBD6F7E319F7C0B0BD59E2CA4BDB556D61A5")), a2, valueOf2, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder k = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000000000000000000001");
            BigInteger a2 = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFE");
            BigInteger a3 = SECNamedCurves.b("B4050A850C04B3ABF54132565044B0B7D7BFD8BA270B39432355FFB4");
            byte[] decode = Hex.decode("BD71344799D5C7FCDC45B59FA3B9AB8F6A948BC5");
            BigInteger a4 = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFF16A2E0B8F03E13DD29455C5C2A3D");
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("04B70E0CBD6BB4BF7F321390B94A03C1D356C21122343280D6115C1D21BD376388B5F723FB4C22DFE6CD4375A05A07476444D5819985007E34")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder l = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F");
            BigInteger bigInteger = ECConstants.ZERO;
            BigInteger valueOf = BigInteger.valueOf(7);
            BigInteger a2 = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141");
            BigInteger valueOf2 = BigInteger.valueOf(1);
            GLVTypeBParameters gLVTypeBParameters = new GLVTypeBParameters(new BigInteger("7ae96a2b657c07106e64479eac3434e99cf0497512f58995c1396c28719501ee", 16), new BigInteger("5363ad4cc05c30e0a5261c028812645a122e22ea20816678df02967c1b23bd72", 16), new BigInteger[]{new BigInteger("3086d221a7d46bcde86c90e49284eb15", 16), new BigInteger("-e4437ed6010e88286f547fa90abfe4c3", 16)}, new BigInteger[]{new BigInteger("114ca50f7a8e2f3f657c1108d9d44cfd8", 16), new BigInteger("3086d221a7d46bcde86c90e49284eb15", 16)}, new BigInteger("3086d221a7d46bcde86c90e49284eb153dab", 16), new BigInteger("e4437ed6010e88286f547fa90abfe4c42212", 16), 272);
            Fp fp = new Fp(a, bigInteger, valueOf, a2, valueOf2);
            ECCurve a3 = SECNamedCurves.b(fp, gLVTypeBParameters);
            X9ECParameters x9ECParameters = new X9ECParameters(a3, a3.decodePoint(Hex.decode("0479BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798483ADA7726A3C4655DA4FBFC0E1108A8FD17B448A68554199C47D08FFB10D4B8")), a2, valueOf2, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder m = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF");
            BigInteger a2 = SECNamedCurves.b("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC");
            BigInteger a3 = SECNamedCurves.b("5AC635D8AA3A93E7B3EBBD55769886BC651D06B0CC53B0F63BCE3C3E27D2604B");
            byte[] decode = Hex.decode("C49D360886E704936A6678E1139D26B7819F7E90");
            BigInteger a4 = SECNamedCurves.b("FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551");
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("046B17D1F2E12C4247F8BCE6E563A440F277037D812DEB33A0F4A13945D898C2964FE342E2FE1A7F9B8EE7EB4A7C0F9E162BCE33576B315ECECBB6406837BF51F5")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder n = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFF");
            BigInteger a2 = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFF0000000000000000FFFFFFFC");
            BigInteger a3 = SECNamedCurves.b("B3312FA7E23EE7E4988E056BE3F82D19181D9C6EFE8141120314088F5013875AC656398D8A2ED19D2A85C8EDD3EC2AEF");
            byte[] decode = Hex.decode("A335926AA319A27A1D00896A6773A4827ACDAC73");
            BigInteger a4 = SECNamedCurves.b("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC7634D81F4372DDF581A0DB248B0A77AECEC196ACCC52973");
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("04AA87CA22BE8B05378EB1C71EF320AD746E1D3B628BA79B9859F741E082542A385502F25DBF55296C3A545E3872760AB73617DE4A96262C6F5D9E98BF9292DC29F8F41DBD289A147CE9DA3113B5F0B8C00A60B1CE1D7E819D7A431D7C90EA0E5F")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder o = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
            BigInteger a2 = SECNamedCurves.b("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFC");
            BigInteger a3 = SECNamedCurves.b("0051953EB9618E1C9A1F929A21A0B68540EEA2DA725B99B315F3B8B489918EF109E156193951EC7E937B1652C0BD3BB1BF073573DF883D2C34F1EF451FD46B503F00");
            byte[] decode = Hex.decode("D09E8800291CB85396CC6717393284AAA0DA64BA");
            BigInteger a4 = SECNamedCurves.b("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFA51868783BF2F966B7FCC0148F709A5D03BB5C9B8899C47AEBB6FB71E91386409");
            BigInteger valueOf = BigInteger.valueOf(1);
            Fp fp = new Fp(a, a2, a3, a4, valueOf);
            ECCurve a5 = SECNamedCurves.b((ECCurve) fp);
            X9ECParameters x9ECParameters = new X9ECParameters(a5, a5.decodePoint(Hex.decode("0400C6858E06B70404E9CD9E3ECB662395B4429C648139053FB521F828AF606B4D3DBAA14B5E77EFE75928FE1DC127A2FFA8DE3348B3C1856A429BF97E7E31C2E5BD66011839296A789A3BC0045C8A5FB42C7D1BD998F54449579B446817AFBD17273E662C97EE72995EF42640C550B9013FAD0761353C7086A272C24088BE94769FD16650")), a4, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder p = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("003088250CA6E7C7FE649CE85820F7");
            BigInteger a2 = SECNamedCurves.b("00E8BEE4D3E2260744188BE0E9C723");
            byte[] decode = Hex.decode("10E723AB14D696E6768756151756FEBF8FCB49A9");
            BigInteger a3 = SECNamedCurves.b("0100000000000000D9CCEC8A39E56F");
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m(113, 9, a, a2, a3, valueOf);
            ECCurve a4 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a4, a4.decodePoint(Hex.decode("04009D73616F35F4AB1407D73562C10F00A52830277958EE84D1315ED31886")), a3, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder q = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("00689918DBEC7E5A0DD6DFC0AA55C7");
            BigInteger a2 = SECNamedCurves.b("0095E9A9EC9B297BD4BF36E059184F");
            byte[] decode = Hex.decode("10C0FB15760860DEF1EEF4D696E676875615175D");
            BigInteger a3 = SECNamedCurves.b("010000000000000108789B2496AF93");
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m(113, 9, a, a2, a3, valueOf);
            ECCurve a4 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a4, a4.decodePoint(Hex.decode("0401A57A6A7B26CA5EF52FCDB816479700B3ADC94ED1FE674C06E695BABA1D")), a3, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder r = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("07A11B09A76B562144418FF3FF8C2570B8");
            BigInteger a2 = SECNamedCurves.b("0217C05610884B63B9C6C7291678F9D341");
            byte[] decode = Hex.decode("4D696E676875615175985BD3ADBADA21B43A97E2");
            BigInteger a3 = SECNamedCurves.b("0400000000000000023123953A9464B54D");
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m(131, 2, 3, 8, a, a2, a3, valueOf);
            ECCurve a4 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a4, a4.decodePoint(Hex.decode("040081BAF91FDF9833C40F9C181343638399078C6E7EA38C001F73C8134B1B4EF9E150")), a3, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder s = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("03E5A88919D7CAFCBF415F07C2176573B2");
            BigInteger a2 = SECNamedCurves.b("04B8266A46C55657AC734CE38F018F2192");
            byte[] decode = Hex.decode("985BD3ADBAD4D696E676875615175A21B43A97E3");
            BigInteger a3 = SECNamedCurves.b("0400000000000000016954A233049BA98F");
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m(131, 2, 3, 8, a, a2, a3, valueOf);
            ECCurve a4 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a4, a4.decodePoint(Hex.decode("040356DCD8F2F95031AD652D23951BB366A80648F06D867940A5366D9E265DE9EB240F")), a3, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder t = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger valueOf2 = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("04000000000000000000020108A2E0CC0D99F8A5EF");
            BigInteger valueOf3 = BigInteger.valueOf(2);
            F2m f2m = new F2m((int) CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 3, 6, 7, valueOf, valueOf2, a, valueOf3);
            ECCurve a2 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a2, a2.decodePoint(Hex.decode("0402FE13C0537BBC11ACAA07D793DE4E6D5E5C94EEE80289070FB05D38FF58321F2E800536D538CCDAA3D9")), a, valueOf3, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder u = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("07B6882CAAEFA84F9554FF8428BD88E246D2782AE2");
            BigInteger a2 = SECNamedCurves.b("0713612DCDDCB40AAB946BDA29CA91F73AF958AFD9");
            byte[] decode = Hex.decode("24B7B137C8A14D696E6768756151756FD0DA2E5C");
            BigInteger a3 = SECNamedCurves.b("03FFFFFFFFFFFFFFFFFFFF48AAB689C29CA710279B");
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m((int) CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 3, 6, 7, a, a2, a3, valueOf);
            ECCurve a4 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a4, a4.decodePoint(Hex.decode("040369979697AB43897789566789567F787A7876A65400435EDB42EFAFB2989D51FEFCE3C80988F41FF883")), a3, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder v = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("020A601907B8C953CA1481EB10512F78744A3205FD");
            byte[] decode = Hex.decode("85E25BFE5C86226CDB12016F7553F9D0E693A268");
            BigInteger a2 = SECNamedCurves.b("040000000000000000000292FE77E70C12A4234C33");
            BigInteger valueOf2 = BigInteger.valueOf(2);
            F2m f2m = new F2m((int) CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, 3, 6, 7, valueOf, a, a2, valueOf2);
            ECCurve a3 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a3, a3.decodePoint(Hex.decode("0403F0EBA16286A2D57EA0991168D4994637E8343E3600D51FBC6C71A0094FA2CDD545B11C5C0C797324F1")), a2, valueOf2, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder w = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("0017858FEB7A98975169E171F77B4087DE098AC8A911DF7B01");
            BigInteger a2 = SECNamedCurves.b("00FDFB49BFE6C3A89FACADAA7A1E5BBC7CC1C2E5D831478814");
            byte[] decode = Hex.decode("103FAEC74D696E676875615175777FC5B191EF30");
            BigInteger a3 = SECNamedCurves.b("01000000000000000000000000C7F34A778F443ACC920EBA49");
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m((int) CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, 15, a, a2, a3, valueOf);
            ECCurve a4 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a4, a4.decodePoint(Hex.decode("0401F481BC5F0FF84A74AD6CDF6FDEF4BF6179625372D8C0C5E10025E399F2903712CCF3EA9E3A1AD17FB0B3201B6AF7CE1B05")), a3, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder x = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger a = SECNamedCurves.b("0163F35A5137C2CE3EA6ED8667190B0BC43ECD69977702709B");
            BigInteger a2 = SECNamedCurves.b("00C9BB9E8927D4D64C377E2AB2856A5B16E3EFB7F61D4316AE");
            byte[] decode = Hex.decode("10B7B4D696E676875615175137C8A16FD0DA2211");
            BigInteger a3 = SECNamedCurves.b("010000000000000000000000015AAB561B005413CCD4EE99D5");
            BigInteger valueOf = BigInteger.valueOf(2);
            F2m f2m = new F2m((int) CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, 15, a, a2, a3, valueOf);
            ECCurve a4 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a4, a4.decodePoint(Hex.decode("0400D9B67D192E0367C803F39E1A7E82CA14A651350AAE617E8F01CE94335607C304AC29E7DEFBD9CA01F596F927224CDECF6C")), a3, valueOf, decode);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder y = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger bigInteger = ECConstants.ZERO;
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("8000000000000000000000000000069D5BB915BCD46EFB1AD5F173ABDF");
            BigInteger valueOf2 = BigInteger.valueOf(4);
            F2m f2m = new F2m(233, 74, bigInteger, valueOf, a, valueOf2);
            ECCurve a2 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a2, a2.decodePoint(Hex.decode("04017232BA853A7E731AF129F22FF4149563A419C26BF50A4C9D6EEFAD612601DB537DECE819B7F70F555A67C427A8CD9BF18AEB9B56E0C11056FAE6A3")), a, valueOf2, null);
            return x9ECParameters;
        }
    };
    static X9ECParametersHolder z = new X9ECParametersHolder() {
        /* access modifiers changed from: protected */
        public X9ECParameters createParameters() {
            BigInteger valueOf = BigInteger.valueOf(1);
            BigInteger a = SECNamedCurves.b("0066647EDE6C332C7F8C0923BB58213B333B20E9CE4281FE115F7D8F90AD");
            byte[] decode = Hex.decode("74D59FF07F6B413D0EA14B344B20A2DB049B50C3");
            BigInteger a2 = SECNamedCurves.b("01000000000000000000000000000013E974E72F8A6922031D2603CFE0D7");
            BigInteger valueOf2 = BigInteger.valueOf(2);
            F2m f2m = new F2m(233, 74, valueOf, a, a2, valueOf2);
            ECCurve a3 = SECNamedCurves.b((ECCurve) f2m);
            X9ECParameters x9ECParameters = new X9ECParameters(a3, a3.decodePoint(Hex.decode("0400FAC9DFCBAC8313BB2139F1BB755FEF65BC391F8B36F8F8EB7371FD558B01006A08A41903350678E58528BEBF8A0BEFF867A7CA36716F7E01F81052")), a2, valueOf2, decode);
            return x9ECParameters;
        }
    };

    static {
        a("secp112r1", SECObjectIdentifiers.secp112r1, a);
        a("secp112r2", SECObjectIdentifiers.secp112r2, b);
        a("secp128r1", SECObjectIdentifiers.secp128r1, c);
        a("secp128r2", SECObjectIdentifiers.secp128r2, d);
        a("secp160k1", SECObjectIdentifiers.secp160k1, e);
        a("secp160r1", SECObjectIdentifiers.secp160r1, f);
        a("secp160r2", SECObjectIdentifiers.secp160r2, g);
        a("secp192k1", SECObjectIdentifiers.secp192k1, h);
        a("secp192r1", SECObjectIdentifiers.secp192r1, i);
        a("secp224k1", SECObjectIdentifiers.secp224k1, j);
        a("secp224r1", SECObjectIdentifiers.secp224r1, k);
        a("secp256k1", SECObjectIdentifiers.secp256k1, l);
        a("secp256r1", SECObjectIdentifiers.secp256r1, m);
        a("secp384r1", SECObjectIdentifiers.secp384r1, n);
        a("secp521r1", SECObjectIdentifiers.secp521r1, o);
        a("sect113r1", SECObjectIdentifiers.sect113r1, p);
        a("sect113r2", SECObjectIdentifiers.sect113r2, q);
        a("sect131r1", SECObjectIdentifiers.sect131r1, r);
        a("sect131r2", SECObjectIdentifiers.sect131r2, s);
        a("sect163k1", SECObjectIdentifiers.sect163k1, t);
        a("sect163r1", SECObjectIdentifiers.sect163r1, u);
        a("sect163r2", SECObjectIdentifiers.sect163r2, v);
        a("sect193r1", SECObjectIdentifiers.sect193r1, w);
        a("sect193r2", SECObjectIdentifiers.sect193r2, x);
        a("sect233k1", SECObjectIdentifiers.sect233k1, y);
        a("sect233r1", SECObjectIdentifiers.sect233r1, z);
        a("sect239k1", SECObjectIdentifiers.sect239k1, A);
        a("sect283k1", SECObjectIdentifiers.sect283k1, B);
        a("sect283r1", SECObjectIdentifiers.sect283r1, C);
        a("sect409k1", SECObjectIdentifiers.sect409k1, D);
        a("sect409r1", SECObjectIdentifiers.sect409r1, E);
        a("sect571k1", SECObjectIdentifiers.sect571k1, F);
        a("sect571r1", SECObjectIdentifiers.sect571r1, G);
    }

    static void a(String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, X9ECParametersHolder x9ECParametersHolder) {
        H.put(str, aSN1ObjectIdentifier);
        J.put(aSN1ObjectIdentifier, str);
        I.put(aSN1ObjectIdentifier, x9ECParametersHolder);
    }

    /* access modifiers changed from: private */
    public static BigInteger b(String str) {
        return new BigInteger(1, Hex.decode(str));
    }

    /* access modifiers changed from: private */
    public static ECCurve b(ECCurve eCCurve) {
        return eCCurve;
    }

    /* access modifiers changed from: private */
    public static ECCurve b(ECCurve eCCurve, GLVTypeBParameters gLVTypeBParameters) {
        return eCCurve.configure().setEndomorphism(new GLVTypeBEndomorphism(eCCurve, gLVTypeBParameters)).create();
    }

    public static X9ECParameters getByName(String str) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) H.get(Strings.toLowerCase(str));
        if (aSN1ObjectIdentifier != null) {
            return getByOID(aSN1ObjectIdentifier);
        }
        return null;
    }

    public static X9ECParameters getByOID(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        X9ECParametersHolder x9ECParametersHolder = (X9ECParametersHolder) I.get(aSN1ObjectIdentifier);
        if (x9ECParametersHolder != null) {
            return x9ECParametersHolder.getParameters();
        }
        return null;
    }

    public static String getName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (String) J.get(aSN1ObjectIdentifier);
    }

    public static Enumeration getNames() {
        return H.keys();
    }

    public static ASN1ObjectIdentifier getOID(String str) {
        return (ASN1ObjectIdentifier) H.get(Strings.toLowerCase(str));
    }
}
