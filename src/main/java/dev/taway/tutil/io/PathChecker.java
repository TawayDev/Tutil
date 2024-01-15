package dev.taway.tutil.io;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathChecker {

    /**
     * Checks if the final segment of a path doesn't contain a dot,
     * assuming that it does not contain any invalid characters.
     *
     * @param path File path.
     * @return True if it's a valid directory path.
     * @since 0.1.2
     */
    public static boolean pathIsDirectory(String path) {
        try {
            if (containsInvalidChars(path)) return false;

            Path p = Paths.get(path);
            String finalSegment = p.getFileName().toString();
            return !finalSegment.contains(".");
        } catch (InvalidPathException e) {
            return false;
        }
    }

    /**
     * Checks if the final segment of a path contains a dot,
     * assuming that it does not contain any invalid characters.
     *
     * @param path File path.
     * @return True if it's a valid file path.
     * @since 0.1.2
     */
    public static boolean pathIsFile(String path) {
        try {
            if (containsInvalidChars(path)) return false;

            Path p = Paths.get(path);
            String finalSegment = p.getFileName().toString();
            return finalSegment.contains(".");
        } catch (InvalidPathException e) {
            return false;
        }
    }

    /**
     * Checks if a path contains invalid characters, such as NUL.
     *
     * @param path The path to check.
     * @return True if the path contains invalid characters.
     */
    private static boolean containsInvalidChars(String path) {
        return path.contains("\u0000") || path.matches(".*[<>:\"|?*].*");
    }
}
