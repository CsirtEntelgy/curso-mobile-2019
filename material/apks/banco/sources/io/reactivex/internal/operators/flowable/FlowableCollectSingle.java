package io.reactivex.internal.operators.flowable;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.BiConsumer;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.FuseToFlowable;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscription;

public final class FlowableCollectSingle<T, U> extends Single<U> implements FuseToFlowable<U> {
    final Flowable<T> a;
    final Callable<? extends U> b;
    final BiConsumer<? super U, ? super T> c;

    static final class CollectSubscriber<T, U> implements FlowableSubscriber<T>, Disposable {
        final SingleObserver<? super U> a;
        final BiConsumer<? super U, ? super T> b;
        final U c;
        Subscription d;
        boolean e;

        CollectSubscriber(SingleObserver<? super U> singleObserver, U u, BiConsumer<? super U, ? super T> biConsumer) {
            this.a = singleObserver;
            this.b = biConsumer;
            this.c = u;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.validate(this.d, subscription)) {
                this.d = subscription;
                this.a.onSubscribe(this);
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.e) {
                try {
                    this.b.accept(this.c, t);
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    this.d.cancel();
                    onError(th);
                }
            }
        }

        public void onError(Throwable th) {
            if (this.e) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.e = true;
            this.d = SubscriptionHelper.CANCELLED;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.e) {
                this.e = true;
                this.d = SubscriptionHelper.CANCELLED;
                this.a.onSuccess(this.c);
            }
        }

        public void dispose() {
            this.d.cancel();
            this.d = SubscriptionHelper.CANCELLED;
        }

        public boolean isDisposed() {
            return this.d == SubscriptionHelper.CANCELLED;
        }
    }

    public FlowableCollectSingle(Flowable<T> flowable, Callable<? extends U> callable, BiConsumer<? super U, ? super T> biConsumer) {
        this.a = flowable;
        this.b = callable;
        this.c = biConsumer;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super U> singleObserver) {
        try {
            this.a.subscribe((FlowableSubscriber<? super T>) new CollectSubscriber<Object>(singleObserver, ObjectHelper.requireNonNull(this.b.call(), "The initialSupplier returned a null value"), this.c));
        } catch (Throwable th) {
            EmptyDisposable.error(th, singleObserver);
        }
    }

    public Flowable<U> fuseToFlowable() {
        return RxJavaPlugins.onAssembly((Flowable<T>) new FlowableCollect<T>(this.a, this.b, this.c));
    }
}
