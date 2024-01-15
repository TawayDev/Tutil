package dev.taway.tutil.logging;

import dev.taway.tutil.RuntimeConfig;
import dev.taway.tutil.io.file.File;
import dev.taway.tutil.time.Formatter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * Object used for logging to console and or files. Behavior can be configured in {@link RuntimeConfig}
 *
 * @see LogLevel
 * @see RuntimeConfig
 * @since 0.1.1
 */
@Getter
@Setter
public class Logger {
    String className;
    File file;

    boolean forceLogToFile = false;
    boolean forceLogToConsole = false;

    /**
     * Creates a logger object for the specified class. Logs into a file which is specified in {@link RuntimeConfig.LOGGING}.
     *
     * @param className Class name for which this logger exists.
     * @since 0.1.1
     */
    public Logger(String className) {
        this.className = className;
        file = new File(RuntimeConfig.LOGGING.logFilePath);
    }

    /**
     * Creates a logger object for the specified class.
     *
     * @param className Class name for which this logger exists.
     * @param logPath   Custom logging file path.
     * @since 0.1.1
     */
    public Logger(String className, String logPath) {
        this.className = className;
        file = new File(logPath);
    }

    /**
     * Creates a logger object for the specified class.
     *
     * @param className         Name of the class being logged.
     * @param forceLogToFile    Overrides logBelowLevel from RuntimeConfig and saves everything.
     * @param forceLogToConsole Overrides logBelowLevel from RuntimeConfig and prints everything.
     */
    public Logger(String className, boolean forceLogToFile, boolean forceLogToConsole) {
        this(className);
        this.forceLogToFile = forceLogToFile;
        this.forceLogToConsole = forceLogToConsole;
    }

    /**
     * Logs to console and to a file. You can customise console and file logging format in {@link RuntimeConfig.LOGGING}
     *
     * @param logLevel Severity of the log.
     * @param method   Method from which this method was called from.
     * @param text     Message to be logged.
     */
    public void log(LogLevel logLevel, String method, String text) {
        toFile(logLevel, method, text);
        toConsole(logLevel, method, text);
        if (logLevel.NAME.equals("FATAL") && RuntimeConfig.LOGGING.exitOnFatal) System.exit(-1);
    }

    private String prepareMessage(LogLevel logLevel, String method, String text, String color) {
        return RuntimeConfig.LOGGING.consoleLogFormat
                .replace("{TIME}", Formatter.formatTime(System.currentTimeMillis()))
                .replace("{LEVEL}", color + logLevel.NAME + (color.equals("") ? "" : ConsoleColor.RESET.COLOR))
                .replace("{CLASS}", className)
                .replace("{METHOD}", method)
                .replace("{MESSAGE}", text);
    }

    private void toFile(LogLevel logLevel, String method, String text) {
        if (!forceLogToFile && logLevel.LEVEL < RuntimeConfig.LOGGING.dontLogToFileBelowLevel) return;
        try {
            final String message = prepareMessage(logLevel, method, text, "");
            file.append(message, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void toConsole(LogLevel logLevel, String method, String text) {
        if (!forceLogToConsole && logLevel.LEVEL < RuntimeConfig.LOGGING.dontLogToConsoleBelowLevel) return;
        final String message = prepareMessage(logLevel, method, text, logLevel.COLOR);
        System.out.println(message);
    }
}
