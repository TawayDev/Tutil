package dev.taway.io.directory;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Directory implements IDirectory {
    String absolutePath;
    String path;
    Path pPath;

    public Directory(String path) throws IOException {
//        if (!IOChecker.pathIsDirectory(path)) logger.log(LogLevel.FATAL, "Constructor", "Path \"" + path + "\" is not a valid directory path.");
        File file = new File(path);
        this.absolutePath = file.getAbsolutePath();
        this.path = path;
        this.pPath = new File(path).toPath().toAbsolutePath();
    }

    /**
     * Creates the directory.
     *
     * @return Returns true if the directory was created.
     * @since 0.1.2
     */
    @Override
    public boolean create() throws IOException {
        if (exists()) return true;
        Files.createDirectory(pPath);
        return true;
    }

    /**
     * Deletes the directory regardless if it contains anything.
     *
     * @return Returns true if the directory was successfully deleted.
     * @see #safeDelete()
     * @since 0.1.2
     */
    @Override
    public boolean delete() throws IOException {
        return Files.deleteIfExists(pPath);
    }

    /**
     * Deletes empty directory and returns true however if the directory contains files it will not be deleted.
     *
     * @return Returns true if folder was successfully deleted.
     * @see #delete()
     * @since 0.1.2
     */
    @Override
    public boolean safeDelete() throws IOException {
        if (!isEmpty()) {
//            TODO: add custom exception
//            logger.log(LogLevel.ERROR, "safeDelete", "Directory could not be deleted because it is not empty.");
            return false;
        }
        return Files.deleteIfExists(pPath);
    }

    /**
     * Checks if directory exists.
     *
     * @return True if directory exists.
     * @since 0.1.2
     */
    @Override
    public boolean exists() {
        return new File(path).exists();
    }

    /**
     * Checks if the directory contains files. If it is empty it returns true.
     *
     * @return True if empty.
     * @since 0.1.2
     */
    @Override
    public boolean isEmpty() throws IOException {
        if (Files.isDirectory(pPath)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(pPath)) {
                return !directory.iterator().hasNext();
            }
        }
        return false;
    }
}
