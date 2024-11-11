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

//region Taway's Silliness
/*
    It may be dumb from the POV of more experienced developers but from my experience
    I want some warns and errors to be treated more like debug messages and like silent errors ?
*/
    DEBUG_WARN(1, "DEBUG WARN", ConsoleColor.YELLOW.COLOR),
    DEBUG_ERROR(1, "DEBUG ERROR", ConsoleColor.RED.COLOR),
    DEBUG_SUCCESS(1, "DEBUG SUCCESS", ConsoleColor.GREEN.COLOR),
    DEBUG_FAILURE(1, "DEBUG FAILURE", ConsoleColor.RED.COLOR),
//endregion
    INFO(2, "INFO", ""),
    SUCCESS(2, "SUCCESS", ConsoleColor.GREEN.COLOR),
    FAILURE(2, "FAILURE", ConsoleColor.RED.COLOR),
    WARN(3, "WARN", ConsoleColor.YELLOW_BRIGHT.COLOR),
    ERROR(4, "ERROR", ConsoleColor.RED_BRIGHT.COLOR),
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
