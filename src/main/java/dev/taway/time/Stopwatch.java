package dev.taway.time;

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
        return Formatter.formatTime(elapsedMillis);
    }
}
