package org.joda.time;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;
import org.joda.convert.FromString;
import org.joda.time.base.BaseDateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.field.AbstractReadableInstantFieldProperty;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public final class DateTime extends BaseDateTime implements Serializable, ReadableDateTime {
    private static final long serialVersionUID = -5171125899451703815L;

    public static final class Property extends AbstractReadableInstantFieldProperty {
        private static final long serialVersionUID = -6983323811635733510L;
        private DateTime a;
        private DateTimeField b;

        Property(DateTime dateTime, DateTimeField dateTimeField) {
            this.a = dateTime;
            this.b = dateTimeField;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) {
            objectOutputStream.writeObject(this.a);
            objectOutputStream.writeObject(this.b.getType());
        }

        private void readObject(ObjectInputStream objectInputStream) {
            this.a = (DateTime) objectInputStream.readObject();
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

        public DateTime getDateTime() {
            return this.a;
        }

        public DateTime addToCopy(int i) {
            return this.a.withMillis(this.b.add(this.a.getMillis(), i));
        }

        public DateTime addToCopy(long j) {
            return this.a.withMillis(this.b.add(this.a.getMillis(), j));
        }

        public DateTime addWrapFieldToCopy(int i) {
            return this.a.withMillis(this.b.addWrapField(this.a.getMillis(), i));
        }

        public DateTime setCopy(int i) {
            return this.a.withMillis(this.b.set(this.a.getMillis(), i));
        }

        public DateTime setCopy(String str, Locale locale) {
            return this.a.withMillis(this.b.set(this.a.getMillis(), str, locale));
        }

        public DateTime setCopy(String str) {
            return setCopy(str, null);
        }

        public DateTime withMaximumValue() {
            try {
                return setCopy(getMaximumValue());
            } catch (RuntimeException e) {
                if (IllegalInstantException.isIllegalInstant(e)) {
                    return new DateTime(getChronology().getZone().previousTransition(getMillis() + 86400000), getChronology());
                }
                throw e;
            }
        }

        public DateTime withMinimumValue() {
            try {
                return setCopy(getMinimumValue());
            } catch (RuntimeException e) {
                if (IllegalInstantException.isIllegalInstant(e)) {
                    return new DateTime(getChronology().getZone().nextTransition(getMillis() - 86400000), getChronology());
                }
                throw e;
            }
        }

        public DateTime roundFloorCopy() {
            return this.a.withMillis(this.b.roundFloor(this.a.getMillis()));
        }

        public DateTime roundCeilingCopy() {
            return this.a.withMillis(this.b.roundCeiling(this.a.getMillis()));
        }

        public DateTime roundHalfFloorCopy() {
            return this.a.withMillis(this.b.roundHalfFloor(this.a.getMillis()));
        }

        public DateTime roundHalfCeilingCopy() {
            return this.a.withMillis(this.b.roundHalfCeiling(this.a.getMillis()));
        }

        public DateTime roundHalfEvenCopy() {
            return this.a.withMillis(this.b.roundHalfEven(this.a.getMillis()));
        }
    }

    public DateTime toDateTime() {
        return this;
    }

    public static DateTime now() {
        return new DateTime();
    }

    public static DateTime now(DateTimeZone dateTimeZone) {
        if (dateTimeZone != null) {
            return new DateTime(dateTimeZone);
        }
        throw new NullPointerException("Zone must not be null");
    }

    public static DateTime now(Chronology chronology) {
        if (chronology != null) {
            return new DateTime(chronology);
        }
        throw new NullPointerException("Chronology must not be null");
    }

    @FromString
    public static DateTime parse(String str) {
        return parse(str, ISODateTimeFormat.dateTimeParser().withOffsetParsed());
    }

    public static DateTime parse(String str, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.parseDateTime(str);
    }

    public DateTime() {
    }

    public DateTime(DateTimeZone dateTimeZone) {
        super(dateTimeZone);
    }

    public DateTime(Chronology chronology) {
        super(chronology);
    }

    public DateTime(long j) {
        super(j);
    }

    public DateTime(long j, DateTimeZone dateTimeZone) {
        super(j, dateTimeZone);
    }

    public DateTime(long j, Chronology chronology) {
        super(j, chronology);
    }

    public DateTime(Object obj) {
        super(obj, (Chronology) null);
    }

    public DateTime(Object obj, DateTimeZone dateTimeZone) {
        super(obj, dateTimeZone);
    }

    public DateTime(Object obj, Chronology chronology) {
        super(obj, DateTimeUtils.getChronology(chronology));
    }

    public DateTime(int i, int i2, int i3, int i4, int i5) {
        super(i, i2, i3, i4, i5, 0, 0);
    }

    public DateTime(int i, int i2, int i3, int i4, int i5, DateTimeZone dateTimeZone) {
        super(i, i2, i3, i4, i5, 0, 0, dateTimeZone);
    }

    public DateTime(int i, int i2, int i3, int i4, int i5, Chronology chronology) {
        super(i, i2, i3, i4, i5, 0, 0, chronology);
    }

    public DateTime(int i, int i2, int i3, int i4, int i5, int i6) {
        super(i, i2, i3, i4, i5, i6, 0);
    }

    public DateTime(int i, int i2, int i3, int i4, int i5, int i6, DateTimeZone dateTimeZone) {
        super(i, i2, i3, i4, i5, i6, 0, dateTimeZone);
    }

    public DateTime(int i, int i2, int i3, int i4, int i5, int i6, Chronology chronology) {
        super(i, i2, i3, i4, i5, i6, 0, chronology);
    }

    public DateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        super(i, i2, i3, i4, i5, i6, i7);
    }

    public DateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, DateTimeZone dateTimeZone) {
        super(i, i2, i3, i4, i5, i6, i7, dateTimeZone);
    }

    public DateTime(int i, int i2, int i3, int i4, int i5, int i6, int i7, Chronology chronology) {
        super(i, i2, i3, i4, i5, i6, i7, chronology);
    }

    public DateTime toDateTimeISO() {
        if (getChronology() == ISOChronology.getInstance()) {
            return this;
        }
        return super.toDateTimeISO();
    }

    public DateTime toDateTime(DateTimeZone dateTimeZone) {
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        if (getZone() == zone) {
            return this;
        }
        return super.toDateTime(zone);
    }

    public DateTime toDateTime(Chronology chronology) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        if (getChronology() == chronology2) {
            return this;
        }
        return super.toDateTime(chronology2);
    }

    public DateTime withMillis(long j) {
        return j == getMillis() ? this : new DateTime(j, getChronology());
    }

    public DateTime withChronology(Chronology chronology) {
        Chronology chronology2 = DateTimeUtils.getChronology(chronology);
        return chronology2 == getChronology() ? this : new DateTime(getMillis(), chronology2);
    }

    public DateTime withZone(DateTimeZone dateTimeZone) {
        return withChronology(getChronology().withZone(dateTimeZone));
    }

    public DateTime withZoneRetainFields(DateTimeZone dateTimeZone) {
        DateTimeZone zone = DateTimeUtils.getZone(dateTimeZone);
        DateTimeZone zone2 = DateTimeUtils.getZone(getZone());
        if (zone == zone2) {
            return this;
        }
        return new DateTime(zone2.getMillisKeepLocal(zone, getMillis()), getChronology().withZone(zone));
    }

    public DateTime withEarlierOffsetAtOverlap() {
        return withMillis(getZone().adjustOffset(getMillis(), false));
    }

    public DateTime withLaterOffsetAtOverlap() {
        return withMillis(getZone().adjustOffset(getMillis(), true));
    }

    public DateTime withDate(int i, int i2, int i3) {
        Chronology chronology = getChronology();
        return withMillis(chronology.getZone().convertLocalToUTC(chronology.withUTC().getDateTimeMillis(i, i2, i3, getMillisOfDay()), false, getMillis()));
    }

    public DateTime withDate(LocalDate localDate) {
        return withDate(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
    }

    public DateTime withTime(int i, int i2, int i3, int i4) {
        Chronology chronology = getChronology();
        return withMillis(chronology.getZone().convertLocalToUTC(chronology.withUTC().getDateTimeMillis(getYear(), getMonthOfYear(), getDayOfMonth(), i, i2, i3, i4), false, getMillis()));
    }

    public DateTime withTime(LocalTime localTime) {
        return withTime(localTime.getHourOfDay(), localTime.getMinuteOfHour(), localTime.getSecondOfMinute(), localTime.getMillisOfSecond());
    }

    public DateTime withTimeAtStartOfDay() {
        return toLocalDate().toDateTimeAtStartOfDay(getZone());
    }

    public DateTime withFields(ReadablePartial readablePartial) {
        return readablePartial == null ? this : withMillis(getChronology().set(readablePartial, getMillis()));
    }

    public DateTime withField(DateTimeFieldType dateTimeFieldType, int i) {
        if (dateTimeFieldType != null) {
            return withMillis(dateTimeFieldType.getField(getChronology()).set(getMillis(), i));
        }
        throw new IllegalArgumentException("Field must not be null");
    }

    public DateTime withFieldAdded(DurationFieldType durationFieldType, int i) {
        if (durationFieldType == null) {
            throw new IllegalArgumentException("Field must not be null");
        } else if (i == 0) {
            return this;
        } else {
            return withMillis(durationFieldType.getField(getChronology()).add(getMillis(), i));
        }
    }

    public DateTime withDurationAdded(long j, int i) {
        return (j == 0 || i == 0) ? this : withMillis(getChronology().add(getMillis(), j, i));
    }

    public DateTime withDurationAdded(ReadableDuration readableDuration, int i) {
        return (readableDuration == null || i == 0) ? this : withDurationAdded(readableDuration.getMillis(), i);
    }

    public DateTime withPeriodAdded(ReadablePeriod readablePeriod, int i) {
        return (readablePeriod == null || i == 0) ? this : withMillis(getChronology().add(readablePeriod, getMillis(), i));
    }

    public DateTime plus(long j) {
        return withDurationAdded(j, 1);
    }

    public DateTime plus(ReadableDuration readableDuration) {
        return withDurationAdded(readableDuration, 1);
    }

    public DateTime plus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, 1);
    }

    public DateTime plusYears(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().years().add(getMillis(), i));
    }

    public DateTime plusMonths(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().months().add(getMillis(), i));
    }

    public DateTime plusWeeks(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().weeks().add(getMillis(), i));
    }

    public DateTime plusDays(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().days().add(getMillis(), i));
    }

    public DateTime plusHours(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().hours().add(getMillis(), i));
    }

    public DateTime plusMinutes(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().minutes().add(getMillis(), i));
    }

    public DateTime plusSeconds(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().seconds().add(getMillis(), i));
    }

    public DateTime plusMillis(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().millis().add(getMillis(), i));
    }

    public DateTime minus(long j) {
        return withDurationAdded(j, -1);
    }

    public DateTime minus(ReadableDuration readableDuration) {
        return withDurationAdded(readableDuration, -1);
    }

    public DateTime minus(ReadablePeriod readablePeriod) {
        return withPeriodAdded(readablePeriod, -1);
    }

    public DateTime minusYears(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().years().subtract(getMillis(), i));
    }

    public DateTime minusMonths(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().months().subtract(getMillis(), i));
    }

    public DateTime minusWeeks(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().weeks().subtract(getMillis(), i));
    }

    public DateTime minusDays(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().days().subtract(getMillis(), i));
    }

    public DateTime minusHours(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().hours().subtract(getMillis(), i));
    }

    public DateTime minusMinutes(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().minutes().subtract(getMillis(), i));
    }

    public DateTime minusSeconds(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().seconds().subtract(getMillis(), i));
    }

    public DateTime minusMillis(int i) {
        if (i == 0) {
            return this;
        }
        return withMillis(getChronology().millis().subtract(getMillis(), i));
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
    public DateMidnight toDateMidnight() {
        return new DateMidnight(getMillis(), getChronology());
    }

    @Deprecated
    public YearMonthDay toYearMonthDay() {
        return new YearMonthDay(getMillis(), getChronology());
    }

    @Deprecated
    public TimeOfDay toTimeOfDay() {
        return new TimeOfDay(getMillis(), getChronology());
    }

    public LocalDateTime toLocalDateTime() {
        return new LocalDateTime(getMillis(), getChronology());
    }

    public LocalDate toLocalDate() {
        return new LocalDate(getMillis(), getChronology());
    }

    public LocalTime toLocalTime() {
        return new LocalTime(getMillis(), getChronology());
    }

    public DateTime withEra(int i) {
        return withMillis(getChronology().era().set(getMillis(), i));
    }

    public DateTime withCenturyOfEra(int i) {
        return withMillis(getChronology().centuryOfEra().set(getMillis(), i));
    }

    public DateTime withYearOfEra(int i) {
        return withMillis(getChronology().yearOfEra().set(getMillis(), i));
    }

    public DateTime withYearOfCentury(int i) {
        return withMillis(getChronology().yearOfCentury().set(getMillis(), i));
    }

    public DateTime withYear(int i) {
        return withMillis(getChronology().year().set(getMillis(), i));
    }

    public DateTime withWeekyear(int i) {
        return withMillis(getChronology().weekyear().set(getMillis(), i));
    }

    public DateTime withMonthOfYear(int i) {
        return withMillis(getChronology().monthOfYear().set(getMillis(), i));
    }

    public DateTime withWeekOfWeekyear(int i) {
        return withMillis(getChronology().weekOfWeekyear().set(getMillis(), i));
    }

    public DateTime withDayOfYear(int i) {
        return withMillis(getChronology().dayOfYear().set(getMillis(), i));
    }

    public DateTime withDayOfMonth(int i) {
        return withMillis(getChronology().dayOfMonth().set(getMillis(), i));
    }

    public DateTime withDayOfWeek(int i) {
        return withMillis(getChronology().dayOfWeek().set(getMillis(), i));
    }

    public DateTime withHourOfDay(int i) {
        return withMillis(getChronology().hourOfDay().set(getMillis(), i));
    }

    public DateTime withMinuteOfHour(int i) {
        return withMillis(getChronology().minuteOfHour().set(getMillis(), i));
    }

    public DateTime withSecondOfMinute(int i) {
        return withMillis(getChronology().secondOfMinute().set(getMillis(), i));
    }

    public DateTime withMillisOfSecond(int i) {
        return withMillis(getChronology().millisOfSecond().set(getMillis(), i));
    }

    public DateTime withMillisOfDay(int i) {
        return withMillis(getChronology().millisOfDay().set(getMillis(), i));
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

    public Property hourOfDay() {
        return new Property(this, getChronology().hourOfDay());
    }

    public Property minuteOfDay() {
        return new Property(this, getChronology().minuteOfDay());
    }

    public Property minuteOfHour() {
        return new Property(this, getChronology().minuteOfHour());
    }

    public Property secondOfDay() {
        return new Property(this, getChronology().secondOfDay());
    }

    public Property secondOfMinute() {
        return new Property(this, getChronology().secondOfMinute());
    }

    public Property millisOfDay() {
        return new Property(this, getChronology().millisOfDay());
    }

    public Property millisOfSecond() {
        return new Property(this, getChronology().millisOfSecond());
    }
}
