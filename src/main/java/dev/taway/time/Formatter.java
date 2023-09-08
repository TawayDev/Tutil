package dev.taway.time;

import dev.taway.RuntimeConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Formatter {
    public static String formatTime(long timestamp) {
        Date date = new Date(timestamp);
        DateFormat formatter = new SimpleDateFormat(RuntimeConfig.TIME.timeFormat);
        formatter.setTimeZone(TimeZone.getTimeZone(RuntimeConfig.TIME.timeZone));
        return formatter.format(date);
    }
    public static String formatTime(long timestamp, String timeFormat, String timeZone) {
        Date date = new Date(timestamp);
        DateFormat formatter = new SimpleDateFormat(timeFormat);
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        return formatter.format(date);
    }
}
