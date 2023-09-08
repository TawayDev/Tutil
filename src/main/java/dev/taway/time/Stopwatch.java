package dev.taway.time;

import dev.taway.RuntimeConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Stopwatch {
    long startMillis;
    long endMillis;
    long elapsedMillis;

    public Stopwatch() {}
    public void start() {
        startMillis = System.nanoTime();
    }

    public void stop() {
        endMillis = System.nanoTime();
        elapsedMillis = endMillis - startMillis;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    public long getElapsedMillis() {
        return elapsedMillis;
    }

    public String getFormattedTime() {
//        Date date = new Date(elapsedMillis);
//        DateFormat formatter = new SimpleDateFormat(RuntimeConfig.STOPWATCH.stopwatchTimeFormat);
//        formatter.setTimeZone(TimeZone.getTimeZone(RuntimeConfig.STOPWATCH.stopwatchTimeZone));
//        return formatter.format(date);
        return Formatter.formatTime(elapsedMillis);
    }
}
