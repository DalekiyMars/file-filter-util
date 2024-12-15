package org.example.dataConfigs;

import org.example.Parameters;
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

class IntDataConfigTest {
    private IntDataConfig intDataConfig;
    private final String outputDir = "output";
    private final String prefix = "test_";

    @BeforeEach
    void setUp() {
        List<Integer> ints = Arrays.asList(1, 200, 501);
        intDataConfig = new IntDataConfig(ints);
    }

    // Проверка что пустой лист не сохраняется
    @Test
    void testProcessEmptyList() {
        intDataConfig = new IntDataConfig(Collections.emptyList());

        try (MockedStatic<FileHandler> mockedWriteToFile = Mockito.mockStatic(FileHandler.class)) {
            intDataConfig.process(
                    new Parameters.ParameterBuilder(outputDir, prefix)
                            .setAppendMarker(false)
                            .setShortStatsMarker(false)
                            .setFullStats(false)
                            .build()
            );

            // Проверка что метод не вызывается
            mockedWriteToFile.verifyNoInteractions();
        }
    }

    @Test
    void testProcessShortStats() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            intDataConfig.process(
                    new Parameters.ParameterBuilder(outputDir, prefix)
                            .setAppendMarker(false)
                            .setShortStatsMarker(true)
                            .setFullStats(false)
                            .build()
            );

            // Ожидаемый вывод
            String expectedStats = "Integers: 3";

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
            intDataConfig.process(
                    new Parameters.ParameterBuilder(outputDir, prefix)
                            .setAppendMarker(false)
                            .setShortStatsMarker(false)
                            .setFullStats(true)
                            .build()
            );

            // Проверка что запись вызывается с правильными параметрами
            mockedWriteToFile.verify(() -> writeToFile(outputDir, prefix + Constants.FilePath.INT_FILE_NAME, intDataConfig.getIntegers(), false));

            //Ожидаемый вывод
            String expectedStats = """
                    Integers: min = 1,
                        max = 501,
                        sum = 702,
                        average value = 234.0""";

            // Проверка что вывод статистики верный
            assertEquals(expectedStats.trim(), outContent.toString().trim(), "Console output does not match expected statistics.");
        } finally {
            System.setOut(System.out);
        }
    }
}