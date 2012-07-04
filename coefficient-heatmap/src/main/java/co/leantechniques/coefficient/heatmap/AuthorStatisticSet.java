package co.leantechniques.coefficient.heatmap;

import java.util.*;

public class AuthorStatisticSet {
    int totalCommits = 0;
    int totalTestedCommits = 0;
    Map<String, AuthorStatistic> commitStatisticsByAuthor = new HashMap<String, AuthorStatistic>();

    public int getTotalCommits() {
        return totalCommits;
    }

    public int getTotalTestedCommits() {
        return totalTestedCommits;
    }

    public List<AuthorStatistic> toList() {
        ArrayList<AuthorStatistic> list = new ArrayList<AuthorStatistic>(commitStatisticsByAuthor.values());
        Collections.sort(list, new AuthorStatisticComparator());
        return list;
    }

    public void incrementTotalCommits() {
        totalCommits++;
    }

    public void incrementTestedCommits() {
        totalTestedCommits++;
    }

    public AuthorStatistic getCommitStatisticForAuthor(String author) {
        if (!commitStatisticsByAuthor.containsKey(author)) commitStatisticsByAuthor.put(author, new AuthorStatistic(author));
        return commitStatisticsByAuthor.get(author);
    }

    public void setTotalCommits(int totalCommits) {
        this.totalCommits = totalCommits;
    }

    public double getPercentageOfTestedCommits() {
        return (double) totalTestedCommits / totalCommits;
    }
}