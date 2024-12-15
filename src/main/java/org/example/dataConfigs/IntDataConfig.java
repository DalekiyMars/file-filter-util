package org.example.dataConfigs;

import org.example.config.Configuration;
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
    public void process(Configuration configuration) {
        if (integers.isEmpty()) return;

        String fileName = configuration.getPrefix() + Constants.FilePath.INT_FILE_NAME;
        writeToFile(configuration.getOutputDir(), fileName, integers, configuration.isAppend());

        if (configuration.isShortStats()) {
            System.out.println("Integers: " + integers.size());
        }

        if (configuration.isFullStats()) {
            System.out.println("Integers: min = " + Collections.min(integers) + ",\n" +
                    "    max = " + Collections.max(integers) + ",\n" +
                    "    sum = " + integers.stream().mapToInt(Integer::intValue).sum() + ",\n" +
                    "    average value = " + integers.stream().mapToInt(Integer::intValue).average().orElse(0));
        }
    }
}
