package org.example.handler;

import org.example.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileHandler {
    /**
     * Writes a list of data to a file.
     *
     * @param outputDir The directory to write the file.
     * @param fileName  The name of the file.
     * @param data      The data to write to the file.
     * @param append    Whether to append to the file or overwrite it.
     */
    public static void writeToFile(String outputDir, String fileName, List<?> data, boolean append) {
        File file = new File(outputDir, fileName);
        try (FileWriter fileWriter = new FileWriter(file, append);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (var item : data) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + file.getAbsolutePath());
        }
    }

    /**
     * Reads all lines from a file.
     *
     * @param fileName The name of the file to read.
     * @return A list of strings representing the lines of the file.
     */
    public static List<String> readFile(String fileName) {
        try {
            fileName = getFileDir() + fileName;
            return Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
            return Collections.emptyList();
        }
    }

    public static String getFileDir() {
        String patch = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(patch);
        String jarPath = "";
        try {
            jarPath = file.getCanonicalPath();
        } catch (IOException e) {
            System.err.println("File directory error");
        }
        return jarPath.substring( 0, jarPath.lastIndexOf( File.separator )) + File.separator;
    }
}
