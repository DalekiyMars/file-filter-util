package org.example.config;

public class Configuration {
    private final String outputDir;
    private final String prefix;
    private final boolean append;
    private final boolean shortStats;
    private final boolean fullStats;

    private Configuration(ConfigurationBuilder configurationBuilder) {
        this.outputDir = configurationBuilder.getOutputDir();
        this.prefix = configurationBuilder.getPrefix();
        this.append = configurationBuilder.isAppend();
        this.shortStats = configurationBuilder.isShortStats();
        this.fullStats = configurationBuilder.isFullStats();
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppend() {
        return append;
    }

    public boolean isShortStats() {
        return shortStats;
    }

    public boolean isFullStats() {
        return fullStats;
    }

    public static class ConfigurationBuilder {
        private final String outputDir;
        private final String prefix;
        private boolean append;
        private boolean shortStats;

        private boolean fullStats;

        /**
         * @param outputDir  Директория для записи.
         * @param prefix     Префикс имени.
         */
        public ConfigurationBuilder(String outputDir, String prefix) {
            this.outputDir = outputDir;
            this.prefix = prefix;
        }

        public ConfigurationBuilder setFullStats(boolean fullStats) {
            this.fullStats = fullStats;
            return this;
        }

        public ConfigurationBuilder setAppendMarker(boolean append){
            this.append = append;
            return this;
        }

        public ConfigurationBuilder setShortStatsMarker(boolean shortStats){
            this.shortStats = shortStats;
            return this;
        }

        public String getOutputDir() {
            return outputDir;
        }

        public String getPrefix() {
            return prefix;
        }

        public boolean isAppend() {
            return append;
        }

        public boolean isShortStats() {
            return shortStats;
        }

        public boolean isFullStats() {
            return fullStats;
        }

        public Configuration build(){
            return new Configuration(this);
        }
    }
}
