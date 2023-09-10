package dev.taway.io.directory;

import dev.taway.io.file.File;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryTest {
    Directory dir = new Directory("./testDir/");
    @Test
    void create() {
        assertTrue(dir.create());
    }

    @Test
    void delete() {
        dir.create();
        assertTrue(dir.delete());
    }

    @Test
    void safeDelete() {
        dir.create();
        File file = new File("./testDir/file.txt");
        file.create();
        assertFalse(dir.safeDelete());
        file.delete();
        assertTrue(dir.safeDelete());
    }

    @Test
    void exists() {
        dir.create();
        assertTrue(dir.exists());
        dir.delete();
    }

    @Test
    void isEmpty() {
        dir.create();
        File file = new File("./testDir/file.txt");
        file.create();
        assertFalse(dir.isEmpty());
        file.delete();
        assertTrue(dir.isEmpty());
        dir.delete();
    }
}