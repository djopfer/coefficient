package co.leantechniques.coefficient.heatmap;

import java.util.Map;

public class HtmlRenderer {
    public String render(Map<String, Integer> files) {
        String html = "";
        for (String file : files.keySet()) {
            html += "<div size='" + files.get(file) + "'>" + file + "</div>";
        }
        return html;
    }
}
