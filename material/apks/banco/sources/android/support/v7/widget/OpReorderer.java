package android.support.v7.widget;

import java.util.List;

class OpReorderer {
    final Callback a;

    interface Callback {
        UpdateOp a(int i, int i2, int i3, Object obj);

        void a(UpdateOp updateOp);
    }

    OpReorderer(Callback callback) {
        this.a = callback;
    }

    /* access modifiers changed from: 0000 */
    public void a(List<UpdateOp> list) {
        while (true) {
            int b = b(list);
            if (b != -1) {
                a(list, b, b + 1);
            } else {
                return;
            }
        }
    }

    private void a(List<UpdateOp> list, int i, int i2) {
        UpdateOp updateOp = (UpdateOp) list.get(i);
        UpdateOp updateOp2 = (UpdateOp) list.get(i2);
        int i3 = updateOp2.a;
        if (i3 != 4) {
            switch (i3) {
                case 1:
                    c(list, i, updateOp, i2, updateOp2);
                    return;
                case 2:
                    a(list, i, updateOp, i2, updateOp2);
                    return;
                default:
                    return;
            }
        } else {
            b(list, i, updateOp, i2, updateOp2);
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0068  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0097  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(java.util.List<android.support.v7.widget.AdapterHelper.UpdateOp> r9, int r10, android.support.v7.widget.AdapterHelper.UpdateOp r11, int r12, android.support.v7.widget.AdapterHelper.UpdateOp r13) {
        /*
            r8 = this;
            int r0 = r11.b
            int r1 = r11.d
            r2 = 0
            r3 = 1
            if (r0 >= r1) goto L_0x001c
            int r0 = r13.b
            int r1 = r11.b
            if (r0 != r1) goto L_0x001a
            int r0 = r13.d
            int r1 = r11.d
            int r4 = r11.b
            int r1 = r1 - r4
            if (r0 != r1) goto L_0x001a
            r0 = 0
        L_0x0018:
            r2 = 1
            goto L_0x002f
        L_0x001a:
            r0 = 0
            goto L_0x002f
        L_0x001c:
            int r0 = r13.b
            int r1 = r11.d
            int r1 = r1 + r3
            if (r0 != r1) goto L_0x002e
            int r0 = r13.d
            int r1 = r11.b
            int r4 = r11.d
            int r1 = r1 - r4
            if (r0 != r1) goto L_0x002e
            r0 = 1
            goto L_0x0018
        L_0x002e:
            r0 = 1
        L_0x002f:
            int r1 = r11.d
            int r4 = r13.b
            r5 = 2
            if (r1 >= r4) goto L_0x003c
            int r1 = r13.b
            int r1 = r1 - r3
            r13.b = r1
            goto L_0x005b
        L_0x003c:
            int r1 = r11.d
            int r4 = r13.b
            int r6 = r13.d
            int r4 = r4 + r6
            if (r1 >= r4) goto L_0x005b
            int r10 = r13.d
            int r10 = r10 - r3
            r13.d = r10
            r11.a = r5
            r11.d = r3
            int r10 = r13.d
            if (r10 != 0) goto L_0x005a
            r9.remove(r12)
            android.support.v7.widget.OpReorderer$Callback r9 = r8.a
            r9.a(r13)
        L_0x005a:
            return
        L_0x005b:
            int r1 = r11.b
            int r4 = r13.b
            r6 = 0
            if (r1 > r4) goto L_0x0068
            int r1 = r13.b
            int r1 = r1 + r3
            r13.b = r1
            goto L_0x0089
        L_0x0068:
            int r1 = r11.b
            int r4 = r13.b
            int r7 = r13.d
            int r4 = r4 + r7
            if (r1 >= r4) goto L_0x0089
            int r1 = r13.b
            int r4 = r13.d
            int r1 = r1 + r4
            int r4 = r11.b
            int r1 = r1 - r4
            android.support.v7.widget.OpReorderer$Callback r4 = r8.a
            int r7 = r11.b
            int r7 = r7 + r3
            android.support.v7.widget.AdapterHelper$UpdateOp r6 = r4.a(r5, r7, r1, r6)
            int r1 = r11.b
            int r3 = r13.b
            int r1 = r1 - r3
            r13.d = r1
        L_0x0089:
            if (r2 == 0) goto L_0x0097
            r9.set(r10, r13)
            r9.remove(r12)
            android.support.v7.widget.OpReorderer$Callback r9 = r8.a
            r9.a(r11)
            return
        L_0x0097:
            if (r0 == 0) goto L_0x00d0
            if (r6 == 0) goto L_0x00b5
            int r0 = r11.b
            int r1 = r6.b
            if (r0 <= r1) goto L_0x00a8
            int r0 = r11.b
            int r1 = r6.d
            int r0 = r0 - r1
            r11.b = r0
        L_0x00a8:
            int r0 = r11.d
            int r1 = r6.b
            if (r0 <= r1) goto L_0x00b5
            int r0 = r11.d
            int r1 = r6.d
            int r0 = r0 - r1
            r11.d = r0
        L_0x00b5:
            int r0 = r11.b
            int r1 = r13.b
            if (r0 <= r1) goto L_0x00c2
            int r0 = r11.b
            int r1 = r13.d
            int r0 = r0 - r1
            r11.b = r0
        L_0x00c2:
            int r0 = r11.d
            int r1 = r13.b
            if (r0 <= r1) goto L_0x0106
            int r0 = r11.d
            int r1 = r13.d
            int r0 = r0 - r1
            r11.d = r0
            goto L_0x0106
        L_0x00d0:
            if (r6 == 0) goto L_0x00ec
            int r0 = r11.b
            int r1 = r6.b
            if (r0 < r1) goto L_0x00df
            int r0 = r11.b
            int r1 = r6.d
            int r0 = r0 - r1
            r11.b = r0
        L_0x00df:
            int r0 = r11.d
            int r1 = r6.b
            if (r0 < r1) goto L_0x00ec
            int r0 = r11.d
            int r1 = r6.d
            int r0 = r0 - r1
            r11.d = r0
        L_0x00ec:
            int r0 = r11.b
            int r1 = r13.b
            if (r0 < r1) goto L_0x00f9
            int r0 = r11.b
            int r1 = r13.d
            int r0 = r0 - r1
            r11.b = r0
        L_0x00f9:
            int r0 = r11.d
            int r1 = r13.b
            if (r0 < r1) goto L_0x0106
            int r0 = r11.d
            int r1 = r13.d
            int r0 = r0 - r1
            r11.d = r0
        L_0x0106:
            r9.set(r10, r13)
            int r13 = r11.b
            int r0 = r11.d
            if (r13 == r0) goto L_0x0113
            r9.set(r12, r11)
            goto L_0x0116
        L_0x0113:
            r9.remove(r12)
        L_0x0116:
            if (r6 == 0) goto L_0x011b
            r9.add(r10, r6)
        L_0x011b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.OpReorderer.a(java.util.List, int, android.support.v7.widget.AdapterHelper$UpdateOp, int, android.support.v7.widget.AdapterHelper$UpdateOp):void");
    }

    private void c(List<UpdateOp> list, int i, UpdateOp updateOp, int i2, UpdateOp updateOp2) {
        int i3 = updateOp.d < updateOp2.b ? -1 : 0;
        if (updateOp.b < updateOp2.b) {
            i3++;
        }
        if (updateOp2.b <= updateOp.b) {
            updateOp.b += updateOp2.d;
        }
        if (updateOp2.b <= updateOp.d) {
            updateOp.d += updateOp2.d;
        }
        updateOp2.b += i3;
        list.set(i, updateOp2);
        list.set(i2, updateOp);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(java.util.List<android.support.v7.widget.AdapterHelper.UpdateOp> r8, int r9, android.support.v7.widget.AdapterHelper.UpdateOp r10, int r11, android.support.v7.widget.AdapterHelper.UpdateOp r12) {
        /*
            r7 = this;
            int r0 = r10.d
            int r1 = r12.b
            r2 = 4
            r3 = 0
            r4 = 1
            if (r0 >= r1) goto L_0x000f
            int r0 = r12.b
            int r0 = r0 - r4
            r12.b = r0
            goto L_0x0028
        L_0x000f:
            int r0 = r10.d
            int r1 = r12.b
            int r5 = r12.d
            int r1 = r1 + r5
            if (r0 >= r1) goto L_0x0028
            int r0 = r12.d
            int r0 = r0 - r4
            r12.d = r0
            android.support.v7.widget.OpReorderer$Callback r0 = r7.a
            int r1 = r10.b
            java.lang.Object r5 = r12.c
            android.support.v7.widget.AdapterHelper$UpdateOp r0 = r0.a(r2, r1, r4, r5)
            goto L_0x0029
        L_0x0028:
            r0 = r3
        L_0x0029:
            int r1 = r10.b
            int r5 = r12.b
            if (r1 > r5) goto L_0x0035
            int r1 = r12.b
            int r1 = r1 + r4
            r12.b = r1
            goto L_0x0056
        L_0x0035:
            int r1 = r10.b
            int r5 = r12.b
            int r6 = r12.d
            int r5 = r5 + r6
            if (r1 >= r5) goto L_0x0056
            int r1 = r12.b
            int r3 = r12.d
            int r1 = r1 + r3
            int r3 = r10.b
            int r1 = r1 - r3
            android.support.v7.widget.OpReorderer$Callback r3 = r7.a
            int r5 = r10.b
            int r5 = r5 + r4
            java.lang.Object r4 = r12.c
            android.support.v7.widget.AdapterHelper$UpdateOp r3 = r3.a(r2, r5, r1, r4)
            int r2 = r12.d
            int r2 = r2 - r1
            r12.d = r2
        L_0x0056:
            r8.set(r11, r10)
            int r10 = r12.d
            if (r10 <= 0) goto L_0x0061
            r8.set(r9, r12)
            goto L_0x0069
        L_0x0061:
            r8.remove(r9)
            android.support.v7.widget.OpReorderer$Callback r10 = r7.a
            r10.a(r12)
        L_0x0069:
            if (r0 == 0) goto L_0x006e
            r8.add(r9, r0)
        L_0x006e:
            if (r3 == 0) goto L_0x0073
            r8.add(r9, r3)
        L_0x0073:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.OpReorderer.b(java.util.List, int, android.support.v7.widget.AdapterHelper$UpdateOp, int, android.support.v7.widget.AdapterHelper$UpdateOp):void");
    }

    private int b(List<UpdateOp> list) {
        boolean z = false;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (((UpdateOp) list.get(size)).a != 8) {
                z = true;
            } else if (z) {
                return size;
            }
        }
        return -1;
    }
}
