package org.bouncycastle.jcajce.provider.symmetric;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.RC532Engine;
import org.bouncycastle.crypto.engines.RC564Engine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.macs.CFBBlockCipherMac;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameterGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseMac;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public final class RC5 {

    public static class AlgParamGen extends BaseAlgorithmParameterGenerator {
        /* access modifiers changed from: protected */
        public AlgorithmParameters engineGenerateParameters() {
            byte[] bArr = new byte[8];
            if (this.random == null) {
                this.random = new SecureRandom();
            }
            this.random.nextBytes(bArr);
            try {
                AlgorithmParameters instance = AlgorithmParameters.getInstance("RC5", BouncyCastleProvider.PROVIDER_NAME);
                instance.init(new IvParameterSpec(bArr));
                return instance;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        /* access modifiers changed from: protected */
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
            throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for RC5 parameter generation.");
        }
    }

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "RC5 IV";
        }
    }

    public static class CBC32 extends BaseBlockCipher {
        public CBC32() {
            super((BlockCipher) new CBCBlockCipher(new RC532Engine()), 64);
        }
    }

    public static class CFB8Mac32 extends BaseMac {
        public CFB8Mac32() {
            super(new CFBBlockCipherMac(new RC532Engine()));
        }
    }

    public static class ECB32 extends BaseBlockCipher {
        public ECB32() {
            super((BlockCipher) new RC532Engine());
        }
    }

    public static class ECB64 extends BaseBlockCipher {
        public ECB64() {
            super((BlockCipher) new RC564Engine());
        }
    }

    public static class KeyGen32 extends BaseKeyGenerator {
        public KeyGen32() {
            super("RC5", 128, new CipherKeyGenerator());
        }
    }

    public static class KeyGen64 extends BaseKeyGenerator {
        public KeyGen64() {
            super("RC5-64", 256, new CipherKeyGenerator());
        }
    }

    public static class Mac32 extends BaseMac {
        public Mac32() {
            super(new CBCBlockCipherMac(new RC532Engine()));
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = RC5.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$ECB32");
            configurableProvider.addAlgorithm("Cipher.RC5", sb.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Cipher.RC5-32", "RC5");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append("$ECB64");
            configurableProvider.addAlgorithm("Cipher.RC5-64", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$KeyGen32");
            configurableProvider.addAlgorithm("KeyGenerator.RC5", sb3.toString());
            configurableProvider.addAlgorithm("Alg.Alias.KeyGenerator.RC5-32", "RC5");
            StringBuilder sb4 = new StringBuilder();
            sb4.append(a);
            sb4.append("$KeyGen64");
            configurableProvider.addAlgorithm("KeyGenerator.RC5-64", sb4.toString());
            StringBuilder sb5 = new StringBuilder();
            sb5.append(a);
            sb5.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.RC5", sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append(a);
            sb6.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.RC5-64", sb6.toString());
            StringBuilder sb7 = new StringBuilder();
            sb7.append(a);
            sb7.append("$Mac32");
            configurableProvider.addAlgorithm("Mac.RC5MAC", sb7.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Mac.RC5", "RC5MAC");
            StringBuilder sb8 = new StringBuilder();
            sb8.append(a);
            sb8.append("$CFB8Mac32");
            configurableProvider.addAlgorithm("Mac.RC5MAC/CFB8", sb8.toString());
            configurableProvider.addAlgorithm("Alg.Alias.Mac.RC5/CFB8", "RC5MAC/CFB8");
        }
    }

    private RC5() {
    }
}
