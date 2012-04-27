package co.leantechniques.coefficient.heatmap;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heatmap {

    private ScmRepository hgLog;
    private Writer writer;

    public Heatmap(ScmRepository log, Writer writer) {
        hgLog = log;
        this.writer = writer;
    }

    public String generate() {
        try {
            ChangesetAnalyzer changesetAnalyzer = new ChangesetAnalyzer(hgLog.execute(), "||", "\\s+");
            Map<String, Integer> files = changesPerFile(changesetAnalyzer.groupChangesetsByStory());
            String results = render(files);
            save(results);
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String render(Map<String, Integer> files) {
        return new HtmlRenderer(files).render();
    }

    private void save(String results) throws IOException {
        writer.write(results);
        writer.close();
    }

    private Map<String, Integer> changesPerFile(Map<String, Set<String>> changesets) {
        Map<String, Integer> numberOfChangesOrganizedByFile = new HashMap<String, Integer>();
        for (String story : changesets.keySet()) {
            for (String file : changesets.get(story)) {
                if (fileExists(numberOfChangesOrganizedByFile, file)) {
                    incrementCountForFile(numberOfChangesOrganizedByFile, file);
                } else {
                    initializeCount(numberOfChangesOrganizedByFile, file);
                }
            }
        }
        return numberOfChangesOrganizedByFile;
    }

    private boolean fileExists(Map<String, Integer> r, String file) {
        return r.containsKey(file);
    }

    private void initializeCount(Map<String, Integer> r, String file) {
        r.put(file, 1);
    }

    private void incrementCountForFile(Map<String, Integer> r, String file) {
        r.put(file, r.get(file) + 1);
    }

}
