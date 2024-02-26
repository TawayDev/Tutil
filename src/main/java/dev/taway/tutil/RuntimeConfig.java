package dev.taway.tutil;

import dev.taway.tutil.crypto.RSA;
import dev.taway.tutil.format.TextAlign;
import dev.taway.tutil.format.TimeFormatter;
import dev.taway.tutil.format.StringFormatter;
import dev.taway.tutil.logging.LogLevel;
import dev.taway.tutil.logging.Logger;
import dev.taway.tutil.net.sql.SQLExecutor;
import dev.taway.tutil.time.Stopwatch;

/**
 * Configuration for runtime of Tutil. You can modify a lot of behavior from here directly at runtime.<br>
 * This makes it easier for the developer that builds with this library to specify one thing here, and it will be applied globally.
 *
 * @version 0.2
 * @since 0.1
 */
public final class RuntimeConfig {
    /**
     * Runtime configuration of {@link Logger} class.
     *
     * @since 0.1.1
     */
    public static class LOGGING {
        /**
         * Specifies the format of console logs. <br>
         * These keywords will be replaced in format string:
         * {TIME}, {LEVEL}, {CLASS}, {METHOD} and {MESSAGE} <br>
         * For example you can have fileLogFormat as "[{TIME}] [{LEVEL}] [{CLASS}.{METHOD}] {MESSAGE}" <br>
         * Which will result in: "[22:58 08.09.2023] [INFO] [Main.myMethod] This is an example message!"
         *
         * @since 0.1.1
         */
        public static String fileLogFormat = "[{TIME}] [{LEVEL}] [{CLASS}.{METHOD}] {MESSAGE}";
        /**
         * Specifies the format of console logs. <br>
         * These keywords will be replaced in format string:
         * {TIME}, {LEVEL}, {CLASS}, {METHOD} and {MESSAGE} <br>
         * For example you can have fileLogFormat as "[{TIME}] [{LEVEL}] [{CLASS}.{METHOD}] {MESSAGE}" <br>
         * Which will result in: "[22:58 08.09.2023] [INFO] [Main.myMethod] This is an example message!"
         *
         * @since 0.1.1
         */
        public static String consoleLogFormat = "[{TIME}] [{LEVEL}] [{CLASS}.{METHOD}] {MESSAGE}";
        /**
         * Specifies the time format for console logs.
         */
        public static String consoleTimeFormat = "hh:mm";
        /**
         * Specifies the time format for file logs.
         */
        public static String fileTimeFormat = "hh:mm";
        /**
         * Specifies to which file logger should write to. <br>
         * <b>NOTE:</b> File path cannot be changed once logger is instantiated!
         *
         * @since 0.1.1
         */
        public static String logFilePath = "./log.txt";
        /**
         * Specifies the minimum {@link LogLevel} level for logs to be displayed in the console.
         * Any level below the one defined here will be ignored.
         *
         * @since 0.1.1
         */
        public static LogLevel dontLogToConsoleBelowLevel = LogLevel.TRACE;
        /**
         * Specifies the minimum {@link LogLevel} level for logs to be written to a file.
         * Any log level below the one defined here will be ignored and not written to the file.
         */
        public static LogLevel dontLogToFileBelowLevel = LogLevel.TRACE;
        /**
         * Indicates whether console logging is enabled or disabled. If set to
         * {@code true}, console logging will be disabled; if set to {@code false},
         * console logging will be enabled. (This variable overrides force log and other settings)
         */
        public static boolean disableConsoleLog = false;
        /**
         * Indicates whether file logging is enabled or disabled. If set to
         * {@code true}, file logging will be disabled; if set to {@code false},
         * file logging will be enabled.(This variable overrides force log and other settings)
         */
        public static boolean disableFileLog = false;

        /**
         * If fatal error occurred aka got logged and exitOnFatal is true then the program will terminate itself.
         *
         * @since 0.1.1
         */
        public static boolean exitOnFatal = true;
    }

    /**
     * Runtime configuration of {@link Stopwatch} & {@link TimeFormatter} classes.
     *
     * @since 0.1
     */
    public static class TIME {
        /**
         * Time format used by getFormattedTime method.
         *
         * @since 0.1
         */
        public static String timeFormat = "hh:mm dd.MM.yyyy";
        /**
         * Timezone used by getFormattedTime method.
         *
         * @since 0.1
         */
        public static String timeZone = "CET";
    }

    /**
     * Runtime configuration of {@link RSA} class.
     *
     * @since 0.1.5
     */
    public static class CRYPTO {
        public static boolean warnUnsafeRSAKeySizes = true;
    }

    /**
     * Runtime configuration of {@link SQLExecutor}
     */
    public static class SQL {
        public static String URL = null;
        public static String username = null;
        public static String password = null;
    }

    /**
     * Runtime configuration of {@link StringFormatter}
     */
    public static class STRING_FORMATTING {
        /**
         * Represents the amount by which the size of a formatted string should be increased.
         * It is used in the StringFormatter class to align strings to a specified size by adding padding characters.
         * The value of increaseFormattedStringSizeBy should be a non-negative integer.
         * For the best results use a number that is divisible by two.
         */
        public static int increaseFormattedStringSizeBy = 6;

        /**
         * The default text alignment used by the StringFormatter class.
         */
        public static TextAlign defaultTextAlign = TextAlign.MIDDLE;
    }
}
