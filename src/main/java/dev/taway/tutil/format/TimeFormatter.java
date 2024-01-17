package dev.taway.tutil.format;

import dev.taway.tutil.RuntimeConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeFormatter {
    /**
     * Formats a timestamp according to format and timezone that is defined in {@link RuntimeConfig.TIME}
     *
     * @param timestamp
     * @return Returns inputted timestamp formatted as string.
     * @see System#nanoTime()
     * @since 0.1.1
     */
    public static String formatTime(long timestamp) {
        Date date = new Date(timestamp);
        DateFormat formatter = new SimpleDateFormat(RuntimeConfig.TIME.timeFormat);
        formatter.setTimeZone(TimeZone.getTimeZone(RuntimeConfig.TIME.timeZone));
        return formatter.format(date);
    }

    /**
     * Formats a timestamp according to inputted format and timezone.
     *
     * @param timestamp
     * @param timeFormat Time format (E.g. hh:mm dd.MM.yyyy)
     * @param timeZone   Timezone code (E.g CET)
     * @return Returns inputted timestamp formatted as string.
     * @see System#nanoTime()
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html">Java documentation - SimpleDateFormat</a>
     * @see <a href="https://en.wikipedia.org/wiki/List_of_tz_database_time_zones">Wikipedia - Timezone code names</a>
     * @since 0.1.1
     */
    public static String formatTime(long timestamp, String timeFormat, String timeZone) {
        Date date = new Date(timestamp);
        DateFormat formatter = new SimpleDateFormat(timeFormat);
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        return formatter.format(date);
    }
}
