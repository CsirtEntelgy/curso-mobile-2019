package org.joda.time.field;

import java.io.Serializable;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;

public abstract class BaseDurationField extends DurationField implements Serializable {
    private static final long serialVersionUID = -2554245107589433218L;
    private final DurationFieldType a;

    public final boolean isSupported() {
        return true;
    }

    protected BaseDurationField(DurationFieldType durationFieldType) {
        if (durationFieldType == null) {
            throw new IllegalArgumentException("The type must not be null");
        }
        this.a = durationFieldType;
    }

    public final DurationFieldType getType() {
        return this.a;
    }

    public final String getName() {
        return this.a.getName();
    }

    public int getValue(long j) {
        return FieldUtils.safeToInt(getValueAsLong(j));
    }

    public long getValueAsLong(long j) {
        return j / getUnitMillis();
    }

    public int getValue(long j, long j2) {
        return FieldUtils.safeToInt(getValueAsLong(j, j2));
    }

    public long getMillis(int i) {
        return ((long) i) * getUnitMillis();
    }

    public long getMillis(long j) {
        return FieldUtils.safeMultiply(j, getUnitMillis());
    }

    public int getDifference(long j, long j2) {
        return FieldUtils.safeToInt(getDifferenceAsLong(j, j2));
    }

    public int compareTo(DurationField durationField) {
        long unitMillis = durationField.getUnitMillis();
        long unitMillis2 = getUnitMillis();
        if (unitMillis2 == unitMillis) {
            return 0;
        }
        return unitMillis2 < unitMillis ? -1 : 1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DurationField[");
        sb.append(getName());
        sb.append(']');
        return sb.toString();
    }
}
