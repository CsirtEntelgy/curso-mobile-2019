package android.support.constraint.solver.widgets;

public class ConstraintHorizontalLayout extends ConstraintWidgetContainer {
    private ContentAlignment V = ContentAlignment.MIDDLE;

    public enum ContentAlignment {
        BEGIN,
        MIDDLE,
        END,
        TOP,
        VERTICAL_MIDDLE,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public ConstraintHorizontalLayout() {
    }

    public ConstraintHorizontalLayout(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
    }

    public ConstraintHorizontalLayout(int i, int i2) {
        super(i, i2);
    }

    /* JADX WARNING: type inference failed for: r4v3 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addToSolver(android.support.constraint.solver.LinearSystem r10) {
        /*
            r9 = this;
            java.util.ArrayList r0 = r9.mChildren
            int r0 = r0.size()
            if (r0 == 0) goto L_0x0069
            r0 = 0
            java.util.ArrayList r1 = r9.mChildren
            int r1 = r1.size()
            r4 = r9
        L_0x0010:
            if (r0 >= r1) goto L_0x0052
            java.util.ArrayList r2 = r9.mChildren
            java.lang.Object r2 = r2.get(r0)
            r8 = r2
            android.support.constraint.solver.widgets.ConstraintWidget r8 = (android.support.constraint.solver.widgets.ConstraintWidget) r8
            if (r4 == r9) goto L_0x002c
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r2 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            r8.connect(r2, r4, r3)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r2 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            r4.connect(r2, r8, r3)
            goto L_0x0040
        L_0x002c:
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r2 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.STRONG
            android.support.constraint.solver.widgets.ConstraintHorizontalLayout$ContentAlignment r3 = r9.V
            android.support.constraint.solver.widgets.ConstraintHorizontalLayout$ContentAlignment r5 = android.support.constraint.solver.widgets.ConstraintHorizontalLayout.ContentAlignment.END
            if (r3 != r5) goto L_0x0036
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r2 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.WEAK
        L_0x0036:
            r7 = r2
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.LEFT
            r6 = 0
            r2 = r8
            r2.connect(r3, r4, r5, r6, r7)
        L_0x0040:
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r2 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.TOP
            r8.connect(r2, r9, r3)
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r2 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.BOTTOM
            r8.connect(r2, r9, r3)
            int r0 = r0 + 1
            r4 = r8
            goto L_0x0010
        L_0x0052:
            if (r4 == r9) goto L_0x0069
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r0 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.STRONG
            android.support.constraint.solver.widgets.ConstraintHorizontalLayout$ContentAlignment r1 = r9.V
            android.support.constraint.solver.widgets.ConstraintHorizontalLayout$ContentAlignment r2 = android.support.constraint.solver.widgets.ConstraintHorizontalLayout.ContentAlignment.BEGIN
            if (r1 != r2) goto L_0x005e
            android.support.constraint.solver.widgets.ConstraintAnchor$Strength r0 = android.support.constraint.solver.widgets.ConstraintAnchor.Strength.WEAK
        L_0x005e:
            r7 = r0
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r3 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            android.support.constraint.solver.widgets.ConstraintAnchor$Type r5 = android.support.constraint.solver.widgets.ConstraintAnchor.Type.RIGHT
            r6 = 0
            r2 = r4
            r4 = r9
            r2.connect(r3, r4, r5, r6, r7)
        L_0x0069:
            super.addToSolver(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintHorizontalLayout.addToSolver(android.support.constraint.solver.LinearSystem):void");
    }
}
