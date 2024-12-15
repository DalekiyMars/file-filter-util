package org.example.dataConfigs;

import org.example.Parameters;
import org.example.constants.Constants;
import org.example.handler.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.example.handler.FileHandler.writeToFile;
import static org.junit.jupiter.api.Assertions.*;

class StringDataConfigTest {
    private StringDataConfig strDataConfig;
    private final String outputDir = "output";
    private final String prefix = "test_";

    @BeforeEach
    void setUp() {
        List<String> strings = Arrays.asList("Lorem ipsum dolor sit amet", "consectetur adipiscing", "test");
        strDataConfig = new StringDataConfig(strings);
    }

    // Проверка что пустой лист не сохраняется
    @Test
    void testProcessEmptyList() {
        strDataConfig = new StringDataConfig(Collections.emptyList());

        try (MockedStatic<FileHandler> mockedWriteToFile = Mockito.mockStatic(FileHandler.class)) {
            strDataConfig.process(
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
            strDataConfig.process(
                    new Parameters.ParameterBuilder(outputDir, prefix)
                    .setAppendMarker(false)
                    .setShortStatsMarker(true)
                    .setFullStats(false)
                    .build());

            // Ожидаемый вывод
            String expectedStats = "Strings: 3";

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
            strDataConfig.process(
                    new Parameters.ParameterBuilder(outputDir, prefix)
                    .setAppendMarker(false)
                    .setShortStatsMarker(false)
                    .setFullStats(true)
                    .build());

            // Проверка что запись вызывается с правильными параметрами
            mockedWriteToFile.verify(() -> writeToFile(outputDir, prefix + Constants.FilePath.STRING_FILE_NAME, strDataConfig.getStrings(), false));

            //Ожидаемый вывод
            String expectedStats = "Strings: longest = Lorem ipsum dolor sit amet (length = 26),\n" +
                    "   shortest = test (length=4)";

            // Проверка что вывод статистики верный
            assertEquals(expectedStats.trim(), outContent.toString().trim(), "Console output does not match expected statistics.");
        } finally {
            System.setOut(System.out);
        }
    }
}