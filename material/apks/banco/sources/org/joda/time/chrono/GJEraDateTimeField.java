package org.joda.time.chrono;

import java.util.Locale;
import org.joda.time.DateTimeField;
import org.joda.time.DateTimeFieldType;
import org.joda.time.DurationField;
import org.joda.time.DurationFieldType;
import org.joda.time.field.BaseDateTimeField;
import org.joda.time.field.FieldUtils;
import org.joda.time.field.UnsupportedDurationField;

final class GJEraDateTimeField extends BaseDateTimeField {
    private final BasicChronology a;

    public int getMaximumValue() {
        return 1;
    }

    public int getMinimumValue() {
        return 0;
    }

    public DurationField getRangeDurationField() {
        return null;
    }

    public boolean isLenient() {
        return false;
    }

    GJEraDateTimeField(BasicChronology basicChronology) {
        super(DateTimeFieldType.era());
        this.a = basicChronology;
    }

    public int get(long j) {
        return this.a.a(j) <= 0 ? 0 : 1;
    }

    public String getAsText(int i, Locale locale) {
        return GJLocaleSymbols.a(locale).a(i);
    }

    public long set(long j, int i) {
        FieldUtils.verifyValueBounds((DateTimeField) this, i, 0, 1);
        if (get(j) == i) {
            return j;
        }
        return this.a.f(j, -this.a.a(j));
    }

    public long set(long j, String str, Locale locale) {
        return set(j, GJLocaleSymbols.a(locale).a(str));
    }

    public long roundFloor(long j) {
        if (get(j) == 1) {
            return this.a.f(0, 1);
        }
        return Long.MIN_VALUE;
    }

    public long roundCeiling(long j) {
        if (get(j) == 0) {
            return this.a.f(0, 1);
        }
        return Long.MAX_VALUE;
    }

    public long roundHalfFloor(long j) {
        return roundFloor(j);
    }

    public long roundHalfCeiling(long j) {
        return roundFloor(j);
    }

    public long roundHalfEven(long j) {
        return roundFloor(j);
    }

    public DurationField getDurationField() {
        return UnsupportedDurationField.getInstance(DurationFieldType.eras());
    }

    public int getMaximumTextLength(Locale locale) {
        return GJLocaleSymbols.a(locale).a();
    }
}
