package co.leantechniques.coefficient.heatmap;

import java.util.Map;

import static co.leantechniques.coefficient.heatmap.CommitParser.EachCommit;

public class Heatmap {

    private HgLog hgLog;
    private CommitAnalyzer commitAnalyzer = new CommitAnalyzer();

    public Heatmap(HgLog log) {
        hgLog = log;
    }

    public String generate() {
        new CommitParser(hgLog.execute()).analyze(new EachCommit() {
            @Override
            public void process(String commit, String[] fileNames) {
                commitAnalyzer.process(fileNames);
            }
        });

        return renderHtml(commitAnalyzer.results());
    }

    private String renderHtml(Map<String, Integer> files) {
        String html = "";
        for (String file : files.keySet()) {
            html += "<div size='" + files.get(file) + "'>" + file + "</div>";
        }
        return html;
    }

}
