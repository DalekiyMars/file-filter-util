package org.example.handler;
import java.util.*;

public interface FlagHandler {
    /**
     * Processes a specific flag from the command-line arguments.
     *
     * @param args    The list of command-line arguments.
     * @param index   The index of the current flag in the arguments list.
     * @param context A map to store processed flag data.
     */
    void handle(List<String> args, int index, Map<String, Object> context);
}
