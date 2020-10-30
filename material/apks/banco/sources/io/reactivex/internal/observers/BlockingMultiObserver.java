package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class BlockingMultiObserver<T> extends CountDownLatch implements CompletableObserver, MaybeObserver<T>, SingleObserver<T> {
    T a;
    Throwable b;
    Disposable c;
    volatile boolean d;

    public BlockingMultiObserver() {
        super(1);
    }

    /* access modifiers changed from: 0000 */
    public void a() {
        this.d = true;
        Disposable disposable = this.c;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void onSubscribe(Disposable disposable) {
        this.c = disposable;
        if (this.d) {
            disposable.dispose();
        }
    }

    public void onSuccess(T t) {
        this.a = t;
        countDown();
    }

    public void onError(Throwable th) {
        this.b = th;
        countDown();
    }

    public void onComplete() {
        countDown();
    }

    public T blockingGet() {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                a();
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }
        Throwable th = this.b;
        if (th == null) {
            return this.a;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }

    public T blockingGet(T t) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                a();
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }
        Throwable th = this.b;
        if (th != null) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
        T t2 = this.a;
        return t2 != null ? t2 : t;
    }

    public Throwable blockingGetError() {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e) {
                a();
                return e;
            }
        }
        return this.b;
    }

    public Throwable blockingGetError(long j, TimeUnit timeUnit) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                if (!await(j, timeUnit)) {
                    a();
                    throw ExceptionHelper.wrapOrThrow(new TimeoutException());
                }
            } catch (InterruptedException e) {
                a();
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }
        return this.b;
    }

    public boolean blockingAwait(long j, TimeUnit timeUnit) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                if (!await(j, timeUnit)) {
                    a();
                    return false;
                }
            } catch (InterruptedException e) {
                a();
                throw ExceptionHelper.wrapOrThrow(e);
            }
        }
        Throwable th = this.b;
        if (th == null) {
            return true;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }
}
