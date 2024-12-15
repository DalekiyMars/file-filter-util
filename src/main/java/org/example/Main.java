package org.example;

import org.example.filter.FileFilterRealization;

public class Main {
    public static void main(String[] args) {
        FileFilterRealization filter = new FileFilterRealization();
        filter.handleArgs(args);
    }
}
