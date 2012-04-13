package co.leantechniques.coefficient.heatmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Heatmap {
    public static final String WHITESPACE = "\\s+";
    public static final String MESSAGE_AND_FILES_SEPARATOR = "\\|\\|";

    private HgLog hgLog;

    public Heatmap(HgLog log) {
        hgLog = log;
    }

    public String generateForRepository() {
        Map<String, Integer> files = new HashMap<String, Integer>();
        Scanner scanner = new Scanner(hgLog.execute());
        while (scanner.hasNextLine()) {
            String filesInThisCommit = scanner.nextLine().split(MESSAGE_AND_FILES_SEPARATOR)[1];
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
