package org.example;

import java.io.IOException;
import java.util.*;

import static org.example.StringTypeQualifier.*;

public class FilterRealization {
    private final List<String> stringList = new ArrayList<>();
    private final List<String> integerList = new ArrayList<>();
    private final List<String> floatList = new ArrayList<>();
    private String fileNameWithStrings = Constants.BaseFileNames.FILE_WITH_STRINGS;
    private String fileNameWithInts = Constants.BaseFileNames.FILE_WITH_INTEGERS;
    private String fileNameWithFloat = Constants.BaseFileNames.FILE_WITH_FLOATS;
    private final Map<String, String> activeFlags = new HashMap<>();



    public FilterRealization(List<String> args){
        try {
            fillActualFlags(args);
            filterFiles(TextFileReaderWriter.collectLinesFromFiles(args));
        } catch (IOException e) {
            System.err.println("Error of reading content from files");
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

    public void realizeFlags(String[] args) {

    }

    private void fillActualFlags(List<String> args){
        for (String arg : args) {
            Pair<String, Boolean> pair = Flags.checkFlag(arg);
            if (pair != null) {
                activeFlags.put(arg, takeNextParam(args, arg));
            }
        }
    }
    private String takeNextParam(List<String> args, String arg){
        return args.get(args.indexOf(arg) + 1);
    }

    public void setFileNameWithStrings(String fileNameWithStrings) {
        this.fileNameWithStrings = fileNameWithStrings;
    }

    public void setFileNameWithInts(String fileNameWithInts) {
        this.fileNameWithInts = fileNameWithInts;
    }

    public void setFileNameWithFloat(String fileNameWithFloat) {
        this.fileNameWithFloat = fileNameWithFloat;
    }

    public String getFileNameWithStrings() {
        return fileNameWithStrings;
    }

    public String getFileNameWithInts() {
        return fileNameWithInts;
    }

    public String getFileNameWithFloat() {
        return fileNameWithFloat;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public List<String> getIntegerList() {
        return integerList;
    }

    public List<String> getFloatList() {
        return floatList;
    }
}
