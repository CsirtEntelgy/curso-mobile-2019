package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v7.widget.LinearLayoutManager.LayoutChunkResult;
import android.support.v7.widget.RecyclerView.LayoutManager.LayoutPrefetchRegistry;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.Arrays;

public class GridLayoutManager extends LinearLayoutManager {
    public static final int DEFAULT_SPAN_COUNT = -1;
    boolean a = false;
    int b = -1;
    int[] c;
    View[] d;
    final SparseIntArray e = new SparseIntArray();
    final SparseIntArray f = new SparseIntArray();
    SpanSizeLookup g = new DefaultSpanSizeLookup();
    final Rect h = new Rect();

    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
        public int getSpanSize(int i) {
            return 1;
        }

        public int getSpanIndex(int i, int i2) {
            return i % i2;
        }
    }

    public static class LayoutParams extends android.support.v7.widget.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        int a = -1;
        int b = 0;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(android.support.v7.widget.RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public int getSpanIndex() {
            return this.a;
        }

        public int getSpanSize() {
            return this.b;
        }
    }

    public static abstract class SpanSizeLookup {
        final SparseIntArray a = new SparseIntArray();
        private boolean b = false;

        public abstract int getSpanSize(int i);

        public void setSpanIndexCacheEnabled(boolean z) {
            this.b = z;
        }

        public void invalidateSpanIndexCache() {
            this.a.clear();
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public int a(int i, int i2) {
            if (!this.b) {
                return getSpanIndex(i, i2);
            }
            int i3 = this.a.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int spanIndex = getSpanIndex(i, i2);
            this.a.put(i, spanIndex);
            return spanIndex;
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x002c  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x003e A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x003f A[RETURN] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int getSpanIndex(int r6, int r7) {
            /*
                r5 = this;
                int r0 = r5.getSpanSize(r6)
                r1 = 0
                if (r0 != r7) goto L_0x0008
                return r1
            L_0x0008:
                boolean r2 = r5.b
                if (r2 == 0) goto L_0x0028
                android.util.SparseIntArray r2 = r5.a
                int r2 = r2.size()
                if (r2 <= 0) goto L_0x0028
                int r2 = r5.a(r6)
                if (r2 < 0) goto L_0x0028
                android.util.SparseIntArray r3 = r5.a
                int r3 = r3.get(r2)
                int r4 = r5.getSpanSize(r2)
                int r3 = r3 + r4
                int r2 = r2 + 1
                goto L_0x002a
            L_0x0028:
                r2 = 0
                r3 = 0
            L_0x002a:
                if (r2 >= r6) goto L_0x003b
                int r4 = r5.getSpanSize(r2)
                int r3 = r3 + r4
                if (r3 != r7) goto L_0x0035
                r3 = 0
                goto L_0x0038
            L_0x0035:
                if (r3 <= r7) goto L_0x0038
                r3 = r4
            L_0x0038:
                int r2 = r2 + 1
                goto L_0x002a
            L_0x003b:
                int r0 = r0 + r3
                if (r0 > r7) goto L_0x003f
                return r3
            L_0x003f:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.GridLayoutManager.SpanSizeLookup.getSpanIndex(int, int):int");
        }

        /* access modifiers changed from: 0000 */
        public int a(int i) {
            int size = this.a.size() - 1;
            int i2 = 0;
            while (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                if (this.a.keyAt(i3) < i) {
                    i2 = i3 + 1;
                } else {
                    size = i3 - 1;
                }
            }
            int i4 = i2 - 1;
            if (i4 < 0 || i4 >= this.a.size()) {
                return -1;
            }
            return this.a.keyAt(i4);
        }

        public int getSpanGroupIndex(int i, int i2) {
            int spanSize = getSpanSize(i);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int spanSize2 = getSpanSize(i5);
                i3 += spanSize2;
                if (i3 == i2) {
                    i4++;
                    i3 = 0;
                } else if (i3 > i2) {
                    i4++;
                    i3 = spanSize2;
                }
            }
            return i3 + spanSize > i2 ? i4 + 1 : i4;
        }
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setSpanCount(getProperties(context, attributeSet, i, i2).spanCount);
    }

    public GridLayoutManager(Context context, int i) {
        super(context);
        setSpanCount(i);
    }

    public GridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        setSpanCount(i);
    }

    public void setStackFromEnd(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    public int getRowCountForAccessibility(Recycler recycler, State state) {
        if (this.i == 0) {
            return this.b;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return a(recycler, state, state.getItemCount() - 1) + 1;
    }

    public int getColumnCountForAccessibility(Recycler recycler, State state) {
        if (this.i == 1) {
            return this.b;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return a(recycler, state, state.getItemCount() - 1) + 1;
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.a(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int a2 = a(recycler, state, layoutParams2.getViewLayoutPosition());
        if (this.i == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), a2, 1, this.b > 1 && layoutParams2.getSpanSize() == this.b, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(a2, 1, layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), this.b > 1 && layoutParams2.getSpanSize() == this.b, false));
        }
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        if (state.isPreLayout()) {
            h();
        }
        super.onLayoutChildren(recycler, state);
        g();
    }

    public void onLayoutCompleted(State state) {
        super.onLayoutCompleted(state);
        this.a = false;
    }

    private void g() {
        this.e.clear();
        this.f.clear();
    }

    private void h() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            this.e.put(viewLayoutPosition, layoutParams.getSpanSize());
            this.f.put(viewLayoutPosition, layoutParams.getSpanIndex());
        }
    }

    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        this.g.invalidateSpanIndexCache();
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        this.g.invalidateSpanIndexCache();
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        this.g.invalidateSpanIndexCache();
    }

    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        this.g.invalidateSpanIndexCache();
    }

    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        this.g.invalidateSpanIndexCache();
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.i == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public boolean checkLayoutParams(android.support.v7.widget.RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.g = spanSizeLookup;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.g;
    }

    private void i() {
        int i;
        if (getOrientation() == 1) {
            i = (getWidth() - getPaddingRight()) - getPaddingLeft();
        } else {
            i = (getHeight() - getPaddingBottom()) - getPaddingTop();
        }
        b(i);
    }

    public void setMeasuredDimension(Rect rect, int i, int i2) {
        int i3;
        int i4;
        if (this.c == null) {
            super.setMeasuredDimension(rect, i, i2);
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.i == 1) {
            i4 = chooseSize(i2, rect.height() + paddingTop, getMinimumHeight());
            i3 = chooseSize(i, this.c[this.c.length - 1] + paddingLeft, getMinimumWidth());
        } else {
            i3 = chooseSize(i, rect.width() + paddingLeft, getMinimumWidth());
            i4 = chooseSize(i2, this.c[this.c.length - 1] + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(i3, i4);
    }

    private void b(int i) {
        this.c = a(this.c, this.b, i);
    }

    static int[] a(int[] iArr, int i, int i2) {
        int i3;
        if (!(iArr != null && iArr.length == i + 1 && iArr[iArr.length - 1] == i2)) {
            iArr = new int[(i + 1)];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i2 / i;
        int i6 = i2 % i;
        int i7 = 0;
        for (int i8 = 1; i8 <= i; i8++) {
            i4 += i6;
            if (i4 <= 0 || i - i4 >= i6) {
                i3 = i5;
            } else {
                i3 = i5 + 1;
                i4 -= i;
            }
            i7 += i3;
            iArr[i8] = i7;
        }
        return iArr;
    }

    /* access modifiers changed from: 0000 */
    public int a(int i, int i2) {
        if (this.i != 1 || !isLayoutRTL()) {
            return this.c[i2 + i] - this.c[i];
        }
        return this.c[this.b - i] - this.c[(this.b - i) - i2];
    }

    /* access modifiers changed from: 0000 */
    public void a(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
        super.a(recycler, state, anchorInfo, i);
        i();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            b(recycler, state, anchorInfo, i);
        }
        j();
    }

    private void j() {
        if (this.d == null || this.d.length != this.b) {
            this.d = new View[this.b];
        }
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        i();
        j();
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        i();
        j();
        return super.scrollVerticallyBy(i, recycler, state);
    }

    private void b(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
        boolean z = i == 1;
        int b2 = b(recycler, state, anchorInfo.b);
        if (z) {
            while (b2 > 0 && anchorInfo.b > 0) {
                anchorInfo.b--;
                b2 = b(recycler, state, anchorInfo.b);
            }
            return;
        }
        int itemCount = state.getItemCount() - 1;
        int i2 = anchorInfo.b;
        while (i2 < itemCount) {
            int i3 = i2 + 1;
            int b3 = b(recycler, state, i3);
            if (b3 <= b2) {
                break;
            }
            i2 = i3;
            b2 = b3;
        }
        anchorInfo.b = i2;
    }

    /* access modifiers changed from: 0000 */
    public View a(Recycler recycler, State state, int i, int i2, int i3) {
        a();
        int startAfterPadding = this.j.getStartAfterPadding();
        int endAfterPadding = this.j.getEndAfterPadding();
        int i4 = i2 > i ? 1 : -1;
        View view = null;
        View view2 = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < i3 && b(recycler, state, position) == 0) {
                if (((android.support.v7.widget.RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else if (this.j.getDecoratedStart(childAt) < endAfterPadding && this.j.getDecoratedEnd(childAt) >= startAfterPadding) {
                    return childAt;
                } else {
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            i += i4;
        }
        if (view == null) {
            view = view2;
        }
        return view;
    }

    private int a(Recycler recycler, State state, int i) {
        if (!state.isPreLayout()) {
            return this.g.getSpanGroupIndex(i, this.b);
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.g.getSpanGroupIndex(convertPreLayoutPositionToPostLayout, this.b);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot find span size for pre layout position. ");
        sb.append(i);
        Log.w("GridLayoutManager", sb.toString());
        return 0;
    }

    private int b(Recycler recycler, State state, int i) {
        if (!state.isPreLayout()) {
            return this.g.a(i, this.b);
        }
        int i2 = this.f.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.g.a(convertPreLayoutPositionToPostLayout, this.b);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
        sb.append(i);
        Log.w("GridLayoutManager", sb.toString());
        return 0;
    }

    private int c(Recycler recycler, State state, int i) {
        if (!state.isPreLayout()) {
            return this.g.getSpanSize(i);
        }
        int i2 = this.e.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.g.getSpanSize(convertPreLayoutPositionToPostLayout);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
        sb.append(i);
        Log.w("GridLayoutManager", sb.toString());
        return 1;
    }

    /* access modifiers changed from: 0000 */
    public void a(State state, LayoutState layoutState, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i = this.b;
        for (int i2 = 0; i2 < this.b && layoutState.a(state) && i > 0; i2++) {
            int i3 = layoutState.d;
            layoutPrefetchRegistry.addPosition(i3, Math.max(0, layoutState.g));
            i -= this.g.getSpanSize(i3);
            layoutState.d += layoutState.e;
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(Recycler recycler, State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z;
        Recycler recycler2 = recycler;
        State state2 = state;
        LayoutState layoutState2 = layoutState;
        LayoutChunkResult layoutChunkResult2 = layoutChunkResult;
        int modeInOther = this.j.getModeInOther();
        boolean z2 = modeInOther != 1073741824;
        int i9 = getChildCount() > 0 ? this.c[this.b] : 0;
        if (z2) {
            i();
        }
        boolean z3 = layoutState2.e == 1;
        int i10 = this.b;
        if (!z3) {
            i10 = b(recycler2, state2, layoutState2.d) + c(recycler2, state2, layoutState2.d);
        }
        int i11 = 0;
        int i12 = 0;
        while (i12 < this.b && layoutState2.a(state2) && i10 > 0) {
            int i13 = layoutState2.d;
            int c2 = c(recycler2, state2, i13);
            if (c2 > this.b) {
                StringBuilder sb = new StringBuilder();
                sb.append("Item at position ");
                sb.append(i13);
                sb.append(" requires ");
                sb.append(c2);
                sb.append(" spans but GridLayoutManager has only ");
                sb.append(this.b);
                sb.append(" spans.");
                throw new IllegalArgumentException(sb.toString());
            }
            i10 -= c2;
            if (i10 < 0) {
                break;
            }
            View a2 = layoutState2.a(recycler2);
            if (a2 == null) {
                break;
            }
            i11 += c2;
            this.d[i12] = a2;
            i12++;
        }
        if (i12 == 0) {
            layoutChunkResult2.mFinished = true;
            return;
        }
        float f2 = BitmapDescriptorFactory.HUE_RED;
        int i14 = i12;
        a(recycler2, state2, i12, i11, z3);
        int i15 = 0;
        for (int i16 = 0; i16 < i14; i16++) {
            View view = this.d[i16];
            if (layoutState2.k != null) {
                z = false;
                if (z3) {
                    addDisappearingView(view);
                } else {
                    addDisappearingView(view, 0);
                }
            } else if (z3) {
                addView(view);
                z = false;
            } else {
                z = false;
                addView(view, 0);
            }
            calculateItemDecorationsForChild(view, this.h);
            a(view, modeInOther, z);
            int decoratedMeasurement = this.j.getDecoratedMeasurement(view);
            if (decoratedMeasurement > i15) {
                i15 = decoratedMeasurement;
            }
            float decoratedMeasurementInOther = (((float) this.j.getDecoratedMeasurementInOther(view)) * 1.0f) / ((float) ((LayoutParams) view.getLayoutParams()).b);
            if (decoratedMeasurementInOther > f2) {
                f2 = decoratedMeasurementInOther;
            }
        }
        if (z2) {
            a(f2, i9);
            i15 = 0;
            for (int i17 = 0; i17 < i14; i17++) {
                View view2 = this.d[i17];
                a(view2, 1073741824, true);
                int decoratedMeasurement2 = this.j.getDecoratedMeasurement(view2);
                if (decoratedMeasurement2 > i15) {
                    i15 = decoratedMeasurement2;
                }
            }
        }
        for (int i18 = 0; i18 < i14; i18++) {
            View view3 = this.d[i18];
            if (this.j.getDecoratedMeasurement(view3) != i15) {
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                Rect rect = layoutParams.d;
                int i19 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
                int i20 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
                int a3 = a(layoutParams.a, layoutParams.b);
                if (this.i == 1) {
                    int childMeasureSpec = getChildMeasureSpec(a3, 1073741824, i20, layoutParams.width, false);
                    i8 = MeasureSpec.makeMeasureSpec(i15 - i19, 1073741824);
                    i7 = childMeasureSpec;
                } else {
                    i7 = MeasureSpec.makeMeasureSpec(i15 - i20, 1073741824);
                    i8 = getChildMeasureSpec(a3, 1073741824, i19, layoutParams.height, false);
                }
                a(view3, i7, i8, true);
            }
        }
        int i21 = 0;
        layoutChunkResult2.mConsumed = i15;
        if (this.i == 1) {
            if (layoutState2.f == -1) {
                int i22 = layoutState2.b;
                i = i22;
                i2 = i22 - i15;
            } else {
                int i23 = layoutState2.b;
                i2 = i23;
                i = i15 + i23;
            }
            i4 = 0;
            i3 = 0;
        } else if (layoutState2.f == -1) {
            int i24 = layoutState2.b;
            i2 = 0;
            i = 0;
            int i25 = i24 - i15;
            i3 = i24;
            i4 = i25;
        } else {
            i4 = layoutState2.b;
            i3 = i15 + i4;
            i2 = 0;
            i = 0;
        }
        while (i21 < i14) {
            View view4 = this.d[i21];
            LayoutParams layoutParams2 = (LayoutParams) view4.getLayoutParams();
            if (this.i != 1) {
                i2 = getPaddingTop() + this.c[layoutParams2.a];
                i = this.j.getDecoratedMeasurementInOther(view4) + i2;
            } else if (isLayoutRTL()) {
                int paddingLeft = getPaddingLeft() + this.c[this.b - layoutParams2.a];
                i5 = paddingLeft;
                i6 = paddingLeft - this.j.getDecoratedMeasurementInOther(view4);
                int i26 = i2;
                int i27 = i;
                layoutDecoratedWithMargins(view4, i6, i26, i5, i27);
                if (!layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                    layoutChunkResult2.mIgnoreConsumed = true;
                }
                layoutChunkResult2.mFocusable |= view4.hasFocusable();
                i21++;
                i4 = i6;
                i2 = i26;
                i3 = i5;
                i = i27;
            } else {
                i4 = getPaddingLeft() + this.c[layoutParams2.a];
                i3 = this.j.getDecoratedMeasurementInOther(view4) + i4;
            }
            i6 = i4;
            i5 = i3;
            int i262 = i2;
            int i272 = i;
            layoutDecoratedWithMargins(view4, i6, i262, i5, i272);
            if (!layoutParams2.isItemRemoved()) {
            }
            layoutChunkResult2.mIgnoreConsumed = true;
            layoutChunkResult2.mFocusable |= view4.hasFocusable();
            i21++;
            i4 = i6;
            i2 = i262;
            i3 = i5;
            i = i272;
        }
        Arrays.fill(this.d, null);
    }

    private void a(View view, int i, boolean z) {
        int i2;
        int i3;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = layoutParams.d;
        int i4 = rect.top + rect.bottom + layoutParams.topMargin + layoutParams.bottomMargin;
        int i5 = rect.left + rect.right + layoutParams.leftMargin + layoutParams.rightMargin;
        int a2 = a(layoutParams.a, layoutParams.b);
        if (this.i == 1) {
            i2 = getChildMeasureSpec(a2, i, i5, layoutParams.width, false);
            i3 = getChildMeasureSpec(this.j.getTotalSpace(), getHeightMode(), i4, layoutParams.height, true);
        } else {
            int childMeasureSpec = getChildMeasureSpec(a2, i, i4, layoutParams.height, false);
            int childMeasureSpec2 = getChildMeasureSpec(this.j.getTotalSpace(), getWidthMode(), i5, layoutParams.width, true);
            i3 = childMeasureSpec;
            i2 = childMeasureSpec2;
        }
        a(view, i2, i3, z);
    }

    private void a(float f2, int i) {
        b(Math.max(Math.round(f2 * ((float) this.b)), i));
    }

    private void a(View view, int i, int i2, boolean z) {
        boolean z2;
        android.support.v7.widget.RecyclerView.LayoutParams layoutParams = (android.support.v7.widget.RecyclerView.LayoutParams) view.getLayoutParams();
        if (z) {
            z2 = a(view, i, i2, layoutParams);
        } else {
            z2 = b(view, i, i2, layoutParams);
        }
        if (z2) {
            view.measure(i, i2);
        }
    }

    private void a(Recycler recycler, State state, int i, int i2, boolean z) {
        int i3;
        int i4;
        int i5 = -1;
        int i6 = 0;
        if (z) {
            i5 = i;
            i4 = 0;
            i3 = 1;
        } else {
            i4 = i - 1;
            i3 = -1;
        }
        while (i4 != i5) {
            View view = this.d[i4];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.b = c(recycler, state, getPosition(view));
            layoutParams.a = i6;
            i6 += layoutParams.b;
            i4 += i3;
        }
    }

    public int getSpanCount() {
        return this.b;
    }

    public void setSpanCount(int i) {
        if (i != this.b) {
            this.a = true;
            if (i < 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("Span count should be at least 1. Provided ");
                sb.append(i);
                throw new IllegalArgumentException(sb.toString());
            }
            this.b = i;
            this.g.invalidateSpanIndexCache();
            requestLayout();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00d8, code lost:
        if (r13 == (r2 > r8)) goto L_0x00b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00f5, code lost:
        if (r13 == r10) goto L_0x00bb;
     */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0104  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View onFocusSearchFailed(android.view.View r27, int r28, android.support.v7.widget.RecyclerView.Recycler r29, android.support.v7.widget.RecyclerView.State r30) {
        /*
            r26 = this;
            r0 = r26
            r1 = r29
            r2 = r30
            android.view.View r3 = r26.findContainingItemView(r27)
            r4 = 0
            if (r3 != 0) goto L_0x000e
            return r4
        L_0x000e:
            android.view.ViewGroup$LayoutParams r5 = r3.getLayoutParams()
            android.support.v7.widget.GridLayoutManager$LayoutParams r5 = (android.support.v7.widget.GridLayoutManager.LayoutParams) r5
            int r6 = r5.a
            int r7 = r5.a
            int r5 = r5.b
            int r7 = r7 + r5
            android.view.View r5 = super.onFocusSearchFailed(r27, r28, r29, r30)
            if (r5 != 0) goto L_0x0022
            return r4
        L_0x0022:
            r5 = r28
            int r5 = r0.a(r5)
            r9 = 1
            if (r5 != r9) goto L_0x002d
            r5 = 1
            goto L_0x002e
        L_0x002d:
            r5 = 0
        L_0x002e:
            boolean r10 = r0.k
            if (r5 == r10) goto L_0x0034
            r5 = 1
            goto L_0x0035
        L_0x0034:
            r5 = 0
        L_0x0035:
            r10 = -1
            if (r5 == 0) goto L_0x0040
            int r5 = r26.getChildCount()
            int r5 = r5 - r9
            r11 = -1
            r12 = -1
            goto L_0x0047
        L_0x0040:
            int r5 = r26.getChildCount()
            r11 = r5
            r5 = 0
            r12 = 1
        L_0x0047:
            int r13 = r0.i
            if (r13 != r9) goto L_0x0053
            boolean r13 = r26.isLayoutRTL()
            if (r13 == 0) goto L_0x0053
            r13 = 1
            goto L_0x0054
        L_0x0053:
            r13 = 0
        L_0x0054:
            int r14 = r0.a(r1, r2, r5)
            r10 = r4
            r8 = -1
            r15 = 0
            r17 = 0
            r18 = -1
        L_0x005f:
            if (r5 == r11) goto L_0x0146
            int r9 = r0.a(r1, r2, r5)
            android.view.View r1 = r0.getChildAt(r5)
            if (r1 != r3) goto L_0x006d
            goto L_0x0146
        L_0x006d:
            boolean r20 = r1.hasFocusable()
            if (r20 == 0) goto L_0x0087
            if (r9 == r14) goto L_0x0087
            if (r4 == 0) goto L_0x0079
            goto L_0x0146
        L_0x0079:
            r21 = r3
            r23 = r8
            r24 = r10
            r22 = r11
            r8 = r17
            r11 = r18
            goto L_0x0132
        L_0x0087:
            android.view.ViewGroup$LayoutParams r9 = r1.getLayoutParams()
            android.support.v7.widget.GridLayoutManager$LayoutParams r9 = (android.support.v7.widget.GridLayoutManager.LayoutParams) r9
            int r2 = r9.a
            r21 = r3
            int r3 = r9.a
            r22 = r11
            int r11 = r9.b
            int r3 = r3 + r11
            boolean r11 = r1.hasFocusable()
            if (r11 == 0) goto L_0x00a3
            if (r2 != r6) goto L_0x00a3
            if (r3 != r7) goto L_0x00a3
            return r1
        L_0x00a3:
            boolean r11 = r1.hasFocusable()
            if (r11 == 0) goto L_0x00ab
            if (r4 == 0) goto L_0x00b3
        L_0x00ab:
            boolean r11 = r1.hasFocusable()
            if (r11 != 0) goto L_0x00be
            if (r10 != 0) goto L_0x00be
        L_0x00b3:
            r23 = r8
            r24 = r10
            r8 = r17
        L_0x00b9:
            r11 = r18
        L_0x00bb:
            r19 = 1
            goto L_0x0102
        L_0x00be:
            int r11 = java.lang.Math.max(r2, r6)
            int r20 = java.lang.Math.min(r3, r7)
            int r11 = r20 - r11
            boolean r20 = r1.hasFocusable()
            if (r20 == 0) goto L_0x00db
            if (r11 <= r15) goto L_0x00d1
            goto L_0x00b3
        L_0x00d1:
            if (r11 != r15) goto L_0x00f8
            if (r2 <= r8) goto L_0x00d7
            r11 = 1
            goto L_0x00d8
        L_0x00d7:
            r11 = 0
        L_0x00d8:
            if (r13 != r11) goto L_0x00f8
            goto L_0x00b3
        L_0x00db:
            if (r4 != 0) goto L_0x00f8
            r23 = r8
            r24 = r10
            r8 = 1
            r10 = 0
            boolean r16 = r0.isViewPartiallyVisible(r1, r10, r8)
            if (r16 == 0) goto L_0x00fc
            r8 = r17
            if (r11 <= r8) goto L_0x00ee
            goto L_0x00b9
        L_0x00ee:
            if (r11 != r8) goto L_0x00fe
            r11 = r18
            if (r2 <= r11) goto L_0x00f5
            r10 = 1
        L_0x00f5:
            if (r13 != r10) goto L_0x0100
            goto L_0x00bb
        L_0x00f8:
            r23 = r8
            r24 = r10
        L_0x00fc:
            r8 = r17
        L_0x00fe:
            r11 = r18
        L_0x0100:
            r19 = 0
        L_0x0102:
            if (r19 == 0) goto L_0x0132
            boolean r10 = r1.hasFocusable()
            if (r10 == 0) goto L_0x011f
            int r4 = r9.a
            int r3 = java.lang.Math.min(r3, r7)
            int r2 = java.lang.Math.max(r2, r6)
            int r3 = r3 - r2
            r15 = r3
            r17 = r8
            r18 = r11
            r10 = r24
            r8 = r4
            r4 = r1
            goto L_0x013a
        L_0x011f:
            int r8 = r9.a
            int r3 = java.lang.Math.min(r3, r7)
            int r2 = java.lang.Math.max(r2, r6)
            int r3 = r3 - r2
            r10 = r1
            r17 = r3
            r18 = r8
            r8 = r23
            goto L_0x013a
        L_0x0132:
            r17 = r8
            r18 = r11
            r8 = r23
            r10 = r24
        L_0x013a:
            int r5 = r5 + r12
            r3 = r21
            r11 = r22
            r1 = r29
            r2 = r30
            r9 = 1
            goto L_0x005f
        L_0x0146:
            r24 = r10
            if (r4 == 0) goto L_0x014c
            r24 = r4
        L_0x014c:
            return r24
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.GridLayoutManager.onFocusSearchFailed(android.view.View, int, android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State):android.view.View");
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.n == null && !this.a;
    }
}
