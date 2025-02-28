package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

@GwtCompatible
public abstract class ForwardingMap<K, V> extends ForwardingObject implements Map<K, V> {

    @Beta
    public abstract class StandardEntrySet extends EntrySet<K, V> {
        public StandardEntrySet() {
        }
    }

    @Beta
    public class StandardKeySet extends KeySet<K, V> {
        public StandardKeySet() {
            super(ForwardingMap.this);
        }
    }

    @Beta
    public class StandardValues extends Values<K, V> {
        public StandardValues() {
            super(ForwardingMap.this);
        }
    }

    /* access modifiers changed from: protected */
    public abstract Map<K, V> delegate();

    protected ForwardingMap() {
    }

    public int size() {
        return delegate().size();
    }

    public boolean isEmpty() {
        return delegate().isEmpty();
    }

    @CanIgnoreReturnValue
    public V remove(Object obj) {
        return delegate().remove(obj);
    }

    public void clear() {
        delegate().clear();
    }

    public boolean containsKey(@Nullable Object obj) {
        return delegate().containsKey(obj);
    }

    public boolean containsValue(@Nullable Object obj) {
        return delegate().containsValue(obj);
    }

    public V get(@Nullable Object obj) {
        return delegate().get(obj);
    }

    @CanIgnoreReturnValue
    public V put(K k, V v) {
        return delegate().put(k, v);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        delegate().putAll(map);
    }

    public Set<K> keySet() {
        return delegate().keySet();
    }

    public Collection<V> values() {
        return delegate().values();
    }

    public Set<Entry<K, V>> entrySet() {
        return delegate().entrySet();
    }

    public boolean equals(@Nullable Object obj) {
        return obj == this || delegate().equals(obj);
    }

    public int hashCode() {
        return delegate().hashCode();
    }

    /* access modifiers changed from: protected */
    public void standardPutAll(Map<? extends K, ? extends V> map) {
        Maps.a((Map<K, V>) this, map);
    }

    /* access modifiers changed from: protected */
    @Beta
    public V standardRemove(@Nullable Object obj) {
        Iterator it = entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (Objects.equal(entry.getKey(), obj)) {
                V value = entry.getValue();
                it.remove();
                return value;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public void standardClear() {
        Iterators.b(entrySet().iterator());
    }

    /* access modifiers changed from: protected */
    @Beta
    public boolean standardContainsKey(@Nullable Object obj) {
        return Maps.d(this, obj);
    }

    /* access modifiers changed from: protected */
    public boolean standardContainsValue(@Nullable Object obj) {
        return Maps.e(this, obj);
    }

    /* access modifiers changed from: protected */
    public boolean standardIsEmpty() {
        return !entrySet().iterator().hasNext();
    }

    /* access modifiers changed from: protected */
    public boolean standardEquals(@Nullable Object obj) {
        return Maps.f(this, obj);
    }

    /* access modifiers changed from: protected */
    public int standardHashCode() {
        return Sets.a(entrySet());
    }

    /* access modifiers changed from: protected */
    public String standardToString() {
        return Maps.a((Map<?, ?>) this);
    }
}
