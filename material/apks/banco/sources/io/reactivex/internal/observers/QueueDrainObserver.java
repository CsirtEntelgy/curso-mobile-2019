package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.ObservableQueueDrain;
import io.reactivex.internal.util.QueueDrainHelper;

public abstract class QueueDrainObserver<T, U, V> extends QueueDrainSubscriberPad2 implements Observer<T>, ObservableQueueDrain<U, V> {
    protected final Observer<? super V> actual;
    /* access modifiers changed from: protected */
    public volatile boolean cancelled;
    protected volatile boolean done;
    protected Throwable error;
    /* access modifiers changed from: protected */
    public final SimplePlainQueue<U> queue;

    public void accept(Observer<? super V> observer, U u) {
    }

    public QueueDrainObserver(Observer<? super V> observer, SimplePlainQueue<U> simplePlainQueue) {
        this.actual = observer;
        this.queue = simplePlainQueue;
    }

    public final boolean cancelled() {
        return this.cancelled;
    }

    public final boolean done() {
        return this.done;
    }

    public final boolean enter() {
        return this.a.getAndIncrement() == 0;
    }

    public final boolean fastEnter() {
        return this.a.get() == 0 && this.a.compareAndSet(0, 1);
    }

    /* access modifiers changed from: protected */
    public final void fastPathEmit(U u, boolean z, Disposable disposable) {
        Observer<? super V> observer = this.actual;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (this.a.get() != 0 || !this.a.compareAndSet(0, 1)) {
            simplePlainQueue.offer(u);
            if (!enter()) {
                return;
            }
        } else {
            accept(observer, u);
            if (leave(-1) == 0) {
                return;
            }
        }
        QueueDrainHelper.drainLoop(simplePlainQueue, observer, z, disposable, this);
    }

    /* access modifiers changed from: protected */
    public final void fastPathOrderedEmit(U u, boolean z, Disposable disposable) {
        Observer<? super V> observer = this.actual;
        SimplePlainQueue<U> simplePlainQueue = this.queue;
        if (this.a.get() != 0 || !this.a.compareAndSet(0, 1)) {
            simplePlainQueue.offer(u);
            if (!enter()) {
                return;
            }
        } else if (simplePlainQueue.isEmpty()) {
            accept(observer, u);
            if (leave(-1) == 0) {
                return;
            }
        } else {
            simplePlainQueue.offer(u);
        }
        QueueDrainHelper.drainLoop(simplePlainQueue, observer, z, disposable, this);
    }

    public final Throwable error() {
        return this.error;
    }

    public final int leave(int i) {
        return this.a.addAndGet(i);
    }
}
