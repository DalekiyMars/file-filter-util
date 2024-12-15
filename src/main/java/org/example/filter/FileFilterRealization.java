package org.example.filter;

import org.apache.commons.lang3.math.NumberUtils;
import org.example.config.Configuration;
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
            if (NumberUtils.isCreatable(value)) {
                if (value.matches("-?\\d+")) { // Проверяем, является ли это целым числом
                    integers.add(Integer.parseInt(value));
                } else { // Считаем, что это число с плавающей точкой или экспонентой
                    floats.add(Float.valueOf(value));
                }
            } else {
                strings.add(value); // Если не число, то это обычная строка
            }
        });

        Configuration configuration = new Configuration.ConfigurationBuilder(
                (String) context.getOrDefault("outputDir", getFileDir()),
                (String) context.getOrDefault("prefix", "output")
        ).setAppendMarker((boolean) context.getOrDefault("append", false))
         .setShortStatsMarker((boolean) context.getOrDefault("shortStats", false))
         .setAppendMarker((boolean) context.getOrDefault("fullStats", false))
         .build();


        new IntDataConfig(integers).process(configuration);
        new FloatDataConfig(floats).process(configuration);
        new StringDataConfig(strings).process(configuration);
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
