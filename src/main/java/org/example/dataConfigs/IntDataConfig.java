package org.example.dataConfigs;

import org.example.Parameters;
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
    public void process(Parameters parameters) {
        if (integers.isEmpty()) return;

        String fileName = parameters.getPrefix() + Constants.FilePath.INT_FILE_NAME;
        writeToFile(parameters.getOutputDir(), fileName, integers, parameters.isAppend());

        if (parameters.isShortStats()) {
            System.out.println("Integers: " + integers.size());
        }

        if (parameters.isFullStats()) {
            System.out.println("Integers: min = " + Collections.min(integers) + ",\n" +
                    "    max = " + Collections.max(integers) + ",\n" +
                    "    sum = " + integers.stream().mapToInt(Integer::intValue).sum() + ",\n" +
                    "    average value = " + integers.stream().mapToInt(Integer::intValue).average().orElse(0));
        }
    }
}
