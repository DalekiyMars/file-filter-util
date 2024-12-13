package org.example.dataConfigs;

import org.example.Constants;

import java.util.Collections;
import java.util.List;

import static org.example.handler.FileHandler.writeToFile;

public class FloatDataConfig implements DataConfig {
    private final List<Float> floats;

    public FloatDataConfig(List<Float> floats) {
        this.floats = floats;
    }

    @Override
    public void process(String outputDir, String prefix, boolean append, boolean shortStats, boolean fullStats) {
        if (floats.isEmpty()) return;

        String fileName = prefix + Constants.FilePath.FLOAT_FILE_NAME;
        writeToFile(outputDir, fileName, floats, append);

        if (shortStats) {
            System.out.println("Floats: " + floats.size());
        }

        if (fullStats) {
            System.out.println("Floats: min = " + Collections.min(floats) + ",\n        " +
                    "max = " + Collections.max(floats) + ",\n        " +
                    "sum = " + floats.stream().mapToDouble(Float::doubleValue).sum() + ",\n        " +
                    "average value = " + floats.stream().mapToDouble(Float::doubleValue).average().orElse(0));
        }
    }
}
