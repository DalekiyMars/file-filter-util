package org.example.handler;

import java.util.List;
import java.util.Map;

public class GenericFlagHandler implements FlagHandler {
    private final String key;
    private final boolean parameter;

    /**
     * Constructor for a generic flag handler.
     *
     * @param key           The key to store the flag value in the context.
     * @param parameter Whether the flag requires an associated value.
     */
    public GenericFlagHandler(String key, boolean parameter) {
        this.key = key;
        this.parameter = parameter;
    }

    @Override
    public void handle(List<String> args, int index, Map<String, Object> context) {
        if (parameter) {
            if (index + 1 < args.size()) {
                context.put(key, args.get(index + 1));
            } else {
                throw new IllegalArgumentException("Flag " + key + " requires a value.");
            }
        } else {
            context.put(key, true);
        }
    }
}
