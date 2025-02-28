package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedMap;

@GwtIncompatible
public abstract class ForwardingNavigableMap<K, V> extends ForwardingSortedMap<K, V> implements NavigableMap<K, V> {

    @Beta
    public class StandardDescendingMap extends DescendingMap<K, V> {
        public StandardDescendingMap() {
        }

        /* access modifiers changed from: 0000 */
        public NavigableMap<K, V> a() {
            return ForwardingNavigableMap.this;
        }

        /* access modifiers changed from: protected */
        public Iterator<Entry<K, V>> entryIterator() {
            return new Iterator<Entry<K, V>>() {
                private Entry<K, V> b = null;
                private Entry<K, V> c = StandardDescendingMap.this.a().lastEntry();

                public boolean hasNext() {
                    return this.c != null;
                }

                /* renamed from: a */
                public Entry<K, V> next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    try {
                        return this.c;
                    } finally {
                        this.b = this.c;
                        this.c = StandardDescendingMap.this.a().lowerEntry(this.c.getKey());
                    }
                }

                public void remove() {
                    CollectPreconditions.a(this.b != null);
                    StandardDescendingMap.this.a().remove(this.b.getKey());
                    this.b = null;
                }
            };
        }
    }

    @Beta
    public class StandardNavigableKeySet extends NavigableKeySet<K, V> {
        public StandardNavigableKeySet() {
            super(ForwardingNavigableMap.this);
        }
    }

    /* access modifiers changed from: protected */
    public abstract NavigableMap<K, V> delegate();

    protected ForwardingNavigableMap() {
    }

    public Entry<K, V> lowerEntry(K k) {
        return delegate().lowerEntry(k);
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> standardLowerEntry(K k) {
        return headMap(k, false).lastEntry();
    }

    public K lowerKey(K k) {
        return delegate().lowerKey(k);
    }

    /* access modifiers changed from: protected */
    public K standardLowerKey(K k) {
        return Maps.b(lowerEntry(k));
    }

    public Entry<K, V> floorEntry(K k) {
        return delegate().floorEntry(k);
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> standardFloorEntry(K k) {
        return headMap(k, true).lastEntry();
    }

    public K floorKey(K k) {
        return delegate().floorKey(k);
    }

    /* access modifiers changed from: protected */
    public K standardFloorKey(K k) {
        return Maps.b(floorEntry(k));
    }

    public Entry<K, V> ceilingEntry(K k) {
        return delegate().ceilingEntry(k);
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> standardCeilingEntry(K k) {
        return tailMap(k, true).firstEntry();
    }

    public K ceilingKey(K k) {
        return delegate().ceilingKey(k);
    }

    /* access modifiers changed from: protected */
    public K standardCeilingKey(K k) {
        return Maps.b(ceilingEntry(k));
    }

    public Entry<K, V> higherEntry(K k) {
        return delegate().higherEntry(k);
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> standardHigherEntry(K k) {
        return tailMap(k, false).firstEntry();
    }

    public K higherKey(K k) {
        return delegate().higherKey(k);
    }

    /* access modifiers changed from: protected */
    public K standardHigherKey(K k) {
        return Maps.b(higherEntry(k));
    }

    public Entry<K, V> firstEntry() {
        return delegate().firstEntry();
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> standardFirstEntry() {
        return (Entry) Iterables.getFirst(entrySet(), null);
    }

    /* access modifiers changed from: protected */
    public K standardFirstKey() {
        Entry firstEntry = firstEntry();
        if (firstEntry != null) {
            return firstEntry.getKey();
        }
        throw new NoSuchElementException();
    }

    public Entry<K, V> lastEntry() {
        return delegate().lastEntry();
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> standardLastEntry() {
        return (Entry) Iterables.getFirst(descendingMap().entrySet(), null);
    }

    /* access modifiers changed from: protected */
    public K standardLastKey() {
        Entry lastEntry = lastEntry();
        if (lastEntry != null) {
            return lastEntry.getKey();
        }
        throw new NoSuchElementException();
    }

    public Entry<K, V> pollFirstEntry() {
        return delegate().pollFirstEntry();
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> standardPollFirstEntry() {
        return (Entry) Iterators.a(entrySet().iterator());
    }

    public Entry<K, V> pollLastEntry() {
        return delegate().pollLastEntry();
    }

    /* access modifiers changed from: protected */
    public Entry<K, V> standardPollLastEntry() {
        return (Entry) Iterators.a(descendingMap().entrySet().iterator());
    }

    public NavigableMap<K, V> descendingMap() {
        return delegate().descendingMap();
    }

    public NavigableSet<K> navigableKeySet() {
        return delegate().navigableKeySet();
    }

    public NavigableSet<K> descendingKeySet() {
        return delegate().descendingKeySet();
    }

    /* access modifiers changed from: protected */
    @Beta
    public NavigableSet<K> standardDescendingKeySet() {
        return descendingMap().navigableKeySet();
    }

    /* access modifiers changed from: protected */
    public SortedMap<K, V> standardSubMap(K k, K k2) {
        return subMap(k, true, k2, false);
    }

    public NavigableMap<K, V> subMap(K k, boolean z, K k2, boolean z2) {
        return delegate().subMap(k, z, k2, z2);
    }

    public NavigableMap<K, V> headMap(K k, boolean z) {
        return delegate().headMap(k, z);
    }

    public NavigableMap<K, V> tailMap(K k, boolean z) {
        return delegate().tailMap(k, z);
    }

    /* access modifiers changed from: protected */
    public SortedMap<K, V> standardHeadMap(K k) {
        return headMap(k, false);
    }

    /* access modifiers changed from: protected */
    public SortedMap<K, V> standardTailMap(K k) {
        return tailMap(k, true);
    }
}
