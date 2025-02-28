package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableMergeIterable extends Completable {
    final Iterable<? extends CompletableSource> a;

    static final class MergeCompletableObserver extends AtomicBoolean implements CompletableObserver {
        private static final long serialVersionUID = -7730517613164279224L;
        final CompositeDisposable a;
        final CompletableObserver b;
        final AtomicInteger c;

        MergeCompletableObserver(CompletableObserver completableObserver, CompositeDisposable compositeDisposable, AtomicInteger atomicInteger) {
            this.b = completableObserver;
            this.a = compositeDisposable;
            this.c = atomicInteger;
        }

        public void onSubscribe(Disposable disposable) {
            this.a.add(disposable);
        }

        public void onError(Throwable th) {
            this.a.dispose();
            if (compareAndSet(false, true)) {
                this.b.onError(th);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        public void onComplete() {
            if (this.c.decrementAndGet() == 0 && compareAndSet(false, true)) {
                this.b.onComplete();
            }
        }
    }

    public CompletableMergeIterable(Iterable<? extends CompletableSource> iterable) {
        this.a = iterable;
    }

    public void subscribeActual(CompletableObserver completableObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        completableObserver.onSubscribe(compositeDisposable);
        try {
            Iterator it = (Iterator) ObjectHelper.requireNonNull(this.a.iterator(), "The source iterator returned is null");
            AtomicInteger atomicInteger = new AtomicInteger(1);
            MergeCompletableObserver mergeCompletableObserver = new MergeCompletableObserver(completableObserver, compositeDisposable, atomicInteger);
            while (!compositeDisposable.isDisposed()) {
                try {
                    if (!it.hasNext()) {
                        mergeCompletableObserver.onComplete();
                        return;
                    } else if (!compositeDisposable.isDisposed()) {
                        try {
                            CompletableSource completableSource = (CompletableSource) ObjectHelper.requireNonNull(it.next(), "The iterator returned a null CompletableSource");
                            if (!compositeDisposable.isDisposed()) {
                                atomicInteger.getAndIncrement();
                                completableSource.subscribe(mergeCompletableObserver);
                            } else {
                                return;
                            }
                        } catch (Throwable th) {
                            Exceptions.throwIfFatal(th);
                            compositeDisposable.dispose();
                            mergeCompletableObserver.onError(th);
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    compositeDisposable.dispose();
                    mergeCompletableObserver.onError(th2);
                    return;
                }
            }
        } catch (Throwable th3) {
            Exceptions.throwIfFatal(th3);
            completableObserver.onError(th3);
        }
    }
}
