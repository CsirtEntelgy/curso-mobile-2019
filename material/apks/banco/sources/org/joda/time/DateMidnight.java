package org.joda.time;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;
import org.joda.convert.FromString;
import org.joda.time.base.BaseDateTime;
import org.joda.time.field.AbstractReadableInstantFieldProperty;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

@Deprecated
public final class DateMidnight extends BaseDateTime implements Serializable, ReadableDateTime {
    private static final long serialVersionUID = 156371964018738L;

    public static final class Property extends AbstractReadableInstantFieldProperty {
        private static final long serialVersionUID = 257629620;
        private DateMidnight a;
        private DateTimeField b;

        Property(DateMidnight dateMidnight, DateTimeField dateTimeField) {
            this.a = dateMidnight;
            this.b = dateTimeField;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(this.b.getType());
        }

        private void readObject(ObjectInputStream objectInputStream) {
            this.a = (DateMidnight) objectInputStream.readObject();
            this.b = ((DateTimeFieldType) objectInputStream.readObject()).getField(this.a.getChronology());
        }

        public DateTimeField getField() {
            return this.b;
        }

        /* access modifiers changed from: protected */
        public long getMillis() {
            return this.a.getMillis();
        }

        /* access modifiers changed from: protected */
        public Chronology getChronology() {
            return this.a.getChronology();
        }

        public DateMidnight getDateMidnight() {
            return this.a;
        }

        public DateMidnight addToCopy(int i) {
            return this.a.withMillis(this.b.add(this.a.getMillis(), i));
        }

        public DateMidnight addToCopy(long j) {
            return this.a.withMillis(this.b.add(this.a.getMillis(), j));
        }

        public DateMidnight addWrapFieldToCopy(int i) {
            return this.a.withMillis(this.b.addWrapField(this.a.getMillis(), i));
        }

        public DateMidnight setCopy(int i) {
            return this.a.withMillis(this.b.set(this.a.getMillis(), i));
        }

        public DateMidnight setCopy(String str, Locale locale) {
            return this.a.withMillis(this.b.set(this.a.getMillis(), str, locale));
        }

        public DateMidnight setCopy(String str) {
            return setCopy(str, null);
        }

        public DateMidnight withMaximumValue() {
            return setCopy(getMaximumValue());
        }

        public DateMidnight withMinimumValue() {
            return setCopy(getMinimumValue());
        }

        public DateMidnight roundFloorCopy() {
            return this.a.withMillis(this.b.roundFloor(this.a.getMillis()));
        }

        public DateMidnight roundCeilingCopy() {
            return this.a.withMillis(this.b.roundCeiling(this.a.getMillis()));
        }

        public DateMidnight roundHalfFloorCopy() {
            return this.a.withMillis(this.b.roundHalfFloor(this.a.getMillis()));
        }

        public DateMidnight roundHalfCeilingCopy() {
            return this.a.withMillis(this.b.roundHalfCeiling(this.a.getMillis()));
        }

        public DateMidnight roundHalfEvenCopy() {
            return this.a.withMillis(this.b.roundHalfEven(this.a.getMillis()));
        }
    }

    public static DateMidnight now() {
        return new DateMidnight();
    }

