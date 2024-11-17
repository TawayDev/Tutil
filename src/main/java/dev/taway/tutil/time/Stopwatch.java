package dev.taway.tutil.time;

import dev.taway.tutil.RuntimeConfig;
import dev.taway.tutil.format.TimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Stopwatch {
    long startNano;
    long endNano;
    long elapsedNano;

    public void start() {
        startNano = System.nanoTime();
    }

    public void stop() {
        endNano = System.nanoTime();
        elapsedNano = endNano - startNano;
    }

    public String getFormattedTime() {
        return TimeFormatter.formatTime(elapsedNano);
    }

    public String getFormattedTime(String format) {
        return this.getFormattedTime(format, RuntimeConfig.TIME.TIME_ZONE);
    }

    public String getFormattedTime(String format, String timezone) {
        return TimeFormatter.formatTime(elapsedNano, format, timezone);
    }

    public long getElapsedMillis() {
        return elapsedNano / 1_000_000;
    }
}
