package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.List;
import java.util.RandomAccess;
import javax.annotation.Nullable;

@GwtCompatible
@Beta
final class SortedLists {

    public enum KeyAbsentBehavior {
        NEXT_LOWER {
            /* access modifiers changed from: 0000 */
            public int a(int i) {
                return i - 1;
            }
        },
        NEXT_HIGHER {
            public int a(int i) {
                return i;
            }
        },
        INVERTED_INSERTION_INDEX {
            public int a(int i) {
                return i ^ -1;
            }
        };

        /* access modifiers changed from: 0000 */
        public abstract int a(int i);
    }

    public enum KeyPresentBehavior {
        ANY_PRESENT {
            /* access modifiers changed from: 0000 */
            public <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i) {
                return i;
            }
        },
        LAST_PRESENT {
            /* access modifiers changed from: 0000 */
            public <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i) {
                int size = list.size() - 1;
                while (i < size) {
                    int i2 = ((i + size) + 1) >>> 1;
                    if (comparator.compare(list.get(i2), e) > 0) {
                        size = i2 - 1;
                    } else {
                        i = i2;
                    }
                }
                return i;
            }
        },
        FIRST_PRESENT {
            /* access modifiers changed from: 0000 */
            public <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i) {
                int i2 = 0;
                while (i2 < i) {
                    int i3 = (i2 + i) >>> 1;
                    if (comparator.compare(list.get(i3), e) < 0) {
                        i2 = i3 + 1;
                    } else {
                        i = i3;
                    }
                }
                return i2;
            }
        },
        FIRST_AFTER {
            public <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i) {
                return LAST_PRESENT.a(comparator, e, list, i) + 1;
            }
        },
        LAST_BEFORE {
            public <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i) {
                return FIRST_PRESENT.a(comparator, e, list, i) - 1;
            }
        };

        /* access modifiers changed from: 0000 */
        public abstract <E> int a(Comparator<? super E> comparator, E e, List<? extends E> list, int i);
    }

    private SortedLists() {
    }

    public static <E, K extends Comparable> int a(List<E> list, Function<? super E, K> function, @Nullable K k, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        return a(list, function, k, Ordering.natural(), keyPresentBehavior, keyAbsentBehavior);
    }

    public static <E, K> int a(List<E> list, Function<? super E, K> function, @Nullable K k, Comparator<? super K> comparator, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        return a(Lists.transform(list, function), (E) k, comparator, keyPresentBehavior, keyAbsentBehavior);
    }

    public static <E> int a(List<? extends E> list, @Nullable E e, Comparator<? super E> comparator, KeyPresentBehavior keyPresentBehavior, KeyAbsentBehavior keyAbsentBehavior) {
        Preconditions.checkNotNull(comparator);
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(keyPresentBehavior);
        Preconditions.checkNotNull(keyAbsentBehavior);
        if (!(list instanceof RandomAccess)) {
            list = Lists.newArrayList((Iterable<? extends E>) list);
        }
        int i = 0;
        int size = list.size() - 1;
        while (i <= size) {
            int i2 = (i + size) >>> 1;
            int compare = comparator.compare(e, list.get(i2));
            if (compare < 0) {
                size = i2 - 1;
            } else if (compare <= 0) {
                return i + keyPresentBehavior.a(comparator, e, list.subList(i, size + 1), i2 - i);
            } else {
                i = i2 + 1;
            }
        }
        return keyAbsentBehavior.a(i);
    }
}