    public static DateMidnight now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new DateMidnight(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static DateMidnight now(Chronology chronology) {
        if (chronology != null) {
            return new DateMidnight(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    @FromString
    public static DateMidnight parse(String str) {
        return parse(str, ISODateTimeFormat.dateTimeParser().withOffsetParsed());
    }

    public static DateMidnight parse(String str, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.parseDateTime(str).toDateMidnight();
    }

    public DateMidnight() {
    }

    public DateMidnight(DateTimeZone dateTimeZone) {
        super(dateTimeZone);
    }

    public DateMidnight(Chronology chronology) {
        super(chronology);
    }

    public DateMidnight(long j) {
        super(j);
    }

    public DateMidnight(long j, DateTimeZone dateTimeZone) {
        super(j, dateTimeZone);
    }

    public DateMidnight(long j, Chronology chronology) {
        super(j, chronology);
    }

    public DateMidnight(Object obj) {
        super(obj, (Chronology) null);
    }

    public DateMidnight(Object obj, DateTimeZone dateTimeZone) {
        super(obj, dateTimeZone);
    }

    public DateMidnight(Object obj, Chronology chronology) {
        super(obj, DateTimeUtils.getChronology(chronology));
    }

    public DateMidnight(int i, int i2, int i3) {
        super(i, i2, i3, 0, 0, 0, 0);
    }

    public DateMidnight(int i, int i2, int i3, DateTimeZone dateTimeZone) {
        super(i, i2, i3, 0, 0, 0, 0, dateTimeZone);
    }

    public DateMidnight(int i, int i2, int i3, Chronology chronology) {
        super(i, i2, i3, 0, 0, 0, 0, chronology);
    }

    /* access modifiers changed from: protected */
    public long checkInstant(long j, Chronology chronology) {
        return chronology.dayOfMonth().roundFloor(j);
    }

    public DateMidnight withMillis(long j) {
        Chronology chronology = getChronology();
        long checkInstant = checkInstant(j, chronology);
        return checkInstant == getMillis() ? this : new DateMidnight(checkInstant, chronology);
    }

    public DateMidnight withChronology(Chronology chronology) {
        return chronology == getChronology() ? this : new DateMidnight(getMillis(), chronology);
    }

    public DateMidnight withZoneRetainFields(DateTimeZone dateTimeZone) {
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        DateTimeZone zone2 = DateTimeUtils.getZone(getZone());
        if (zone == zone2) {
            return this;
        }
        return new DateMidnight(zone2.getMillisKeepLocal(zone, getMillis()), getChronology().withZone(zone));
    }

    public DateMidnight withFields(ReadablePartial readablePartial) {
        return readablePartial == null ? this : withMillis(getChronology().set(readablePartial, getMillis()));
    }

    public DateMidnight withField(DateTimeFieldType dateTimeFieldType, int i) {
        if (dateTimeFieldType != null) {
            return withMillis(dateTimeFieldType.getField(getChronology()).set(getMillis(), i));
        }
        throw new IllegalArgumentException("Field must not be null");
    }

    public DateMidnight withFieldAdded(DurationFieldType durationFieldType, int i) {
        if (durationFieldType == null) {
            throw new IllegalArgumentException("Field must not be null");
        } else if (i == 0) {
            return this;
        } else {
            return withMillis(durationFieldType.getField(getChronology()).add(getMillis(), i));
        }
    }

    public DateMidnight withDurationAdded(long j, int i) {
        return (j == 0 || i == 0) ? this : withMillis(getChronology().add(getMillis(), j, i));
    }

    public DateMidnight withDurationAdded(ReadableDuration readableDuration, int i) {
        return (readableDuration == null || i == 0) ? this : withDurationAdded(readableDuration.getMillis(), i);
    }

    public DateMidnight withPeriodAdded(ReadablePeriod readablePeriod, int i) {
        return (readablePeriod == null || i == 0) ? this : withMillis(getChronology().add(readablePeriod, getMillis(), i));
    }

    public DateMidnight plus(long j) {
        return withDurationAdded(j, 1);
    }

    public DateMidnight plus(ReadableDuration readableDuration) {
        return withDurationAdded(readableDuration, 1);
    }

    public DateMidnight plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public DateMidnight plusYears(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().years().add(getMillis(), i));
    }

    public DateMidnight plusMonths(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().months().add(getMillis(), i));
    }

    public DateMidnight plusWeeks(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().weeks().add(getMillis(), i));
    }

    public DateMidnight plusDays(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().days().add(getMillis(), i));
    }

    public DateMidnight minus(long j) {
        return withDurationAdded(j, -1);
    }

