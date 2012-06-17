package co.leantechniques.coefficient.heatmap;

import java.util.*;

public class ChangesetAnalyzer {
    private final CodeRepository codeRepository;

    public ChangesetAnalyzer(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public Map<String, Set<String>> groupChangesetsByStory() {
        Set<Commit> commits = codeRepository.getCommits();
        return new CommitMetric().filesByStory(commits);
    }

    public Map<String, Set<Commit>> groupByAuthor() {
        Set<Commit> commits = codeRepository.getCommits();
        return new CommitMetric().commitsByAuthor(commits);
    }

    public TestReport getRankReport() {
        return new CommitMetric().getReport(codeRepository.getCommits());
    }
}