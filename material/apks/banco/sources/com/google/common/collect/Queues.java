package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

@GwtCompatible(emulated = true)
public final class Queues {
    private Queues() {
    }

    @GwtIncompatible
    public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(int i) {
        return new ArrayBlockingQueue<>(i);
    }

    public static <E> ArrayDeque<E> newArrayDeque() {
        return new ArrayDeque<>();
    }

    public static <E> ArrayDeque<E> newArrayDeque(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new ArrayDeque<>(Collections2.a(iterable));
        }
        ArrayDeque<E> arrayDeque = new ArrayDeque<>();
        Iterables.addAll(arrayDeque, iterable);
        return arrayDeque;
    }

    @GwtIncompatible
    public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    @GwtIncompatible
    public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new ConcurrentLinkedQueue<>(Collections2.a(iterable));
        }
        ConcurrentLinkedQueue<E> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        Iterables.addAll(concurrentLinkedQueue, iterable);
        return concurrentLinkedQueue;
    }

    @GwtIncompatible
    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque() {
        return new LinkedBlockingDeque<>();
    }

    @GwtIncompatible
    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(int i) {
        return new LinkedBlockingDeque<>(i);
    }

    @GwtIncompatible
    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new LinkedBlockingDeque<>(Collections2.a(iterable));
        }
        LinkedBlockingDeque<E> linkedBlockingDeque = new LinkedBlockingDeque<>();
        Iterables.addAll(linkedBlockingDeque, iterable);
        return linkedBlockingDeque;
    }

    @GwtIncompatible
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue() {
        return new LinkedBlockingQueue<>();
    }

    @GwtIncompatible
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(int i) {
        return new LinkedBlockingQueue<>(i);
    }

    @GwtIncompatible
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new LinkedBlockingQueue<>(Collections2.a(iterable));
        }
        LinkedBlockingQueue<E> linkedBlockingQueue = new LinkedBlockingQueue<>();
        Iterables.addAll(linkedBlockingQueue, iterable);
        return linkedBlockingQueue;
    }

    @GwtIncompatible
    public static <E extends Comparable> PriorityBlockingQueue<E> newPriorityBlockingQueue() {
        return new PriorityBlockingQueue<>();
    }

    @GwtIncompatible
    public static <E extends Comparable> PriorityBlockingQueue<E> newPriorityBlockingQueue(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new PriorityBlockingQueue<>(Collections2.a(iterable));
        }
        PriorityBlockingQueue<E> priorityBlockingQueue = new PriorityBlockingQueue<>();
        Iterables.addAll(priorityBlockingQueue, iterable);
        return priorityBlockingQueue;
    }

    public static <E extends Comparable> PriorityQueue<E> newPriorityQueue() {
        return new PriorityQueue<>();
    }

    public static <E extends Comparable> PriorityQueue<E> newPriorityQueue(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new PriorityQueue<>(Collections2.a(iterable));
        }
        PriorityQueue<E> priorityQueue = new PriorityQueue<>();
        Iterables.addAll(priorityQueue, iterable);
        return priorityQueue;
    }

    @GwtIncompatible
    public static <E> SynchronousQueue<E> newSynchronousQueue() {
        return new SynchronousQueue<>();
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @Beta
    public static <E> int drain(BlockingQueue<E> blockingQueue, Collection<? super E> collection, int i, long j, TimeUnit timeUnit) {
        Preconditions.checkNotNull(collection);
        long nanoTime = System.nanoTime() + timeUnit.toNanos(j);
        int i2 = 0;
        while (i2 < i) {
            i2 += blockingQueue.drainTo(collection, i - i2);
            if (i2 < i) {
                Object poll = blockingQueue.poll(nanoTime - System.nanoTime(), TimeUnit.NANOSECONDS);
                if (poll == null) {
                    break;
                }
                collection.add(poll);
                i2++;
            }
        }
        return i2;
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @Beta
    public static <E> int drainUninterruptibly(BlockingQueue<E> blockingQueue, Collection<? super E> collection, int i, long j, TimeUnit timeUnit) {
        Object poll;
        Preconditions.checkNotNull(collection);
        long nanoTime = System.nanoTime() + timeUnit.toNanos(j);
        int i2 = 0;
        boolean z = false;
        while (i2 < i) {
            try {
                i2 += blockingQueue.drainTo(collection, i - i2);
                if (i2 < i) {
                    while (true) {
                        try {
                            poll = blockingQueue.poll(nanoTime - System.nanoTime(), TimeUnit.NANOSECONDS);
                            break;
                        } catch (InterruptedException unused) {
                            z = true;
                        }
                    }
                    if (poll == null) {
                        break;
                    }
                    collection.add(poll);
                    i2++;
                }
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return i2;
    }

    public static <E> Queue<E> synchronizedQueue(Queue<E> queue) {
        return Synchronized.a(queue, (Object) null);
    }

    public static <E> Deque<E> synchronizedDeque(Deque<E> deque) {
        return Synchronized.a(deque, (Object) null);
    }
}
