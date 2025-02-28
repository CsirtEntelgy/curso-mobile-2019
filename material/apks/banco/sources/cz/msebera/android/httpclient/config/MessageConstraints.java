package cz.msebera.android.httpclient.config;

import cz.msebera.android.httpclient.util.Args;

public class MessageConstraints implements Cloneable {
    public static final MessageConstraints DEFAULT = new Builder().build();
    private final int a;
    private final int b;

    public static class Builder {
        private int a = -1;
        private int b = -1;

        Builder() {
        }

        public Builder setMaxLineLength(int i) {
            this.a = i;
            return this;
        }

        public Builder setMaxHeaderCount(int i) {
            this.b = i;
            return this;
        }

        public MessageConstraints build() {
            return new MessageConstraints(this.a, this.b);
        }
    }

    MessageConstraints(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public int getMaxLineLength() {
        return this.a;
    }

    public int getMaxHeaderCount() {
        return this.b;
    }

    /* access modifiers changed from: protected */
    public MessageConstraints clone() {
        return (MessageConstraints) super.clone();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[maxLineLength=");
        sb.append(this.a);
        sb.append(", maxHeaderCount=");
        sb.append(this.b);
        sb.append("]");
        return sb.toString();
    }

    public static MessageConstraints lineLen(int i) {
        return new MessageConstraints(Args.notNegative(i, "Max line length"), -1);
    }

    public static Builder custom() {
        return new Builder();
    }

    public static Builder copy(MessageConstraints messageConstraints) {
        Args.notNull(messageConstraints, "Message constraints");
        return new Builder().setMaxHeaderCount(messageConstraints.getMaxHeaderCount()).setMaxLineLength(messageConstraints.getMaxLineLength());
    }
}
