package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Collection;
import java.util.SortedMap;
import java.util.SortedSet;

@GwtCompatible
abstract class AbstractSortedKeySortedSetMultimap<K, V> extends AbstractSortedSetMultimap<K, V> {
    AbstractSortedKeySortedSetMultimap(SortedMap<K, Collection<V>> sortedMap) {
        super(sortedMap);
    }

    public SortedMap<K, Collection<V>> asMap() {
        return (SortedMap) super.asMap();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: m */
    public SortedMap<K, Collection<V>> e() {
        return (SortedMap) super.e();
    }

    public SortedSet<K> keySet() {
        return (SortedSet) super.keySet();
    }
}
