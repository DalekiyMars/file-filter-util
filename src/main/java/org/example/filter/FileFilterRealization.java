package org.example.filter;

import org.example.dataConfigs.FloatDataConfig;
import org.example.dataConfigs.IntDataConfig;
import org.example.dataConfigs.StringDataConfig;
import org.example.handler.FlagHandler;
import org.example.handler.GenericFlagHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.handler.FileHandler.getFileDir;
import static org.example.handler.FileHandler.readFile;
import static org.example.handler.StringTypeQualifier.*;

public class FileFilterRealization {
    private final Map<String, FlagHandler> handlers = new HashMap<>(){{
        put("-o", new GenericFlagHandler("outputDir", true));
        put("-p", new GenericFlagHandler("prefix", true));
        put("-a", new GenericFlagHandler("append", false));
        put("-s", new GenericFlagHandler("shortStats", false));
        put("-f", new GenericFlagHandler("fullStats", false));
    }};

    // Получаемые записи
    private final Map<String, Object> context = new HashMap<>();

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

        // Выводим результаты
        System.out.println(contextToString());
        System.out.println("Files: " + files);

        List<String> values = new ArrayList<>();
        for (String fileName : files) {
            values.addAll(readFile(fileName));
        }

        processValues(values, context);
    }

    /**
     * Обрабатывает предоставленные значения и классифицирует их на целые числа, числа с плавающей точкой и строки.
     *
     * @param values  Список обрабатываемых значений.
     * @param context Контекст, содержащий параметры конфигурации.
     */
    public static void processValues(List<String> values, Map<String, Object> context) {
        List<Integer> integers = new ArrayList<>();
        List<Float> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        values.forEach(value -> {
            if (isInteger(value)) {
                integers.add(Integer.parseInt(value));
            } else if (isDecimal(value) || isScientificNotation(value)) {
                floats.add(Float.parseFloat(value));
            } else {
                strings.add(value);
            }
        });

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

    public Map<String, Object> getContext() {
        return this.context;
    }

    public List<String> getFiles() {
        return this.files;
    }
}
