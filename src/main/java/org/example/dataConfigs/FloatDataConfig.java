package org.example.dataConfigs;

import org.example.config.Configuration;
import org.example.constants.Constants;

import java.util.Collections;
import java.util.List;

import static org.example.handler.FileHandler.writeToFile;

public class FloatDataConfig implements DataConfig {
    private final List<Float> floats;

    public FloatDataConfig(List<Float> floats) {
        this.floats = floats;
    }

    public List<Float> getFloats(){
        return this.floats;
    }

    @Override
    public void process(Configuration configuration) {
        if (floats.isEmpty()) return;

        String fileName = configuration.getPrefix() + Constants.FilePath.FLOAT_FILE_NAME;
        writeToFile(configuration.getOutputDir(), fileName, floats, configuration.isAppend());

        if (configuration.isShortStats()) {
            System.out.println("Floats: " + floats.size());
        }

        if (configuration.isFullStats()) {
            System.out.println("Floats: min = " + Collections.min(floats) + ",\n " +
                    "   max = " + Collections.max(floats) + ",\n " +
                    "   sum = " + floats.stream().mapToDouble(Float::doubleValue).sum() + ",\n " +
                    "   average value = " + floats.stream().mapToDouble(Float::doubleValue).average().orElse(0));
        }
    }
}
