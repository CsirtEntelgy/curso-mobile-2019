package org.bouncycastle.pqc.math.linearalgebra;

public abstract class GF2nElement implements GFElement {
    protected int mDegree;
    protected GF2nField mField;

    /* access modifiers changed from: 0000 */
    public abstract void a();

    /* access modifiers changed from: 0000 */
    public abstract boolean a(int i);

    public abstract Object clone();

    public final GF2nElement convert(GF2nField gF2nField) {
        return this.mField.convert(this, gF2nField);
    }

    public final GF2nField getField() {
        return this.mField;
    }

    public abstract GF2nElement increase();

    public abstract void increaseThis();

    public abstract GF2nElement solveQuadraticEquation();

    public abstract GF2nElement square();

    public abstract GF2nElement squareRoot();

    public abstract void squareRootThis();

    public abstract void squareThis();

    public final GFElement subtract(GFElement gFElement) {
        return add(gFElement);
    }

    public final void subtractFromThis(GFElement gFElement) {
        addToThis(gFElement);
    }

    public abstract boolean testRightmostBit();

    public abstract int trace();
}
