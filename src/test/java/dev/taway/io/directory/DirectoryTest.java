package dev.taway.io.directory;

import dev.taway.io.file.File;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DirectoryTest {
    Directory dir = new Directory("./testDir/");

    DirectoryTest() throws IOException {
    }

    @Test
    void create() throws IOException {
        assertTrue(dir.create());
    }

    @Test
    void delete() throws IOException {
        dir.create();
        assertTrue(dir.delete());
    }

    @Test
    void safeDelete() throws IOException {
        dir.create();
        File file = new File("./testDir/file.txt");
        file.create();
        assertFalse(dir.safeDelete());
        file.delete();
        assertTrue(dir.safeDelete());
    }

    @Test
    void exists() throws IOException {
        dir.create();
        assertTrue(dir.exists());
        dir.delete();
    }

    @Test
    void isEmpty() throws IOException {
        dir.create();
        File file = new File("./testDir/file.txt");
        file.create();
        assertFalse(dir.isEmpty());
        file.delete();
        assertTrue(dir.isEmpty());
        dir.delete();
    }
}