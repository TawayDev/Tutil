package dev.taway.tutil.io.file;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    String path = "./testFile.txt";

//    @AfterEach
//    void tearDown() throws IOException {
//        File file = new File(path);
//        java.io.File f = new java.io.File(path);
//        if (!f.delete()) {
//            System.out.println("After test cleanup FAILURE! canRead=" + f.canRead() + " canWrite=" + f.canWrite() + " canExecute=" + f.canExecute() + " inUse=" + file.isInUse());
//        }
//    }

    @Test
    void create() throws IOException {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        file.create();
        assertTrue(f.exists());
        file.delete();
    }

    @Test
    void delete() throws IOException {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        if (!f.exists()) file.create();
        file.delete();
        assertFalse(file.exists());
    }

    @Test
    void exists() throws IOException {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        if (!f.exists()) f.createNewFile();
        assertTrue(file.exists());
    }

    @Test
    void overwrite() throws IOException {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        file.create();
        file.overwrite("hello world!");
        String contents = file.readAllAsString();
        assertEquals("hello world!", contents);
        file.delete();
    }

    @Test
    void append() throws IOException {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        file.create();
        file.overwrite("hello world!");
        file.append("balls!");
        String contents = file.readAllAsString();
        assertEquals("hello world!balls!", contents);
        file.delete();
    }

    @Test
    void readAllAsString() throws IOException {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        file.create();
        file.overwrite("hello world!");
        file.append("hello world!", true, false);
        String contents = file.readAllAsString();
        assertEquals("hello world!\nhello world!", contents);
        file.delete();
    }

    @Test
    void readAllAsStringArr() throws IOException {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        file.create();
        file.overwrite("hello world!");
        file.append("hello world!", true, false);
        String[] contents = file.readAllAsStringArr();
//        System.out.println(contents.length + " | " + Arrays.toString(contents));
        assertEquals("hello world!hello world!", contents[0] + contents[1]);
        file.delete();
    }
}