package io.reactivex.internal.operators.maybe;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import java.util.Arrays;

public final class MaybeZipIterable<T, R> extends Maybe<R> {
    final Iterable<? extends MaybeSource<? extends T>> a;
    final Function<? super Object[], ? extends R> b;

    final class SingletonArrayFunc implements Function<T, R> {
        SingletonArrayFunc() {
        }

        public R apply(T t) {
            return MaybeZipIterable.this.b.apply(new Object[]{t});
        }
    }

    public MaybeZipIterable(Iterable<? extends MaybeSource<? extends T>> iterable, Function<? super Object[], ? extends R> function) {
        this.a = iterable;
        this.b = function;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super R> maybeObserver) {
        MaybeSource[] maybeSourceArr = new MaybeSource[8];
        try {
            MaybeSource[] maybeSourceArr2 = maybeSourceArr;
            int i = 0;
            for (MaybeSource maybeSource : this.a) {
                if (maybeSource == null) {
                    EmptyDisposable.error((Throwable) new NullPointerException("One of the sources is null"), maybeObserver);
                    return;
                }
                if (i == maybeSourceArr2.length) {
                    maybeSourceArr2 = (MaybeSource[]) Arrays.copyOf(maybeSourceArr2, (i >> 2) + i);
                }
                int i2 = i + 1;
                maybeSourceArr2[i] = maybeSource;
                i = i2;
            }
            if (i == 0) {
                EmptyDisposable.complete(maybeObserver);
            } else if (i == 1) {
                maybeSourceArr2[0].subscribe(new MapMaybeObserver(maybeObserver, new SingletonArrayFunc()));
            } else {
                ZipCoordinator zipCoordinator = new ZipCoordinator(maybeObserver, i, this.b);
                maybeObserver.onSubscribe(zipCoordinator);
                for (int i3 = 0; i3 < i && !zipCoordinator.isDisposed(); i3++) {
                    maybeSourceArr2[i3].subscribe(zipCoordinator.c[i3]);
                }
            }
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, maybeObserver);
        }
    }
}
