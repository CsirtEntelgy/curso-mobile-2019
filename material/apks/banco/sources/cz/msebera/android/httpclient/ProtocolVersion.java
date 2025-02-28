package cz.msebera.android.httpclient;

import cz.msebera.android.httpclient.annotation.Immutable;
import cz.msebera.android.httpclient.util.Args;
import java.io.Serializable;
import org.bouncycastle.apache.bzip2.BZip2Constants;

@Immutable
public class ProtocolVersion implements Serializable, Cloneable {
    private static final long serialVersionUID = 8950662842175091068L;
    protected final int major;
    protected final int minor;
    protected final String protocol;

    public ProtocolVersion(String str, int i, int i2) {
        this.protocol = (String) Args.notNull(str, "Protocol name");
        this.major = Args.notNegative(i, "Protocol minor version");
        this.minor = Args.notNegative(i2, "Protocol minor version");
    }

    public final String getProtocol() {
        return this.protocol;
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    public ProtocolVersion forVersion(int i, int i2) {
        if (i == this.major && i2 == this.minor) {
            return this;
        }
        return new ProtocolVersion(this.protocol, i, i2);
    }

    public final int hashCode() {
        return (this.protocol.hashCode() ^ (this.major * BZip2Constants.baseBlockSize)) ^ this.minor;
    }

    public final boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProtocolVersion)) {
            return false;
        }
        ProtocolVersion protocolVersion = (ProtocolVersion) obj;
        if (!(this.protocol.equals(protocolVersion.protocol) && this.major == protocolVersion.major && this.minor == protocolVersion.minor)) {
            z = false;
        }
        return z;
    }

    public boolean isComparable(ProtocolVersion protocolVersion) {
        return protocolVersion != null && this.protocol.equals(protocolVersion.protocol);
    }

    public int compareToVersion(ProtocolVersion protocolVersion) {
        Args.notNull(protocolVersion, "Protocol version");
        Args.check(this.protocol.equals(protocolVersion.protocol), "Versions for different protocols cannot be compared: %s %s", this, protocolVersion);
        int major2 = getMajor() - protocolVersion.getMajor();
        return major2 == 0 ? getMinor() - protocolVersion.getMinor() : major2;
    }

    public final boolean greaterEquals(ProtocolVersion protocolVersion) {
        return isComparable(protocolVersion) && compareToVersion(protocolVersion) >= 0;
    }

    public final boolean lessEquals(ProtocolVersion protocolVersion) {
        return isComparable(protocolVersion) && compareToVersion(protocolVersion) <= 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.protocol);
        sb.append('/');
        sb.append(Integer.toString(this.major));
        sb.append('.');
        sb.append(Integer.toString(this.minor));
        return sb.toString();
    }

    public Object clone() {
        return super.clone();
    }
}
