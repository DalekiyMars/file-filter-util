package org.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static Map<String, Runnable> flagActions = new HashMap<>(){{
        put("-o", FilterRealization::changeOutputFileDirectory);
        put("-p", FilterRealization::changeOutputFileName);
        put("-s", FilterRealization::writeShortStatistics);
        put("-f", FilterRealization::writeFullStatistics);
        put("-a", FilterRealization::writeToOldFile);
    }};

    public static void main(String[] args) {
        FilterRealization realization = new FilterRealization(Arrays.stream(args).toList());
        //FilterRealization realization = new FilterRealization(List.of("-a", "-b", "haha1.txt", "haha2.txt"));
        System.out.println(realization.getFloatList());
        System.out.println(realization.getIntegerList());
        System.out.println(realization.getStringList());
        realizeFlags(args);
    }

    private static void realizeFlags(String[] args) {
        for (String arg : args) {
            Runnable action = flagActions.get(arg);
            if (action != null) {
                action.run();
            }
        }
    }
}
