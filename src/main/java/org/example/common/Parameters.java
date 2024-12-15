package org.example.common;

public class Parameters {
    private final String outputDir;
    private final String prefix;
    private final boolean append;
    private final boolean shortStats;
    private final boolean fullStats;

    private Parameters(ParameterBuilder parameterBuilder) {
        this.outputDir = parameterBuilder.getOutputDir();
        this.prefix = parameterBuilder.getPrefix();
        this.append = parameterBuilder.isAppend();
        this.shortStats = parameterBuilder.isShortStats();
        this.fullStats = parameterBuilder.isFullStats();
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

    public static class ParameterBuilder {
        private final String outputDir;
        private final String prefix;
        private boolean append;
        private boolean shortStats;
        private boolean fullStats;

        /**
         * @param outputDir  Директория для записи.
         * @param prefix     Префикс имени.
         */
        public ParameterBuilder(String outputDir, String prefix) {
            this.outputDir = outputDir;
            this.prefix = prefix;
        }

        public ParameterBuilder setFullStats(boolean fullStats) {
            this.fullStats = fullStats;
            return this;
        }

        public ParameterBuilder setAppendMarker(boolean append){
            this.append = append;
            return this;
        }

        public ParameterBuilder setShortStatsMarker(boolean shortStats){
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

        public Parameters build(){
            return new Parameters(this);
        }
    }
}
