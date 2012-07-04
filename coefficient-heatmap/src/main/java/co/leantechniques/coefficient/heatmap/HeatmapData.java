package co.leantechniques.coefficient.heatmap;

class HeatmapData {
    public int changes;
    public int defects;

    void incrementCounters(boolean isDefect) {
        changes++;
        if (isDefect) {
            defects++;
        }
    }
}
