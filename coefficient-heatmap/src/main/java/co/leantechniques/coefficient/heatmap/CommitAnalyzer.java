package co.leantechniques.coefficient.heatmap;

import java.util.HashMap;
import java.util.Map;

public class CommitAnalyzer {
    private Map<String, Integer> files = new HashMap<String, Integer>();

    public void process(String[] fileNames) {
        for (String filename : fileNames) {
            if (files.containsKey(filename)) {
                incrementFileAppearances(files, filename);
            } else {
                files.put(filename, 1);
            }
        }
    }

    public Map<String, Integer> results() {
        return files;
    }

    private void incrementFileAppearances(Map<String, Integer> files, String filename) {
        files.put(filename, files.get(filename) + 1);
    }
}
