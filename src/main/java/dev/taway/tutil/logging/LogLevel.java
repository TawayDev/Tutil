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
    DEBUG(0, "DEBUG", ConsoleColor.WHITE.COLOR),
    INFO(1, "INFO", ""),
    WARN(2, "WARN", ConsoleColor.YELLOW.COLOR),
    ERROR(3, "ERROR", ConsoleColor.RED.COLOR),
    FATAL(4, "FATAL", ConsoleColor.PURPLE.COLOR);

    public final int LEVEL;
    public final String NAME;
    public final String COLOR;

    LogLevel(int LEVEL, String NAME, String COLOR) {
        this.LEVEL = LEVEL;
        this.NAME = NAME;
        this.COLOR = COLOR;
    }
}
