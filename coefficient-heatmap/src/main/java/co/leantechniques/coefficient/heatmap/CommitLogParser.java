package co.leantechniques.coefficient.heatmap;

import java.io.InputStream;
import java.util.Scanner;

import static java.util.regex.Pattern.quote;

public class CommitLogParser {
    final Scanner scanner;
    final String messageSeparator = "||";
    final String filesSeparator = "\\s+";

    public CommitLogParser(InputStream logMessageStream) {
        scanner = new Scanner(logMessageStream);
    }

    private Commit createFrom(String message) {
        while(!doneReadingCommitMessage(message)){
            message += nextLine();
        }
        String[] commitData = breakOnMessageSeparator(message);
        if(containsFileList(commitData))
            return new Commit(commitData[0], commitData[1], commitData[2].split(filesSeparator));
        else
            return new Commit(commitData[0], commitData[1]);
    }

    private boolean containsFileList(String[] commitData) {
        return (commitData.length == 3);
    }

    private boolean doneReadingCommitMessage(String message) {
        return message.matches("(.*)\\|\\|(.*)\\|\\|(.*)");
    }

    private String[] breakOnMessageSeparator(String message) {
        return message.split(quote(messageSeparator));
    }

    private String nextLine() {
        return scanner.nextLine();
    }

    public boolean hasMoreCommits() {
        return scanner.hasNextLine();
    }

    public Commit nextCommit() {
        return createFrom(nextLine());
    }
}