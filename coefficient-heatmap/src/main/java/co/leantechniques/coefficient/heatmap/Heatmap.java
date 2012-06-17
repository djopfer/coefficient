package co.leantechniques.coefficient.heatmap;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heatmap {
    private Writer writer;
    private CodeRepository codeRepository;

    public Heatmap(CodeRepository codeRepository, Writer writer) {
        this.codeRepository = codeRepository;
        this.writer = writer;
    }

    public String generate() {
        try {
            ChangesetAnalyzer changesetAnalyzer = new ChangesetAnalyzer(codeRepository);
            Map<String, ChangeInfo> files = changesPerFile(changesetAnalyzer.groupChangesetsByStory());
            String results = render(files);
            save(results);
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String render(Map<String, ChangeInfo> files) {
        return new HtmlRenderer(files).render();
    }

    private void save(String results) throws IOException {
        writer.write(results);
        writer.close();
    }

    private Map<String, ChangeInfo> changesPerFile(Map<String, Set<String>> changesets) {
        Map<String, ChangeInfo> numberOfChangesOrganizedByFile = new HashMap<String, ChangeInfo>();
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

    private boolean fileExists(Map<String, ChangeInfo> r, String file) {
        return r.containsKey(file);
    }

    private void initializeCount(Map<String, ChangeInfo> r, String file) {
        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.changedForStory();
        r.put(file, changeInfo);
    }

    private void incrementCountForFile(Map<String, ChangeInfo> r, String file) {
        r.get(file).changedForStory();
    }
}
