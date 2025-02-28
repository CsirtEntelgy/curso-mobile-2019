package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Comparator;

@GwtCompatible(serializable = true)
final class CompoundOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    final ImmutableList<Comparator<? super T>> a;

    CompoundOrdering(Comparator<? super T> comparator, Comparator<? super T> comparator2) {
        this.a = ImmutableList.of(comparator, comparator2);
    }

    CompoundOrdering(Iterable<? extends Comparator<? super T>> iterable) {
        this.a = ImmutableList.copyOf(iterable);
    }

    public int compare(T t, T t2) {
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            int compare = ((Comparator) this.a.get(i)).compare(t, t2);
            if (compare != 0) {
                return compare;
            }
        }
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CompoundOrdering)) {
            return false;
        }
        return this.a.equals(((CompoundOrdering) obj).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ordering.compound(");
        sb.append(this.a);
        sb.append(")");
        return sb.toString();
    }
}
