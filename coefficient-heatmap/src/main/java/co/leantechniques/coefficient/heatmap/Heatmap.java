package co.leantechniques.coefficient.heatmap;

import java.util.HashSet;

public class Heatmap {
    private HgLog hgLog;

    public Heatmap(HgLog log) {
        hgLog = log;
    }

    public String generateForRepository() {
        HashSet<String> files = new HashSet<String>();

        for (String commitData : hgLog.execute()) {
            String[] fs = commitData.split("\\|\\|")[1].split("\\s+");
            for (String f : fs) {
                files.add(f);
            }
        }
        return files.toString();
    }

}
