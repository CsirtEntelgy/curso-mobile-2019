package io.reactivex.internal.functions;

import io.reactivex.Notification;
import io.reactivex.Scheduler;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.functions.Function5;
import io.reactivex.functions.Function6;
import io.reactivex.functions.Function7;
import io.reactivex.functions.Function8;
import io.reactivex.functions.Function9;
import io.reactivex.functions.LongConsumer;
import io.reactivex.functions.Predicate;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;

public final class Functions {
    public static final Action EMPTY_ACTION = new EmptyAction();
    public static final LongConsumer EMPTY_LONG_CONSUMER = new EmptyLongConsumer();
    public static final Runnable EMPTY_RUNNABLE = new EmptyRunnable();
    public static final Consumer<Throwable> ERROR_CONSUMER = new ErrorConsumer();
    public static final Consumer<Throwable> ON_ERROR_MISSING = new OnErrorMissingConsumer();
    public static final Consumer<Subscription> REQUEST_MAX = new MaxRequestSubscription();
    static final Function<Object, Object> a = new Identity();
    static final Consumer<Object> b = new EmptyConsumer();
    static final Predicate<Object> c = new TruePredicate();
    static final Predicate<Object> d = new FalsePredicate();
    static final Callable<Object> e = new NullCallable();
    static final Comparator<Object> f = new NaturalObjectComparator();

    static final class ActionConsumer<T> implements Consumer<T> {
        final Action a;

        ActionConsumer(Action action) {
            this.a = action;
        }

        public void accept(T t) {
            this.a.run();
        }
    }

    static final class Array2Func<T1, T2, R> implements Function<Object[], R> {
        final BiFunction<? super T1, ? super T2, ? extends R> a;

        Array2Func(BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
            this.a = biFunction;
        }

