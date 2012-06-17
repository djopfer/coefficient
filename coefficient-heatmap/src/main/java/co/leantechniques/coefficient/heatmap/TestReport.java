package co.leantechniques.coefficient.heatmap;

import java.util.*;

public class TestReport {
    private int totalCommits = 0;
    private int totalTestedCommits = 0;
    private Map<String, AuthorCommitInfo> commitsByAuthor = new HashMap<String, AuthorCommitInfo>();

    public int getTotalCommits() {
        return totalCommits;
    }

    public int getTotalTestedCommits() {
        return totalTestedCommits;
    }

    public List<AuthorCommitInfo> getAuthors() {
        ArrayList<AuthorCommitInfo> authorCommitInfos = new ArrayList<AuthorCommitInfo>(commitsByAuthor.values());
        Collections.sort(authorCommitInfos, new AuthorCommitInfoComparer());
        return authorCommitInfos;
    }

    public void incrementTotalCommits() {
        totalCommits++;
    }

    public void incrementTestedCommits() {
        totalTestedCommits++;
    }

    public AuthorCommitInfo  getAuthorByName(String author) {
        if(!commitsByAuthor.containsKey(author)) commitsByAuthor.put(author, new AuthorCommitInfo(author));
        return commitsByAuthor.get(author);
    }

    public void setTotalCommits(int totalCommits) {
        this.totalCommits = totalCommits;
    }

    public double getPercentageOfTestedCommits() {
        return (double)totalTestedCommits/totalCommits;
    }

    public class AuthorCommitInfoComparer implements Comparator<AuthorCommitInfo> {
        @Override
        public int compare(AuthorCommitInfo o1, AuthorCommitInfo o2) {
            return (o1.getPercentageOfTestedCommits()>o2.getPercentageOfTestedCommits() ? -1 : (o1.getPercentageOfTestedCommits()==o2.getPercentageOfTestedCommits() ? 0 : 1));
        }
}
}
