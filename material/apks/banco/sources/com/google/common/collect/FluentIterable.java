package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
public abstract class FluentIterable<E> implements Iterable<E> {
    private final Optional<Iterable<E>> a;

    protected FluentIterable() {
        this.a = Optional.absent();
    }

    FluentIterable(Iterable<E> iterable) {
        Preconditions.checkNotNull(iterable);
        if (this == iterable) {
            iterable = null;
        }
        this.a = Optional.fromNullable(iterable);
    }

    private Iterable<E> a() {
        return (Iterable) this.a.or(this);
    }

    public static <E> FluentIterable<E> from(final Iterable<E> iterable) {
        return iterable instanceof FluentIterable ? (FluentIterable) iterable : new FluentIterable<E>(iterable) {
            public Iterator<E> iterator() {
                return iterable.iterator();
            }
        };
    }

    @Beta
    public static <E> FluentIterable<E> from(E[] eArr) {
        return from((Iterable<E>) Arrays.asList(eArr));
    }

    @Deprecated
    public static <E> FluentIterable<E> from(FluentIterable<E> fluentIterable) {
        return (FluentIterable) Preconditions.checkNotNull(fluentIterable);
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        return concat((Iterable<? extends Iterable<? extends T>>) ImmutableList.of(iterable, iterable2));
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3) {
        return concat((Iterable<? extends Iterable<? extends T>>) ImmutableList.of(iterable, iterable2, iterable3));
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3, Iterable<? extends T> iterable4) {
        return concat((Iterable<? extends Iterable<? extends T>>) ImmutableList.of(iterable, iterable2, iterable3, iterable4));
    }

    @Beta
    public static <T> FluentIterable<T> concat(Iterable<? extends T>... iterableArr) {
        return concat((Iterable<? extends Iterable<? extends T>>) ImmutableList.copyOf((E[]) iterableArr));
    }

    @Beta
    public static <T> FluentIterable<T> concat(final Iterable<? extends Iterable<? extends T>> iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable<T>() {
            public Iterator<T> iterator() {
                return Iterators.concat(Iterables.transform(iterable, Iterables.a()).iterator());
            }
        };
    }

    @Beta
    public static <E> FluentIterable<E> of() {
        return from((Iterable<E>) ImmutableList.of());
    }

    @Deprecated
    @Beta
    public static <E> FluentIterable<E> of(E[] eArr) {
        return from((Iterable<E>) Lists.newArrayList(eArr));
    }

    @Beta
    public static <E> FluentIterable<E> of(@Nullable E e, E... eArr) {
        return from((Iterable<E>) Lists.asList(e, eArr));
    }

    public String toString() {
        return Iterables.toString(a());
    }

    public final int size() {
        return Iterables.size(a());
    }

    public final boolean contains(@Nullable Object obj) {
        return Iterables.contains(a(), obj);
    }

    public final FluentIterable<E> cycle() {
        return from(Iterables.cycle(a()));
    }

    @Beta
    public final FluentIterable<E> append(Iterable<? extends E> iterable) {
        return from(concat(a(), iterable));
    }

    @Beta
    public final FluentIterable<E> append(E... eArr) {
        return from(concat(a(), Arrays.asList(eArr)));
    }

    public final FluentIterable<E> filter(Predicate<? super E> predicate) {
        return from(Iterables.filter(a(), predicate));
    }

    @GwtIncompatible
    public final <T> FluentIterable<T> filter(Class<T> cls) {
        return from(Iterables.filter(a(), cls));
    }

    public final boolean anyMatch(Predicate<? super E> predicate) {
        return Iterables.any(a(), predicate);
    }

    public final boolean allMatch(Predicate<? super E> predicate) {
        return Iterables.all(a(), predicate);
    }

    public final Optional<E> firstMatch(Predicate<? super E> predicate) {
        return Iterables.tryFind(a(), predicate);
    }

    public final <T> FluentIterable<T> transform(Function<? super E, T> function) {
        return from(Iterables.transform(a(), function));
    }

    public <T> FluentIterable<T> transformAndConcat(Function<? super E, ? extends Iterable<? extends T>> function) {
        return from(concat((Iterable<? extends Iterable<? extends T>>) transform(function)));
    }

    public final Optional<E> first() {
        Iterator it = a().iterator();
        return it.hasNext() ? Optional.of(it.next()) : Optional.absent();
    }

    public final Optional<E> last() {
        Object next;
        Iterable a2 = a();
        if (a2 instanceof List) {
            List list = (List) a2;
            if (list.isEmpty()) {
                return Optional.absent();
            }
            return Optional.of(list.get(list.size() - 1));
        }
        Iterator it = a2.iterator();
        if (!it.hasNext()) {
            return Optional.absent();
        }
        if (a2 instanceof SortedSet) {
            return Optional.of(((SortedSet) a2).last());
        }
        do {
            next = it.next();
        } while (it.hasNext());
        return Optional.of(next);
    }

    public final FluentIterable<E> skip(int i) {
        return from(Iterables.skip(a(), i));
    }

    public final FluentIterable<E> limit(int i) {
        return from(Iterables.limit(a(), i));
    }

    public final boolean isEmpty() {
        return !a().iterator().hasNext();
    }

    public final ImmutableList<E> toList() {
        return ImmutableList.copyOf(a());
    }

    public final ImmutableList<E> toSortedList(Comparator<? super E> comparator) {
        return Ordering.from(comparator).immutableSortedCopy(a());
    }

    public final ImmutableSet<E> toSet() {
        return ImmutableSet.copyOf(a());
    }

    public final ImmutableSortedSet<E> toSortedSet(Comparator<? super E> comparator) {
        return ImmutableSortedSet.copyOf(comparator, a());
    }

    public final ImmutableMultiset<E> toMultiset() {
        return ImmutableMultiset.copyOf(a());
    }

    public final <V> ImmutableMap<E, V> toMap(Function<? super E, V> function) {
        return Maps.toMap(a(), function);
    }

    public final <K> ImmutableListMultimap<K, E> index(Function<? super E, K> function) {
        return Multimaps.index(a(), function);
    }

    public final <K> ImmutableMap<K, E> uniqueIndex(Function<? super E, K> function) {
        return Maps.uniqueIndex(a(), function);
    }

    @GwtIncompatible
    public final E[] toArray(Class<E> cls) {
        return Iterables.toArray(a(), cls);
    }

    @CanIgnoreReturnValue
    public final <C extends Collection<? super E>> C copyInto(C c) {
        Preconditions.checkNotNull(c);
        Iterable<Object> a2 = a();
        if (a2 instanceof Collection) {
            c.addAll(Collections2.a(a2));
        } else {
            for (Object add : a2) {
                c.add(add);
            }
        }
        return c;
    }

    @Beta
    public final String join(Joiner joiner) {
        return joiner.join((Iterable<?>) this);
    }

    public final E get(int i) {
        return Iterables.get(a(), i);
    }
}
