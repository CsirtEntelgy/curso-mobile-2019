package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicBoolean;

public final class MaybeAmb<T> extends Maybe<T> {
    private final MaybeSource<? extends T>[] a;
    private final Iterable<? extends MaybeSource<? extends T>> b;

    static final class AmbMaybeObserver<T> extends AtomicBoolean implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = -7044685185359438206L;
        final MaybeObserver<? super T> a;
        final CompositeDisposable b = new CompositeDisposable();

        AmbMaybeObserver(MaybeObserver<? super T> maybeObserver) {
            this.a = maybeObserver;
        }

        public void dispose() {
            if (compareAndSet(false, true)) {
                this.b.dispose();
            }
        }

        public boolean isDisposed() {
            return get();
        }

        public void onSubscribe(Disposable disposable) {
            this.b.add(disposable);
        }

        public void onSuccess(T t) {
            if (compareAndSet(false, true)) {
                this.b.dispose();
                this.a.onSuccess(t);
            }
        }

        public void onError(Throwable th) {
            if (compareAndSet(false, true)) {
                this.b.dispose();
                this.a.onError(th);
                return;
            }
            RxJavaPlugins.onError(th);
        }

        public void onComplete() {
            if (compareAndSet(false, true)) {
                this.b.dispose();
                this.a.onComplete();
            }
        }
    }

    public MaybeAmb(MaybeSource<? extends T>[] maybeSourceArr, Iterable<? extends MaybeSource<? extends T>> iterable) {
        this.a = maybeSourceArr;
        this.b = iterable;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        int i;
        MaybeSource<? extends T>[] maybeSourceArr = this.a;
        int i2 = 0;
        if (maybeSourceArr == null) {
            maybeSourceArr = new MaybeSource[8];
            try {
                i = 0;
                for (MaybeSource<? extends T> maybeSource : this.b) {
                    if (maybeSource == null) {
                        EmptyDisposable.error((Throwable) new NullPointerException("One of the sources is null"), maybeObserver);
                        return;
                    }
                    if (i == maybeSourceArr.length) {
                        MaybeSource<? extends T>[] maybeSourceArr2 = new MaybeSource[((i >> 2) + i)];
                        System.arraycopy(maybeSourceArr, 0, maybeSourceArr2, 0, i);
                        maybeSourceArr = maybeSourceArr2;
                    }
                    int i3 = i + 1;
                    maybeSourceArr[i] = maybeSource;
                    i = i3;
                }
            } catch (Throwable th) {
                Exceptions.throwIfFatal(th);
                EmptyDisposable.error(th, maybeObserver);
                return;
            }
        } else {
            i = maybeSourceArr.length;
        }
        AmbMaybeObserver ambMaybeObserver = new AmbMaybeObserver(maybeObserver);
        maybeObserver.onSubscribe(ambMaybeObserver);
        while (i2 < i) {
            MaybeSource<? extends T> maybeSource2 = maybeSourceArr[i2];
            if (!ambMaybeObserver.isDisposed()) {
                if (maybeSource2 == null) {
                    ambMaybeObserver.onError(new NullPointerException("One of the MaybeSources is null"));
                    return;
                } else {
                    maybeSource2.subscribe(ambMaybeObserver);
                    i2++;
                }
            } else {
                return;
            }
        }
        if (i == 0) {
            maybeObserver.onComplete();
        }
    }
}
