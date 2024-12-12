package org.example;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.example.StringTypeQualifier.*;

public class FilterRealization {
    private final List<String> stringList = new ArrayList<>();
    private final List<String> integerList = new ArrayList<>();
    private final List<String> floatList = new ArrayList<>();
    public final Map<String, String> activeFlags = new HashMap<>();
    private static StringBuilder outputDirectory;
    private static StringBuilder prefix = new StringBuilder();


    public FilterRealization(List<String> args){
        try {
            fillActualFlags(args);
            filterFiles(TextFileReaderWriter.collectLinesFromFiles(args));
            realizeFlags();
        } catch (IOException e) {
            System.err.println("Error of reading content from files");
        }
    }

    public static void printShortStatistic() {
        //TODO вывод короткой статистики
    }

    public static void printFullStatistic() {
        //TODO вывод полной статистики
    }

    public static void appendExistedFiles() {
        //TODO добавление в существующий файл
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

    public void realizeFlags() {
        for (var argument :
                activeFlags.keySet()) {
            Flags flag = Flags.getFlagByName(argument);
            if (Objects.requireNonNull(flag).hasPair()) {
                flag.execute(activeFlags.get(argument));
            } else {
                flag.execute(null);
            }
        }
    }

    private void fillActualFlags(List<String> args){
        for (String arg : args) {
            var flag = Flags.getFlagByName(arg);
            if (flag != null && flag.hasPair()) {
                activeFlags.put(arg, takeNextParam(args, arg));
            } else if (flag != null) {
                activeFlags.put(arg, null);
            }
        }
    }

    public static void setOutputDirectory(String directory) throws IOException {
        if (TextFileReaderWriter.doesDirectoryExist(directory)) {
            FilterRealization.outputDirectory = new StringBuilder(directory);
            if (!(outputDirectory.lastIndexOf(File.separator) == outputDirectory.length()-1)){
                outputDirectory.append(File.separator);
            }
        } else {
            System.err.println("Directory not exists! Files will be saved to base directory");
            FilterRealization.outputDirectory = new StringBuilder(TextFileReaderWriter.getFileDir());
        }
    }

    private String takeNextParam(List<String> args, String arg){
        try {
            return args.get(args.indexOf(arg) + 1);
        } catch (Exception e){
            return null;
        }
    }

    public static void setPrefix(StringBuilder prefix) {
        FilterRealization.prefix = prefix;
    }

    public String getFileNameWithStrings() {
        return getFileDirWithPrefix() + Constants.BaseFileNames.FILE_WITH_STRINGS;
    }

    public String getFileNameWithInts() {
        return getFileDirWithPrefix() + Constants.BaseFileNames.FILE_WITH_INTEGERS;
    }

    public String getFileNameWithFloat() {
        return getFileDirWithPrefix() + Constants.BaseFileNames.FILE_WITH_FLOATS;
    }

    private String getFileDirWithPrefix(){
        return outputDirectory.toString() + prefix;
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
