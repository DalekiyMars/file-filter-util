package org.example;

import org.example.dataConfigs.FloatDataConfig;
import org.example.dataConfigs.IntDataConfig;
import org.example.dataConfigs.StringDataConfig;
import org.example.handler.FlagHandler;
import org.example.handler.GenericFlagHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.handler.FileHandler.getFileDir;
import static org.example.handler.FileHandler.readFile;

public class FileFilterRealization {
    private final Map<String, FlagHandler> handlers = new HashMap<>(){{
        put("-o", new GenericFlagHandler("outputDir", true));
        put("-p", new GenericFlagHandler("prefix", true));
        put("-a", new GenericFlagHandler("append", false));
        put("-s", new GenericFlagHandler("shortStats", false));
        put("-f", new GenericFlagHandler("fullStats", false));
    }};
    // Context to store parsed data
    private final Map<String, Object> context = new HashMap<>();

    // List to store files
    private final List<String> files = new ArrayList<>();

    public void handleArgs(String[] args){
        List<String> arguments = List.of(args);
        for (int i = 0; i < arguments.size(); i++) {
            String arg = arguments.get(i);
            if (handlers.containsKey(arg)) {
                handlers.get(arg).handle(arguments, i, context);
            } else {
                if (arg.endsWith(".txt")){
                    files.add(arg);
                }
            }
        }

        // Display the results
        System.out.println(contextToString());
        System.out.println("Files: " + files);

        List<String> values = new ArrayList<>();
        for (String fileName : files) {
            values.addAll(readFile(fileName));
        }

        processValues(values, context);
    }

    /**
     * Processes the provided values and categorizes them into integers, floats, and strings.
     *
     * @param values  The list of values to process.
     * @param context The context containing configuration options.
     */
    private static void processValues(List<String> values, Map<String, Object> context) {
        List<Integer> integers = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        for (String value : values) {
            try {
                integers.add(Integer.parseInt(value));
            } catch (NumberFormatException e1) {
                try {
                    floats.add(Float.parseFloat(value));
                } catch (NumberFormatException e2) {
                    strings.add(value);
                }
            }
        }

        String outputDir = (String) context.getOrDefault("outputDir", getFileDir());
        String prefix = (String) context.getOrDefault("prefix", "output");
        boolean append = (boolean) context.getOrDefault("append", false);
        boolean shortStats = (boolean) context.getOrDefault("shortStats", false);
        boolean fullStats = (boolean) context.getOrDefault("fullStats", false);

        new IntDataConfig(integers).process(outputDir, prefix, append, shortStats, fullStats);
        new FloatDataConfig(floats).process(outputDir, prefix, append, shortStats, fullStats);
        new StringDataConfig(strings).process(outputDir, prefix, append, shortStats, fullStats);
    }

    private String contextToString(){
        StringBuilder sb = new StringBuilder();
        for (var item :
                context.entrySet()) {
            sb.append(item.getKey()).append(" = ").append(item.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }
}