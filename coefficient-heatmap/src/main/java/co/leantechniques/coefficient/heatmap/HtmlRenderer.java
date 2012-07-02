package co.leantechniques.coefficient.heatmap;

import java.io.File;
import java.util.*;

public class HtmlRenderer {
    public static final int MINIMUM_SIZE = 6;
    public static final int DIFFERENCE_IN_TAG_SIZE = 3;
    public static final int NUMBER_OF_CLASSIFICATIONS = 10;

    private Map<String, HeatmapData> files;
    private final float range;
    private final Integer floor;

    public HtmlRenderer(Map<String, HeatmapData> files) {
        this.files = files;
        ArrayList changeCounts = sorted(files.values());
        floor = min(changeCounts);
        range = max(changeCounts) - floor;
    }

    public String render() {
        String heatmap = "<html>" +
                "<head>" +
                "<title>SCM Heatmap</title>" +
                "<style type='text/css'>" +
                "body { font-family: sans-serif; padding: 0; margin: 0; }" +
                "ol { margin: 0; padding: 20px; }" +
                "ol li { display: inline-block; margin: 2px; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<ol>";
        for (String file : sorted(files.keySet())) {
            heatmap += tagFor(file);
        }
        return heatmap +    "</ol>" +
                "</body>" +
                "</html>";
    }

    private ArrayList sorted(Collection<HeatmapData> values) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(HeatmapData changeData : values) {
            list.add(changeData.changes);
        }
        Collections.sort(list);
        return list;
    }

    private Integer max(ArrayList<Integer> presortedValues) {
        return presortedValues.get(presortedValues.size() - 1);
    }

    private Integer min(ArrayList<Integer> presortedValues) {
        return presortedValues.get(0);
    }

    private String tagFor(String file) {
        return "<li " + styleOf(numberOfChangesTo(file),numberOfDefectsFor(file)) + " title='" + file + " -> " +
                "Changes: " + numberOfChangesTo(file) +
                "  Defects: " + numberOfDefectsFor(file) + "'>" + baseNameOf(file) + "</li>";
    }

    private Integer numberOfChangesTo(String file) {
        return files.get(file).changes;
    }

    private Integer numberOfDefectsFor(String file) {
        return files.get(file).defects;
    }

    private String styleOf(int changeCount, int defectCount) {
        return "style='font-size: " + calculatedSizeFor(changeCount) + ";color: " + calculateColorFor(changeCount, defectCount) + "'";
    }

    private int calculatedSizeFor(int changeCount) {
        return MINIMUM_SIZE + adjustedSize(changeCount);
    }

    private String calculateColorFor(int changeCount, int defectCount) {
        double percentOfDefects = ((double) defectCount) / changeCount;
        int red = (int)(percentOfDefects * 255);
        if (red == 0)
            return "rgb(211,211,211)";
        else
            return "rgb(" + red + ",0,0)";
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
