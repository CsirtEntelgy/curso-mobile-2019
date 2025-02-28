package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleResumeNext<T> extends Single<T> {
    final SingleSource<? extends T> a;
    final Function<? super Throwable, ? extends SingleSource<? extends T>> b;

    static final class ResumeMainSingleObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Disposable {
        private static final long serialVersionUID = -5314538511045349925L;
        final SingleObserver<? super T> a;
        final Function<? super Throwable, ? extends SingleSource<? extends T>> b;

        ResumeMainSingleObserver(SingleObserver<? super T> singleObserver, Function<? super Throwable, ? extends SingleSource<? extends T>> function) {
            this.a = singleObserver;
            this.b = function;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(T t) {
            this.a.onSuccess(t);
        }

        public void onError(Throwable th) {
            try {
                ((SingleSource) ObjectHelper.requireNonNull(this.b.apply(th), "The nextFunction returned a null SingleSource.")).subscribe(new ResumeSingleObserver(this, this.a));
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.a.onError(new CompositeException(th, th2));
            }
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }

    public SingleResumeNext(SingleSource<? extends T> singleSource, Function<? super Throwable, ? extends SingleSource<? extends T>> function) {
        this.a = singleSource;
        this.b = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.a.subscribe(new ResumeMainSingleObserver(singleObserver, this.b));
    }
}
