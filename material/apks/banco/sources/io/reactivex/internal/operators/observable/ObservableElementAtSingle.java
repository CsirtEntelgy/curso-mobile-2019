package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.fuseable.FuseToObservable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.NoSuchElementException;

public final class ObservableElementAtSingle<T> extends Single<T> implements FuseToObservable<T> {
    final ObservableSource<T> a;
    final long b;
    final T c;

    static final class ElementAtObserver<T> implements Observer<T>, Disposable {
        final SingleObserver<? super T> a;
        final long b;
        final T c;
        Disposable d;
        long e;
        boolean f;

        ElementAtObserver(SingleObserver<? super T> singleObserver, long j, T t) {
            this.a = singleObserver;
            this.b = j;
            this.c = t;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.d, disposable)) {
                this.d = disposable;
                this.a.onSubscribe(this);
            }
        }

        public void dispose() {
            this.d.dispose();
        }

        public boolean isDisposed() {
            return this.d.isDisposed();
        }

        public void onNext(T t) {
            if (!this.f) {
                long j = this.e;
                if (j == this.b) {
                    this.f = true;
                    this.d.dispose();
                    this.a.onSuccess(t);
                    return;
                }
                this.e = j + 1;
            }
        }

        public void onError(Throwable th) {
            if (this.f) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.f = true;
            this.a.onError(th);
        }

        public void onComplete() {
            if (!this.f) {
                this.f = true;
                T t = this.c;
                if (t != null) {
                    this.a.onSuccess(t);
                } else {
                    this.a.onError(new NoSuchElementException());
                }
            }
        }
    }

    public ObservableElementAtSingle(ObservableSource<T> observableSource, long j, T t) {
        this.a = observableSource;
        this.b = j;
        this.c = t;
    }

    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new ElementAtObserver(singleObserver, this.b, this.c));
    }

    public Observable<T> fuseToObservable() {
        ObservableElementAt observableElementAt = new ObservableElementAt(this.a, this.b, this.c, true);
        return RxJavaPlugins.onAssembly((Observable<T>) observableElementAt);
    }
}
