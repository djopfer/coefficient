package co.leantechniques.coefficient.heatmap;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class HtmlRenderer {
    public String render(Map<String, Integer> files) {
        String html = "";
        for (String file : sorted(files.keySet())) {
            File f = new File(file);
            html += "<li style='foo' title='" + file + "'>" + f.getName().split("\\.")[0] + "</li>";
        }
        return html;
    }

    private ArrayList<String> sorted(Set<String> filenames) {
        ArrayList<String> files = new ArrayList<String>(filenames);
        Collections.sort(files);
        return files;
    }
}
