package io.reactivex.internal.observers;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class ResumeSingleObserver<T> implements SingleObserver<T> {
    final AtomicReference<Disposable> a;
    final SingleObserver<? super T> b;

    public ResumeSingleObserver(AtomicReference<Disposable> atomicReference, SingleObserver<? super T> singleObserver) {
        this.a = atomicReference;
        this.b = singleObserver;
    }

    public void onSubscribe(Disposable disposable) {
        DisposableHelper.replace(this.a, disposable);
    }

    public void onSuccess(T t) {
        this.b.onSuccess(t);
    }

    public void onError(Throwable th) {
        this.b.onError(th);
    }
}
