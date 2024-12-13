package org.example.dataConfigs;

import org.example.Constants;

import java.util.Comparator;
import java.util.List;

import static org.example.handler.FileHandler.writeToFile;

public class StringDataConfig implements DataConfig {
    private final List<String> strings;

    public StringDataConfig(List<String> strings) {
        this.strings = strings;
    }

    @Override
    public void process(String outputDir, String prefix, boolean append, boolean shortStats, boolean fullStats) {
        if (strings.isEmpty()) return;

        String fileName = prefix + Constants.FilePath.STRING_FILE_NAME;
        writeToFile(outputDir, fileName, strings, append);

        if (shortStats) {
            System.out.println("Strings: " + strings.size());
        }

        if (fullStats) {
            String longest = strings.stream().max(Comparator.comparingInt(String::length)).orElse("");
            String shortest = strings.stream().min(Comparator.comparingInt(String::length)).orElse("");
            System.out.println("Strings: longest = " + longest + " (length = " + longest.length() + "),\n        " +
                    "shortest = " + shortest + " (length=" + shortest.length() + ")");
        }
    }
}
