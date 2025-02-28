package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.subscribers.QueueDrainSubscriber;
import io.reactivex.internal.subscriptions.EmptySubscription;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.subscribers.DisposableSubscriber;
import io.reactivex.subscribers.SerializedSubscriber;
import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class FlowableBufferExactBoundary<T, U extends Collection<? super T>, B> extends AbstractFlowableWithUpstream<T, U> {
    final Publisher<B> b;
    final Callable<U> c;

    static final class BufferBoundarySubscriber<T, U extends Collection<? super T>, B> extends DisposableSubscriber<B> {
        final BufferExactBoundarySubscriber<T, U, B> a;

        BufferBoundarySubscriber(BufferExactBoundarySubscriber<T, U, B> bufferExactBoundarySubscriber) {
            this.a = bufferExactBoundarySubscriber;
        }

        public void onNext(B b) {
            this.a.a();
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void onComplete() {
            this.a.onComplete();
        }
    }

    static final class BufferExactBoundarySubscriber<T, U extends Collection<? super T>, B> extends QueueDrainSubscriber<T, U, U> implements FlowableSubscriber<T>, Disposable, Subscription {
        final Callable<U> a;
        final Publisher<B> b;
        Subscription c;
        Disposable d;
        U e;

        BufferExactBoundarySubscriber(Subscriber<? super U> subscriber, Callable<U> callable, Publisher<B> publisher) {
            super(subscriber, new MpscLinkedQueue());
            this.a = callable;
            this.b = publisher;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.c, subscription)) {
                this.c = subscription;
                try {
                    this.e = (Collection) ObjectHelper.requireNonNull(this.a.call(), "The buffer supplied is null");
                    BufferBoundarySubscriber bufferBoundarySubscriber = new BufferBoundarySubscriber(this);
                    this.d = bufferBoundarySubscriber;
                    this.actual.onSubscribe(this);
                    if (!this.cancelled) {
                        subscription.request(Long.MAX_VALUE);
                        this.b.subscribe(bufferBoundarySubscriber);
                    }
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.cancelled = true;
                    subscription.cancel();
                    EmptySubscription.error(th, this.actual);
                }
            }
        }

        public void onNext(T t) {
            synchronized (this) {
                U u = this.e;
                if (u != null) {
                    u.add(t);
                }
            }
        }

        public void onError(Throwable th) {
            cancel();
            this.actual.onError(th);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
            io.reactivex.internal.util.QueueDrainHelper.drainMaxLoop(r3.queue, r3.actual, false, r3, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0021, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x000b, code lost:
            r3.queue.offer(r0);
            r3.done = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
            if (enter() == false) goto L_0x0021;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onComplete() {
            /*
                r3 = this;
                monitor-enter(r3)
                U r0 = r3.e     // Catch:{ all -> 0x0022 }
                if (r0 != 0) goto L_0x0007
                monitor-exit(r3)     // Catch:{ all -> 0x0022 }
                return
            L_0x0007:
                r1 = 0
                r3.e = r1     // Catch:{ all -> 0x0022 }
                monitor-exit(r3)     // Catch:{ all -> 0x0022 }
                io.reactivex.internal.fuseable.SimplePlainQueue r1 = r3.queue
                r1.offer(r0)
                r0 = 1
                r3.done = r0
                boolean r0 = r3.enter()
                if (r0 == 0) goto L_0x0021
                io.reactivex.internal.fuseable.SimplePlainQueue r0 = r3.queue
                org.reactivestreams.Subscriber r1 = r3.actual
                r2 = 0
                io.reactivex.internal.util.QueueDrainHelper.drainMaxLoop(r0, r1, r2, r3, r3)
            L_0x0021:
                return
            L_0x0022:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0022 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.flowable.FlowableBufferExactBoundary.BufferExactBoundarySubscriber.onComplete():void");
        }

        public void request(long j) {
            requested(j);
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.d.dispose();
                this.c.cancel();
                if (enter()) {
                    this.queue.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void a() {
            try {
                U u = (Collection) ObjectHelper.requireNonNull(this.a.call(), "The buffer supplied is null");
                synchronized (this) {
                    U u2 = this.e;
                    if (u2 != null) {
                        this.e = u;
                        fastPathEmitMax(u2, false, this);
                    }
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                cancel();
                this.actual.onError(th);
            }
        }

        public void dispose() {
            cancel();
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* renamed from: a */
        public boolean accept(Subscriber<? super U> subscriber, U u) {
            this.actual.onNext(u);
            return true;
        }
    }

    public FlowableBufferExactBoundary(Flowable<T> flowable, Publisher<B> publisher, Callable<U> callable) {
        super(flowable);
        this.b = publisher;
        this.c = callable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super U> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super T>) new BufferExactBoundarySubscriber<Object>(new SerializedSubscriber(subscriber), this.c, this.b));
    }
}
