package org.example;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FilterRealization realization = new FilterRealization(Arrays.stream(args).toList());
        //FilterRealization realization = new FilterRealization(List.of("-a", "-p", "-b", "haha1.txt", "haha2.txt"));
        System.out.println(realization.getFloatList());
        System.out.println(realization.getIntegerList());
        System.out.println(realization.getStringList());
        realization.realizeFlags(args);
    }
}
