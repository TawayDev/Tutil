package dev.taway.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IOCheckerTest {

    @Test
    void pathIsDirectory() {
        assertFalse(IOChecker.pathIsDirectory("this/is/a/path/toFile.txt"));
        assertTrue(IOChecker.pathIsDirectory("this/is/a/path/toFolder/"));
    }

    @Test
    void pathIsFile() {
        assertFalse(IOChecker.pathIsFile("this/is/a/path/toFolder/"));
        assertTrue(IOChecker.pathIsFile("this/is/a/path/toFile.txt"));
    }
}