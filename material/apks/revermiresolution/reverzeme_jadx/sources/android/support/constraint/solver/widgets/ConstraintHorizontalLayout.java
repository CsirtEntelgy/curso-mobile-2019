package android.support.constraint.solver.widgets;

import android.support.constraint.solver.widgets.ConstraintWidget.ContentAlignment;

public class ConstraintHorizontalLayout extends ConstraintWidgetContainer {
    private ContentAlignment mAlignment = ContentAlignment.MIDDLE;

    public ConstraintHorizontalLayout() {
    }

    public ConstraintHorizontalLayout(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public ConstraintHorizontalLayout(int width, int height) {
        super(width, height);
    }

    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addToSolver(android.support.constraint.solver.LinearSystem r15, int r16) {
        /*
            r14 = this;
            java.util.ArrayList r1 = r14.mChildren
            int r1 = r1.size()
            if (r1 == 0) goto L_0x0066
            r2 = r14
            r12 = 0
            java.util.ArrayList r1 = r14.mChildren
            int r13 = r1.size()
        L_0x0010:
            if (r12 >= r13) goto L_0x004f
            java.util.ArrayList r1 = r14.mChildren
            java.lang.Object r0 = r1.get(r12)
            android.support.constraint.solver.widgets.ConstraintWidget r0 = (android.support.constraint.solver.widgets.ConstraintWidget) r0
            if (r2 == r14) goto L_0x003c
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r1 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            r0.connect(r1, r2, r3)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r1 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            r2.connect(r1, r0, r3)
        L_0x002a:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r1 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            r0.connect(r1, r14, r3)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r1 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            r0.connect(r1, r14, r3)
            r2 = r0
            int r12 = r12 + 1
            goto L_0x0010
        L_0x003c:
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.STRONG
            android.support.constraint.solver.widgets.ConstraintWidget$ContentAlignment r1 = r14.mAlignment
            android.support.constraint.solver.widgets.ConstraintWidget$ContentAlignment r3 = android.support.constraint.solver.widgets.ConstraintWidget.ContentAlignment.END
            if (r1 != r3) goto L_0x0046
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.WEAK
        L_0x0046:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r1 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            r4 = 0
            r0.connect(r1, r2, r3, r4, r5)
            goto L_0x002a
        L_0x004f:
            if (r2 == r14) goto L_0x0066
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.STRONG
            android.support.constraint.solver.widgets.ConstraintWidget$ContentAlignment r1 = r14.mAlignment
            android.support.constraint.solver.widgets.ConstraintWidget$ContentAlignment r3 = android.support.constraint.solver.widgets.ConstraintWidget.ContentAlignment.BEGIN
            if (r1 != r3) goto L_0x005b
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.WEAK
        L_0x005b:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r7 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r9 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            r10 = 0
            r6 = r2
            r8 = r14
            r11 = r5
            r6.connect(r7, r8, r9, r10, r11)
        L_0x0066:
            super.addToSolver(r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintHorizontalLayout.addToSolver(android.support.constraint.solver.LinearSystem, int):void");
    }
}
