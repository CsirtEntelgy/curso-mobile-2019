package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;

public final class ObservableFromUnsafeSource<T> extends Observable<T> {
    final ObservableSource<T> a;

    public ObservableFromUnsafeSource(ObservableSource<T> observableSource) {
        this.a = observableSource;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.a.subscribe(observer);
    }
}
