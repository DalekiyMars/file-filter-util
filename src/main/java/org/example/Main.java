package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        FilterRealization realization = new FilterRealization(Arrays.stream(args).toList());
        System.out.println(realization.getFloatList());
        System.out.println(realization.getIntegerList());
        System.out.println(realization.getStringList());
        System.out.println(realization.activeFlags);
        System.out.println(realization.getFileNameWithFloat());
        System.out.println(realization.getFileNameWithStrings());
        System.out.println(realization.getFileNameWithInts());
    }
}
