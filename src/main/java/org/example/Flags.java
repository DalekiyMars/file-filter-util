package org.example;

import java.io.IOException;
import java.util.function.Consumer;

public enum Flags {
    FLAG_O("-o", true, Flags::setDirectory),
    FLAG_S("-s", false, Flags::printShortStatistics),
    FLAG_F("-f", false, Flags::printFullStatistics),
    FLAG_A("-a", false, Flags::appendExistedFiles),
    FLAG_P("-p", true, Flags::setPrefix);

    private final String name;
    private final boolean hasPair;
    private final Consumer<String> action;

    Flags(String name, boolean hasPair, Consumer<String> action) {
        this.name = name;
        this.hasPair = hasPair;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public boolean hasPair() {
        return hasPair;
    }

    public void execute(String arg) {
        action.accept(arg);
    }

    public static Flags getFlagByName(String input) {
        for (Flags flag : Flags.values()) {
            if (flag.getName().equals(input)) {
                return flag;
            }
        }
        return null;
    }

    private static void setDirectory(String directory){
        try {
            FilterRealization.setOutputDirectory(directory);
        } catch (IOException e) {
            System.out.println("File dir error");
        }
    }

    private static void printShortStatistics(String arg){
        FilterRealization.printShortStatistic();
    }

    private static void setPrefix(String prefix){
        FilterRealization.setPrefix(new StringBuilder(prefix));
    }

    private static void appendExistedFiles(String string) {
        FilterRealization.appendExistedFiles();
    }

    private static void printFullStatistics(String string) {
        FilterRealization.printFullStatistic();
    }
}
