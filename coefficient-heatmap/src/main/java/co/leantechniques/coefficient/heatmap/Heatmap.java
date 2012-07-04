package co.leantechniques.coefficient.heatmap;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            Map<String, HeatmapData> files = changesPerFile(changesetAnalyzer.groupChangesetsByStory());
            String results = render(files);
            save(results);
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String render(Map<String, HeatmapData> files) {
        return new HtmlRenderer(files).render();
    }

    private void save(String results) throws IOException {
        writer.write(results);
        writer.close();
    }

    private Map<String, HeatmapData> changesPerFile(Map<String, Set<String>> changesets) {
        Map<String, HeatmapData> numberOfChangesOrganizedByFile = new HashMap<String, HeatmapData>();
        boolean isDefect;
        for (String story : changesets.keySet()) {
            isDefect = isDefect(story);
            for (String file : changesets.get(story)) {
//                if (includes.contains(GetFileExtension(file))) {
                    if (fileExists(numberOfChangesOrganizedByFile, file)) {
                        incrementChangeCountForFile(numberOfChangesOrganizedByFile, file, isDefect);
                    } else {
                        initializeCount(numberOfChangesOrganizedByFile, file, isDefect);
                    }
//                }
            }
        }
        return numberOfChangesOrganizedByFile;
    }

    private boolean fileExists(Map<String, HeatmapData> r, String file) {
        return r.containsKey(file);
    }

    private void initializeCount(Map<String, HeatmapData> r, String file, boolean isDefect) {
        HeatmapData heatmapdata = new HeatmapData();
        heatmapdata.changes = 1;
        if (isDefect) {
            heatmapdata.defects = 1;
        }
        else{
            heatmapdata.defects = 0;
        }
        r.put(file, heatmapdata);
    }
    public boolean isDefect(String story) {
        Matcher matcher = Pattern.compile("DE\\d+").matcher(story);
        return matcher.find();
    }

    private void incrementChangeCountForFile(Map<String, HeatmapData> r, String file, boolean isDefect) {
        HeatmapData heatmapData = r.get(file);
        heatmapData.incrementCounters(isDefect);
    }

}
