package dev.taway.tutil.logging;

import dev.taway.tutil.RuntimeConfig;
import dev.taway.tutil.format.StringFormatter;
import dev.taway.tutil.format.TimeFormatter;
import dev.taway.tutil.io.file.File;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Arrays;
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
    // TODO: Add buffer for writing into files because writing each log into files on call is dumb and slow!
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
        getOwnerClass();
        file = new File(RuntimeConfig.LOGGING.LOG_PATH_FILE);
    }

    /**
     * Creates a logger object for the specified class.
     *
     * @param logPath Custom logging file path.
     * @since 0.1.7
     */
    public Logger(String logPath) {
        getOwnerClass();
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
     * Retrieves the class name of the owner of the class instance.
     */
    private void getOwnerClass() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String name = stackTraceElements[3].getClassName();
        this.className = name;
    }

    /**
     * Gets method name which is executing stuff in this instance
     *
     * @return A method name
     */
    private String getCurrentMethodCaller() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String name = stackTraceElements[3].getMethodName();
        return name.equals("<init>") && RuntimeConfig.LOGGING.REPLACE_INIT ? "CONSTRUCTOR" : name;
    }

    /**
     * Logs a message with the specified log level, method name, and text.
     * The log is written to a file and printed to the console if configured to do so.
     * If the log level is "FATAL" and the "exitOnFatal" flag is enabled in {@link RuntimeConfig.LOGGING},
     * the program will exit with exit code -1 after the log is written.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param logLevel The log level of the message.
     * @param method   The name of the method from which the log method is called.
     * @param text     The text of the log message to be logged.
     */
    public void log(LogLevel logLevel, String method, Object... text) {
        String objectArrayString = concatObjects(text);
        toFile(logLevel, method, objectArrayString);
        toConsole(logLevel, method, objectArrayString);
        if (logLevel.NAME.equals("FATAL") && RuntimeConfig.LOGGING.EXIT_ON_FATAL_LOG) System.exit(-1);
    }

    /**
     * Logs a message with the specified log level, method name, and text.
     * The log is written to a file and printed to the console if configured to do so.
     * If the log level is "FATAL" and the "exitOnFatal" flag is enabled in {@link RuntimeConfig.LOGGING},
     * the program will exit with exit code -1 after the log is written.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param logLevel The log level of the message.
     * @param text     The text of the log message to be logged.
     */
    public void log(LogLevel logLevel, Object... text) {
        String method = getCurrentMethodCaller();
        String objectArrayString = concatObjects(text);
        toFile(logLevel, method, objectArrayString);
        toConsole(logLevel, method, objectArrayString);
        if (logLevel.NAME.equals("FATAL") && RuntimeConfig.LOGGING.EXIT_ON_FATAL_LOG) System.exit(-1);
    }

    /**
     * Logs a trace message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#TRACE}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the trace message to be logged.
     */
    public void trace(Object... text) {
        log(LogLevel.TRACE, getCurrentMethodCaller(), concatObjects(text));
    }

    /**
     * Logs a debug message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#DEBUG}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the debug message to be logged.
     */
    public void debug(Object... text) {
        log(LogLevel.DEBUG, getCurrentMethodCaller(), concatObjects(text));
    }

    /**
     * Logs a debug message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#DEBUG_WARN}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the debug message to be logged.
     */
    public void debugWarn(Object... text) {
        log(LogLevel.DEBUG_WARN, getCurrentMethodCaller(), concatObjects(text));
    }

    /**
     * Logs a debug message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#DEBUG_ERROR}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the debug message to be logged.
     */
    public void debugError(Object... text) {
        log(LogLevel.DEBUG_ERROR, getCurrentMethodCaller(), concatObjects(text));
    }

    /**
     * Logs a debug message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#DEBUG_SUCCESS}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the debug message to be logged.
     */
    public void debugSuccess(Object... text) {
        log(LogLevel.DEBUG_SUCCESS, getCurrentMethodCaller(), concatObjects(text));
    }

    /**
     * Logs a debug message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#DEBUG_FAILURE}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the debug message to be logged.
     */
    public void debugFailure(Object text) {
        log(LogLevel.DEBUG_FAILURE, getCurrentMethodCaller(), text.toString());
    }

    /**
     * Logs an informational message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#INFO}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the informational message to be logged.
     */
    public void info(Object... text) {
        log(LogLevel.INFO, getCurrentMethodCaller(), concatObjects(text));
    }

    /**
     * Logs an informational message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#SUCCESS}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the informational message to be logged.
     */
    public void success(Object... text) {
        log(LogLevel.SUCCESS, getCurrentMethodCaller(), concatObjects(text));
    }

    /**
     * Logs an informational message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#FAILURE}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the informational message to be logged.
     */
    public void failure(Object... text) {
        log(LogLevel.FAILURE, getCurrentMethodCaller(), concatObjects(text));
    }

    /**
     * Logs a warning message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#WARN}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the warning message to be logged.
     */
    public void warn(Object... text) {
        log(LogLevel.WARN, getCurrentMethodCaller(), concatObjects(text));
    }

    /**
     * Logs an error message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#ERROR}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The error message to be logged.
     */
    public void error(Object... text) {
        log(LogLevel.ERROR, getCurrentMethodCaller(), concatObjects(text));
    }

    /**
     * Logs a fatal message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
     * method with the log level set to {@link LogLevel#FATAL}.
     * <br>
     * Method uses Object.toString to convert any object that is not string already into a string.
     * Make sure logged data is convertible into string to not encounter any errors!
     * <br>
     * @param text The text of the fatal message to be logged.
     */
    public void fatal(Object... text) {
        log(LogLevel.FATAL, getCurrentMethodCaller(), concatObjects(text));
    }

