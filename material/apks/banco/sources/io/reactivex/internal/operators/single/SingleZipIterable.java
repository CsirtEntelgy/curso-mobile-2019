package io.reactivex.internal.operators.single;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.Arrays;
import java.util.NoSuchElementException;

public final class SingleZipIterable<T, R> extends Single<R> {
    final Iterable<? extends SingleSource<? extends T>> a;
    final Function<? super Object[], ? extends R> b;

    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        public R apply(T t) {
            return SingleZipIterable.this.b.apply(new Object[]{t});
        }
    }

    public SingleZipIterable(Iterable<? extends SingleSource<? extends T>> iterable, Function<? super Object[], ? extends R> function) {
        this.a = iterable;
        this.b = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super R> singleObserver) {
        SingleSource[] singleSourceArr = new SingleSource[8];
        try {
            SingleSource[] singleSourceArr2 = singleSourceArr;
            int i = 0;
            for (SingleSource singleSource : this.a) {
                if (singleSource == null) {
                    EmptyDisposable.error((Throwable) new NullPointerException("One of the sources is null"), singleObserver);
                    return;
                }
                if (i == singleSourceArr2.length) {
                    singleSourceArr2 = (SingleSource[]) Arrays.copyOf(singleSourceArr2, (i >> 2) + i);
                }
                int i2 = i + 1;
                singleSourceArr2[i] = singleSource;
                i = i2;
            }
            if (i == 0) {
                EmptyDisposable.error((Throwable) new NoSuchElementException(), singleObserver);
            } else if (i == 1) {
                singleSourceArr2[0].subscribe(new MapSingleObserver(singleObserver, new SingletonArrayFunc()));
            } else {
                ZipCoordinator zipCoordinator = new ZipCoordinator(singleObserver, i, this.b);
                singleObserver.onSubscribe(zipCoordinator);
                for (int i3 = 0; i3 < i && !zipCoordinator.isDisposed(); i3++) {
                    singleSourceArr2[i3].subscribe(zipCoordinator.c[i3]);
                }
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, singleObserver);
        }
    }
}
