package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.observers.ResumeSingleObserver;
import java.util.concurrent.atomic.AtomicReference;

public final class SingleDelayWithSingle<T, U> extends Single<T> {
    final SingleSource<T> a;
    final SingleSource<U> b;

    static final class OtherObserver<T, U> extends AtomicReference<Disposable> implements SingleObserver<U>, Disposable {
        private static final long serialVersionUID = -8565274649390031272L;
        final SingleObserver<? super T> a;
        final SingleSource<T> b;

        OtherObserver(SingleObserver<? super T> singleObserver, SingleSource<T> singleSource) {
            this.a = singleObserver;
            this.b = singleSource;
        }

        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.set(this, disposable)) {
                this.a.onSubscribe(this);
            }
        }

        public void onSuccess(U u) {
            this.b.subscribe(new ResumeSingleObserver(this, this.a));
        }

        public void onError(Throwable th) {
            this.a.onError(th);
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }

    public SingleDelayWithSingle(SingleSource<T> singleSource, SingleSource<U> singleSource2) {
        this.a = singleSource;
        this.b = singleSource2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> singleObserver) {
        this.b.subscribe(new OtherObserver(singleObserver, this.a));
    }
}
