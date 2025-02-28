package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.internal.fuseable.ScalarCallable;
import io.reactivex.internal.operators.observable.ObservableScalarXMap.ScalarDisposable;

public final class ObservableJust<T> extends Observable<T> implements ScalarCallable<T> {
    private final T a;

    public ObservableJust(T t) {
        this.a = t;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        ScalarDisposable scalarDisposable = new ScalarDisposable(observer, this.a);
        observer.onSubscribe(scalarDisposable);
        scalarDisposable.run();
    }

    public T call() {
        return this.a;
    }
}
