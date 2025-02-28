package android.support.constraint.solver;

final class Pools {

    interface Pool<T> {
        T a();

        void a(T[] tArr, int i);

        boolean a(T t);
    }

    static class SimplePool<T> implements Pool<T> {
        private final Object[] a;
        private int b;

        SimplePool(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0");
            }
            this.a = new Object[i];
        }

        public T a() {
            if (this.b <= 0) {
                return null;
            }
            int i = this.b - 1;
            T t = this.a[i];
            this.a[i] = null;
            this.b--;
            return t;
        }

        public boolean a(T t) {
            if (this.b >= this.a.length) {
                return false;
            }
            this.a[this.b] = t;
            this.b++;
            return true;
        }

        public void a(T[] tArr, int i) {
            if (i > tArr.length) {
                i = tArr.length;
            }
            for (int i2 = 0; i2 < i; i2++) {
                T t = tArr[i2];
                if (this.b < this.a.length) {
                    this.a[this.b] = t;
                    this.b++;
                }
            }
        }
    }

    private Pools() {
    }
}
