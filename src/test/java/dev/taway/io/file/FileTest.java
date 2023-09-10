package dev.taway.io.file;

import org.junit.jupiter.api.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {

    String path = "./testFile.txt";
    @AfterEach
    void tearDown() {
        File file = new File(path);
        java.io.File f = new java.io.File(path);
        if(!f.delete()) {
            System.out.println("After test cleanup FAILURE! canRead=" + f.canRead() + " canWrite=" + f.canWrite() + " canExecute=" + f.canExecute() + " inUse=" + file.isInUse());
        }
    }

    @Test
    void create() {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        file.create();
        assertTrue(f.exists());
    }

    @Test
    void delete() {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        if(!f.exists()) file.create();
        file.delete();
        assertFalse(file.exists());
    }

    @Test
    void exists() throws IOException {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        if(!f.exists()) f.createNewFile();
        assertTrue(file.exists());
    }

    @Test
    void overwrite() {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        file.create();
        file.overwrite("hello world!");
        String contents = file.readAllAsString();
        assertEquals("hello world!", contents);
    }

    @Test
    void append() {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        file.create();
        file.overwrite("hello world!");
        file.append("balls!");
        String contents = file.readAllAsString();
        assertEquals("hello world!balls!", contents);
    }

    @Test
    void readAllAsString() {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        file.create();
        file.overwrite("hello world!");
        file.append("hello world!", true);
        String contents = file.readAllAsString();
        assertEquals("hello world!\nhello world!", contents);
    }

    @Test
    void readAllAsStringArr() {
        File file = new File(path);
        java.io.File f = new java.io.File(path);

        file.create();
        file.overwrite("hello world!");
        file.append("hello world!", true);
        String[] contents = file.readAllAsStringArr();
        assertEquals("hello world!hello world!", contents[0] + contents[1]);
    }
}