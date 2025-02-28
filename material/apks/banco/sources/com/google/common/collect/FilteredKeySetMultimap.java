package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Predicate;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
final class FilteredKeySetMultimap<K, V> extends FilteredKeyMultimap<K, V> implements FilteredSetMultimap<K, V> {

    class EntrySet extends Entries implements Set<Entry<K, V>> {
        EntrySet() {
            super();
        }

        public int hashCode() {
            return Sets.a(this);
        }

        public boolean equals(@Nullable Object obj) {
            return Sets.a((Set<?>) this, obj);
        }
    }

    FilteredKeySetMultimap(SetMultimap<K, V> setMultimap, Predicate<? super K> predicate) {
        super(setMultimap, predicate);
    }

    /* renamed from: d */
    public SetMultimap<K, V> a() {
        return (SetMultimap) this.a;
    }

    public Set<V> get(K k) {
        return (Set) super.get(k);
    }

    public Set<V> removeAll(Object obj) {
        return (Set) super.removeAll(obj);
    }

    public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
        return (Set) super.replaceValues(k, iterable);
    }

    public Set<Entry<K, V>> entries() {
        return (Set) super.entries();
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public Set<Entry<K, V>> j() {
        return new EntrySet();
    }
}
