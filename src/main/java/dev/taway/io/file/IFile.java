package dev.taway.io.file;
/**
 * A simple wrapper for {@link java.io.File}. Handles one file at a time.
 * @since 0.1
 */
public interface IFile {
    boolean create();
    boolean delete();
    void deleteOnExit();
    boolean exists();
    void overwrite(String text);
    void append(String text);
    void append(String text, boolean newLine);
    String readAllAsString();
    String[] readAllAsStringArr();
    Boolean isInUse();
    String getAbsolutePath();
    String getPath();
}
