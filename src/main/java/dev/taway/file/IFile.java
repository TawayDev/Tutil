package dev.taway.file;
/***
 * A simple wrapper for java.io.File
 * @since 0.1
 */
public interface IFile {
    /**
     * If the file does not exist it creates it.
     * @return If the file was created it returns true otherwise false
     * @since 0.1
     */
    boolean create();
    /**
     * If the file exists it deletes it.
     * @return Returns true if the file doesn't exist.
     * @since 0.1
     */
    boolean delete();
    /** Deletes the file on file exit.
     * @since 0.1
     * */
    void deleteOnExit();
    /** File? What file?
     * @return Returns true if the file exist.
     * @since 0.1
     * */
    boolean exists();
    /** Overwrites the file with specified text.
     * @since 0.1
     * */
    void overwrite(String text);
    /** Appends text to the end of the file.
     * @since 0.1
     * */
    void append(String text);

    void append(String text, boolean newLine);

    /** Reads the whole file and returns it as a single string. New lines are separated by \n
     * @return File as string.
     * @since 0.1
     * */
    String readAllAsString();
    /** Reads the whole file and returns it as a string array. Each line is on a separate position in the array.
     * @return File as string array.
     * @since 0.1
     * */
    String[] readAllAsStringArr();

    /***
     * Attempts to get a exclusive lock on a file. If that fails then the file is in use otherwise it is not.
     * @return Returns a bool if file is in use.
     * @since 0.1
     */
    Boolean isInUse();
    /***
     * @return Returns absolute path of the file.
     * @since 0.1
     */
    String getAbsolutePath();
    /***
     * @return Returns inputted path (the one you put into the constructor)
     * @since 0.1
     */
    String getPath();
}
