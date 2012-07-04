package co.leantechniques.coefficient.heatmap;

import java.util.*;

public class CommitsetStatistic {
    int totalCommits = 0;
    int totalTestedCommits = 0;
    Map<String, AuthorCommitStatistic> commitsByAuthor = new HashMap<String, AuthorCommitStatistic>();

    public CommitsetStatistic() {
    }

    public int getTotalCommits() {
        return totalCommits;
    }

    public int getTotalTestedCommits() {
        return totalTestedCommits;
    }

    public List<AuthorCommitStatistic> getCommitStatistics() {
        ArrayList<AuthorCommitStatistic> authorCommitInfos = new ArrayList<AuthorCommitStatistic>(commitsByAuthor.values());
        Collections.sort(authorCommitInfos, new AuthorCommitInfoComparer());
        return authorCommitInfos;
    }

    public void incrementTotalCommits() {
        totalCommits++;
    }

    public void incrementTestedCommits() {
        totalTestedCommits++;
    }

    public AuthorCommitStatistic getAuthorByName(String author) {
        if (!commitsByAuthor.containsKey(author)) commitsByAuthor.put(author, new AuthorCommitStatistic(author));
        return commitsByAuthor.get(author);
    }

    public void setTotalCommits(int totalCommits) {
        this.totalCommits = totalCommits;
    }

    public double getPercentageOfTestedCommits() {
        return (double) totalTestedCommits / totalCommits;
    }
    class AuthorCommitInfoComparer implements Comparator<AuthorCommitStatistic> {
        @Override
        public int compare(AuthorCommitStatistic o1, AuthorCommitStatistic o2) {
            return (o1.getPercentageOfTestedCommits() > o2.getPercentageOfTestedCommits() ? -1 : (o1.getPercentageOfTestedCommits() == o2.getPercentageOfTestedCommits() ? 0 : 1));
        }
    }

}