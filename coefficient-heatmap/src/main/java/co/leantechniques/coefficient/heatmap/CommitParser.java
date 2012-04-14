package co.leantechniques.coefficient.heatmap;

import java.io.InputStream;
import java.util.Scanner;

import static java.util.regex.Pattern.*;

public class CommitParser {
    Scanner scanner;

    public CommitParser(InputStream commitData) {
        scanner = new Scanner(commitData);
    }

    String nextCommit() {
        return scanner.nextLine();
    }

    boolean hasMoreCommits() {
        return scanner.hasNextLine();
    }

    public void analyze(EachCommit callback) {
        while (hasMoreCommits()) {
            String[] commitData = nextCommit().split(quote("||"));
            callback.process(commitData[0], commitData[1].split("\\s+"));
        }

    }

    public static interface EachCommit {
        public void process(String commit, String[] filenames);
    }
}