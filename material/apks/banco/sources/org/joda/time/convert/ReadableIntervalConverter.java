package org.joda.time.convert;

import org.joda.time.Chronology;
import org.joda.time.DateTimeUtils;
import org.joda.time.ReadWritableInterval;
import org.joda.time.ReadWritablePeriod;
import org.joda.time.ReadableInterval;

class ReadableIntervalConverter extends AbstractConverter implements DurationConverter, IntervalConverter, PeriodConverter {
    static final ReadableIntervalConverter a = new ReadableIntervalConverter();

    public boolean isReadableInterval(Object obj, Chronology chronology) {
        return true;
    }

    protected ReadableIntervalConverter() {
    }

    public long getDurationMillis(Object obj) {
        return ((ReadableInterval) obj).toDurationMillis();
    }

    public void setInto(ReadWritablePeriod readWritablePeriod, Object obj, Chronology chronology) {
        ReadableInterval readableInterval = (ReadableInterval) obj;
        if (chronology == null) {
            chronology = DateTimeUtils.getIntervalChronology(readableInterval);
        }
        int[] iArr = chronology.get(readWritablePeriod, readableInterval.getStartMillis(), readableInterval.getEndMillis());
        for (int i = 0; i < iArr.length; i++) {
            readWritablePeriod.setValue(i, iArr[i]);
        }
    }

    public void setInto(ReadWritableInterval readWritableInterval, Object obj, Chronology chronology) {
        ReadableInterval readableInterval = (ReadableInterval) obj;
        readWritableInterval.setInterval(readableInterval);
        if (chronology != null) {
            readWritableInterval.setChronology(chronology);
        } else {
            readWritableInterval.setChronology(readableInterval.getChronology());
        }
    }

    public Class<?> getSupportedType() {
        return ReadableInterval.class;
    }
}
