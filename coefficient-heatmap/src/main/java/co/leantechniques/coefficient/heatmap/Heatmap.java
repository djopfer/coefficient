package co.leantechniques.coefficient.heatmap;

import java.util.HashMap;
import java.util.Map;

public class Heatmap {
    public static final String WHITESPACE = "\\s+";
    public static final String MESSAGE_AND_FILES_SEPARATOR = "\\|\\|";

    private HgLog hgLog;

    public Heatmap(HgLog log) {
        hgLog = log;
    }


    public String generate() {
        Map<String, Integer> files = new HashMap<String, Integer>();
        CommitReader commits = new CommitReader(hgLog.execute());
        while (commits.hasMoreCommits()) {
            String filesInThisCommit = commits.nextCommit().split(MESSAGE_AND_FILES_SEPARATOR)[1];
            for (String filename : filesInThisCommit.split(WHITESPACE)) {
                setFileAppearances(files, filename);
            }
        }

        return renderHtml(files);
    }

    private String renderHtml(Map<String, Integer> files) {
        String html = "";
        for (String file : files.keySet()) {
            html += "<div size='" + files.get(file) + "'>" + file + "</div>";
        }
        return html;
    }

    private void setFileAppearances(Map<String, Integer> files, String filename) {
        if (!files.containsKey(filename)) {
            files.put(filename, 1);
        } else {
            incrementFileAppearances(files, filename);
        }
    }

    private void incrementFileAppearances(Map<String, Integer> files, String filename) {
        files.put(filename, files.get(filename) + 1);
    }

}
