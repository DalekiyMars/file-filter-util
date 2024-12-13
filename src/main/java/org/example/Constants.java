package org.example;

import java.util.HashSet;
import java.util.Set;

public class Constants {
    public static final Set<String> unacceptableFileNames = new HashSet<>() {{
        add("prn");
        add("nul");
        add("lpt0-lpt9");
        add("con");
    }};

    public static class FilePath{
        public static final String STRING_FILE_NAME = "strings.txt";
        public static final String INT_FILE_NAME = "integers.txt";
        public static final String FLOAT_FILE_NAME = "floats.txt";
    }
}
