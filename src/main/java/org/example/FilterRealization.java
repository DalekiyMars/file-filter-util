package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.StringTypeQualifier.*;

public class FilterRealization {
    public List<String> getStringList() {
        return stringList;
    }

    public List<String> getIntegerList() {
        return integerList;
    }

    public List<String> getFloatList() {
        return floatList;
    }

    private final List<String> stringList = new ArrayList<>();
    private final List<String> integerList = new ArrayList<>();
    private final List<String> floatList = new ArrayList<>();
    public FilterRealization(List<String> filePaths){
        try {
            List<String> allLines = TextFileReaderWriter.collectLinesFromFiles(filePaths);
            filterFiles(allLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    private void filterFiles(List<String> inputStrings){
        for (String input : inputStrings) {
            if (isInteger(input)) {
                integerList.add(input);
            } else if (isDecimal(input) || isScientificNotation(input)) {
                floatList.add(input);
            } else {
                stringList.add(input);
            }
        }
    }
}
