package co.leantechniques.coefficient.heatmap;

import java.util.Map;
import java.util.Set;

public class CommitMetric {

    public Map<String, Set<String>> filesByStory(Iterable<Commit> commits) {
        return new Commitset(commits).filesByStory();
    }

    public FilteredCommitSet getTestedCommitsByAuthor(Set<Commit> commits) {
        return new Commitset(commits).getTestedCommitsByAuthor();
    }

    public FilteredCommitSet getTestedCommitsByStory(Set<Commit> commits) {
        return new Commitset(commits).getTestedCommitsByStory();
    }

    public CommitsetStatistic getReport(Set<Commit> expectedCommits) {
        return new Commitset(expectedCommits).getReport();
    }

}
