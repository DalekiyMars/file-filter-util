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
}