        /* renamed from: a */
        public R apply(Object[] objArr) {
            if (objArr.length == 2) {
                return this.a.apply(objArr[0], objArr[1]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Array of size 2 expected but got ");
            sb.append(objArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static final class Array3Func<T1, T2, T3, R> implements Function<Object[], R> {
        final Function3<T1, T2, T3, R> a;

        Array3Func(Function3<T1, T2, T3, R> function3) {
            this.a = function3;
        }

        /* renamed from: a */
        public R apply(Object[] objArr) {
            if (objArr.length == 3) {
                return this.a.apply(objArr[0], objArr[1], objArr[2]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Array of size 3 expected but got ");
            sb.append(objArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static final class Array4Func<T1, T2, T3, T4, R> implements Function<Object[], R> {
        final Function4<T1, T2, T3, T4, R> a;

        Array4Func(Function4<T1, T2, T3, T4, R> function4) {
            this.a = function4;
        }

        /* renamed from: a */
        public R apply(Object[] objArr) {
            if (objArr.length == 4) {
                return this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Array of size 4 expected but got ");
            sb.append(objArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static final class Array5Func<T1, T2, T3, T4, T5, R> implements Function<Object[], R> {
        private final Function5<T1, T2, T3, T4, T5, R> a;

        Array5Func(Function5<T1, T2, T3, T4, T5, R> function5) {
            this.a = function5;
        }

        /* renamed from: a */
        public R apply(Object[] objArr) {
            if (objArr.length == 5) {
                return this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Array of size 5 expected but got ");
            sb.append(objArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static final class Array6Func<T1, T2, T3, T4, T5, T6, R> implements Function<Object[], R> {
        final Function6<T1, T2, T3, T4, T5, T6, R> a;

        Array6Func(Function6<T1, T2, T3, T4, T5, T6, R> function6) {
            this.a = function6;
        }

        /* renamed from: a */
        public R apply(Object[] objArr) {
            if (objArr.length == 6) {
                return this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Array of size 6 expected but got ");
            sb.append(objArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static final class Array7Func<T1, T2, T3, T4, T5, T6, T7, R> implements Function<Object[], R> {
        final Function7<T1, T2, T3, T4, T5, T6, T7, R> a;

        Array7Func(Function7<T1, T2, T3, T4, T5, T6, T7, R> function7) {
            this.a = function7;
        }

        /* renamed from: a */
        public R apply(Object[] objArr) {
            if (objArr.length == 7) {
                return this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Array of size 7 expected but got ");
            sb.append(objArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static final class Array8Func<T1, T2, T3, T4, T5, T6, T7, T8, R> implements Function<Object[], R> {
        final Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> a;

        Array8Func(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function8) {
            this.a = function8;
        }

        /* renamed from: a */
        public R apply(Object[] objArr) {
            if (objArr.length == 8) {
                return this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Array of size 8 expected but got ");
            sb.append(objArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static final class Array9Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> implements Function<Object[], R> {
        final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> a;

        Array9Func(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function9) {
            this.a = function9;
        }

        /* renamed from: a */
        public R apply(Object[] objArr) {
            if (objArr.length == 9) {
                return this.a.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7], objArr[8]);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Array of size 9 expected but got ");
            sb.append(objArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static final class ArrayListCapacityCallable<T> implements Callable<List<T>> {
        final int a;

        ArrayListCapacityCallable(int i) {
            this.a = i;
        }

        /* renamed from: a */
        public List<T> call() {
            return new ArrayList(this.a);
        }
    }

    static final class BooleanSupplierPredicateReverse<T> implements Predicate<T> {
        final BooleanSupplier a;

        BooleanSupplierPredicateReverse(BooleanSupplier booleanSupplier) {
            this.a = booleanSupplier;
        }

        public boolean test(T t) {
            return !this.a.getAsBoolean();
        }
    }

    static final class CastToClass<T, U> implements Function<T, U> {
        final Class<U> a;

        CastToClass(Class<U> cls) {
            this.a = cls;
        }

        public U apply(T t) {
            return this.a.cast(t);
        }
    }

    static final class ClassFilter<T, U> implements Predicate<T> {
        final Class<U> a;

        ClassFilter(Class<U> cls) {
            this.a = cls;
        }

        public boolean test(T t) {
            return this.a.isInstance(t);
        }
    }

    static final class EmptyAction implements Action {
        public void run() {
        }

        public String toString() {
            return "EmptyAction";
        }

        EmptyAction() {
        }
    }

    static final class EmptyConsumer implements Consumer<Object> {
        public void accept(Object obj) {
        }

        public String toString() {
            return "EmptyConsumer";
        }

        EmptyConsumer() {
        }
    }

    static final class EmptyLongConsumer implements LongConsumer {
        public void accept(long j) {
        }

        EmptyLongConsumer() {
        }
    }

    static final class EmptyRunnable implements Runnable {
        public void run() {
        }

        public String toString() {
            return "EmptyRunnable";
        }

        EmptyRunnable() {
        }
    }

    static final class EqualsPredicate<T> implements Predicate<T> {
        final T a;

        EqualsPredicate(T t) {
            this.a = t;
        }

        public boolean test(T t) {
            return ObjectHelper.equals(t, this.a);
        }
    }

    static final class ErrorConsumer implements Consumer<Throwable> {
        ErrorConsumer() {
        }

        /* renamed from: a */
        public void accept(Throwable th) {
            RxJavaPlugins.onError(th);
        }
    }

    static final class FalsePredicate implements Predicate<Object> {
        public boolean test(Object obj) {
            return false;
        }

        FalsePredicate() {
        }
    }

    static final class FutureAction implements Action {
        final Future<?> a;

        FutureAction(Future<?> future) {
            this.a = future;
        }

        public void run() {
            this.a.get();
        }
    }

    enum HashSetCallable implements Callable<Set<Object>> {
        INSTANCE;

        /* renamed from: a */
        public Set<Object> call() {
            return new HashSet();
        }
    }

    static final class Identity implements Function<Object, Object> {
        public Object apply(Object obj) {
            return obj;
        }

        public String toString() {
            return "IdentityFunction";
        }

        Identity() {
        }
    }

    static final class JustValue<T, U> implements Function<T, U>, Callable<U> {
        final U a;

        JustValue(U u) {
            this.a = u;
        }

        public U call() {
            return this.a;
        }

        public U apply(T t) {
            return this.a;
        }
    }

    static final class ListSorter<T> implements Function<List<T>, List<T>> {
        final Comparator<? super T> a;

        ListSorter(Comparator<? super T> comparator) {
            this.a = comparator;
        }

        /* renamed from: a */
        public List<T> apply(List<T> list) {
            Collections.sort(list, this.a);
            return list;
        }
    }

    static final class MaxRequestSubscription implements Consumer<Subscription> {
        MaxRequestSubscription() {
        }

        /* renamed from: a */
        public void accept(Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }
    }

    enum NaturalComparator implements Comparator<Object> {
        INSTANCE;

        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    static final class NaturalObjectComparator implements Comparator<Object> {
        NaturalObjectComparator() {
        }

        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    static final class NotificationOnComplete<T> implements Action {
        final Consumer<? super Notification<T>> a;

        NotificationOnComplete(Consumer<? super Notification<T>> consumer) {
            this.a = consumer;
        }

        public void run() {
            this.a.accept(Notification.createOnComplete());
        }
    }

    static final class NotificationOnError<T> implements Consumer<Throwable> {
        final Consumer<? super Notification<T>> a;

        NotificationOnError(Consumer<? super Notification<T>> consumer) {
            this.a = consumer;
        }

        /* renamed from: a */
        public void accept(Throwable th) {
            this.a.accept(Notification.createOnError(th));
        }
    }

    static final class NotificationOnNext<T> implements Consumer<T> {
        final Consumer<? super Notification<T>> a;

        NotificationOnNext(Consumer<? super Notification<T>> consumer) {
            this.a = consumer;
        }

        public void accept(T t) {
            this.a.accept(Notification.createOnNext(t));
        }
    }

    static final class NullCallable implements Callable<Object> {
        public Object call() {
            return null;
        }

        NullCallable() {
        }
    }

    static final class OnErrorMissingConsumer implements Consumer<Throwable> {
        OnErrorMissingConsumer() {
        }

        /* renamed from: a */
        public void accept(Throwable th) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException(th));
        }
    }

    static final class TimestampFunction<T> implements Function<T, Timed<T>> {
        final TimeUnit a;
        final Scheduler b;

        TimestampFunction(TimeUnit timeUnit, Scheduler scheduler) {
            this.a = timeUnit;
            this.b = scheduler;
        }

        /* renamed from: a */
        public Timed<T> apply(T t) {
            return new Timed<>(t, this.b.now(this.a), this.a);
        }
    }

    static final class ToMapKeySelector<K, T> implements BiConsumer<Map<K, T>, T> {
        private final Function<? super T, ? extends K> a;

        ToMapKeySelector(Function<? super T, ? extends K> function) {
            this.a = function;
        }

        /* renamed from: a */
        public void accept(Map<K, T> map, T t) {
            map.put(this.a.apply(t), t);
        }
    }

    static final class ToMapKeyValueSelector<K, V, T> implements BiConsumer<Map<K, V>, T> {
        private final Function<? super T, ? extends V> a;
        private final Function<? super T, ? extends K> b;

        ToMapKeyValueSelector(Function<? super T, ? extends V> function, Function<? super T, ? extends K> function2) {
            this.a = function;
            this.b = function2;
        }

        /* renamed from: a */
        public void accept(Map<K, V> map, T t) {
            map.put(this.b.apply(t), this.a.apply(t));
        }
    }

    static final class ToMultimapKeyValueSelector<K, V, T> implements BiConsumer<Map<K, Collection<V>>, T> {
        private final Function<? super K, ? extends Collection<? super V>> a;
        private final Function<? super T, ? extends V> b;
        private final Function<? super T, ? extends K> c;

        ToMultimapKeyValueSelector(Function<? super K, ? extends Collection<? super V>> function, Function<? super T, ? extends V> function2, Function<? super T, ? extends K> function3) {
            this.a = function;
            this.b = function2;
            this.c = function3;
        }

        /* renamed from: a */
        public void accept(Map<K, Collection<V>> map, T t) {
            Object apply = this.c.apply(t);
            Collection collection = (Collection) map.get(apply);
            if (collection == null) {
                collection = (Collection) this.a.apply(apply);
                map.put(apply, collection);
            }
            collection.add(this.b.apply(t));
        }
    }

    static final class TruePredicate implements Predicate<Object> {
        public boolean test(Object obj) {
            return true;
        }

        TruePredicate() {
        }
    }

    private Functions() {
        throw new IllegalStateException("No instances!");
    }

    public static <T1, T2, R> Function<Object[], R> toFunction(BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        ObjectHelper.requireNonNull(biFunction, "f is null");
        return new Array2Func(biFunction);
    }

    public static <T1, T2, T3, R> Function<Object[], R> toFunction(Function3<T1, T2, T3, R> function3) {
        ObjectHelper.requireNonNull(function3, "f is null");
        return new Array3Func(function3);
    }

    public static <T1, T2, T3, T4, R> Function<Object[], R> toFunction(Function4<T1, T2, T3, T4, R> function4) {
        ObjectHelper.requireNonNull(function4, "f is null");
        return new Array4Func(function4);
    }

    public static <T1, T2, T3, T4, T5, R> Function<Object[], R> toFunction(Function5<T1, T2, T3, T4, T5, R> function5) {
        ObjectHelper.requireNonNull(function5, "f is null");
        return new Array5Func(function5);
    }

    public static <T1, T2, T3, T4, T5, T6, R> Function<Object[], R> toFunction(Function6<T1, T2, T3, T4, T5, T6, R> function6) {
        ObjectHelper.requireNonNull(function6, "f is null");
        return new Array6Func(function6);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Function<Object[], R> toFunction(Function7<T1, T2, T3, T4, T5, T6, T7, R> function7) {
        ObjectHelper.requireNonNull(function7, "f is null");
        return new Array7Func(function7);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Function<Object[], R> toFunction(Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> function8) {
        ObjectHelper.requireNonNull(function8, "f is null");
        return new Array8Func(function8);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Function<Object[], R> toFunction(Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> function9) {
        ObjectHelper.requireNonNull(function9, "f is null");
        return new Array9Func(function9);
    }

    public static <T> Function<T, T> identity() {
        return a;
    }

    public static <T> Consumer<T> emptyConsumer() {
        return b;
    }

    public static <T> Predicate<T> alwaysTrue() {
        return c;
    }

    public static <T> Predicate<T> alwaysFalse() {
        return d;
    }

    public static <T> Callable<T> nullSupplier() {
        return e;
    }

    public static <T> Comparator<T> naturalOrder() {
        return f;
    }

    public static Action futureAction(Future<?> future) {
        return new FutureAction(future);
    }

    public static <T> Callable<T> justCallable(T t) {
        return new JustValue(t);
    }

    public static <T, U> Function<T, U> justFunction(U u) {
        return new JustValue(u);
    }

    public static <T, U> Function<T, U> castFunction(Class<U> cls) {
        return new CastToClass(cls);
    }

    public static <T> Callable<List<T>> createArrayList(int i) {
        return new ArrayListCapacityCallable(i);
    }

    public static <T> Predicate<T> equalsWith(T t) {
        return new EqualsPredicate(t);
    }

    public static <T> Callable<Set<T>> createHashSet() {
        return HashSetCallable.INSTANCE;
    }

    public static <T> Consumer<T> notificationOnNext(Consumer<? super Notification<T>> consumer) {
        return new NotificationOnNext(consumer);
    }

    public static <T> Consumer<Throwable> notificationOnError(Consumer<? super Notification<T>> consumer) {
        return new NotificationOnError(consumer);
    }

    public static <T> Action notificationOnComplete(Consumer<? super Notification<T>> consumer) {
        return new NotificationOnComplete(consumer);
    }

    public static <T> Consumer<T> actionConsumer(Action action) {
        return new ActionConsumer(action);
    }

    public static <T, U> Predicate<T> isInstanceOf(Class<U> cls) {
        return new ClassFilter(cls);
    }

    public static <T> Predicate<T> predicateReverseFor(BooleanSupplier booleanSupplier) {
        return new BooleanSupplierPredicateReverse(booleanSupplier);
    }

    public static <T> Function<T, Timed<T>> timestampWith(TimeUnit timeUnit, Scheduler scheduler) {
        return new TimestampFunction(timeUnit, scheduler);
    }

    public static <T, K> BiConsumer<Map<K, T>, T> toMapKeySelector(Function<? super T, ? extends K> function) {
        return new ToMapKeySelector(function);
    }

    public static <T, K, V> BiConsumer<Map<K, V>, T> toMapKeyValueSelector(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2) {
        return new ToMapKeyValueSelector(function2, function);
    }

    public static <T, K, V> BiConsumer<Map<K, Collection<V>>, T> toMultimapKeyValueSelector(Function<? super T, ? extends K> function, Function<? super T, ? extends V> function2, Function<? super K, ? extends Collection<? super V>> function3) {
        return new ToMultimapKeyValueSelector(function3, function2, function);
    }

    public static <T> Comparator<T> naturalComparator() {
        return NaturalComparator.INSTANCE;
    }

    public static <T> Function<List<T>, List<T>> listSorter(Comparator<? super T> comparator) {
        return new ListSorter(comparator);
    }
}
