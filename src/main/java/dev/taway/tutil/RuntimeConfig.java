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
        public static String LOG_FORMAT_FILE = "[{TIME}] [{LEVEL}] [{CLASS}.{METHOD}] {MESSAGE}";
        /**
         * Specifies the format of console logs. <br>
         * These keywords will be replaced in format string:
         * {TIME}, {LEVEL}, {CLASS}, {METHOD} and {MESSAGE} <br>
         * For example you can have fileLogFormat as "[{TIME}] [{LEVEL}] [{CLASS}.{METHOD}] {MESSAGE}" <br>
         * Which will result in: "[22:58 08.09.2023] [INFO] [Main.myMethod] This is an example message!"
         *
         * @since 0.1.1
         */
        public static String LOG_FORMAT_CONSOLE = "[{TIME}] [{LEVEL}] [{CLASS}.{METHOD}] {MESSAGE}";
        /**
         * Specifies the time format for console logs.
         */
        public static String TIME_FORMAT_CONSOLE = "hh:mm";
        /**
         * Specifies the time format for file logs.
         */
        public static String TIME_FORMAT_FILE = "hh:mm";
        /**
         * Specifies to which file logger should write to. <br>
         * <b>NOTE:</b> File path cannot be changed once logger is instantiated!
         *
         * @since 0.1.1
         */
        public static String LOG_PATH_FILE = "./log.txt";
        /**
         * Specifies the minimum {@link LogLevel} level for logs to be displayed in the console.
         * Any level below the one defined here will be ignored.
         *
         * @since 0.1.1
         */
        public static LogLevel MINIMUM_LOG_LEVEL_CONSOLE = LogLevel.TRACE;
        /**
         * Specifies the minimum {@link LogLevel} level for logs to be written to a file.
         * Any log level below the one defined here will be ignored and not written to the file.
         */
        public static LogLevel MINIMUM_LOG_LEVEL_FILE = LogLevel.TRACE;
        /**
         * Indicates whether console logging is enabled or disabled. If set to
         * {@code true}, console logging will be disabled; if set to {@code false},
         * console logging will be enabled. (This variable overrides force log and other settings)
         */
        public static boolean DISABLE_LOG_CONSOLE = false;
        /**
         * Indicates whether file logging is enabled or disabled. If set to
         * {@code true}, file logging will be disabled; if set to {@code false},
         * file logging will be enabled.(This variable overrides force log and other settings)
         */
        public static boolean DISABLE_LOG_FILE = false;

        /**
         * If fatal error occurred aka got logged and exitOnFatal is true then the program will terminate itself.
         *
         * @since 0.1.1
         */
        public static boolean EXIT_ON_FATAL_LOG = true;

        /**
         * Indicates if constructor method should be called {@code <init>} or {@code CONSTRUCTOR} in logs. <br>
         * false = {@code <init>} <br>
         * true = {@code CONSTRUCTOR}<br>
         */
        public static boolean REPLACE_INIT = false;
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
        public static String TIME_FORMAT = "hh:mm dd.MM.yyyy";
        /**
         * Timezone used by getFormattedTime method.
         *
         * @since 0.1
         */
        public static String TIME_ZONE = "CET";
    }

    /**
     * Runtime configuration of {@link RSA} class.
     *
     * @since 0.1.5
     */
    public static class CRYPTO {
        /**
         * Indicates whether a warning should be displayed when an unsafe RSA key size is used.
         *
         * <p>
         * The value of this variable determines whether a warning message should be logged when
         * an RSA key size smaller than 2048 bits is used. Using keys smaller than 2048 bits is
         * not recommended for better security.
         * </p>
         *
         * <p>
         * RSA keys smaller than 2048 bits are considered unsafe because they are more vulnerable
         * to brute force attacks and other cryptographic attacks.
         * </p>
         *
         * <p>
         * The initial value of this variable is set to {@code true} in the {@link RSA} class,
         * which is the runtime configuration class for the {@link RSA} class.
         * </p>
         *
         * @since 0.1.5
         *
         * @see RSA#RSA(int)
         */
        public static boolean WARN_UNSAFE_RSA_KEY_SIZE_USED = true;
    }

    /**
     * Runtime configuration of {@link SQLExecutor}
     */
    public static class SQL {
        public static String URL = null;
        public static String USERNAME = null;
        public static String PASSWORD = null;
    }

    /**
     * Runtime configuration of {@link StringFormatter}
     */
    public static class STRING_FORMATTING {
        /**
         * Represents the amount by which the size of a formatted string should be increased.
         * It is used in the StringFormatter class to align strings to a specified size by adding padding characters.
         * The value of INCREASE_PADDING_BY should be a non-negative integer.
         * For the best results use a number that is divisible by two.
         */
        public static int INCREASE_PADDING_BY = 6;

        /**
         * The default text alignment used by the StringFormatter class.
         */
        public static TextAlign DEFAULT_TEXT_ALIGN = TextAlign.MIDDLE;
    }
}
