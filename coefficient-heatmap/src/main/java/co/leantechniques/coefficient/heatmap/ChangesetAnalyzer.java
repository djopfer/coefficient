package co.leantechniques.coefficient.heatmap;

import java.util.*;

public class ChangesetAnalyzer {
    private final CodeRepository codeRepository;

    public ChangesetAnalyzer(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Map<String, Set<String>> getFilesByStory() {
        Set<Commit> commits = codeRepository.getCommits();
        return new Commitset(commits).filesByStory();
    }

    public AuthorStatisticSet getRankReport() {
        return new Commitset(codeRepository.getCommits()).getAuthorStatistics();
    }
}