package org.example.dataConfigs;

import org.example.config.Configuration;
import org.example.constants.Constants;
import org.example.handler.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.example.handler.FileHandler.writeToFile;
import static org.junit.jupiter.api.Assertions.*;

class FloatDataConfigTest {
    private FloatDataConfig floatDataConfig;
    private final String outputDir = "output";
    private final String prefix = "test_";

    @BeforeEach
    void setUp() {
        List<Float> floats = Arrays.asList(1.5f, 2.3f, 3.7f);
        floatDataConfig = new FloatDataConfig(floats);
    }

    // Проверка что пустой лист не сохраняется
    @Test
    void testProcessEmptyList() {
        floatDataConfig = new FloatDataConfig(Collections.emptyList());

        try (MockedStatic<FileHandler> mockedWriteToFile = Mockito.mockStatic(FileHandler.class)) {
            floatDataConfig.process(
                    new Configuration.ConfigurationBuilder(outputDir, prefix)
                    .setAppendMarker(false)
                    .setShortStatsMarker(false)
                    .setFullStats(false)
                    .build());

            // Проверка что метод не вызывается
            mockedWriteToFile.verifyNoInteractions();
        }
    }

    @Test
    void testProcessShortStats() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            floatDataConfig.process(
                    new Configuration.ConfigurationBuilder(outputDir, prefix)
                    .setAppendMarker(false)
                    .setShortStatsMarker(true)
                    .setFullStats(false)
                    .build());

            // Ожидаемый вывод
            String expectedStats = "Floats: 3";

            // Проверка что вывод статистики верный
            assertEquals(expectedStats.trim(), outContent.toString().trim(), "Console output does not match expected short statistics.");
        } finally {
            // Restore the original System.out
            System.setOut(System.out);
        }
    }

    @Test
    void testProcessFullStats() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        try (MockedStatic<FileHandler> mockedWriteToFile = Mockito.mockStatic(FileHandler.class)) {
            floatDataConfig.process(
                    new Configuration.ConfigurationBuilder(outputDir, prefix)
                    .setAppendMarker(false)
                    .setShortStatsMarker(false)
                    .setFullStats(true)
                    .build());

            // Проверка что запись вызывается с правильными параметрами
            mockedWriteToFile.verify(() -> writeToFile(outputDir, prefix + Constants.FilePath.FLOAT_FILE_NAME, floatDataConfig.getFloats(), false));

            //Ожидаемый вывод
            String expectedStats = """
                    Floats: min = 1.5,
                        max = 3.7,
                        sum = 7.5,
                        average value = 2.5""";

            // Проверка что вывод статистики верный
            assertEquals(expectedStats.trim(), outContent.toString().trim(), "Console output does not match expected statistics.");
        } finally {
            System.setOut(System.out);
        }
    }
}