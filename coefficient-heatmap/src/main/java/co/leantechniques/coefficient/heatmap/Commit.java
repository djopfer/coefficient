package co.leantechniques.coefficient.heatmap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Commit {
    private String message;
    private final Set<String> files;

    public Commit(String message, String... files) {
        this.files = new HashSet<String>(Arrays.asList(files));
        this.message = message;
    }

    public String getStory() {
        Matcher matcher = Pattern.compile("(US|DE)\\d+").matcher(message);
        boolean wasFound = matcher.find();
        if (wasFound)
            return matcher.group();
        else
            return "Unknown";
    }

    public Set<String> getFiles() {
        return files;
    }
}
