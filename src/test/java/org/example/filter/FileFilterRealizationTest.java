package org.example.filter;

import org.example.constants.Constants;
import org.example.handler.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileFilterRealizationTest {
    private FileFilterRealization fileFilterRealization;

    @BeforeEach
    void setUp() {
        fileFilterRealization = new FileFilterRealization();
    }

    @Test
    void testHandleArgsValidFlags() {
        String[] args = {"-o", "outputDir", "-p", "prefix", "-a", "-s", "-f", "file1.txt", "file2.txt"};

        fileFilterRealization.handleArgs(args);

        Map<String, Object> context = fileFilterRealization.getContext();
        List<String> files = fileFilterRealization.getFiles();

        // Check context values
        assertEquals("outputDir", context.get("outputDir"), "Output directory flag not parsed correctly");
        assertEquals("prefix", context.get("prefix"), "Prefix flag not parsed correctly");
        assertTrue((boolean) context.get("append"), "Append flag not set correctly");
        assertTrue((boolean) context.get("shortStats"), "Short stats flag not set correctly");
        assertTrue((boolean) context.get("fullStats"), "Full stats flag not set correctly");

        // Check files
        assertEquals(2, files.size(), "Number of files parsed incorrectly");
        assertTrue(files.contains("file1.txt"), "file1.txt not added to files list");
        assertTrue(files.contains("file2.txt"), "file2.txt not added to files list");
    }

    @Test
    void testProcessValues() {
        List<String> values = List.of("123", "456.78", "hello", "789", "3.14", "world");

        try (MockedStatic<FileHandler> mockedFileHandler = Mockito.mockStatic(FileHandler.class)) {
            FileFilterRealization.processValues(values, Map.of(
                    "outputDir", "output",
                    "prefix", "test_",
                    "append", false,
                    "shortStats", true,
                    "fullStats", false
            ));

            // Проверка вызовов
            mockedFileHandler.verify(() -> FileHandler.writeToFile("output", "test_" + Constants.FilePath.INT_FILE_NAME, List.of(123, 789), false));
            mockedFileHandler.verify(() -> FileHandler.writeToFile("output", "test_" + Constants.FilePath.FLOAT_FILE_NAME, List.of(456.78f, 3.14f), false));
            mockedFileHandler.verify(() -> FileHandler.writeToFile("output", "test_" + Constants.FilePath.STRING_FILE_NAME, List.of("hello", "world"), false));
        }
    }
}