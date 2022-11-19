package org.bean.converter.metadata.model;

public class DatePatternMetadata {
    private final String datePattern;
    private final String timeZone;

    public DatePatternMetadata(String datePattern, String timeZone) {
        this.datePattern = datePattern;
        this.timeZone = timeZone;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public String getTimeZone() {
        return timeZone;
    }

    @Override
    public String toString() {
        return "DatePatternMetadata : {" +
                "datePattern='" + datePattern + '\'' +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