    public DateMidnight minus(ReadableDuration readableDuration) {
        return withDurationAdded(readableDuration, -1);
    }

    public DateMidnight minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public DateMidnight minusYears(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().years().subtract(getMillis(), i));
    }

    public DateMidnight minusMonths(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().months().subtract(getMillis(), i));
    }

    public DateMidnight minusWeeks(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().weeks().subtract(getMillis(), i));
    }

    public DateMidnight minusDays(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().days().subtract(getMillis(), i));
    }

    public Property property(DateTimeFieldType dateTimeFieldType) {
        if (dateTimeFieldType == null) {
            throw new IllegalArgumentException("The DateTimeFieldType must not be null");
        }
        DateTimeField field = dateTimeFieldType.getField(getChronology());
        if (field.isSupported()) {
            return new Property(this, field);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Field '");
        sb.append(dateTimeFieldType);
        sb.append("' is not supported");
        throw new IllegalArgumentException(sb.toString());
    }

    @Deprecated
    public YearMonthDay toYearMonthDay() {
        return new YearMonthDay(getMillis(), getChronology());
    }

    public LocalDate toLocalDate() {
        return new LocalDate(getMillis(), getChronology());
    }

    public Interval toInterval() {
        Chronology chronology = getChronology();
        long millis = getMillis();
        Interval interval = new Interval(millis, DurationFieldType.days().getField(chronology).add(millis, 1), chronology);
        return interval;
    }

    public DateMidnight withEra(int i) {
        return withMillis(getChronology().era().set(getMillis(), i));
    }

    public DateMidnight withCenturyOfEra(int i) {
        return withMillis(getChronology().centuryOfEra().set(getMillis(), i));
    }

    public DateMidnight withYearOfEra(int i) {
        return withMillis(getChronology().yearOfEra().set(getMillis(), i));
    }

    public DateMidnight withYearOfCentury(int i) {
        return withMillis(getChronology().yearOfCentury().set(getMillis(), i));
    }

    public DateMidnight withYear(int i) {
        return withMillis(getChronology().year().set(getMillis(), i));
    }

    public DateMidnight withWeekyear(int i) {
        return withMillis(getChronology().weekyear().set(getMillis(), i));
    }

    public DateMidnight withMonthOfYear(int i) {
        return withMillis(getChronology().monthOfYear().set(getMillis(), i));
    }

    public DateMidnight withWeekOfWeekyear(int i) {
        return withMillis(getChronology().weekOfWeekyear().set(getMillis(), i));
    }

    public DateMidnight withDayOfYear(int i) {
        return withMillis(getChronology().dayOfYear().set(getMillis(), i));
    }

    public DateMidnight withDayOfMonth(int i) {
        return withMillis(getChronology().dayOfMonth().set(getMillis(), i));
    }

    public DateMidnight withDayOfWeek(int i) {
        return withMillis(getChronology().dayOfWeek().set(getMillis(), i));
    }

    public Property era() {
        return new Property(this, getChronology().era());
    }

    public Property centuryOfEra() {
        return new Property(this, getChronology().centuryOfEra());
    }

    public Property yearOfCentury() {
        return new Property(this, getChronology().yearOfCentury());
    }

    public Property yearOfEra() {
        return new Property(this, getChronology().yearOfEra());
    }

    public Property year() {
        return new Property(this, getChronology().year());
    }

    public Property weekyear() {
        return new Property(this, getChronology().weekyear());
    }

    public Property monthOfYear() {
        return new Property(this, getChronology().monthOfYear());
    }

    public Property weekOfWeekyear() {
        return new Property(this, getChronology().weekOfWeekyear());
    }

    public Property dayOfYear() {
        return new Property(this, getChronology().dayOfYear());
    }

    public Property dayOfMonth() {
        return new Property(this, getChronology().dayOfMonth());
    }

    public Property dayOfWeek() {
        return new Property(this, getChronology().dayOfWeek());
    }
}
