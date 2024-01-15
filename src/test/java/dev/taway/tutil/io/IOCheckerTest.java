package dev.taway.tutil.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IOCheckerTest {

    @Test
    void pathIsDirectory() {
        assertFalse(PathChecker.pathIsDirectory("this/is/a/path/toFile.txt"));
        assertTrue(PathChecker.pathIsDirectory("this/is/a/path/toFolder/"));
    }

    @Test
    void pathIsFile() {
        assertFalse(PathChecker.pathIsFile("this/is/a/path/toFolder/"));
        assertTrue(PathChecker.pathIsFile("this/is/a/path/toFile.txt"));
    }
}