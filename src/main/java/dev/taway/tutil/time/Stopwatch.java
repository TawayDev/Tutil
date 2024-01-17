package dev.taway.tutil.time;

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
}
