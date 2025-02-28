package com.google.common.graph;

import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Iterator;
import javax.annotation.Nullable;

@Beta
public abstract class EndpointPair<N> implements Iterable<N> {
    private final N a;
    private final N b;

    static final class Ordered<N> extends EndpointPair<N> {
        public boolean isOrdered() {
            return true;
        }

        public /* bridge */ /* synthetic */ Iterator iterator() {
            return EndpointPair.super.iterator();
        }

        private Ordered(N n, N n2) {
            super(n, n2);
        }

        public N source() {
            return nodeU();
        }

        public N target() {
            return nodeV();
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof EndpointPair)) {
                return false;
            }
            EndpointPair endpointPair = (EndpointPair) obj;
            if (isOrdered() != endpointPair.isOrdered()) {
                return false;
            }
            if (!source().equals(endpointPair.source()) || !target().equals(endpointPair.target())) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return Objects.hashCode(source(), target());
        }

        public String toString() {
            return String.format("<%s -> %s>", new Object[]{source(), target()});
        }
    }

    static final class Unordered<N> extends EndpointPair<N> {
        public boolean isOrdered() {
            return false;
        }

        public /* bridge */ /* synthetic */ Iterator iterator() {
            return EndpointPair.super.iterator();
        }

        private Unordered(N n, N n2) {
            super(n, n2);
        }

        public N source() {
            throw new UnsupportedOperationException("Cannot call source()/target() on a EndpointPair from an undirected graph. Consider calling adjacentNode(node) if you already have a node, or nodeU()/nodeV() if you don't.");
        }

        public N target() {
            throw new UnsupportedOperationException("Cannot call source()/target() on a EndpointPair from an undirected graph. Consider calling adjacentNode(node) if you already have a node, or nodeU()/nodeV() if you don't.");
        }

        public boolean equals(@Nullable Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof EndpointPair)) {
                return false;
            }
            EndpointPair endpointPair = (EndpointPair) obj;
            if (isOrdered() != endpointPair.isOrdered()) {
                return false;
            }
            if (nodeU().equals(endpointPair.nodeU())) {
                return nodeV().equals(endpointPair.nodeV());
            }
            if (!nodeU().equals(endpointPair.nodeV()) || !nodeV().equals(endpointPair.nodeU())) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return nodeU().hashCode() + nodeV().hashCode();
        }

        public String toString() {
            return String.format("[%s, %s]", new Object[]{nodeU(), nodeV()});
        }
    }

    public abstract boolean equals(@Nullable Object obj);

    public abstract int hashCode();

    public abstract boolean isOrdered();

    public abstract N source();

    public abstract N target();

    private EndpointPair(N n, N n2) {
        this.a = Preconditions.checkNotNull(n);
        this.b = Preconditions.checkNotNull(n2);
    }

    public static <N> EndpointPair<N> ordered(N n, N n2) {
        return new Ordered(n, n2);
    }

    public static <N> EndpointPair<N> unordered(N n, N n2) {
        return new Unordered(n2, n);
    }

    static <N> EndpointPair<N> a(Network<?, ?> network, N n, N n2) {
        return network.isDirected() ? ordered(n, n2) : unordered(n, n2);
    }

    public final N nodeU() {
        return this.a;
    }

    public final N nodeV() {
        return this.b;
    }

    public final N adjacentNode(Object obj) {
        if (obj.equals(this.a)) {
            return this.b;
        }
        if (obj.equals(this.b)) {
            return this.a;
        }
        throw new IllegalArgumentException(String.format("EndpointPair %s does not contain node %s", new Object[]{this, obj}));
    }

    public final UnmodifiableIterator<N> iterator() {
        return Iterators.forArray(this.a, this.b);
    }
}
