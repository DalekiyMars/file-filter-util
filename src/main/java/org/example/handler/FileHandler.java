package org.example.handler;

import org.example.entryPoint.Main;

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
     * Записывает данные в файл
     *
     * @param outputDir Директория для сохранения.
     * @param fileName  Название файла.
     * @param data      Данные для записи в файл.
     * @param append    Добавлять ли данные в старый файл.
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
     * Прочитать строки из файла.
     *
     * @param fileName Название файла для прочтения.
     * @return Список строк из файла.
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

    /**
     * Получить директорию из которой вызывается entryPoint.
     *
     * @return директория исполяемого файла.
     */
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
