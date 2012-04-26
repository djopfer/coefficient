package co.leantechniques.coefficient.heatmap;

import java.io.File;
import java.util.*;

public class HtmlRenderer {
    public static final int MINIMUM_SIZE = 6;
    public static final int DIFFERENCE_IN_TAG_SIZE = 3;
    public static final int NUMBER_OF_CLASSIFICATIONS = 10;

    private Map<String, Integer> files;
    private final float range;
    private final Integer floor;

    public HtmlRenderer(Map<String, Integer> files) {
        this.files = files;
        ArrayList changeCounts = sorted(files.values());
        floor = min(changeCounts);
        range = max(changeCounts) - floor;
    }

    public String render() {
        String heatmap = "";
        for (String file : sorted(files.keySet())) {
            heatmap += tagFor(file);
        }
        return heatmap;
    }

    private ArrayList sorted(Collection<Integer> values) {
        ArrayList changeCounts1 = new ArrayList(values);
        Collections.sort(changeCounts1);
        return changeCounts1;
    }

    private Integer max(ArrayList<Integer> changesValues) {
        return changesValues.get(changesValues.size() - 1);
    }

    private Integer min(ArrayList<Integer> changesValues) {
        return changesValues.get(0);
    }

    private String tagFor(String file) {
        return "<li " + styleOf(files.get(file)) + " title='" + file + "'>" + baseNameOf(file) + "</li>";
    }

    private String styleOf(int changeCount) {
        return "style='font-size: " + calculatedSizeFor(changeCount) + "'";
    }

    private int calculatedSizeFor(int changeCount) {
        return MINIMUM_SIZE + adjustedSize(changeCount);
    }

    private int adjustedSize(int changeCount) {
        return amplify(classify(normalized(changeCount)));
    }

    private int amplify(int i) {
        return i * DIFFERENCE_IN_TAG_SIZE;
    }

    private int classify(float changes) {
        return (int) (changes * NUMBER_OF_CLASSIFICATIONS);
    }

    private float normalized(int changeCount) {
        return (changeCount - floor) / range;
    }

    private String baseNameOf(String file) {
        File f = new File(file);
        return f.getName().split("\\.")[0];
    }

    private ArrayList<String> sorted(Set<String> filenames) {
        ArrayList<String> files = new ArrayList<String>(filenames);
        Collections.sort(files);
        return files;
    }
}