//region Deprecated

    /**
     * Logs a trace message with the specified method name and text.
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
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
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
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
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
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
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
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
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
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
     * This method calls the {@link Logger#log(LogLevel, String, Object...)}
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
                color.equals("") ? RuntimeConfig.LOGGING.TIME_FORMAT_FILE : RuntimeConfig.LOGGING.TIME_FORMAT_CONSOLE,
                RuntimeConfig.TIME.TIME_ZONE)
        );



        logValues.put("{LEVEL}", color + logLevel.NAME + (color.equals("") ? "" : ConsoleColor.RESET.COLOR));
        logValues.put("{CLASS}", String.valueOf(className));
        logValues.put("{METHOD}", String.valueOf(method));
        logValues.put("{MESSAGE}", String.valueOf(text));
        return StringFormatter.formatString(format, logValues);
    }

    private void toFile(LogLevel logLevel, String method, String text) {
        if (RuntimeConfig.LOGGING.DISABLE_LOG_FILE) return;
        if (!forceLogToFile && logLevel.LEVEL < RuntimeConfig.LOGGING.MINIMUM_LOG_LEVEL_FILE.LEVEL) return;
        try {
            final String message = prepareMessage(RuntimeConfig.LOGGING.LOG_FORMAT_FILE, logLevel, method, text, "");
            file.append(message, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void toConsole(LogLevel logLevel, String method, String text) {
        if (RuntimeConfig.LOGGING.DISABLE_LOG_CONSOLE) return;
        if (!forceLogToConsole && logLevel.LEVEL < RuntimeConfig.LOGGING.MINIMUM_LOG_LEVEL_CONSOLE.LEVEL) return;
//        Makes important log info more visible in console:
        if(logLevel.name().toLowerCase().contains("error") || logLevel.name().toLowerCase().contains("warn") || logLevel.name().toLowerCase().contains("fatal")) {
            text = logLevel.COLOR + text + ConsoleColor.RESET.COLOR;
        }
        final String message = prepareMessage(RuntimeConfig.LOGGING.LOG_FORMAT_CONSOLE, logLevel, method, text, logLevel.COLOR);
        System.out.println(message);
    }

    /**
     * Converts multiple Objects into one string and adds ", " between each object.
     * @param text Array of objects
     * @return A single string representation of input
     */
    private String concatObjects(Object... text) {
        return String.join(RuntimeConfig.LOGGING.MULTIPLE_OBJECTS_LOGGED_SEPARATOR,
                Arrays.stream(text)
                        .map(String::valueOf)
                        .toArray(String[]::new));
    }
}
