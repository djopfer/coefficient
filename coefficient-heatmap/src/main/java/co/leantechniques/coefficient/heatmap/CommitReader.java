package co.leantechniques.coefficient.heatmap;

import java.io.InputStream;
import java.util.Scanner;

public class CommitReader {
    Scanner scanner;

    public CommitReader(InputStream commitData) {
        scanner = new Scanner(commitData);
    }

    String nextCommit() {
        return scanner.nextLine();
    }

    boolean hasMoreCommits() {
        return scanner.hasNextLine();
    }
}