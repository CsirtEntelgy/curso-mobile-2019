package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.observers.DisposableLambdaObserver;

public final class ObservableDoOnLifecycle<T> extends AbstractObservableWithUpstream<T, T> {
    private final Consumer<? super Disposable> a;
    private final Action b;

    public ObservableDoOnLifecycle(Observable<T> observable, Consumer<? super Disposable> consumer, Action action) {
        super(observable);
        this.a = consumer;
        this.b = action;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> observer) {
        this.source.subscribe(new DisposableLambdaObserver(observer, this.a, this.b));
    }
}
