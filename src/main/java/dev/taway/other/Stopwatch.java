package dev.taway.other;

import dev.taway.RunningConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
        Date date = new Date(elapsedMillis);
        DateFormat formatter = new SimpleDateFormat(RunningConfig.stopwatchTimeFormat);
        formatter.setTimeZone(TimeZone.getTimeZone(RunningConfig.stopwatchTimeZone));
        return formatter.format(date);
    }
}
