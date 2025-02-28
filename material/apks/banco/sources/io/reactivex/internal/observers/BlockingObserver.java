package io.reactivex.internal.observers;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.util.NotificationLite;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

public final class BlockingObserver<T> extends AtomicReference<Disposable> implements Observer<T>, Disposable {
    public static final Object TERMINATED = new Object();
    private static final long serialVersionUID = -4875965440900746268L;
    final Queue<Object> a;

    public BlockingObserver(Queue<Object> queue) {
        this.a = queue;
    }

    public void onSubscribe(Disposable disposable) {
        DisposableHelper.setOnce(this, disposable);
    }

    public void onNext(T t) {
        this.a.offer(NotificationLite.next(t));
    }

    public void onError(Throwable th) {
        this.a.offer(NotificationLite.error(th));
    }

    public void onComplete() {
        this.a.offer(NotificationLite.complete());
    }

    public void dispose() {
        if (DisposableHelper.dispose(this)) {
            this.a.offer(TERMINATED);
        }
    }

    public boolean isDisposed() {
        return get() == DisposableHelper.DISPOSED;
    }
}
