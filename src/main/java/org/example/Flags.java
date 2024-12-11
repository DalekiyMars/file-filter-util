package org.example;

public enum Flags {
    FLAG_O ("-o", true),
    FLAG_S ("-s", false),
    FLAG_F ("-f", false),
    FLAG_A ("-a", false),
    FLAG_P ("-p", true);

    private final String name;
    private final boolean hasPair;
    public String getName() {
        return name;
    }

    public boolean hasPair() {
        return hasPair;
    }
    Flags(String name, boolean hasPair) {
        this.name = name;
        this.hasPair = hasPair;
    }

    public static Pair<String, Boolean> checkFlag(String input) {
        for (Flags flag : Flags.values()) {
            if (flag.getName().equals(input)) {
                return new Pair<>(flag.getName(), flag.hasPair());
            }
        }
        return null; // Если флаг не найден
    }
}
