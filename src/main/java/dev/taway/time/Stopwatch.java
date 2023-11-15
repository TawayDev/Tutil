package dev.taway.time;

public class Stopwatch {
    long startMillis;
    long endMillis;
    long elapsedMillis;

    public Stopwatch() {
    }

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
