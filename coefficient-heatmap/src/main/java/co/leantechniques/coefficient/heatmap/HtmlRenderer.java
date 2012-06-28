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
        String heatmap = "<html>" +
                         "<head>" +
                            "<title>SCM Heatmap</title>" +
                            "<style type='text/css'>" +
                                "body { font-family: sans-serif; color: lightgrey; padding: 0px; margin: 0px; }" +
                                "ol { margin: 0px; padding: 20px; }" +
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

    private ArrayList sorted(Collection<Integer> values) {
        ArrayList list = new ArrayList(values);
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
        return "<li " + styleOf(numberOfChangesTo(file)) + " title='" + file + "'>" + baseNameOf(file) + "</li>";
    }

    private Integer numberOfChangesTo(String file) {
        return files.get(file);
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
