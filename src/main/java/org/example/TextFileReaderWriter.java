package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextFileReaderWriter {
    public static void createAndFillFiles(FilterRealization filterRealization){
        writeListToFile(filterRealization.getFileNameWithStrings(), filterRealization.getStringList());
        writeListToFile(filterRealization.getFileNameWithInts(), filterRealization.getIntegerList());
        writeListToFile(filterRealization.getFileNameWithFloat(), filterRealization.getFloatList());
    }

    public static List<String> collectLinesFromFiles(List<String> fileNames) throws IOException {
        List<String> allLines = new ArrayList<>();

        for (String fileName : collectTextFileDirs(fileNames)) {
            try {
                List<String> lines = Files.readAllLines(Path.of(fileName));
                allLines.addAll(lines);
            } catch (IOException e) {
                System.err.println("Error of file reading: " + fileName);
            }
        }
        return allLines;
    }

    public static List<String> collectTextFileDirs(List<String> inputList) throws IOException {
        var textFiles = new ArrayList<>(inputList.stream()
                .filter(item -> item.endsWith(".txt"))
                .toList());

        int listLength = textFiles.size();
        for (int i = 0; i < listLength; i++) {
            textFiles.set(i, getFileDir(textFiles.get(i)));
        }
        return textFiles;
    }

    private static <T> void writeListToFile(String fileName, List<T> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("List is empty. File " + fileName + " wouldn't be created.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            list.forEach(item -> {
                try {
                    writer.write(item.toString() + "\n");
                } catch (IOException e) {
                    System.err.println("Error of file writing: " + fileName);
                }
            });
            System.out.println("Data written to file: " + fileName);
        } catch (IOException e) {
            System.err.println("File error: " + fileName);
        }
    }


    private static String getFileDir(String name) throws IOException {
        String patch = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(patch);
        String jarPath = file.getCanonicalPath();
        return jarPath.substring( 0, jarPath.lastIndexOf( File.separator )) + File.separator + name;
    }
}
