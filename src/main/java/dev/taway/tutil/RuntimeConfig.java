package dev.taway.tutil;

import dev.taway.tutil.crypto.RSA;
import dev.taway.tutil.logging.Logger;
import dev.taway.tutil.net.sql.SQLExecutor;
import dev.taway.tutil.time.Formatter;
import dev.taway.tutil.time.Stopwatch;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

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
         * Specifies to which file logger should write to. <br>
         * <b>NOTE:</b> File path cannot be changed once logger is instantiated!
         *
         * @since 0.1.1
         */
        public static String logFilePath = "./log.txt";
        /**
         * Tells logger which log "request" to ignore.
         * The ones ignored will not be logged into console.
         * 0 = Everything (Fatal, Error, Warn, Info & Debug) <br>
         * 1 = Fatal, Error, Warn & Info <br>
         * 2 = Fatal, Error & Warn <br>
         * 3 = Fatal & Error <br>
         * 4 = Only Fatal <br>
         * 5 = Nothing
         *
         * @since 0.1.1
         */
        @Min(value = 0, message = "0 is the minimal possible value where everything will be logged.")
        @Max(value = 5, message = "5 is the max possible value where nothing will be logged.")
        public static int dontLogToConsoleBelowLevel = 0;
        /**
         * Tells logger which log "request" to ignore.
         * The ones ignored will not be logged into a file.
         * 0 = Everything (Fatal, Error, Warn, Info & Debug) <br>
         * 1 = Fatal, Error, Warn & Info <br>
         * 2 = Fatal, Error & Warn <br>
         * 3 = Fatal & Error <br>
         * 4 = Only Fatal <br>
         * 5 = Nothing
         *
         * @since 0.1.1
         */
        @Min(value = 0, message = "0 is the minimal possible value where everything will be logged.")
        @Max(value = 5, message = "5 is the max possible value where nothing will be logged.")
        public static int dontLogToFileBelowLevel = 0;
        /**
         * If fatal error occurred aka got logged and exitOnFatal is true then the program will terminate itself.
         *
         * @since 0.1.1
         */
        public static boolean exitOnFatal = true;
    }

    /**
     * Runtime configuration of {@link Stopwatch} & {@link Formatter} classes.
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
}
