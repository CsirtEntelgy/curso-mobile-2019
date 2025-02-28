package org.joda.time.chrono;

import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.ReadablePartial;
import org.joda.time.field.DecoratedDateTimeField;
import org.joda.time.field.FieldUtils;

final class GJYearOfEraDateTimeField extends DecoratedDateTimeField {
    private final BasicChronology a;

    public int getMinimumValue() {
        return 1;
    }

    GJYearOfEraDateTimeField(DateTimeField dateTimeField, BasicChronology basicChronology) {
        super(dateTimeField, DateTimeFieldType.yearOfEra());
        this.a = basicChronology;
    }

    public DurationField getRangeDurationField() {
        return this.a.eras();
    }

    public int get(long j) {
        int i = getWrappedField().get(j);
        return i <= 0 ? 1 - i : i;
    }

    public long add(long j, int i) {
        return getWrappedField().add(j, i);
    }

    public long add(long j, long j2) {
        return getWrappedField().add(j, j2);
    }

    public long addWrapField(long j, int i) {
        return getWrappedField().addWrapField(j, i);
    }

    public int[] addWrapField(ReadablePartial readablePartial, int i, int[] iArr, int i2) {
        return getWrappedField().addWrapField(readablePartial, i, iArr, i2);
    }

    public int getDifference(long j, long j2) {
        return getWrappedField().getDifference(j, j2);
    }

    public long getDifferenceAsLong(long j, long j2) {
        return getWrappedField().getDifferenceAsLong(j, j2);
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, 1, getMaximumValue());
        if (this.a.a(j) <= 0) {
            i = 1 - i;
        }
        return super.set(j, i);
    }

    public int getMaximumValue() {
        return getWrappedField().getMaximumValue();
    }

    public long roundFloor(long j) {
        return getWrappedField().roundFloor(j);
    }

    public long roundCeiling(long j) {
        return getWrappedField().roundCeiling(j);
    }

    public long remainder(long j) {
        return getWrappedField().remainder(j);
    }
}
