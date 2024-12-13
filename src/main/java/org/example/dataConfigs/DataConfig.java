package org.example.dataConfigs;

public interface DataConfig {
    /**
     * Processes the categorized data and writes it to an output file.
     *
     * @param outputDir  The directory to write the output file.
     * @param prefix     The prefix for the output file name.
     * @param append     Whether to append to the file or overwrite it.
     * @param shortStats Whether to display short statistics.
     * @param fullStats  Whether to display full statistics.
     */
    void process(String outputDir, String prefix, boolean append, boolean shortStats, boolean fullStats);
}
