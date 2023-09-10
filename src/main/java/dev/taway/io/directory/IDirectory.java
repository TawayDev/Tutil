package dev.taway.io.directory;

public interface IDirectory {
    boolean create();
    boolean delete();
    boolean safeDelete();
    boolean exists();
    boolean isEmpty();
}
