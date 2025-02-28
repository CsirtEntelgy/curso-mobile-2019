package org.joda.time.base;

import java.io.Serializable;
import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInstant;
import org.joda.time.convert.ConverterManager;
import org.joda.time.field.FieldUtils;

public abstract class BaseDuration extends AbstractDuration implements Serializable, ReadableDuration {
    private static final long serialVersionUID = 2581698638990L;
    private volatile long a;

    protected BaseDuration(long j) {
        this.a = j;
    }

    protected BaseDuration(long j, long j2) {
        this.a = FieldUtils.safeSubtract(j2, j);
    }

    protected BaseDuration(ReadableInstant readableInstant, ReadableInstant readableInstant2) {
        if (readableInstant == readableInstant2) {
            this.a = 0;
            return;
        }
        this.a = FieldUtils.safeSubtract(DateTimeUtils.getInstantMillis(readableInstant2), DateTimeUtils.getInstantMillis(readableInstant));
    }

    protected BaseDuration(Object obj) {
        this.a = ConverterManager.getInstance().getDurationConverter(obj).getDurationMillis(obj);
    }

    public long getMillis() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public void setMillis(long j) {
        this.a = j;
    }

    public Period toPeriod(PeriodType periodType) {
        return new Period(getMillis(), periodType);
    }

    public Period toPeriod(Chronology chronology) {
        return new Period(getMillis(), chronology);
    }

    public Period toPeriod(PeriodType periodType, Chronology chronology) {
        return new Period(getMillis(), periodType, chronology);
    }

    public Period toPeriodFrom(ReadableInstant readableInstant) {
        return new Period(readableInstant, (ReadableDuration) this);
    }

    public Period toPeriodFrom(ReadableInstant readableInstant, PeriodType periodType) {
        return new Period(readableInstant, (ReadableDuration) this, periodType);
    }

    public Period toPeriodTo(ReadableInstant readableInstant) {
        return new Period((ReadableDuration) this, readableInstant);
    }

    public Period toPeriodTo(ReadableInstant readableInstant, PeriodType periodType) {
        return new Period((ReadableDuration) this, readableInstant, periodType);
    }

    public Interval toIntervalFrom(ReadableInstant readableInstant) {
        return new Interval(readableInstant, (ReadableDuration) this);
    }

    public Interval toIntervalTo(ReadableInstant readableInstant) {
        return new Interval((ReadableDuration) this, readableInstant);
    }
}
