package org.bouncycastle.jcajce.provider.symmetric;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherKeyGenerator;
import org.bouncycastle.crypto.engines.TEAEngine;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseBlockCipher;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseKeyGenerator;
import org.bouncycastle.jcajce.provider.symmetric.util.IvAlgorithmParameters;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;

public final class TEA {

    public static class AlgParams extends IvAlgorithmParameters {
        /* access modifiers changed from: protected */
        public String engineToString() {
            return "TEA IV";
        }
    }

    public static class ECB extends BaseBlockCipher {
        public ECB() {
            super((BlockCipher) new TEAEngine());
        }
    }

    public static class KeyGen extends BaseKeyGenerator {
        public KeyGen() {
            super("TEA", 128, new CipherKeyGenerator());
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String a = TEA.class.getName();

        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            sb.append(a);
            sb.append("$ECB");
            configurableProvider.addAlgorithm("Cipher.TEA", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(a);
            sb2.append("$KeyGen");
            configurableProvider.addAlgorithm("KeyGenerator.TEA", sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(a);
            sb3.append("$AlgParams");
            configurableProvider.addAlgorithm("AlgorithmParameters.TEA", sb3.toString());
        }
    }

    private TEA() {
    }
}
