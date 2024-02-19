package dev.taway.tutil.logging;

import dev.taway.tutil.RuntimeConfig;
import dev.taway.tutil.format.StringFormatter;
import dev.taway.tutil.format.TimeFormatter;
import dev.taway.tutil.io.file.File;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;

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
     * @since 0.1.7
     */
    public Logger() {
        this.className = this.getClassName();
        file = new File(RuntimeConfig.LOGGING.logFilePath);
    }

    /**
     * Creates a logger object for the specified class.
     *
     * @param logPath   Custom logging file path.
     * @since 0.1.7
     */
    public Logger(String logPath) {
        this.className = this.getClassName();
        file = new File(logPath);
    }

    /**
     * Creates a logger object for the specified class.
     *
     * @param forceLogToFile    Overrides logBelowLevel from RuntimeConfig and saves everything.
     * @param forceLogToConsole Overrides logBelowLevel from RuntimeConfig and prints everything.
     */
    public Logger(boolean forceLogToFile, boolean forceLogToConsole) {
        this();
        this.forceLogToFile = forceLogToFile;
        this.forceLogToConsole = forceLogToConsole;
    }

    /**
     * Logs a message with the specified log level, method name, and text.
     * The log is written to a file and printed to the console if configured to do so.
     * If the log level is "FATAL" and the "exitOnFatal" flag is enabled in {@link RuntimeConfig.LOGGING},
     * the program will exit with exit code -1 after the log is written.
     *
     * @param logLevel The log level of the message.
     * @param text     The text of the log message to be logged.
     */
    public void log(LogLevel logLevel, String text) {
        String method = getCurrentMethodCaller();
        toFile(logLevel, method, text);
        toConsole(logLevel, method, text);
        if (logLevel.NAME.equals("FATAL") && RuntimeConfig.LOGGING.exitOnFatal) System.exit(-1);
    }

    /**
     * Logs a trace message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#TRACE}.
     *
     * @param text   The text of the trace message to be logged.
     */
    public void trace(String text) {
        log(LogLevel.TRACE, getCurrentMethodCaller(), text);
    }

    /**
     * Logs a debug message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#DEBUG}.
     *
     * @param text   The text of the debug message to be logged.
     */
    public void debug(String text) {
        log(LogLevel.DEBUG, getCurrentMethodCaller(), text);
    }

    /**
     * Logs an informational message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#INFO}.
     *
     * @param text   The text of the informational message to be logged.
     */
    public void info(String text) {
        log(LogLevel.INFO, getCurrentMethodCaller(), text);
    }

    /**
     * Logs a warning message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#WARN}.
     *
     * @param text   The text of the warning message to be logged.
     */
    public void warn(String text) {
        log(LogLevel.WARN, getCurrentMethodCaller(), text);
    }

    /**
     * Logs an error message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#ERROR}.
     *
     * @param text   The error message to be logged.
     */
    public void error(String text) {
        log(LogLevel.ERROR, getCurrentMethodCaller(), text);
    }

    /**
     * Logs a fatal message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#FATAL}.
     *
     * @param text   The text of the fatal message to be logged.
     */
    public void fatal(String text) {
        log(LogLevel.FATAL, getCurrentMethodCaller(), text);
    }

//region Deprecated
    /**
     * Logs a message with the specified log level, method name, and text.
     * The log is written to a file and printed to the console if configured to do so.
     * If the log level is "FATAL" and the "exitOnFatal" flag is enabled in {@link RuntimeConfig.LOGGING},
     * the program will exit with exit code -1 after the log is written.
     *
     * @param logLevel The log level of the message.
     * @param method   The name of the method from which the log method is called.
     * @param text     The text of the log message to be logged.
     */
    @Deprecated
    public void log(LogLevel logLevel, String method, String text) {
        toFile(logLevel, method, text);
        toConsole(logLevel, method, text);
        if (logLevel.NAME.equals("FATAL") && RuntimeConfig.LOGGING.exitOnFatal) System.exit(-1);
    }

    /**
     * Logs a trace message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#TRACE}.
     *
     * @param method The name of the method from which the logTrace method is called.
     * @param text   The text of the trace message to be logged.
     */
    @Deprecated
    public void trace(String method, String text) {
        log(LogLevel.TRACE, method, text);
    }

    /**
     * Logs a debug message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#DEBUG}.
     *
     * @param method The name of the method from which the logDebug method is called.
     * @param text   The text of the debug message to be logged.
     */
    @Deprecated
    public void debug(String method, String text) {
        log(LogLevel.DEBUG, method, text);
    }

    /**
     * Logs an informational message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#INFO}.
     *
     * @param method The name of the method from which the logInfo method is called.
     * @param text   The text of the informational message to be logged.
     */
    @Deprecated
    public void info(String method, String text) {
        log(LogLevel.INFO, method, text);
    }

    /**
     * Logs a warning message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#WARN}.
     *
     * @param method The name of the method from which the logWarn method is called.
     * @param text   The text of the warning message to be logged.
     */
    @Deprecated
    public void warn(String method, String text) {
        log(LogLevel.WARN, method, text);
    }

    /**
     * Logs an error message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#ERROR}.
     *
     * @param method The name of the method from which the error occurred.
     * @param text   The error message to be logged.
     */
    @Deprecated
    public void error(String method, String text) {
        log(LogLevel.ERROR, method, text);
    }

    /**
     * Logs a fatal message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, String)}
     * method with the log level set to {@link LogLevel#FATAL}.
     *
     * @param method The name of the method from which the logFatal method is called.
     * @param text   The text of the fatal message to be logged.
     */
    @Deprecated
    public void fatal(String method, String text) {
        log(LogLevel.FATAL, method, text);
    }
//endregion


    private String prepareMessage(String format, LogLevel logLevel, String method, String text, String color) {
        HashMap<String, String> logValues = new HashMap<>();
        logValues.put("{TIME}", TimeFormatter.formatTime(
                System.currentTimeMillis(),
                color.equals("") ? RuntimeConfig.LOGGING.fileTimeFormat : RuntimeConfig.LOGGING.consoleTimeFormat,
                RuntimeConfig.TIME.timeZone)
        );
        logValues.put("{LEVEL}", color + logLevel.NAME + (color.equals("") ? "" : ConsoleColor.RESET.COLOR));
        logValues.put("{CLASS}", className);
        logValues.put("{METHOD}", method);
        logValues.put("{MESSAGE}", text);
        return StringFormatter.formatString(format, logValues);
    }

    private void toFile(LogLevel logLevel, String method, String text) {
        if (RuntimeConfig.LOGGING.disableFileLog) return;
        if (!forceLogToFile && logLevel.LEVEL < RuntimeConfig.LOGGING.dontLogToFileBelowLevel.LEVEL) return;
        try {
            final String message = prepareMessage(RuntimeConfig.LOGGING.fileLogFormat, logLevel, method, text, "");
            file.append(message, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void toConsole(LogLevel logLevel, String method, String text) {
        if (RuntimeConfig.LOGGING.disableConsoleLog) return;
        if (!forceLogToConsole && logLevel.LEVEL < RuntimeConfig.LOGGING.dontLogToConsoleBelowLevel.LEVEL) return;
        final String message = prepareMessage(RuntimeConfig.LOGGING.consoleLogFormat, logLevel, method, text, logLevel.COLOR);
        System.out.println(message);
    }

    /**
     * Gets method name which is executing stuff in this instance
     * @return A method name
     */
    private String getCurrentMethodCaller() {
        return new Object() {}.getClass().getEnclosingMethod().getName();
    }
}
