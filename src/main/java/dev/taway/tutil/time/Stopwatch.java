package dev.taway.tutil.time;

import dev.taway.tutil.RuntimeConfig;
import dev.taway.tutil.format.TimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Stopwatch {
    long startMillis;
    long endMillis;
    long elapsedMillis;

    public void start() {
        startMillis = System.nanoTime();
    }

    public void stop() {
        endMillis = System.nanoTime();
        elapsedMillis = endMillis - startMillis;
    }

    public String getFormattedTime() {
        return TimeFormatter.formatTime(elapsedMillis);
    }

    public String getFormattedTime(String format) {
        return this.getFormattedTime(format, RuntimeConfig.TIME.TIME_ZONE);
    }

    public String getFormattedTime(String format, String timezone) {
        return TimeFormatter.formatTime(elapsedMillis, format, timezone);
    }
}
