package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    static Map<String, Runnable> flagActions = new HashMap<>(){{
        put("-o", Main::changeOutputFileDirectory);
        put("-p", Main::changeOutputFileName);
        put("-s", Main::writeShortStatistics);
        put("-f", Main::writeFullStatistics);
        put("-a", Main::writeToOldFile);
    }};
    public static void main(String[] args) {
        for (String arg : args) {
            Runnable action = flagActions.get(arg); // Получить действие по флагу
            if (action != null) {
                action.run(); // Выполнить действие
            } else {
                System.out.println("Unknown flag: " + arg);
            }
        }
    }

    public static void changeOutputFileDirectory(){

    }

    public static void changeOutputFileName(){

    }

    public static void writeShortStatistics(){

    }

    public static void writeFullStatistics(){

    }

    public static void writeToOldFile(){

    }
}
