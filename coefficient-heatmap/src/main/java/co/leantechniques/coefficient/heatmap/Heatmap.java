package co.leantechniques.coefficient.heatmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heatmap {

    private HgLog hgLog;

    public Heatmap(HgLog log) {
        hgLog = log;
    }

    public String generate() {
        ChangesetAnalyzer changesetAnalyzer = new ChangesetAnalyzer(hgLog.execute(), "||", "\\s+");

        Map<String, Set<String>> changesets = changesetAnalyzer.groupChangesetsByStory();

        Map<String, Integer> files = changesPerFile(changesets);

        return new HtmlRenderer(files).render();
    }

    private Map<String, Integer> changesPerFile(Map<String, Set<String>> changesets) {
        Map<String, Integer> r = new HashMap<String, Integer>();
        for (String story : changesets.keySet()) {
            for (String file : changesets.get(story)) {
                if (r.containsKey(file)) {
                    incrementCountForFile(r, file);
                } else {
                    initializeCount(r, file);
                }
            }
        }
        return r;
    }

    private void initializeCount(Map<String, Integer> r, String file) {
        r.put(file, 1);
    }

    private void incrementCountForFile(Map<String, Integer> r, String file) {
        r.put(file, r.get(file) + 1);
    }

}
