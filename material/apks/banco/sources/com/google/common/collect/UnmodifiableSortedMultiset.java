package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset.Entry;
import java.util.Comparator;
import java.util.NavigableSet;

@GwtCompatible(emulated = true)
final class UnmodifiableSortedMultiset<E> extends UnmodifiableMultiset<E> implements SortedMultiset<E> {
    private static final long serialVersionUID = 0;
    private transient UnmodifiableSortedMultiset<E> d;

    UnmodifiableSortedMultiset(SortedMultiset<E> sortedMultiset) {
        super(sortedMultiset);
    }

    /* access modifiers changed from: protected */
    /* renamed from: b */
    public SortedMultiset<E> delegate() {
        return (SortedMultiset) super.delegate();
    }

    public Comparator<? super E> comparator() {
        return delegate().comparator();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public NavigableSet<E> a() {
        return Sets.unmodifiableNavigableSet(delegate().elementSet());
    }

    public NavigableSet<E> elementSet() {
        return (NavigableSet) super.elementSet();
    }

    public SortedMultiset<E> descendingMultiset() {
        UnmodifiableSortedMultiset<E> unmodifiableSortedMultiset = this.d;
        if (unmodifiableSortedMultiset != null) {
            return unmodifiableSortedMultiset;
        }
        UnmodifiableSortedMultiset<E> unmodifiableSortedMultiset2 = new UnmodifiableSortedMultiset<>(delegate().descendingMultiset());
        unmodifiableSortedMultiset2.d = this;
        this.d = unmodifiableSortedMultiset2;
        return unmodifiableSortedMultiset2;
    }

    public Entry<E> firstEntry() {
        return delegate().firstEntry();
    }

    public Entry<E> lastEntry() {
        return delegate().lastEntry();
    }

    public Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    public Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    public SortedMultiset<E> headMultiset(E e, BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(delegate().headMultiset(e, boundType));
    }

    public SortedMultiset<E> subMultiset(E e, BoundType boundType, E e2, BoundType boundType2) {
        return Multisets.unmodifiableSortedMultiset(delegate().subMultiset(e, boundType, e2, boundType2));
    }

    public SortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(delegate().tailMultiset(e, boundType));
    }
}
