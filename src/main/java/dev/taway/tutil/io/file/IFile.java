package dev.taway.tutil.io.file;

import java.io.IOException;

/**
 * A simple wrapper for {@link java.io.File}. Handles one file at a time.
 *
 * @since 0.1
 */
public interface IFile {
    boolean create() throws IOException;

    boolean delete();

    void deleteOnExit();

    boolean exists();

    void overwrite(String text) throws IOException;

    void append(String text) throws IOException;

    void append(String text, boolean newLine) throws IOException;

    String readAllAsString() throws IOException;

    String[] readAllAsStringArr() throws IOException;

    Boolean isInUse() throws IOException;

    String getAbsolutePath();

    String getPath();
}
