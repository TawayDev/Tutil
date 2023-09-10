package dev.taway.io;

public class IOChecker {
    /**
     * Checks if the final segment of a path doesn't contain a dot.
     * @param path File path.
     * @return True if it's a valid directory path.
     * @since 0.1.2
     */
    public static boolean pathIsDirectory(String path) {
        String[] segments = path.split("/");
        return !segments[segments.length - 1].contains(".");
    }

    /**
     * Checks if the final segment of a path contains a dot.
     * @param path File path.
     * @return True if it's a valid file path.
     * @since 0.1.2
     */
    public static boolean pathIsFile(String path) {
        String[] segments = path.split("/");
        return segments[segments.length - 1].contains(".");
    }
}
