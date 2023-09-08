package dev.taway.logging;

import dev.taway.RuntimeConfig;
import dev.taway.file.File;
import dev.taway.time.Formatter;

public class Logger {
    String className;
    File file;

    /**
     * Creates a logger object for the specified class. Logs into a file which is specified in {@link dev.taway.RuntimeConfig.LOGGING}.
     * @param className Class name for which this logger exists.
     * @since 0.1.1
     */
    public Logger(String className) {
        this.className = className;
        file = new File(RuntimeConfig.LOGGING.logFilePath);
    }

    /**
     * Creates a logger object for the specified class.
     * @param className Class name for which this logger exists.
     * @param logPath Custom logging file path.
     * @since 0.1.1
     */
    public Logger(String className, String logPath) {
        this.className = className;
        file = new File(logPath);
    }

    /**
     * Logs to console and to a file. You can customise console and file logging format in {@link dev.taway.RuntimeConfig.LOGGING}
     * @param logLevel Severity of the log.
     * @param method Method from which this method was called from.
     * @param text Message to be logged.
     */
    public void log(LogLevel logLevel, String method, String text) {
        toFile(logLevel, method, text);
        toConsole(logLevel, method, text);
        if (logLevel.NAME.equals("FATAL") && RuntimeConfig.LOGGING.exitOnFatal) System.exit(-1);
    }

    private void toFile(LogLevel logLevel, String method, String text) {
        if (logLevel.LEVEL < RuntimeConfig.LOGGING.dontLogToFileBelowLevel) return;
        file.append(RuntimeConfig.LOGGING.fileLogFormat
                .replace("{TIME}", Formatter.formatTime(System.currentTimeMillis()))
                .replace("{LEVEL}", logLevel.NAME)
                .replace("{CLASS}", className)
                .replace("{METHOD}", method)
                .replace("{MESSAGE}", text)
        , true);
    }

    private void toConsole(LogLevel logLevel, String method, String text) {
        if (logLevel.LEVEL < RuntimeConfig.LOGGING.dontLogToConsoleBelowLevel) return;
        System.out.println(
                RuntimeConfig.LOGGING.consoleLogFormat
                        .replace("{TIME}", Formatter.formatTime(System.currentTimeMillis()))
                        .replace("{LEVEL}", logLevel.COLOR + logLevel.NAME + ConsoleColor.RESET.COLOR)
                        .replace("{CLASS}", className)
                        .replace("{METHOD}", method)
                        .replace("{MESSAGE}", text)
        );
    }
}
