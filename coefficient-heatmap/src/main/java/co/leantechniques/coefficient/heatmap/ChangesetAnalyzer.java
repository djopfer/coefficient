package co.leantechniques.coefficient.heatmap;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static java.util.regex.Pattern.quote;

public class ChangesetAnalyzer {
    private final Scanner scanner;
    private final String messageSeparator;
    private final String filesSeparator;

    public ChangesetAnalyzer(InputStream commitData, String endOfMessageSeparator, String filesSeparator) {
        scanner = new Scanner(commitData);
        messageSeparator = quote(endOfMessageSeparator);
        this.filesSeparator = filesSeparator;
    }

    public Map<String, Set<String>> groupChangesetsByStory() {
        HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();
        while (hasMoreCommits()) {
            Commit commit = createFrom(nextCommit());
            if (map.containsKey(commit.getStory()))
                map.get(commit.getStory()).addAll(commit.getFiles());
            else
                map.put(commit.getStory(), commit.getFiles());
        }
        return map;
    }

    private Commit createFrom(String message) {
        String[] commitData = message.split(messageSeparator);
        return new Commit(commitData[0], commitData[1].split(filesSeparator));
    }

    private String nextCommit() {
        return scanner.nextLine();
    }

    private boolean hasMoreCommits() {
        return scanner.hasNextLine();
    }
}