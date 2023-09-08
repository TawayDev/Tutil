package dev.taway.file;

import dev.taway.logging.LogLevel;
import dev.taway.logging.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;

/***
 * A simple wrapper for java.io.File
 * @version 0.1.1
 * @since 0.1
 */
public class File implements IFile{

    String absolutePath;
    String path;
    Logger logger = new Logger("File");
    public File(String path) {
        java.io.File file = new java.io.File(path);
        this.absolutePath = file.getAbsolutePath();
        this.path = path;
    }

    /**
     * If the file does not exist it creates it.
     * @return If the file was created it returns true otherwise false
     * @version 0.1.1
     * @since 0.1
     */
    @Override
    public boolean create() {
        try {
            java.io.File file = new java.io.File(absolutePath);
            return file.exists() ? true : file.createNewFile();
        } catch (IOException exception) {
            logger.log(LogLevel.FATAL, "create", exception.getMessage());
        }
        return false;
    }

    /**
     * If the file exists it deletes it.
     * @return Returns true if the file doesn't exist.
     * @version 0.1.1
     * @since 0.1
     */
    @Override
    public boolean delete() {
        try {
            java.io.File file = new java.io.File(absolutePath);
            return file.exists() ? file.delete() : true;
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "delete", exception.getMessage());
        }
        return false;
    }

    /**
     * Deletes the file on virtual machine exit.
     * @version 0.1.1
     * @since 0.1
     */
    @Override
    public void deleteOnExit() {
        try {
            java.io.File file = new java.io.File(absolutePath);
            file.deleteOnExit();
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "deleteOnExit", exception.getMessage());
        }
    }

    /**
     * File? What file?
     * @return Returns true if the file exists.
     * @since 0.1
     */
    @Override
    public boolean exists() {
        java.io.File file = new java.io.File(absolutePath);
        return file.exists();
    }

    /**
     * Overwrites the file with specified text.
     * @param text Text to be written
     * @version 0.1.1
     * @since 0.1
     */
    @Override
    public void overwrite(String text) {
        try {
            java.io.File file = new java.io.File(absolutePath);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "overwrite", exception.getMessage());
        }
    }

    /**
     * Appends text to the end of the file.
     * @param text Text to be written
     * @version 0.1.1
     * @since 0.1
     */
    @Override
    public void append(String text, boolean newLine) {
        try {
            java.io.File file = new java.io.File(absolutePath);
            text = newLine ? "\n"+text : text;
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append(text);
            bufferedWriter.close();
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "append", exception.getMessage());
        }
    }

    /***
     * Appends to the end of the file.
     * @param text Text to append
     * @version 0.1.1
     * @since 0.1
     */
    @Override
    public void append(String text) {
        try {
            java.io.File file = new java.io.File(absolutePath);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.append(text);
            bufferedWriter.close();
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "append", exception.getMessage());
        }
    }

    /**
     * Reads the whole file and returns it as a single string. New lines are separated by \n
     * @return File as string.
     * @version 0.1.1
     * @since 0.1
     */
    @Override
    public String readAllAsString() {
        try {
            java.io.File file = new java.io.File(absolutePath);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
//            Remove trailing new line character and return:
            return stringBuilder.length() > 1 ? stringBuilder.substring(0, stringBuilder.length()-1) : stringBuilder.toString();
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "readAllAsString", exception.getMessage());
        }
        return "";
    }

    /**
     * Reads the whole file and returns it as a string array. Each line is on a separate position in the array.
     * @return File as string array.
     * @version 0.1.1
     * @since 0.1
     */
    @Override
    public String[] readAllAsStringArr() {
        try {
            java.io.File file = new java.io.File(absolutePath);
            ArrayList<String> lines = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
            return lines.toArray(new String[lines.size()]);
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "readAllStringArr", exception.getMessage());
        }
        return new String[0];
    }
    /**
     * Attempts to get a exclusive lock on a file. If that fails then the file is in use otherwise it is not.
     * @return Returns a bool if file is in use.
     * @version 0.1.1
     * @since 0.1
     */
    @Override
    public Boolean isInUse() {
        try (
                RandomAccessFile file = new RandomAccessFile(absolutePath, "rw");
                FileChannel channel = file.getChannel()
        ) {
            FileLock lock = channel.tryLock();
            if (lock != null) {
                lock.release();
                return false;
            } else {
                return true;
            }
        } catch (Exception exception) {
            logger.log(LogLevel.FATAL, "isInUse", exception.getMessage());
        }
        return null;
    }

    @Override
    public String getAbsolutePath() {
        return absolutePath;
    }

    /***
     * @return Returns inputted path (the one you put into the constructor)
     * @since 0.1
     */
    @Override
    public String getPath() {
        return this.path;
    }
}
