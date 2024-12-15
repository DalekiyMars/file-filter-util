package org.example.dataConfigs;

import org.example.Parameters;
import org.example.constants.Constants;

import java.util.Comparator;
import java.util.List;

import static org.example.handler.FileHandler.writeToFile;

public class StringDataConfig implements DataConfig {
    private final List<String> strings;

    public StringDataConfig(List<String> strings) {
        this.strings = strings;
    }

    public List<String> getStrings(){
        return this.strings;
    }

    @Override
    public void process(Parameters parameters) {
        if (strings.isEmpty()) return;

        String fileName = parameters.getPrefix() + Constants.FilePath.STRING_FILE_NAME;
        writeToFile(parameters.getOutputDir(), fileName, strings, parameters.isAppend());

        if (parameters.isShortStats()) {
            System.out.println("Strings: " + strings.size());
        }

        if (parameters.isFullStats()) {
            String longest = strings.stream().max(Comparator.comparingInt(String::length)).orElse("");
            String shortest = strings.stream().min(Comparator.comparingInt(String::length)).orElse("");
            System.out.println("Strings: longest = " + longest + " (length = " + longest.length() + "),\n" +
                    "   shortest = " + shortest + " (length=" + shortest.length() + ")");
        }
    }
}
