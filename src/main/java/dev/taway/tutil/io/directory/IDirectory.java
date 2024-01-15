package dev.taway.tutil.io.directory;

import java.io.IOException;

public interface IDirectory {
    boolean create() throws IOException;

    boolean delete() throws IOException;

    boolean safeDelete() throws IOException;

    boolean exists();

    boolean isEmpty() throws IOException;
}
