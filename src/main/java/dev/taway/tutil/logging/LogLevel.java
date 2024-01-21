package dev.taway.tutil.logging;

import dev.taway.tutil.RuntimeConfig;

/**
 * Used by {@link Logger}
 *
 * @see Logger#log(LogLevel, String, String)
 * @see RuntimeConfig RuntimeConfig
 * @since 0.1.1
 */
public enum LogLevel {
    TRACE(0, "TRACE", ConsoleColor.YELLOW_UNDERLINED.COLOR),
    DEBUG(1, "DEBUG", ConsoleColor.WHITE.COLOR),
    INFO(2, "INFO", ""),
    WARN(3, "WARN", ConsoleColor.YELLOW.COLOR),
    ERROR(4, "ERROR", ConsoleColor.RED.COLOR),
    FATAL(5, "FATAL", ConsoleColor.PURPLE.COLOR);

    public final int LEVEL;
    public final String NAME;
    public final String COLOR;

    LogLevel(int LEVEL, String NAME, String COLOR) {
        this.LEVEL = LEVEL;
        this.NAME = NAME;
        this.COLOR = COLOR;
    }
}
