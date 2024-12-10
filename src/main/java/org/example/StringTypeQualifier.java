package org.example;

public class StringTypeQualifier {
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDecimal(String input) {
        try {
            Double.parseDouble(input);
            return input.contains(".") && !input.toLowerCase().contains("e");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isScientificNotation(String input) {
        try {
            Double.parseDouble(input);
            return input.toLowerCase().contains("e");
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
