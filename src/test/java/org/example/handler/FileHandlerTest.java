package org.example.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {
    private final String outputDir = "test_output";
    private final String fileName = "test_file.txt";
    private final List<String> testData = List.of("line1", "line2", "line3");

    @BeforeEach
    void setUp() throws IOException {
        // Проверяем что директория существует и чистая
        Path dir = Path.of(outputDir);
        Files.createDirectories(dir);
        Files.walk(dir)
                .filter(Files::isRegularFile)

                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }

    @Test
    void testWriteToFileOverwrite() throws IOException {
        // Write to file without appending
        FileHandler.writeToFile(outputDir, fileName, testData, false);

        Path filePath = Path.of(outputDir, fileName);
        assertTrue(Files.exists(filePath), "File should be created");

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(testData, lines, "File content does not match expected data");
    }

    @Test
    void testWriteToFileAppend() throws IOException {
        // Write initial data
        FileHandler.writeToFile(outputDir, fileName, List.of("initial"), false);

        // Append data
        FileHandler.writeToFile(outputDir, fileName, testData, true);

        Path filePath = Path.of(outputDir, fileName);
        assertTrue(Files.exists(filePath), "File should exist");

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(List.of("initial", "line1", "line2", "line3"), lines, "File content does not match expected data after append");
    }

    @Test
    void testReadFileNonexistent() {
        // Attempt to read a non-existent file
        List<String> lines = FileHandler.readFile("nonexistent.txt");

        assertTrue(lines.isEmpty(), "Reading a non-existent file should return an empty list");
    }

    @Test
    void testGetFileDir() {
        String dir = FileHandler.getFileDir();

        assertNotNull(dir, "File directory should not be null");
        assertTrue(Files.exists(Path.of(dir)), "Returned file directory should exist");
    }
}