package org.joda.time.chrono;

import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;
import org.joda.time.field.PreciseDurationDateTimeField;

final class BasicWeekOfWeekyearDateTimeField extends PreciseDurationDateTimeField {
    private final BasicChronology b;

    public int getMaximumValue() {
        return 53;
    }

    public int getMinimumValue() {
        return 1;
    }

    BasicWeekOfWeekyearDateTimeField(BasicChronology basicChronology, DurationField durationField) {
        super(DateTimeFieldType.weekOfWeekyear(), durationField);
        this.b = basicChronology;
    }

    public int get(long j) {
        return this.b.f(j);
    }

    public DurationField getRangeDurationField() {
        return this.b.weekyears();
    }

    public long roundFloor(long j) {
        return super.roundFloor(j + 259200000) - 259200000;
    }

    public long roundCeiling(long j) {
        return super.roundCeiling(j + 259200000) - 259200000;
    }

    public long remainder(long j) {
        return super.remainder(j + 259200000);
    }

    public int getMaximumValue(long j) {
        return this.b.b(this.b.e(j));
    }

    public int getMaximumValue(ReadablePartial readablePartial) {
        if (!readablePartial.isSupported(DateTimeFieldType.weekyear())) {
            return 53;
        }
        return this.b.b(readablePartial.get(DateTimeFieldType.weekyear()));
    }

    public int getMaximumValue(ReadablePartial readablePartial, int[] iArr) {
        int size = readablePartial.size();
        for (int i = 0; i < size; i++) {
            if (readablePartial.getFieldType(i) == DateTimeFieldType.weekyear()) {
                return this.b.b(iArr[i]);
            }
        }
        return 53;
    }

    /* access modifiers changed from: protected */
    public int getMaximumValueForSet(long j, int i) {
        if (i > 52) {
            return getMaximumValue(j);
        }
        return 52;
    }
}
