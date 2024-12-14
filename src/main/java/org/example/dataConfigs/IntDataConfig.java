package org.example.dataConfigs;

import org.example.constants.Constants;

import java.util.Collections;
import java.util.List;

import static org.example.handler.FileHandler.writeToFile;

public class IntDataConfig implements DataConfig {
    private final List<Integer> integers;

    public IntDataConfig(List<Integer> integers) {
        this.integers = integers;
    }

    public List<Integer> getIntegers(){
        return this.integers;
    }

    @Override
    public void process(String outputDir, String prefix, boolean append, boolean shortStats, boolean fullStats) {
        if (integers.isEmpty()) return;

        String fileName = prefix + Constants.FilePath.INT_FILE_NAME;
        writeToFile(outputDir, fileName, integers, append);

        if (shortStats) {
            System.out.println("Integers: " + integers.size());
        }

        if (fullStats) {
            System.out.println("Integers: min = " + Collections.min(integers) + ",\n" +
                    "    max = " + Collections.max(integers) + ",\n" +
                    "    sum = " + integers.stream().mapToInt(Integer::intValue).sum() + ",\n" +
                    "    average value = " + integers.stream().mapToInt(Integer::intValue).average().orElse(0));
        }
    }
}
