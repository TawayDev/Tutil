package dev.taway.io;

import dev.taway.logging.LogLevel;
import dev.taway.logging.Logger;

public class IOChecker {
    static Logger logger = new Logger("IOChecker");
    /**
     * Checks if the final segment of a path doesn't contain a dot.
     * @param path File path.
     * @return True if it's a valid directory path.
     * @since 0.1.2
     */
    public static boolean pathIsDirectory(String path) {
        try {
            String[] segments = path.split("/");
            return !segments[segments.length - 1].contains(".");
        } catch (Exception exception) {
            logger.log(LogLevel.ERROR, "pathIsDirectory", exception.getMessage());
        }
        return false;
    }

    /**
     * Checks if the final segment of a path contains a dot.
     * @param path File path.
     * @return True if it's a valid file path.
     * @since 0.1.2
     */
    public static boolean pathIsFile(String path) {
        try {
            String[] segments = path.split("/");
            return segments[segments.length - 1].contains(".");
        } catch (Exception exception) {
            logger.log(LogLevel.ERROR, "pathIsFile", exception.getMessage());
        }
        return false;
    }
}
