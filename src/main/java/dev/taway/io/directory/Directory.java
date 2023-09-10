package dev.taway.io.directory;

import dev.taway.io.IOChecker;
import dev.taway.logging.LogLevel;
import dev.taway.logging.Logger;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Directory implements IDirectory{
    String absolutePath;
    String path;
    Path pPath;
    static Logger logger = new Logger("Directory");
    public Directory(String path) {
        if (!IOChecker.pathIsDirectory(path)) logger.log(LogLevel.FATAL, "Constructor", "Path \"" + path + "\" is not a valid directory path.");
        File file = new File(path);
        this.absolutePath = file.getAbsolutePath();
        this.path = path;
        this.pPath = new File(path).toPath().toAbsolutePath();
    }

    /**
     * Creates the directory.
     * @return Returns true if the directory was created.
     * @since 0.1.2
     */
    @Override
    public boolean create() {
        try {
            if(exists()) return true;
            Files.createDirectory(pPath);
            return true;
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "create", exception.getMessage());
        }
        return false;
    }

    /**
     * Deletes the directory regardless if it contains anything.
     * @return Returns true if the directory was successfully deleted.
     * @since 0.1.2
     * @see #safeDelete()
     */
    @Override
    public boolean delete() {
        try {
            return Files.deleteIfExists(pPath);
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "safeDelete", exception.getMessage());
        }
        return false;
    }

    /**
     * Deletes empty directory and returns true however if the directory contains files it will not be deleted.
     * @return Returns true if folder was successfully deleted.
     * @since 0.1.2
     * @see #delete()
     */
    @Override
    public boolean safeDelete() {
        try {
            if (!isEmpty()) {
                logger.log(LogLevel.ERROR, "safeDelete", "Directory could not be deleted because it is not empty.");
                return false;
            }
            return Files.deleteIfExists(pPath);
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "safeDelete", exception.getMessage());
        }
        return false;
    }

    /**
     * Checks if directory exists.
     * @return True if directory exists.
     * @since 0.1.2
     */
    @Override
    public boolean exists() {
        try {
            return new File(path).exists();
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "exists", exception.getMessage());
        }
        return false;
    }

    /**
     * Checks if the directory contains files. If it is empty it returns true.
     * @return True if empty.
     * @since 0.1.2
     */
    @Override
    public boolean isEmpty() {
        try {
            if (Files.isDirectory(pPath)) {
                try (DirectoryStream<Path> directory = Files.newDirectoryStream(pPath)) {
                    return !directory.iterator().hasNext();
                }
            }
        } catch (Exception exception) {
//            Not sure if this should be FATAL or just ERROR but let's say that this could cause some more issues if it was only an error.
            logger.log(LogLevel.FATAL, "isEmpty", exception.getMessage());
        }
        return false;
    }
}
