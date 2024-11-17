package dev.taway.tutil.io.file;

import dev.taway.tutil.exception.io.FileException;
import dev.taway.tutil.io.PathChecker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;

/***
 * A simple wrapper for java.io.File
 * @version 0.2.5
 * @since 0.1
 */
public class File implements IFile {

    @Setter private boolean keepOpen = false;

    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    private final java.io.File file;
    @Getter private final String absolutePath;
    @Getter private final String path;

    public File(String path) {
        try {
            if (!PathChecker.pathIsFile(path))
                throw new FileException("Path \"" + path + "\" is not a valid file path.");
        } catch (FileException exception) {
            throw new RuntimeException(exception);
        }
        this.file = new java.io.File(path);
        this.absolutePath = file.getAbsolutePath();
        this.path = path;
    }

    /**
     * If the file does not exist it creates it.
     *
     * @return If the file was created it returns true otherwise false
     * @version 0.2.5
     * @since 0.1
     */
    @Override
    public boolean create() throws IOException {
        return file.exists() || file.createNewFile();
    }

    /**
     * If the file exists it deletes it.
     *
     * @return Returns true if the file doesn't exist.
     * @version 0.2.5
     * @since 0.1
     */
    @Override
    public boolean delete() {
        return !file.exists() || file.delete();
    }

    /**
     * Deletes the file on virtual machine exit.
     *
     * @version 0.2.5
     * @since 0.1
     */
    @Override
    public void deleteOnExit() {
        file.deleteOnExit();
    }

    /**
     * File? What file?
     *
     * @return Returns true if the file exists.
     * @since 0.1
     */
    @Override
    public boolean exists() {
        return file.exists();
    }

    /**
     * Overwrites the file with specified text.
     *
     * @param text Text to be written
     * @version 0.2.5
     * @since 0.1
     */
    @Override
    public void overwrite(String text) throws IOException {
        initBufferedWriter(false);
        bufferedWriter.write(text);
        bufferedWriter.flush();
        if (!keepOpen) close();
    }

    /**
     * Appends text to the end of the file,
     *
     * @param text Text to be written
     * @param newLinePrepend True will put new line before writing text
     * @param newLineAppend True will put new line after writing text
     * @version 0.2.5
     * @since 0.1
     */
    @Override
    public void append(String text, boolean newLinePrepend, boolean newLineAppend) throws IOException {
        initBufferedWriter(true);

        if (newLinePrepend) bufferedWriter.newLine();
        bufferedWriter.append(text);
        if (newLineAppend) bufferedWriter.newLine();

        bufferedWriter.flush();
        if (!keepOpen) close();
    }

    /***
     * Appends to the end of the file.
     * @param text Text to append
     * @version 0.2.5
     * @since 0.1
     */
    @Override
    public void append(String text) throws IOException {
        initBufferedWriter(true);
        bufferedWriter.append(text);
        bufferedWriter.flush();
        if (!keepOpen) close();
    }

    /**
     * Reads the whole file and returns it as a single string. New lines are separated by \n
     *
     * @return File as string.
     * @version 0.2.5
     * @since 0.1
     */
    @Override
    public String readAllAsString() throws IOException {
        initBufferedReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        closeReader(); // Always close reader
        return stringBuilder.length() > 0 ? stringBuilder.substring(0, stringBuilder.length() - 1) : "";
    }

    /**
     * Reads the whole file and returns it as a string array. Each line is on a separate position in the array.
     *
     * @return File as string array.
     * @version 0.2.5
     * @since 0.1
     */
    @Override
    public String[] readAllAsStringArr() throws IOException {
        initBufferedReader();
        ArrayList<String> lines = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        closeReader(); // Always close reader
        return lines.toArray(new String[0]);
    }

    /**
     * Attempts to get an exclusive lock on a file. If that fails then the file is in use otherwise it is not.
     *
     * @return Returns a bool if file is in use.
     * @version 0.2.5
     * @since 0.1
     */
    @Override
    public Boolean isInUse() throws IOException {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(absolutePath, "rw");
             FileChannel channel = randomAccessFile.getChannel()) {
            FileLock lock = channel.tryLock();
            if (lock != null) {
                lock.release();
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public void close() {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
                bufferedWriter = null;
            }
            if (bufferedReader != null) {
                bufferedReader.close();
                bufferedReader = null;
            }
            keepOpen = false;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void initBufferedWriter(boolean append) throws IOException {
        if (bufferedWriter == null) {
            bufferedWriter = new BufferedWriter(new FileWriter(file, append));
        }
    }

    private void initBufferedReader() throws IOException {
        if (bufferedReader == null) {
            bufferedReader = new BufferedReader(new FileReader(file));
        }
    }

    private void closeReader() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
                bufferedReader = null;
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}