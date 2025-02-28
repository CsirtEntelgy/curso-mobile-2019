package io.reactivex.internal.operators.single;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

public final class SingleTakeUntil<T, U> extends Single<T> {
    final SingleSource<T> a;
    final Publisher<U> b;

    static final class TakeUntilMainObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = -622603812305745221L;
        final SingleObserver<? super T> a;
        final TakeUntilOtherSubscriber b = new TakeUntilOtherSubscriber(this);

        TakeUntilMainObserver(SingleObserver<? super T> singleObserver) {
            this.a = singleObserver;
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }

        public void onSuccess(T t) {
            this.b.a();
            if (((Disposable) get()) != DisposableHelper.DISPOSED && ((Disposable) getAndSet(DisposableHelper.DISPOSED)) != DisposableHelper.DISPOSED) {
                this.a.onSuccess(t);
            }
        }

        public void onError(Throwable th) {
            this.b.a();
            if (((Disposable) get()) == DisposableHelper.DISPOSED || ((Disposable) getAndSet(DisposableHelper.DISPOSED)) == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(th);
            } else {
                this.a.onError(th);
            }
        }

        /* access modifiers changed from: 0000 */
        public void a(Throwable th) {
            if (((Disposable) get()) != DisposableHelper.DISPOSED) {
                Disposable disposable = (Disposable) getAndSet(DisposableHelper.DISPOSED);
                if (disposable != DisposableHelper.DISPOSED) {
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    this.a.onError(th);
                    return;
                }
            }
            RxJavaPlugins.onError(th);
        }
    }

    static final class TakeUntilOtherSubscriber extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        private static final long serialVersionUID = 5170026210238877381L;
        final TakeUntilMainObserver<?> a;

        TakeUntilOtherSubscriber(TakeUntilMainObserver<?> takeUntilMainObserver) {
            this.a = takeUntilMainObserver;
        }

        public void onSubscribe(Subscription subscription) {
            if (SubscriptionHelper.setOnce(this, subscription)) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object obj) {
            if (SubscriptionHelper.cancel(this)) {
                this.a.a(new CancellationException());
            }
        }

        public void onError(Throwable th) {
            this.a.a(th);
        }

        public void onComplete() {
            if (get() != SubscriptionHelper.CANCELLED) {
                lazySet(SubscriptionHelper.CANCELLED);
                this.a.a(new CancellationException());
            }
        }

        public void a() {
            SubscriptionHelper.cancel(this);
        }
    }

    public SingleTakeUntil(SingleSource<T> singleSource, Publisher<U> publisher) {
        this.a = singleSource;
        this.b = publisher;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        TakeUntilMainObserver takeUntilMainObserver = new TakeUntilMainObserver(singleObserver);
        singleObserver.onSubscribe(takeUntilMainObserver);
        this.b.subscribe(takeUntilMainObserver.b);
        this.a.subscribe(takeUntilMainObserver);
    }
}
